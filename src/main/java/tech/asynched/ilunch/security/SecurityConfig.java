package tech.asynched.ilunch.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration()
@EnableWebSecurity()
public class SecurityConfig {
  @Autowired()
  @SuppressWarnings("unused")
  private CustomUserDetailsService userDetailsService;

  private JwtAuthenticationEntryPoint authenticationEntryPoint = new JwtAuthenticationEntryPoint();

  @Bean()
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling((exceptionHandling) -> {
          exceptionHandling.authenticationEntryPoint(authenticationEntryPoint);
        })
        .authorizeHttpRequests((authorize) -> {
          authorize.requestMatchers("/health/**").permitAll();
          authorize.requestMatchers(HttpMethod.POST, "/users/sign-*").permitAll();
          authorize.requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll();
          authorize.requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll();
          authorize.anyRequest().authenticated();
        })
        .httpBasic(Customizer.withDefaults()).csrf(customizer -> customizer.disable())
        .sessionManagement(sessionManagement -> {
          sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

    return http.build();
  }

  @Bean()
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean()
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter();
  }

  @Bean()
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
