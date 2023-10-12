package flushd.Bathroom;

import flushd.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import flushd.Bathroom.Bathroom;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BathroomRepository extends JpaRepository<Bathroom, Long> {

    @Transactional
    void deleteById(int id);
    Bathroom findById(int id);
}
