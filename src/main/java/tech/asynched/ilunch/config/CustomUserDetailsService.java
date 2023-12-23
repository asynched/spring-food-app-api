package tech.asynched.ilunch.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tech.asynched.ilunch.repositories.UserRepository;

@Service()
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired()
  private UserRepository userRepository;

  @Override()
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println("I've been called!");

    return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
  }
}
