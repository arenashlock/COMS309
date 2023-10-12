package flushd.Bathroom;

import flushd.Comment.Comment;
import flushd.Comment.CommentRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import flushd.Job.Job;
import flushd.Job.JobRepository;
import flushd.Review.Review;
import flushd.Review.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import flushd.Bathroom.*;

import java.util.List;

@Api(value = "bathroom-controller", description = "REST APIs related to Bathrooms")
@RestController
@RequestMapping("/bathrooms")
public class BathroomController {
    @Autowired
    BathroomRepository bathroomRepository;
    @Autowired
    JobRepository jobRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    CommentRepository commentRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @ApiOperation(value = "Get list of Bathrooms in the System ", response = Iterable.class, tags = "bathrooms")
    @GetMapping(path = "")
    List<Bathroom> getAllBathrooms() {
        return bathroomRepository.findAll();
    }

    @ApiOperation(value = "Create a new bathroom", response = Bathroom.class, tags = "bathrooms")
    @PostMapping(path = "")
    Bathroom createBathroom(@RequestBody Bathroom bathroom){
        if (bathroom == null)
            return null;
        bathroomRepository.save(bathroom);
        return bathroom;
    }
    @ApiOperation(value = "Get specific of Bathroom in the System by ID", response = Bathroom.class, tags = "bathrooms")
    @GetMapping(path = "/{id}")
    Bathroom getBathroomById(@PathVariable int id) {
        return bathroomRepository.findById(id);
    }

    @ApiOperation(value = "Get list of jobs by bathroom ID", response = Iterable.class, tags = "bathrooms")
    @GetMapping(path = "/{id}/jobs")
    List<Job> getJobsFromBathroomId(@PathVariable int id) {
        return jobRepository.findByBathroomId(id);
    }

    @ApiOperation(value = "Get list of reveiws by bathroom ID", response = Iterable.class, tags = "bathrooms")
    @GetMapping(path = "/{id}/reviews")
    List<Review> getAllReviewsFromBathroomId(@PathVariable int id) {
        return reviewRepository.findByBathroomId(id);
    }

    @ApiOperation(value = "Deletes a job by using the bathroom id", response = String.class, tags = "bathrooms")
    @DeleteMapping(path = "/{id}")
    String deleteBathroom(@PathVariable int id) {
        if(bathroomRepository.findById(id) == null)
            return failure;
        for(Job job : jobRepository.findByBathroomId(id))
        {
            jobRepository.deleteById(job.getId());
        }
        for (Review review: reviewRepository.findByBathroomId(id)) {
            List<Comment> list = commentRepository.findByReviewId(review.getId());
            for (int i = 0; i < list.size(); i++) {
                commentRepository.deleteById(list.get(i).getId());
            }
            reviewRepository.deleteById(review.getId());
        }

        bathroomRepository.deleteById(id);
        return success;
    }

    @ApiOperation(value = "Overwrite all params of a bathroom besides ID", response = Bathroom.class, tags = "bathrooms")
    @PutMapping(path = "/{id}")
    Bathroom updateBathroom(@PathVariable int id, @RequestBody Bathroom request){
        Bathroom bathroom = bathroomRepository.findById(id);
        if(bathroom == null)
            return null;

        request.setId(bathroom.getId());
        bathroomRepository.save(request);
        return request;
    }
}
