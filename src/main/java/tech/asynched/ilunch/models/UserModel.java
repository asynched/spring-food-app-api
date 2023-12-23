package tech.asynched.ilunch.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data()
@Entity()
@Table(name = "users")
public class UserModel implements UserDetails {
  @Id()
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long userId;

  private String name;
  private String email;

  @JsonIgnore()
  private String password;
  private String address;
  private String city;
  private String state;

  @CreationTimestamp()
  @Column(name = "created_at")
  private Date createdAt;

  @UpdateTimestamp()
  @Column(name = "updated_at")
  private Date updatedAt;

  @Override
  @JsonIgnore()
  public Collection<Role> getAuthorities() {
    return new ArrayList<Role>() {
      {
        add(Role.USER);
      }
    };
  }

  @Override
  @JsonIgnore()
  public String getUsername() {
    return email;
  }

  @Override
  @JsonIgnore()
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore()
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  @JsonIgnore()
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore()
  public boolean isEnabled() {
    return true;
  }
}

enum Role implements GrantedAuthority {
  USER;

  @Override
  public String getAuthority() {
    return name();
  }
}
