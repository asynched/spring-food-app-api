package tech.asynched.ilunch.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration()
@EnableWebSecurity()
public class SecurityConfig {
  @Autowired()
  private CustomUserDetailsService userDetailsService;

  @Bean()
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests((authorize) -> {
      authorize.requestMatchers("/health/**").permitAll();
      authorize.requestMatchers(HttpMethod.POST, "/users/sign-*").permitAll();
      authorize.requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll();
      authorize.requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll();
      authorize.anyRequest().authenticated();
    });

    http.httpBasic(Customizer.withDefaults());

    http.csrf(customizer -> customizer.disable());

    return http.build();
  }

  @Bean()
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean()
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
