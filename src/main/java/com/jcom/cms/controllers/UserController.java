package com.jcom.cms.controllers;

import com.jcom.cms.components.JwtUtils;
import com.jcom.cms.dto.UserRegistrationDto;
import com.jcom.cms.entity.Users;
import com.jcom.cms.hepers.AuthenticationSystem;
import com.jcom.cms.hepers.Helpers;
import com.jcom.cms.payload.request.LoginRequest;
import com.jcom.cms.payload.response.JwtResponse;
import com.jcom.cms.services.UserDetailsImpl;
import com.jcom.cms.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.Authentication;



@RestController
//@CrossOrigin
@RequestMapping("/api/v1/")
public class UserController extends MainController {
    @Autowired
    private UserServiceImpl userServiceImpl;

  //  @Autowired
  //  MailService mailService;

    @ModelAttribute("users")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    // Instantiate our encoder
    //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;


    // Function to handle the login process
    @PostMapping("/signin") // Map ONLY POST Requests
    public ResponseEntity<?> login(@RequestBody LoginRequest users,
                 HttpServletRequest request) {
           //String checkUser = userServiceImpl.checkUserLogin( users.getEmail(), users.getPassword(), encoder, request);
        //System.out.println("email is1:"+  encoder.encode("123")+":::" );

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(users.getEmail(), users.getPassword()));



        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);


        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, "Bearer",
                userDetails.getId(),
                userDetails.getUsername(),
                 null));

      }

      /*
            @PostMapping("/signin") // Map ONLY POST Requests
            public ResponseEntity<?> login(@RequestBody LoginRequest users,
                                           HttpServletRequest request) {

                return ResponseEntity.ok(new JwtResponse(jwt, "Bearer",
                        userDetails.getId(),
                        userDetails.getUsername(),
                        null));
            }
     */

/*
    // Function to handle the reset password process
    @PostMapping(path="/reset-password") // Map ONLY POST Requests
    public @ResponseBody
    String resetpassword (@ModelAttribute("SpringWeb")Users users) {
        ViewHelper viewHelper= new ViewHelper();
        Users existingUser = userServiceImpl.findByEmailIgnoreCase(users.getEmail());
        if (existingUser != null) {
            String random = Helpers.randomString(50);
            existingUser.setResetToken(random);
            existingUser.setResetTime(LocalDateTime.now());
            userServiceImpl.save(existingUser);

            Map<String,String> map = new HashMap<>();
            map.put("reseturl",  Helpers.baseurl()+"reset-password?email="+existingUser.getEmail()+"&resetToken="+random);

            mailService.sendSystemMail("reset-password", existingUser.getEmail(), map);

            return viewHelper.l("Check your email.");
        } else {
            return viewHelper.l("The account with the email does not exist!");
        }

    }


    // Function to handle the reset password by token process
    // /users/reset-password?email=emil@mm.gm&resetToken=123
    @GetMapping(path="/reset-password") // Map ONLY POST Requests
    public ModelAndView resetpassword_view (@ModelAttribute("SpringWeb")Users users, Model model) {
        ViewHelper viewHelper= new ViewHelper();
        Users existingUser = userServiceImpl.findByEmailIgnoreCase(users.getEmail());
        if (existingUser != null) {
            if (users.getResetToken().compareTo(existingUser.getResetToken())==0 && users.getResetToken().length()>48) {
                model.addAttribute("token",  existingUser.getResetToken());
                model.addAttribute("email",  existingUser.getEmail());
            }else {
               model.addAttribute("message",  Helpers.translate("Incorect url."));

            }
        } else {
            model.addAttribute("message",  Helpers.translate("The account with the email does not exist!"));

        }
        model.addAttribute("title",  Helpers.translate("Reset password."));
        return view("theme::newpassword") ;
    }

    // Function to handle the login process
    @PostMapping(path="/set-password") // Map ONLY POST Requests
    public @ResponseBody
    String setpassword (@ModelAttribute("SpringWeb")Users users, HttpServletRequest request) {
         String setPassByToken = userServiceImpl.setNewPasswordByToken(users, encoder);
         return Helpers.translate(setPassByToken);
     }

    @GetMapping(path="/signup/my-account") // Map ONLY POST Requests
    public ModelAndView editMyAccount (Model model, HttpServletRequest request) {
         int uid= AuthenticationSystem.currentUserId();
          Users user=null;
         if(uid>0){
           user = userServiceImpl.getOne(uid);
         }

        model.addAttribute("title", Helpers.translate("My account"));
        model.addAttribute("row", user);
        return view("theme::myaccount");
    }

    @PostMapping(path="/signup/update")
    public String update (@ModelAttribute("SpringWeb")Users users, HttpServletRequest request) {
        int uid = AuthenticationSystem.currentUserId();
        if(uid>0){
             String setPass= request.getParameter("creataccount");
             userServiceImpl.updateProfile(uid, users, setPass,  encoder);
        }
       return "redirect:" + request.getHeader("Referer");
    }

    */

}