package com.antisocial.service;


import com.antisocial.model.State;
import com.antisocial.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/*
    This service is meant to be used by Spring Security.
    It has no direct relationship with user_details table.
 */

@Service
@Qualifier("userSecurityService")
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserService userService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String ssoId) throws UsernameNotFoundException {
        User user = userService.getUserBySsoId(ssoId, true);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }



        return new org.springframework.security.core.userdetails.User(user.getSsoId(),user.getPassword(),
                user.getState().equals(State.ACTIVE.getName()),true,true,!user.getState().equals(State.BANNED.getName()), getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {

        /*
        For now users can only have 1 role at a time. You cannot have more than 1 role type.
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        UserDetail userDetail = user.getUserDetail();
        String[] list = (userDetail.getType()).split(",");
        for(String i : list){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+i));
        }
         return authorities;
        */

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getUserDetail().getType()));
       return authorities;


    }
}
