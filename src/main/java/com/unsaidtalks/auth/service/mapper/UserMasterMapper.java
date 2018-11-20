package com.unsaidtalks.auth.service.mapper;

import org.mapstruct.Mapper;

import com.unsaidtalks.auth.domain.UserMaster;
import com.unsaidtalks.auth.service.dto.User;
import com.unsaidtalks.auth.service.dto.UserMasterDTO;

/**
 * Mapper for the entity UserMaster and its DTO UserMasterDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserMasterMapper extends EntityMapper<UserMasterDTO, UserMaster> {

	User dtoToAuthDto(UserMaster userDto);

	default UserMaster fromId(String id) {
		if (id == null) {
			return null;
		}
		UserMaster userMaster = new UserMaster();
		userMaster.setId(id);
		return userMaster;
	}
}
