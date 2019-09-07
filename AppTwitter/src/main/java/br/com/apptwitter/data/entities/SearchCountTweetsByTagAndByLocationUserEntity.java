package br.com.apptwitter.data.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCountTweetsByTagAndByLocationUserEntity  implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long countTweets;
	private String location;
	private String tag;
}
