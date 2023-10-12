package flushd.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Transactional
    void deleteById(Long id);
}
