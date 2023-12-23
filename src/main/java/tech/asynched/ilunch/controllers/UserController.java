package tech.asynched.ilunch.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import tech.asynched.ilunch.dto.SignInDto;
import tech.asynched.ilunch.dto.SignUpDto;
import tech.asynched.ilunch.dto.TokenResponseDto;
import tech.asynched.ilunch.models.UserModel;
import tech.asynched.ilunch.security.JwtTokenGenerator;
import tech.asynched.ilunch.services.UserService;

@Slf4j()
@RestController()
@RequestMapping("/users")
public class UserController {
  @Autowired()
  private AuthenticationManager authManager;

  @Autowired()
  private UserService userService;

  @Autowired()
  private JwtTokenGenerator tokenGenerator;

  @PostMapping("/sign-up")
  public UserModel createUser(@RequestBody() @Valid() SignUpDto data) {
    return userService.createUser(data);
  }

  @PostMapping("/sign-in")
  public TokenResponseDto signIn(@RequestBody() @Valid() SignInDto data) {
    var auth = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            data.getEmail(),
            data.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(auth);

    return new TokenResponseDto(tokenGenerator.generateToken(auth));
  }

  @GetMapping("/profile")
  public UserModel getProfile(@AuthenticationPrincipal() UserModel user) {
    log.info("User: {}", user);

    return user;
  }
}
