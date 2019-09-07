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

import br.com.apptwitter.controller.dto.SearchCountTweetsByTagAndByLocationUserDTO;
import br.com.apptwitter.controller.dto.SearchCountTweetsSortByHourOfDayDTO;
import br.com.apptwitter.controller.messages.Message;
import br.com.apptwitter.controller.messages.Response;
import br.com.apptwitter.service.TweetService;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(path = "/v1/tweet")
public class TwitterMessagesController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private TweetService tweetService; 
	
	@ApiOperation(value = "Busca o total de postagens para cada uma das #tag por idioma/país do usuário que postou")
	@GetMapping(path = "/groupbyhashtag", produces= {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<Response<SearchCountTweetsByTagAndByLocationUserDTO>> searchCountTweetsByTagAndByLocationUser() {
		Response<SearchCountTweetsByTagAndByLocationUserDTO> response = null; 
		
		try {
			
			List<SearchCountTweetsByTagAndByLocationUserDTO> dtoList = tweetService.searchCountTweetsByTagAndByLocationUser();
			
			if(dtoList.isEmpty()) {
				response = new Response<SearchCountTweetsByTagAndByLocationUserDTO>();
				response.setDatas(null);
				response.getMessages().add(new Message(new Integer(HttpStatus.NOT_FOUND.value()).toString(), HttpStatus.NOT_FOUND.name()));	
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
				
			}else {
				response = new Response<SearchCountTweetsByTagAndByLocationUserDTO>();
				response.setDatas(dtoList);
				response.getMessages().add(new Message(new Integer(HttpStatus.OK.value()).toString(), HttpStatus.OK.name()));	
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			response = new Response<SearchCountTweetsByTagAndByLocationUserDTO>();
			response.setDatas(null);
			response.getMessages().add(new Message(new Integer(HttpStatus.INTERNAL_SERVER_ERROR.value()).toString(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		
	}
	
	@ApiOperation(value = "Busca o total de postagens, agrupadas por hora do dia")
	@GetMapping(path = "/groupbyhours", produces= {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<Response<SearchCountTweetsSortByHourOfDayDTO>> searchTwittersSortByHourOfDay() {
		Response<SearchCountTweetsSortByHourOfDayDTO> response = null; 
		
		try {
			
			List<SearchCountTweetsSortByHourOfDayDTO> dtoList = tweetService.searchTwittersSortByHourOfDay();
			
			if(dtoList.isEmpty()) {
				response = new Response<SearchCountTweetsSortByHourOfDayDTO>();
				response.setDatas(null);
				response.getMessages().add(new Message(new Integer(HttpStatus.NOT_FOUND.value()).toString(), HttpStatus.NOT_FOUND.name()));	
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
				
			}else {
				response = new Response<SearchCountTweetsSortByHourOfDayDTO>();
				response.setDatas(dtoList);
				response.getMessages().add(new Message(new Integer(HttpStatus.OK.value()).toString(), HttpStatus.OK.name()));	
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			response = new Response<SearchCountTweetsSortByHourOfDayDTO>();
			response.setDatas(null);
			response.getMessages().add(new Message(new Integer(HttpStatus.INTERNAL_SERVER_ERROR.value()).toString(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		
	}
}
