package com.unsaidtalks.auth.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.unsaidtalks.auth.domain.UserMaster;
import com.unsaidtalks.auth.repository.UserMasterRepository;
import com.unsaidtalks.auth.service.dto.User;
import com.unsaidtalks.auth.service.mapper.UserMasterMapper;

@Service
public class PygUserDetailService implements UserDetailsService {

	@Autowired
	private UserMasterRepository userRepository;

	@Autowired
	private UserMasterMapper userMapper;

	@Override
	public User loadUserByUsername(String userId) {
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("id", GenericPropertyMatchers.exact());
		UserMaster userMasterExample = new UserMaster();
		userMasterExample.setId(userId);
		Example<UserMaster> example = Example.of(userMasterExample, matcher);

		Optional<UserMaster> userMaster = userRepository.findOne(example);
		System.out.println("UserMaster found: " + userMaster);
		if (userMaster == null) {
			throw new UsernameNotFoundException(userId);
		}
		return userMapper.dtoToAuthDto(userMaster.get());
	}
}
