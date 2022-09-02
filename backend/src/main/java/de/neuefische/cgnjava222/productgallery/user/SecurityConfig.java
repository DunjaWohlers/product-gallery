package de.neuefische.cgnjava222.productgallery.user;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AppUserService appUserService;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //.and()
                //.authorizeRequests()
                //.antMatchers("/auth/login").permitAll()
                //.antMatchers(HttpMethod.DELETE, "/api").hasRole("ADMIN")
                //.antMatchers("/api/image/**").hasAnyRole("ADMIN")
                //.antMatchers("/api/product/**").hasRole("ADMIN")
                //.antMatchers(HttpMethod.POST, "/api").hasRole("ADMIN")
                //.antMatchers("/admin/**").hasRole("ADMIN")
                //.antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .authorizeRequests()
                .antMatchers("/auth/login").permitAll()
                .antMatchers("/auth/logout").permitAll()
                .antMatchers("/auth/me").permitAll()
                .anyRequest().denyAll()
                .and().httpBasic()
                .and().build();
        //.and()
        //.sessionManagement()
        //.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // .and().httpBasic().authenticationEntryPoint(new CustomAuthentication()).and().build();
    }
}
