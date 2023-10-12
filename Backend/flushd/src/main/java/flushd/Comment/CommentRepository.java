package flushd.Comment;

import flushd.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findById(Long id);
    List<Comment> findByUserId(Long userId);

    List<Comment> findByReviewId(Long reviewId);

    @Transactional
    void deleteById(Long id);
}
