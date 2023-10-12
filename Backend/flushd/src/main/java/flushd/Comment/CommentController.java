package flushd.Comment;
import java.util.List;

import flushd.Bathroom.Bathroom;
import flushd.Bathroom.BathroomRepository;
import flushd.Comment.CommentRepository;
import flushd.Job.Job;
import flushd.Job.JobRepository;
import flushd.Review.Review;
import flushd.Review.ReviewController;
import flushd.Review.ReviewRepository;
import flushd.User.User;
import flushd.User.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "comment-controller", description = "REST APIs related to Comments")
@RestController
@RequestMapping("/comments")
public class CommentController {
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @Autowired
    CommentRepository commentRepo;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewRepository reviewRepository;


    @ApiOperation(value = "Get list of comments in the System ", response = Iterable.class, tags = "comments")
    @GetMapping(path = "")
    public List<Comment> getAllComments() {
        return commentRepo.findAll();
    }

    @ApiOperation(value = "Get specific of comment in the System by ID", response = Comment.class, tags = "comments")
    @GetMapping(path = "/{id}")
    public Comment getCommentByID(@PathVariable long id) {
        Comment comment = commentRepo.findById(id).get();
        if (comment == null) {
            return null;
        } return comment;
    }

    @ApiOperation(value = "Create a new comment", response = Comment.class, tags = "comments")
    @PostMapping(path = "")
    public Comment createComment(@RequestBody Comment comment)
    {
        if(comment == null)
            return null;
        commentRepo.save(comment);
        return comment;
    }

    @ApiOperation(value = "Delete a comment by id", response = String.class, tags = "comments")
    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable Long id) {
        if (commentRepo.findById(id).get() == null) {
            return failure;
        } else {
            commentRepo.deleteById(id);
            return success;
        }
    }


    @ApiOperation(value = "Link a comment to a user", response = String.class, tags = "comments")
    @PutMapping("/{id}/user/{userID}/review/{reviewID}")
    String linkCommentToUserAndReview(@PathVariable Long id, @PathVariable Long userID, @PathVariable Long reviewID)
    {
        User user = userRepository.findById(userID).get();

        if(user == null)
            return failure;
        Comment comment = commentRepo.findById(id).get();
        if(comment == null)
            return failure;

        Review review = reviewRepository.findById(reviewID).get();

        if (review == null) return failure;

        comment.setReview(review);
        comment.setUser(user);
        commentRepo.save(comment);
        return success;

    }
}
