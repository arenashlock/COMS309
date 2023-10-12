package flushd.Job;

import flushd.Comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long>{
    Job findById(int id);
    List<Job> findByOwnerId(Long ownerId);
    @Transactional
    void deleteById(int id);

    List<Job> findByBathroomId(int id);
}
