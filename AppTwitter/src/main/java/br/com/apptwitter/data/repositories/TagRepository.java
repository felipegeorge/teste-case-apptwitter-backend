package br.com.apptwitter.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apptwitter.data.entities.TagEntity;


@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {
    
	//public List<HashtagEntity> listTweetAmountByTagsByUser();

}
