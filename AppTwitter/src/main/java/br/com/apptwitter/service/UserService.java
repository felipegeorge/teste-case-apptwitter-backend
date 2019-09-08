package br.com.apptwitter.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.apptwitter.controller.dto.UserDTO;
import br.com.apptwitter.data.entities.UserEntity;
import br.com.apptwitter.data.repositories.UserRepository;
import br.com.apptwitter.service.loaddata.LoadDataTwitterService;
import br.com.apptwitter.service.wrapper.UserWrapper;

@Service
public class UserService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LoadDataTwitterService loadDataTwitterService;

	public List<UserDTO> searchUsersTopFiveFollowers() {
		long startTime = System.currentTimeMillis();
		List<UserEntity> userList = userRepository.findAll(new Sort(Sort.Direction.DESC, "followersCount"));
		
		if(userList.isEmpty()) {
			loadDataTwitterService.loadDataTwitterServices();
		}
		userList = userRepository.findAll(new Sort(Sort.Direction.DESC, "followersCount"));
		List<UserEntity> userTopFiveList = new ArrayList<UserEntity>();

		for (int i = 0; i < 5; i++) {
			UserEntity u = userList.get(i);
			u.setTweetList(null);
			userTopFiveList.add(u);
		}

		List<UserDTO> listUserDTO = UserWrapper.convertListUserEntityToListUserDTO(userTopFiveList);
		
		long totalTime = System.currentTimeMillis() - startTime;
		System.out.println("Time execution from [UserService.searchUsersTopFiveFollowers()] is: " +totalTime );
		
		return listUserDTO;
	}

}
