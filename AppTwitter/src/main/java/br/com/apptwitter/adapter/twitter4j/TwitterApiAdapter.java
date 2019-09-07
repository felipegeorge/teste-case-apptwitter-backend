package br.com.apptwitter.adapter.twitter4j;

import java.io.Serializable;
import java.net.InetSocketAddress;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.apptwitter.adapter.config.OAuth1AuthorizationHeaderBuilder;

@Component
public class TwitterApiAdapter implements Serializable {

	private static final long serialVersionUID = 1L;

	@Value("${twitter.app.id}")
	private String appId;

	@Value("${twitter.app.name}")
	private String appName;

	@Value("${twitter.consumer.api.key}")
	private String consumerApiKey;

	@Value("${twitter.consumer.api.key.secret}")
	private String consumerApiKeySecret;

	@Value("${twitter.access.token}")
	private String accessToken;

	@Value("${twitter.access.token.secret}")
	private String accessTokenSecret;

	private String generateOAuth1Authorization(String methodHttp, String url) {
		String authorizationHeader = new OAuth1AuthorizationHeaderBuilder()
		         .withMethod(methodHttp)
		         .withURL(url)
		         .withConsumerSecret(consumerApiKeySecret)
		         .withTokenSecret(accessTokenSecret)
		         .withParameter("oauth_consumer_key", consumerApiKey)
		         .withParameter("oauth_token", accessToken)
		         .withNonce(RandomStringUtils.randomAlphabetic(11) )
		         .build();
		
		System.out.println("Autorizador: ["+ authorizationHeader +"]");
		
		return authorizationHeader;
	}

	public String executeOperationGET(HttpMethod pMethodHttp, String pUrl) {
		
		// http headers
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", generateOAuth1Authorization(pMethodHttp.name(), pUrl));
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);		

		// request
		HttpEntity<String> request = new HttpEntity<>(httpHeaders);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(pUrl+"?count=2&screen_name=felipegeorge", pMethodHttp, request,String.class);
		
		return response.toString();
	}
	
	public String executeOperationPOST(HttpMethod pMethodHttp, String pUrl, String query) {
		
		// http headers
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", generateOAuth1Authorization(pMethodHttp.name(), pUrl+query));
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);		
//		httpHeaders.setCacheControl(CacheControl.noCache());
//		httpHeaders.add("Host", "api.twitter.com");
//		httpHeaders.add("Postman-Token", "3da12946-a72c-4639-862d-54a5d7cff84b,b5ae41d1-7ac9-4890-99e7-ab35ab704a92");
//		httpHeaders.add("User-Agent", "PostmanRuntime/7.15.2");
//		httpHeaders.add("Connection", "keep-alive");
//		httpHeaders.add("Accept", "*/*");
//		httpHeaders.add("Accept-Encoding", "gzip, deflate");
		
		// request
		HttpEntity<String> request = new HttpEntity<>(httpHeaders);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(pUrl+query, pMethodHttp, request,String.class);
		
		return response.toString();
	}
	
}
