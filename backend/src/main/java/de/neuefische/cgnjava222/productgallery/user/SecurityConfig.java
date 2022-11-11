package de.neuefische.cgnjava222.productgallery.user;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AppUserService appUserService;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity security) throws Exception {
        return security.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(appUserService)
                .passwordEncoder(encoder())
                .and().build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String admin = "ADMIN";
        return http
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers("/h2-console/**")
                .and()
                .authorizeRequests()
                .antMatchers("/api/users/login", "/api/users/logout", "/api/users/me").permitAll()
                .antMatchers("/api/image/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/product/**").hasAuthority(admin)
                .antMatchers("/api/image/**").hasAuthority(admin)
                .antMatchers(HttpMethod.DELETE, "/api/product/**").hasAuthority(admin)
                .antMatchers(HttpMethod.PUT, "/api/**").hasAuthority(admin)
                .anyRequest().permitAll()
                .and().httpBasic()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
