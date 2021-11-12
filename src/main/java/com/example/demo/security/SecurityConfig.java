package com.example.demo.security;

import com.example.demo.filter.CustomAuthenticationFilter;
import com.example.demo.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers("/styles/", "/scripts/", "/img/**").permitAll();
//        http.authorizeRequests().antMatchers(POST).hasAnyRole("ADMIN","MANAGER")
//                .antMatchers(PUT).hasAnyRole("ADMIN","MANAGER")
//                .antMatchers(DELETE).hasAnyRole("MANAGER")
//                .antMatchers(GET,"/user/getUsers").hasAnyRole("ADMIN","MANAGER","USER")
//                .antMatchers(POST,"/c1/users/addCourse").hasAnyRole("ADMIN","MANAGER","USER")
//                .antMatchers(GET, )
//                .antMatchers(GET,"/c1/users").hasAnyRole("ADMIN","MANAGER")
//                .antMatchers(GET,"/c1/users/{userId}").access("@userSecurity.hasUserId(authentication,#userId)");

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
//        customAuthenticationFilter.setFilterProcessesUrl("/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers(GET, "/login").permitAll();
        http.authorizeRequests().antMatchers(GET, "/register").permitAll();
        http.authorizeRequests().antMatchers(POST, "/create").hasAnyAuthority("ADMIN", "MODERATOR");
        http.authorizeRequests().antMatchers(GET, "/product/get/**").hasAnyAuthority("USER", "MODERATOR");
        http.authorizeRequests().antMatchers(GET, "/user/get/**").hasAnyAuthority("USER", "MODERATOR");
        http.authorizeRequests().antMatchers(POST, "/user/addLoadouts/**").hasAnyAuthority("USER", "MODERATOR");
        http.authorizeRequests().antMatchers(GET, "/user/getUsers/**").hasAnyAuthority("ANALYST");
        http.authorizeRequests().antMatchers(POST, "/user/create/**").hasAnyAuthority("ADMIN", "MODERATOR");
        http.authorizeRequests().antMatchers(POST, "/user/delete/**").hasAnyAuthority("ADMIN", "MODERATOR");
        http.authorizeRequests().antMatchers("/**").hasAnyAuthority("MODERATOR");
//        http.authorizeRequests().antMatchers("/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login").defaultSuccessUrl("/main").permitAll();
        http.addFilter(customAuthenticationFilter);

        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}