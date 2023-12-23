package tech.asynched.ilunch.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tech.asynched.ilunch.dto.SignInDto;
import tech.asynched.ilunch.dto.SignUpDto;
import tech.asynched.ilunch.dto.TokenResponseDto;
import tech.asynched.ilunch.models.UserModel;
import tech.asynched.ilunch.services.UserService;

@RestController()
@RequestMapping("/users")
public class UserController {
  @Autowired()
  private UserService userService;

  @PostMapping("/sign-up")
  public UserModel createUser(@RequestBody() @Valid() SignUpDto user) {
    System.out.printf("User: %s\n", user);

    return userService.createUser(user);
  }

  @PostMapping("/sign-in")
  public TokenResponseDto signIn(@RequestBody() @Valid() SignInDto user) {
    System.out.printf("User: %s\n", user);

    return userService.signIn(user);
  }

  @GetMapping("/profile")
  public UserModel getProfile(@AuthenticationPrincipal() UserModel user) {
    System.out.printf("User: %s\n", user);

    return user;
  }
}
