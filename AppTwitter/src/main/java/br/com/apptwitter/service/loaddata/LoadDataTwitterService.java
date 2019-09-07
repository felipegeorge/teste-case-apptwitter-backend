package br.com.apptwitter.service.loaddata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import br.com.apptwitter.service.Twitter4JService;

@Service
public class LoadDataTwitterService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private Twitter4JService twitter4JService;

	@Bean
	public CommandLineRunner loadDataTwitterServices() {
		return args -> {
			List<String> hashtagList = new ArrayList<String>();
			hashtagList.add("#openbanking");
			hashtagList.add("#apifirst");
			hashtagList.add("#devops");
			hashtagList.add("#cloudfirst");
			hashtagList.add("#microservices");
			hashtagList.add("#apigateway");
			hashtagList.add("#oauth");
			hashtagList.add("#swagger");
			hashtagList.add("#raml");
			hashtagList.add("#openapis");

			twitter4JService.loadTweetsByHashtags(hashtagList);
		};
	}

}
