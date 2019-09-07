package br.com.apptwitter.data.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCountTweetsSortByHourOfDayEntity  implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private Long countTweets;
	private Integer year;
	private Integer month;
	private Integer day;
	private Integer hour;
	
}
