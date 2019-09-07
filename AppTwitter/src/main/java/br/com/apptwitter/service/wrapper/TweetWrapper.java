package br.com.apptwitter.service.wrapper;

import java.util.ArrayList;
import java.util.List;

import br.com.apptwitter.controller.dto.SearchCountTweetsByTagAndByLocationUserDTO;
import br.com.apptwitter.controller.dto.SearchCountTweetsSortByHourOfDayDTO;
import br.com.apptwitter.data.entities.SearchCountTweetsByTagAndByLocationUserEntity;
import br.com.apptwitter.data.entities.SearchCountTweetsSortByHourOfDayEntity;

public class TweetWrapper {

	public static List<SearchCountTweetsByTagAndByLocationUserDTO> convertEntityToListSearchCountTweetsByTagAndByLocationUserDTO(
			List<SearchCountTweetsByTagAndByLocationUserEntity> entityList) {
		List<SearchCountTweetsByTagAndByLocationUserDTO> dtolist = new ArrayList<SearchCountTweetsByTagAndByLocationUserDTO>();

		int id = 1;
		for (SearchCountTweetsByTagAndByLocationUserEntity o : entityList) {
			dtolist.add(new SearchCountTweetsByTagAndByLocationUserDTO(id++, o.getCountTweets(), o.getLocation(), o.getTag()));
		}

		return dtolist;
	}

	public static SearchCountTweetsByTagAndByLocationUserDTO convertEntityToSearchCountTweetsByTagAndByLocationUserDTO(
			SearchCountTweetsByTagAndByLocationUserEntity entity) {
		return new SearchCountTweetsByTagAndByLocationUserDTO(Integer.valueOf(1) , entity.getCountTweets(), entity.getLocation(),
				entity.getTag());

	}

	public static List<SearchCountTweetsSortByHourOfDayDTO> convertEntityToListSearchCountTweetsSortByHourOfDayDTO(
			List<SearchCountTweetsSortByHourOfDayEntity> entityList) {
		List<SearchCountTweetsSortByHourOfDayDTO> dtolist = new ArrayList<SearchCountTweetsSortByHourOfDayDTO>();

		for (SearchCountTweetsSortByHourOfDayEntity o : entityList) {
			dtolist.add(new SearchCountTweetsSortByHourOfDayDTO(o.getCountTweets(), 
					String.format("%02d:00, %02d/%02d/%d", o.getHour(), o.getDay(), o.getMonth(), o.getYear())));
		}

		return dtolist;
	}

	public static SearchCountTweetsSortByHourOfDayDTO convertEntityToSearchCountTweetsSortByHourOfDayDTO(
			SearchCountTweetsSortByHourOfDayEntity entity) {
		return new SearchCountTweetsSortByHourOfDayDTO(entity.getCountTweets(), String.format("%02d:00, %02d/%02d/%d", entity.getHour(), entity.getDay(), entity.getMonth(), entity.getYear()));

	}
}
