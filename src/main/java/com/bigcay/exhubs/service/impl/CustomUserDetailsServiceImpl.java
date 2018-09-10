package com.bigcay.exhubs.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bigcay.exhubs.model.Role;
import com.bigcay.exhubs.repository.UserRepository;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.bigcay.exhubs.model.User domainUser = new com.bigcay.exhubs.model.User();

		try {
			domainUser = userRepository.findByUserId(username);

			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;

			return new User(domainUser.getUserId(), domainUser.getPassword().toLowerCase(), domainUser.getActiveFlag(), accountNonExpired,
					credentialsNonExpired, accountNonLocked, getAuthorities(domainUser.getGroup().getRoles()));
		} catch (Exception e) {
			throw new UsernameNotFoundException("User is not found!", e);
		}
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}

		return authorities;
	}

}
