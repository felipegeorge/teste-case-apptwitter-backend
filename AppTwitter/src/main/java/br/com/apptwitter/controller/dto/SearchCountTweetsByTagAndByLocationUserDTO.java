package br.com.apptwitter.controller.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCountTweetsByTagAndByLocationUserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Long countTweets;
	private String location;
	private String tag;
}
