package br.com.apptwitter.service.wrapper;

import java.util.ArrayList;
import java.util.List;

import br.com.apptwitter.controller.dto.UserDTO;
import br.com.apptwitter.data.entities.UserEntity;

public class UserWrapper {

	public static List<UserDTO> convertListUserEntityToListUserDTO(List<UserEntity> userEntityList){
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		
		for(UserEntity u : userEntityList) {
			userDTOList.add(new UserDTO(u.getId(), u.getUserName(), u.getFollowersCount(), u.getLocation()));
		}
		
		return userDTOList;
	}
	
	public static UserDTO convertUserEntityToUserDTO(UserEntity userEntity){
				
		return new UserDTO(userEntity.getId(), userEntity.getUserName(), userEntity.getFollowersCount(), userEntity.getLocation());
	}
}
