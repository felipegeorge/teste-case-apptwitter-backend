package br.com.apptwitter.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apptwitter.adapter.twitter4j.Twitter4JAdapter;
import br.com.apptwitter.data.entities.TagEntity;
import br.com.apptwitter.data.entities.TweetEntity;
import br.com.apptwitter.data.entities.UserEntity;
import br.com.apptwitter.data.repositories.UserRepository;
import twitter4j.HashtagEntity;
import twitter4j.Status;

@Service
public class Twitter4JService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Twitter4JAdapter twitter4JAdapter;

	public boolean loadTweetsByHashtags(List<String> hashtagList) {
//		TweetEntity te = new TweetEntity(id, createdAt, text, user, tagList);
		hashtagList.forEach(hashtag -> {
			System.out.println("SEARCHING...");

			List<Status> statuses = twitter4JAdapter.getStatusByHashtag(hashtag.toLowerCase());

			statuses.forEach(s -> {
				// dados do usuario
				UserEntity userEntity = new UserEntity(s.getUser().getId(),
						String.format("@%s", s.getUser().getScreenName()), s.getUser().getFollowersCount(),
						s.getUser().getLang(),
						(StringUtils.isBlank(s.getUser().getLocation()) ? null : s.getUser().getLocation()), null);

				// dados da mensagem
				TweetEntity tweetEntity = new TweetEntity(s.getId(), s.getCreatedAt(), s.getText(), userEntity, null);

				// dados da hashtag
				TagEntity tagEntity = new TagEntity();
				HashtagEntity tags[] = s.getHashtagEntities();

				for (HashtagEntity tag : tags) {
					// se existir a hashtag procurada...
					if (("#" + tag.getText()).toLowerCase().equalsIgnoreCase(hashtag)) {
						tagEntity.setTag(String.format("#%s", tag.getText().toLowerCase()));
						tagEntity.setTweet(tweetEntity);

						// ...salvar na base de dados
						tweetEntity.setTagList(Arrays.asList(tagEntity));
						userEntity.setTweetList(Arrays.asList(tweetEntity));
						userRepository.save(userEntity);

						break;
					}
				}

			});
		});

		System.out.println("FINISHED...!");
		return true;
	}
}
