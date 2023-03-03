package com.project.rentauto.configuration;

import com.project.rentauto.security.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final LoginSuccessHandler loginSuccessHandler;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService, LoginSuccessHandler loginSuccessHandler, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.loginSuccessHandler = loginSuccessHandler;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login")
                .successHandler(loginSuccessHandler)
                .loginProcessingUrl("/login")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .permitAll();

        http.logout()
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .and().csrf().disable();

        http
                .authorizeRequests()
                .antMatchers("/login", "/registration").permitAll()
                .antMatchers("/admin/**").access("hasAuthority('ROLE_ADMIN')")
                .antMatchers("/customer/**").access("hasAuthority('ROLE_CUSTOMER')")
                .anyRequest().authenticated();
        return http.build();
    }



}
