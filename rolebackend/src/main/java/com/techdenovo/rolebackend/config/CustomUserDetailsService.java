package com.techdenovo.rolebackend.config;


import com.fasterxml.jackson.databind.util.BeanUtil;
import com.techdenovo.rolebackend.model.DAOUser;
import com.techdenovo.rolebackend.model.ResourceRequest;
import com.techdenovo.rolebackend.model.UserDto;
import com.techdenovo.rolebackend.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> roles = null;
        DAOUser user = userRepository.findByUsername(username);
        if(user!=null){
            roles= Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
            return new User(user.getUsername(),user.getPassword(),roles);
        }
       throw new UsernameNotFoundException("User not found for "+username);
    }
    public DAOUser save(UserDto userDto){
        DAOUser newUser =  new DAOUser();
        //newUser.setUsername(userDto.getUsername());
        //newUser.setPassword(bcryptEncoder.encode(userDto.getPassword()));
        //newUser.setRole(userDto.getRole());
        userDto.setPassword(bcryptEncoder.encode(userDto.getPassword()));
        BeanUtils.copyProperties(userDto, newUser);
        return userRepository.save(newUser);
    }

    public DAOUser getUser(ResourceRequest resourceRequest){
        String username = jwtUtil.getUsernameFromToken(resourceRequest.getToken());
        DAOUser daoUser = userRepository.findByUsername(username);

        return daoUser;
    }
}
