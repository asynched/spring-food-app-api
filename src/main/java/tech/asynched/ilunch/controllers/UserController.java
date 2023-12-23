package tech.asynched.ilunch.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import tech.asynched.ilunch.services.UserService;

@Slf4j()
@RestController()
@RequestMapping("/users")
public class UserController {
  @Autowired()
  private UserService userService;

  @PostMapping("/sign-up")
  public UserModel createUser(@RequestBody() @Valid() SignUpDto user) {
    log.info("User: {}", user);

    return userService.createUser(user);
  }

  @PostMapping("/sign-in")
  public TokenResponseDto signIn(@RequestBody() @Valid() SignInDto user) {
    log.info("User: {}", user);

    return userService.signIn(user);
  }

  @GetMapping("/profile")
  public UserModel getProfile(@AuthenticationPrincipal() UserModel user) {
    log.info("User: {}", user);

    return user;
  }
}
