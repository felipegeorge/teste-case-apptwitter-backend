package br.com.apptwitter.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apptwitter.controller.dto.UserDTO;
import br.com.apptwitter.controller.messages.Message;
import br.com.apptwitter.controller.messages.Response;
import br.com.apptwitter.service.UserService;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(path = "/v1/user")
public class TwitterUserController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "Retorna os 5 Usuários que possuem mais seguidores")
	@GetMapping(path = "/topfive", produces= {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<Response<UserDTO>> searchUsersTopFiveFollowers() {
		Response<UserDTO> response = null; 
		
		try {
			
			List<UserDTO> dtoList = userService.searchUsersTopFiveFollowers();
			
			if(dtoList.isEmpty()) {
				response = new Response<UserDTO>();
				response.setDatas(null);
				response.getMessages().add(new Message(new Integer(HttpStatus.NOT_FOUND.value()).toString(), HttpStatus.NOT_FOUND.name()));	
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
				
			}else {
				response = new Response<UserDTO>();
				response.setDatas(dtoList);
				response.getMessages().add(new Message(new Integer(HttpStatus.OK.value()).toString(), HttpStatus.OK.name()));	
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			response = new Response<UserDTO>();
			response.setDatas(null);
			response.getMessages().add(new Message(new Integer(HttpStatus.INTERNAL_SERVER_ERROR.value()).toString(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		
	}
	
	
//	@GetMapping(path = "/user", produces= {MediaType.APPLICATION_JSON_UTF8_VALUE})
//	public List<UserEntity> searchUsersTopFiveFollowers() {
//		log.info("[public List<UserEntity> searchUsersTopFiveFollowers()]");
//		
//		return twitterService.searchUsersTopFiveFollowers();
//	}
//	
//	@GetMapping(path = "/user2", produces= {MediaType.APPLICATION_JSON_UTF8_VALUE})
//	public List<SearchCountTweetsByTagAndByLocationUserEntity> searchCountTweetsByTagAndByLocationUser() {
//		log.info("[public List<TweetEntity> searchTwittersSortByHourOfDay()]");
//		
//		return twitterService.searchCountTweetsByTagAndByLocationUser();
//	}
//	
//	@GetMapping(path = "/tweet", produces= {MediaType.APPLICATION_JSON_UTF8_VALUE})
//	public List<TweetEntity> searchTwittersSortByHourOfDay() {
//		log.info("[public List<TweetEntity> searchTwittersSortByHourOfDay()]");
//		
//		return twitterService.searchTwittersSortByHourOfDay();
//	}
//	
}
