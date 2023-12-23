package tech.asynched.ilunch.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data()
public class SignUpDto {
  @NotBlank()
  @Length(min = 3, max = 255)
  private String name;

  @NotBlank()
  @Length(max = 255)
  @Email()
  private String email;

  @NotBlank()
  @Length(min = 8, max = 255)
  private String password;

  @NotBlank()
  @Length(min = 4, max = 255)
  private String address;

  @NotBlank()
  @Length(min = 4, max = 32)
  private String city;

  @NotBlank()
  @Length(min = 2, max = 2)
  private String state;
}
