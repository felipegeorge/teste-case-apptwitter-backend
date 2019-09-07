package br.com.apptwitter.controller.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String userName;
	private Integer followersCount;
	private String location;
}
