package no.oslomet.springsecurityh2demo.repository;

import no.oslomet.springsecurityh2demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
}
