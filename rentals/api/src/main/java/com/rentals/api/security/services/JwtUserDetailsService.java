package com.rentals.api.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rentals.api.models.User;
import com.rentals.api.repositories.UserRepository;
import com.rentals.api.security.model.MyUserDetails;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userDao;

	@Override
	public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userDao.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return MyUserDetails.build(user);
	}

}
