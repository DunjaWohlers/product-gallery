package de.neuefische.cgnjava222.productgallery.user;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    private final AppUserService appUserService;

    public SecurityConfig(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http

                .authorizeRequests()
                .antMatchers("/api/users/login").permitAll()
                .antMatchers("/api/users/logout").permitAll()
                .antMatchers("/api/users/me").permitAll()
                .anyRequest().denyAll()

                .and().httpBasic()

                .and().build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager userDetailsService() {
        return new UserDetailsManager() {
            @Override
            public void createUser(UserDetails user) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void updateUser(UserDetails user) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void deleteUser(String username) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void changePassword(String oldPassword, String newPassword) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean userExists(String username) {
                throw new UnsupportedOperationException();
            }

            @Override
            public User loadUserByUsername(String username) {
                return appUserService.loadUserByUsername(username);
            }
        };
    }
}
