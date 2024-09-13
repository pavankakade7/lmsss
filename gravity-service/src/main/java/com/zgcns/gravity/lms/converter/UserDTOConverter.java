package com.zgcns.gravity.lms.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// import com.zgcns.gravity.authmanager.dto.UserAuthDTO;

// import com.zgcns.gravity.authmanager.dto.UserDTO;

import com.zgcns.gravity.lms.dto.UserDTO;

import com.zgcns.gravity.lms.model.User;

@Component
public class UserDTOConverter {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public UserDTO convertUsertoUserDTO(User user) {
		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
		System.out.println("userDTO " + userDTO);
		return userDTO;
	}
	
	public User convertUserDTOtoUser(UserDTO userDTO) {
		User user = modelMapper.map(userDTO, User.class);
		return user;
	}
}