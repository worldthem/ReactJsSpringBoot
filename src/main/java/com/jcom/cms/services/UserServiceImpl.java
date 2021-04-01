package com.jcom.cms.services;



import com.jcom.cms.dto.UserRegistrationDto;
import com.jcom.cms.entity.Role;
import com.jcom.cms.entity.Users;
import com.jcom.cms.repository.RoleRepository;
import com.jcom.cms.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UsersRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public Users save(UserRegistrationDto registrationDto) {

        Role role = roleRepository.findFirstByName(registrationDto.getUserRole());
        if(role==null){
            role = new Role(registrationDto.getUserRole());
        }

        Users user = new Users(registrationDto.getFirstName(),
                registrationDto.getLastName(), registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(role));

        return userRepository.save(user);
    }

    @Override
    public Users update(UserRegistrationDto registrationDto, Integer Id) {

        Users user = userRepository.getOne(Id);
        Role role = roleRepository.findFirstByName(registrationDto.getUserRole());
        if(role==null){
           role = new Role(registrationDto.getUserRole());
        }

        Users users = new Users(registrationDto.getFirstName(),
                registrationDto.getLastName(), registrationDto.getEmail(),
                user.getPassword(), Arrays.asList(role));
                users.setUserid(Id);

            String pass = registrationDto.getPassword();
            if(pass !=null){
                if(!pass.isEmpty()){
                    users.setPassword(passwordEncoder.encode(pass));
                }
            }

         return userRepository.save(users);
    }

    @Override
    public Users findFirstByEmail(String username) {
        Users user = userRepository.findByEmail(username);
        return user;
    }

    @Override
    public Users findByEmailIgnoreCase(String username) {
        Users user = userRepository.findByEmailIgnoreCase(username);
        return user;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         Users user = userRepository.findByEmail(username);
                      //.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }


        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        return UserDetailsImpl.build(user);
        /*
        return new CustomUser(
                user.getEmail(),
                user.getPassword(),
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                mapRolesToAuthorities(user.getRoles()),
                user.getUserid());

         */
        // return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }




    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public Users getOne(Integer id) {
        return userRepository.getOne(id);
    }

    @Override
    public long count(){
        return userRepository.count();
    }


    public String checkUserLogin(String email, String password, BCryptPasswordEncoder encoder, HttpServletRequest request){
        Users existingUser = userRepository.findByEmailIgnoreCase(email);
        String message = "";
        if (existingUser != null){
            // Use encoder.matches to compare raw password with encrypted password
             if (encoder.matches(password, existingUser.getPassword())){
                try {
                    request.login(email, password);
                    String typelogin =request.getParameter("typelogin");
                    message = typelogin!=null ? (typelogin.contains("admin")? "redirect::/admin": "reload") : "reload" ;
                } catch (Exception e) {
                    message = "Bad Credentials";
                }
            } else {
                // Wrong password
                message = "Incorrect password. Try again.";
            }

        } else {
            message = "The account with the email does not exist!";
       }
        return message;
    }


    public void save(Users users){
         userRepository.save(users);
    }

    /**
     * Update profile
     * @param uid
     * @param users
     * @param changePass
     * @param encoder
     */
    public void updateProfile(int uid, Users users, String changePass, BCryptPasswordEncoder encoder){
         Users user = userRepository.getOne(Integer.valueOf(uid));
         user.setFirstName(users.getFirstName());
         user.setLastName(users.getLastName());

        String pass = users.getPassword();
        if(changePass !=null && !pass.isEmpty() && pass != null) {
           user.setPassword(encoder.encode(pass));
        }

        userRepository.save(user);
    }

    /**
     * Set new password by token
     * @param users
     * @param encoder
     * @return
     */
    public String setNewPasswordByToken(Users users, BCryptPasswordEncoder encoder){
        Users existingUser = userRepository.findByEmailIgnoreCase(users.getEmail());
        if (existingUser != null) {
            if (users.getResetToken().compareTo(existingUser.getResetToken())==0 && users.getResetToken().length()>48) {

                existingUser.setResetToken("");
                existingUser.setPassword(encoder.encode(users.getPassword()));
                userRepository.save(existingUser);

                try {
                    //request.login(existingUser.getEmail(), users.getPassword());
                    return  "Your password has been reset, try to login" ;
                } catch (Exception e) {
                    return  "Bad Credentials" ;
                }

                //return "New password seted.";
            }else{
                return  "Incorrect url.";
            }

        } else {
            return "The account with the email does not exist!";
        }
    }
}
