package br.com.apptwitter.adapter.config;

/*
 * Copyright (c) 2019, FusionAuth, All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * https://gist.github.com/robotdan/33f5834399b6b30fea2ae59e87823e1d
 */

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Daniel DeGroff
 */
public class OAuth1AuthorizationHeaderBuilder {
  // https://tools.ietf.org/html/rfc3986#section-2.3
  private static final HashSet<Character> UnreservedChars = new HashSet<>(Arrays.asList(
      'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      '-', '_', '.', '~'));

  public String consumerSecret;

  public String method;

  public String parameterString;

  public Map<String, String> parameters = new LinkedHashMap<>();

  public String signature;

  public String signatureBaseString;

  public String signingKey;

  public String tokenSecret;

  public String url;
  
  public String nonce;

  /***
   * Replaces any character not specifically unreserved to an equivalent percent sequence.
   *
   * @param s the string to encode
   * @return and encoded string
   * @see <a href="https://stackoverflow.com/a/51754473/3892636">https://stackoverflow.com/a/51754473/3892636</a>}
   */
  public static String encodeURIComponent(String s) {
    StringBuilder o = new StringBuilder();
    for (char ch : s.toCharArray()) {
      if (isSafe(ch)) {
        o.append(ch);
      } else {
        o.append('%');
        o.append(toHex(ch / 16));
        o.append(toHex(ch % 16));
      }
    }
    return o.toString();
  }

  private static boolean isSafe(char ch) {
    return UnreservedChars.contains(ch);
  }

  private static char toHex(int ch) {
    return (char) (ch < 10 ? '0' + ch : 'A' + ch - 10);
  }

  public String build() {
    // For testing purposes, only add the timestamp if it has not yet been added
    if (!parameters.containsKey("oauth_timestamp")) {
      parameters.put("oauth_timestamp", "" + Instant.now().getEpochSecond());
    }
    
    // add parameter NONCE
    parameters.put("oauth_nonce", (nonce == null ? "X" : nonce));

    // Boiler plate parameters
    parameters.put("oauth_signature_method", "HMAC-SHA1");
    parameters.put("oauth_version", "1.0");

    // Build the parameter string after sorting the keys in lexicographic order per the OAuth v1 spec.
    parameterString = parameters.entrySet().stream()
                                .sorted(Map.Entry.comparingByKey())
                                .map(e -> encodeURIComponent(e.getKey()) + "=" + encodeURIComponent(e.getValue()))
                                .collect(Collectors.joining("&"));

    // Build the signature base string
    signatureBaseString = method.toUpperCase() + "&" + encodeURIComponent(url) + "&" + encodeURIComponent(parameterString);

    // If the signing key was not provided, build it by encoding the consumer secret + the token secret
    if (signingKey == null) {
      signingKey = encodeURIComponent(consumerSecret) + "&" + (tokenSecret == null ? "" : encodeURIComponent(tokenSecret));
    }

    // Sign the Signature Base String
    signature = generateSignature(signingKey, signatureBaseString);

    // Add the signature to be included in the header
    parameters.put("oauth_signature", signature);

    // Build the authorization header value using the order in which the parameters were added
    return "OAuth " + parameters.entrySet().stream()
                                .map(e -> encodeURIComponent(e.getKey()) + "=\"" + encodeURIComponent(e.getValue()) + "\"")
                                .collect(Collectors.joining(", "));
  }

  public String generateSignature(String secret, String message) {
    try {
      byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
      Mac mac = Mac.getInstance("HmacSHA1");
      mac.init(new SecretKeySpec(bytes, "HmacSHA1"));
      byte[] result = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(result);
    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Set the Consumer Secret
   *
   * @param consumerSecret the Consumer Secret
   * @return this
   */
  public OAuth1AuthorizationHeaderBuilder withConsumerSecret(String consumerSecret) {
    this.consumerSecret = consumerSecret;
    return this;
  }

  /**
   * Set the requested HTTP method
   *
   * @param method the HTTP method you are requesting
   * @return this
   */
  public OAuth1AuthorizationHeaderBuilder withMethod(String method) {
    this.method = method;
    return this;
  }

  /**
   * Add a parameter to the be included when building the signature.
   *
   * @param name  the parameter name
   * @param value the parameter value
   * @return this
   */
  public OAuth1AuthorizationHeaderBuilder withParameter(String name, String value) {
    parameters.put(name, value);
    return this;
  }

  /**
   * Set the OAuth Token Secret
   *
   * @param tokenSecret the OAuth Token Secret
   * @return this
   */
  public OAuth1AuthorizationHeaderBuilder withTokenSecret(String tokenSecret) {
    this.tokenSecret = tokenSecret;
    return this;
  }

  /**
   * Set the requested URL in the builder.
   *
   * @param url the URL you are requesting
   * @return this
   */
  public OAuth1AuthorizationHeaderBuilder withURL(String url) {
    this.url = url;
    return this;
  }
  
  /**
   * Set the NONCE.
   *
   * @param nonce is a random string generated by the client
   * @return this
   */
  public OAuth1AuthorizationHeaderBuilder withNonce(String nonce) {
    this.nonce = nonce;
    return this;
  }
}