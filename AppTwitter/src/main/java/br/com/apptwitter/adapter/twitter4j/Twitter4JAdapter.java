package br.com.apptwitter.adapter.twitter4j;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Component
public class Twitter4JAdapter implements Serializable {

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

	private Twitter getTwitterInstance() {
		ConfigurationBuilder config = new ConfigurationBuilder().setDebugEnabled(true)
				.setOAuthConsumerKey(consumerApiKey).setOAuthConsumerSecret(consumerApiKeySecret)
				.setOAuthAccessToken(accessToken).setOAuthAccessTokenSecret(accessTokenSecret);

		TwitterFactory factory = new TwitterFactory(config.build());
		Twitter twitter = factory.getInstance();

		return twitter;
	}

	public List<Status> getStatusByHashtag(String hashtag) {
		List<Status> statuses = null;

		if (StringUtils.isBlank(hashtag))
			throw new RuntimeException("Hastag não pode ser vazia!");

		try {

			QueryResult queryResult = getTwitterInstance().search(new Query(hashtag));
			statuses = queryResult.getTweets();

			int count = statuses.size();
			while (queryResult.hasNext() && count <= 30) {
				queryResult = getTwitterInstance().search(queryResult.nextQuery());
				statuses.addAll(queryResult.getTweets());

				count = statuses.size();
			}

		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return statuses;
	}

}
