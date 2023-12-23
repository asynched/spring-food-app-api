package tech.asynched.ilunch.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tech.asynched.ilunch.dto.SignInDto;
import tech.asynched.ilunch.dto.SignUpDto;
import tech.asynched.ilunch.dto.TokenResponseDto;
import tech.asynched.ilunch.exceptions.UnauthorizedException;
import tech.asynched.ilunch.exceptions.UserNotFoundException;
import tech.asynched.ilunch.models.UserModel;
import tech.asynched.ilunch.repositories.UserRepository;

@Service()
public class UserService {
  @Autowired()
  private PasswordEncoder passwordEncoder;

  @Autowired()
  private UserRepository userRepository;

  public UserModel createUser(SignUpDto data) {
    data.setPassword(passwordEncoder.encode(data.getPassword()));

    UserModel user = new UserModel();

    BeanUtils.copyProperties(data, user);

    return userRepository.save(user);
  }

  public TokenResponseDto signIn(SignInDto data) {
    var email = data.getEmail();

    var user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());

    var password = data.getPassword();

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new UnauthorizedException();
    }

    return new TokenResponseDto("token");
  }
}
