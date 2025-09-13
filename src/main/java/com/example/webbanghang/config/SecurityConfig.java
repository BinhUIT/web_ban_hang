package com.example.webbanghang.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.webbanghang.middleware.PassEncoder;
import com.example.webbanghang.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserService userService;
    private final JwtAuthFilter jwtAuthFilter;
    public SecurityConfig(UserService userService, JwtAuthFilter jwtAuthFilter) {
        this.userService= userService;
        this.jwtAuthFilter = jwtAuthFilter;
    } 
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService); 
        authenticationProvider.setPasswordEncoder(PassEncoder.getPassEncoder());
        return authenticationProvider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(httpSecurityCsrfConfigurer->httpSecurityCsrfConfigurer.disable()).cors(cors->cors.configurationSource(corsConfigurationSource()))
        .httpBasic(Customizer.withDefaults()) 
        .authorizeHttpRequests(auth->auth.requestMatchers("/unsecure/**").permitAll()
        .requestMatchers("/user/**").hasAnyAuthority("USER","ADMIN") 
        .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
        .anyRequest().authenticated()
        )
        .formLogin(Customizer.withDefaults());
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
