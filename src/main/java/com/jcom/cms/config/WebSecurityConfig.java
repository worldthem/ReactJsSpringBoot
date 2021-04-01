package com.jcom.cms.config;



import com.jcom.cms.components.AuthEntryPointJwt;
import com.jcom.cms.services.UserService;
import com.jcom.cms.services.UserServiceImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
//@EnableWebMvc
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
   @Autowired
    private UserService userService;

    @Value("${static.crossOrigin}")
    private String CrossOrigin;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    /*
    public WebSecurityConfig(UserService userServiceImpl, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

     */

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userServiceImpl).passwordEncoder(passwordEncoder);

    }



    @Bean(name="myAuthenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userServiceImpl);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.cors().and().csrf().disable()
                 .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
               //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

                 .authorizeRequests()
                //.antMatchers("/**","/js/**", "/css/**", "/images/**", "/img/**", "/fonts/**", "/img_theme/**").permitAll()

                 .antMatchers("/admin/**",
                                 "/content/config/**",
                                 "/content/files/**",
                                 "/*.xml",
                                 "/*.json"
                                ).hasAnyRole("ADMIN")

                .antMatchers("/",
                        "/**",
                        "/content/**",
                        "/registration",
                        "/error",
                        "/favicon.ico",
                        "/*.png",
                        "/*.gif",
                        "/*.svg",
                        "/*.jpg",
                        "/*.jpg",
                        //"/*.html",
                        "/*.css",
                        "/*.js").permitAll();
                 /*
                 .anyRequest().authenticated()
                 .and()
                 .formLogin()
                 .loginPage("/api/v1/signin")
                 .permitAll()
                 .and()
                 .logout()
                 .invalidateHttpSession(true)
                 .clearAuthentication(true)
                 .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                 .logoutSuccessUrl("/")
                 .permitAll();
                */
    }



    /*
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        if(CrossOrigin != null)
         configuration.setAllowedOrigins(Arrays.asList(CrossOrigin));

        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

     */



/*
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }



    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
*/

}
