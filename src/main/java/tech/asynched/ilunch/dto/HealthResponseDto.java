package tech.asynched.ilunch.dto;

import java.time.LocalDate;

import lombok.Data;

@Data()
public class HealthResponseDto {
  private String status = "OK";
  private LocalDate date = LocalDate.now();
}
