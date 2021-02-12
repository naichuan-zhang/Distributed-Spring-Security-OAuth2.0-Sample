package com.naichuan.security.uaa.service;

import com.naichuan.security.uaa.dao.UserDao;
import com.naichuan.security.uaa.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Naichuan Zhang
 */
@Service
public class SpringDataUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userDao.getUserByUsername(username);
        if (user == null) {
            return null;
        }
        List<String> permissions = userDao.getPermissionsByUserId(String.valueOf(user.getId()));
        String[] authorities = new String[permissions.size()];
        permissions.toArray(authorities);
        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
