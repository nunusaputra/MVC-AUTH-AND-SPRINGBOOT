package com.example.job.security;

import com.example.job.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration // Marks this as a configuration file
@EnableWebSecurity // Enables security for this application
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Injecting Dependencies
    @Autowired
    private UsersRepository userRepo;
    @Autowired
    private JWTFilter filter;
    @Autowired
    private MyUserDetailsService uds;

    @Override
    protected void configure(HttpSecurity http) throws Exception { // Method to configure your app security
        http.csrf().disable() // Disabling csrf
                .httpBasic().disable() // Disabling http basic
                .cors() // Enabling cors
                .and()
                .authorizeHttpRequests() // Authorizing incoming requests
                .antMatchers("/api/auth/**", "/api/job/**").permitAll()
                .antMatchers("/api/user/**").hasRole("USER")
                .and()
                .userDetailsService(uds)
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                "Unauthorized"))
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}