package com.jcom.cms.services;



import com.jcom.cms.dto.UserRegistrationDto;
import com.jcom.cms.entity.Users;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
    Users save(UserRegistrationDto registrationDto);
    Users update(UserRegistrationDto registrationDto, Integer id);
    Users findFirstByEmail(String email);
    Users findByEmailIgnoreCase(String email);
    Users getOne(Integer id);
    long count();
}
