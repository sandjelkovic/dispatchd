package com.sandjelkovic.dispatchd.api.dto;

import com.sandjelkovic.dispatchd.helper.EmptyCollections;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Email;

import java.util.Set;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private Long id;

	@Email
	private String email;

	private String password;

	private int reportsToKeep = 5;

	private String username;

	private boolean approved = false;

	private Set<String> authorities = EmptyCollections.set();

}
