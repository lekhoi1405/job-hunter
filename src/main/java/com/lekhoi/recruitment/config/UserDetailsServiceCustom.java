package com.lekhoi.recruitment.config;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.lekhoi.recruitment.service.UserService;
import lombok.RequiredArgsConstructor;


@Component("userDetailsService")
@RequiredArgsConstructor
public class UserDetailsServiceCustom implements UserDetailsService {

    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsCustom userDetailsCustom = new UserDetailsCustom(this.userService.handleGetUserByUsername(username));
        return userDetailsCustom;
    }
    
}
