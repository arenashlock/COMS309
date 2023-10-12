package flushd.Review;

import flushd.Comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findById(Long id);
    List<Review> findByUserId(Long userId);
    @Transactional
    void deleteById(Long id);

    List<Review> findByBathroomId(int id);
}
