package flushd.Review;

import java.util.List;
import java.util.Optional;

import flushd.Bathroom.Bathroom;
import flushd.Bathroom.BathroomRepository;
import flushd.Comment.Comment;
import flushd.Comment.CommentRepository;
import flushd.User.User;
import flushd.User.UserRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import flushd.Job.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";


    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    BathroomRepository bathroomRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @ApiOperation(value = "Get a list of all review", response = Iterable.class, tags = "reviews")
    @GetMapping(path = "")
    public List<Review> getAllReviews() { return reviewRepository.findAll(); }

    @ApiOperation(value = "Get specific review", response = Review.class, tags = "reviews")
    @GetMapping(path = "/{id}")
    public Review getReviewById(@PathVariable Long id)
    {
        Optional<Review> result = reviewRepository.findById(id);
        if(!result.isPresent())
            return null;
        return result.get();
    }

    @ApiOperation(value = "Get bathroom of specific review", response = Bathroom.class, tags = "reviews")
    @GetMapping(path = "/{id}/bathroom")
    public Bathroom getBathroomByReviewId(@PathVariable Long id)
    {
        Optional<Review> result = reviewRepository.findById(id);
        if(!result.isPresent())
            return null;
        return result.get().getBathroom();
    }

    @ApiOperation(value = "Get list of comments for a review", response = Bathroom.class, tags = "reviews")
    @GetMapping(path = "/{id}/comments")
    public List<Comment> getCommentsById(@PathVariable Long id)
    {
        List<Comment> result = commentRepository.findByReviewId(id);
        return result;
    }

    @ApiOperation(value = "Create a new review", response = Review.class, tags = "reviews")
    @PostMapping(path = "")
    public Review createReview(@RequestBody Review review)
    {
        if(review == null)
            return null;
        reviewRepository.save(review);
        return review;
    }

    @ApiOperation(value = "Update a review besides Id, Bathroom Foreign Key, and User Foreign Key", response = Review.class, tags = "reviews")
    @PutMapping("/{id}")
    public Review updateReview(@PathVariable Long id, @RequestBody Review request){
        Optional<Review> review = reviewRepository.findById(id);
        if(review == null)
            return null;

        // These parameters are impossible to pass in through the above thus we must set them when converting
        request.setId(id);
        request.setBathroom(review.get().getBathroom());
        request.setUser(review.get().getUser());

        reviewRepository.save(request);
        return request;
    }

    @ApiOperation(value = "Link review to User Foreign Key", response = String.class, tags = "reviews")
    @PutMapping("/{id}/user/{userId}")
    public String linkUseToReview(@PathVariable Long id, @PathVariable Long userId)
    {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent())
            return failure;

        Optional<Review> review = reviewRepository.findById(id);
        if(!review.isPresent())
            return failure;

        review.get().setUser(user.get());
        reviewRepository.save(review.get());

        return success;
    }

    @ApiOperation(value = "Link review to Bathroom Foreign Key", response = String.class, tags = "reviews")
    @PutMapping("/{id}/bathroom/{bathroomId}")
    public String linkUseToReview(@PathVariable Long id, @PathVariable int bathroomId)
    {
        Bathroom bathroom = bathroomRepository.findById(bathroomId);
        if(bathroom == null)
            return failure;

        Optional<Review> review = reviewRepository.findById(id);
        if(!review.isPresent())
            return failure;

        review.get().setBathroom(bathroom);
        reviewRepository.save(review.get());

        return success;
    }
    @ApiOperation(value = "Delete a review by id", response = String.class, tags = "reviews")
    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable Long id)
    {
        if(!reviewRepository.findById(id).isPresent())
            return failure;

        reviewRepository.deleteById(id);
        return success;
    }
}
