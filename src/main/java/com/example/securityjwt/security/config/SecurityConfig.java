package com.example.securityjwt.security.config;

import com.example.securityjwt.security.jwt.AccessDenied;
import com.example.securityjwt.security.jwt.JWTEntryPoint;
import com.example.securityjwt.security.jwt.JWTProvider;
import com.example.securityjwt.security.jwt.JWTTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JWTEntryPoint jwtEntryPoint;
    @Autowired
    private JWTTokenFilter jwtTokenFilter;
    @Autowired
    private AccessDenied accessDenied;
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
       return httpSecurity.
                csrf(AbstractHttpConfigurer::disable).
                authenticationProvider(authenticationProvider()).
                authorizeHttpRequests(
                        (auth)->auth.requestMatchers("/auth/**").permitAll()
                                .anyRequest().authenticated()
                        ).
               exceptionHandling(
                       (auth)->auth.authenticationEntryPoint(jwtEntryPoint)
                               .accessDeniedHandler(accessDenied)
               ).
               addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class).
               build();
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
