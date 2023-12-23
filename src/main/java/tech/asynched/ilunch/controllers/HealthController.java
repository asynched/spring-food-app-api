package tech.asynched.ilunch.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.asynched.ilunch.dto.HealthResponseDto;

@RestController()
@RequestMapping("/health")
public class HealthController {
  @GetMapping("/check")
  public HealthResponseDto check() {
    return new HealthResponseDto();
  }
}
