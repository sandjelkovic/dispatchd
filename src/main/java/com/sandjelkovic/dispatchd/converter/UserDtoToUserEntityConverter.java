package com.sandjelkovic.dispatchd.converter;

import com.sandjelkovic.dispatchd.api.dto.UserDto;
import com.sandjelkovic.dispatchd.domain.data.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToUserEntityConverter implements Converter<UserDto, User> {
	@Override
	public User convert(UserDto source) {
		User user = new User();
		user.setId(source.getId());
		user.setUsername(source.getUsername());
		user.setEmail(source.getEmail());
		user.setPassw(source.getPassword());
		user.setAuthorities(source.getAuthorities());
		user.setReportsToKeep(source.getReportsToKeep());
		return user;
	}
}