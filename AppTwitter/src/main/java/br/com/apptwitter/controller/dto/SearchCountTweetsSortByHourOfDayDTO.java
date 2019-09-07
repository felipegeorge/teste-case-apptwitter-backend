package br.com.apptwitter.controller.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCountTweetsSortByHourOfDayDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long countTweets;
//	private Integer year;
//	private Integer month;
//	private Integer day;
//	private Integer hour;
	private String hourDateFormat;
	
}
