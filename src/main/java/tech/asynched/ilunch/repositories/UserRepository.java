package tech.asynched.ilunch.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.asynched.ilunch.models.UserModel;

@Repository()
public interface UserRepository extends JpaRepository<UserModel, Long> {
  public Optional<UserModel> findByEmail(String email);
}
