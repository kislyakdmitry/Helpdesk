package innowise.zuevsky.helpdesk.repository;

import innowise.zuevsky.helpdesk.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
