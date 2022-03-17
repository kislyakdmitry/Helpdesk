package innowise.zuevsky.helpdesk.repository;

import innowise.zuevsky.helpdesk.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
