package br.com.apptwitter.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.apptwitter.data.entities.SearchCountTweetsByTagAndByLocationUserEntity;
import br.com.apptwitter.data.entities.SearchCountTweetsSortByHourOfDayEntity;
import br.com.apptwitter.data.entities.TweetEntity;


@Repository
public interface TweetRepository extends JpaRepository<TweetEntity, Long> {
    
	@Query("SELECT new br.com.apptwitter.data.entities.SearchCountTweetsSortByHourOfDayEntity( COUNT(t),year(t.createdAt) , month(t.createdAt), day(t.createdAt), hour(t.createdAt) ) " + 
			"FROM TweetEntity t " + 
			"GROUP BY year(t.createdAt) , month(t.createdAt), day(t.createdAt), hour(t.createdAt) " + 
			"ORDER BY year(t.createdAt) DESC, month(t.createdAt) DESC, day(t.createdAt) DESC, hour(t.createdAt) DESC")
	List<SearchCountTweetsSortByHourOfDayEntity> searchCountTweetsSortByHourOfDay();

	@Query("SELECT new br.com.apptwitter.data.entities.SearchCountTweetsByTagAndByLocationUserEntity(COUNT(h.id) , u.location, h.tag) "+
			"FROM UserEntity u "+ 
			"INNER JOIN u.tweetList t "+
			"INNER JOIN t.tagList h "+
			"WHERE u.location is not null "+
			"GROUP BY u.location, h.tag "+
			"ORDER BY COUNT(h.id) DESC, h.tag ASC ")
	List<SearchCountTweetsByTagAndByLocationUserEntity> searchCountTweetsByTagAndByLocationUser();
}
