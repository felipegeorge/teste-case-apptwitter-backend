package br.com.apptwitter.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apptwitter.controller.dto.SearchCountTweetsByTagAndByLocationUserDTO;
import br.com.apptwitter.controller.dto.SearchCountTweetsSortByHourOfDayDTO;
import br.com.apptwitter.data.entities.SearchCountTweetsByTagAndByLocationUserEntity;
import br.com.apptwitter.data.entities.SearchCountTweetsSortByHourOfDayEntity;
import br.com.apptwitter.data.repositories.TweetRepository;
import br.com.apptwitter.service.loaddata.LoadDataTwitterService;
import br.com.apptwitter.service.wrapper.TweetWrapper;

@Service
public class TweetService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private TweetRepository tweetRepository;
	
	@Autowired
	private LoadDataTwitterService loadDataTwitterService;

	public List<SearchCountTweetsByTagAndByLocationUserDTO> searchCountTweetsByTagAndByLocationUser() {
		long startTime = System.currentTimeMillis();
		
		List<SearchCountTweetsByTagAndByLocationUserEntity> list = tweetRepository
				.searchCountTweetsByTagAndByLocationUser()
//				.stream()
//				.sorted(Comparator.comparingLong(SearchCountTweetsByTagAndByLocationUserEntity::getCountTweets).reversed())
//				.sorted(Comparator.comparing(SearchCountTweetsByTagAndByLocationUserEntity::getTag).reversed())
//				.collect(Collectors.toList())
		;
		
		if(list.isEmpty()) {
			loadDataTwitterService.loadDataTwitterServices();
		}
		list = tweetRepository.searchCountTweetsByTagAndByLocationUser();
		List<SearchCountTweetsByTagAndByLocationUserDTO> listDTO = TweetWrapper.convertEntityToListSearchCountTweetsByTagAndByLocationUserDTO(list); 
		
		long totalTime = System.currentTimeMillis() - startTime;
		System.out.println("Time execution from [TweetService.searchCountTweetsByTagAndByLocationUser()] is: " +totalTime );
		
		return listDTO;
	}
	
	public List<SearchCountTweetsSortByHourOfDayDTO> searchTwittersSortByHourOfDay() {
		long startTime = System.currentTimeMillis();
		
		List<SearchCountTweetsSortByHourOfDayEntity> tweetList = tweetRepository.searchCountTweetsSortByHourOfDay()
//				.stream()
//				.sorted(Comparator.comparingLong(TweetEntity::getYear).reversed())
//				.sorted(Comparator.comparingLong(TweetEntity::getMonth).reversed())
//				.sorted(Comparator.comparingLong(TweetEntity::getDay).reversed())
//				.sorted(Comparator.comparingLong(TweetEntity::getHour).reversed()).collect(Collectors.toList())
		;
		
		if(tweetList.isEmpty()) {
			loadDataTwitterService.loadDataTwitterServices();
		}
		tweetList = tweetRepository.searchCountTweetsSortByHourOfDay();
		List<SearchCountTweetsSortByHourOfDayDTO> listDTO =TweetWrapper.convertEntityToListSearchCountTweetsSortByHourOfDayDTO(tweetList); 
		
		long totalTime = System.currentTimeMillis() - startTime;
		System.out.println("Time execution from [TweetService.searchTwittersSortByHourOfDay()] is: " +totalTime );
		
		return listDTO;
	}

}
