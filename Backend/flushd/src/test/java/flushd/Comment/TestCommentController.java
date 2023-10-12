package flushd.Comment;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import flushd.Review.Review;
import flushd.Review.ReviewRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import flushd.User.UserRepository;
import flushd.User.User;
import flushd.User.UserController;

import javax.swing.text.html.Option;

public class TestCommentController {
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @InjectMocks
    CommentController commentController;

    @Mock
    CommentRepository commentRepository;

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    UserRepository userRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCommentById() {
        Optional<Comment> optionalComment = Optional.of(new Comment("HELLO", "1/1/2023"));
        when(commentRepository.findById(1L)).thenReturn(optionalComment);

        Comment comment = commentController.getCommentByID(1L);


        assertEquals("HELLO", comment.getComment());
        assertEquals("1/1/2023", comment.getDate());

    }


    @Test
    public void getAllComments() {
        List<Comment> list = new ArrayList<Comment>();
        Comment testComment = new Comment("HELLO", "1/1/2023");

        list.add(testComment);
        list.add(testComment);
        list.add(testComment);

        when(commentRepository.findAll()).thenReturn(list);

        List<Comment> commentList = commentController.getAllComments();

        assertEquals(3, commentList.size());
        verify(commentRepository, times(1)).findAll();
    }

    @Test
    public void createComment() {
        Comment testComment = new Comment("HELLO", "1/1/2023");
        commentController.createComment(testComment);
        verify(commentRepository, times(1)).save(testComment);
    }
    @Test
    public void deleteComment() {
        Comment testComment = new Comment("HELLO", "1/1/2023");
        testComment.setId(1L);
        Optional<Comment> optionalComment = Optional.of(testComment);

        when(commentRepository.findById(1L)).thenReturn(optionalComment);

        String returnString = commentController.deleteComment(1L);

        verify(commentRepository, times(1)).deleteById(1L);
        assertEquals(success, returnString);
    }


    @Test
    public void linkCommentToUserAndReview() {
        Comment testComment = new Comment("HELLO", "1/1/2023");
        testComment.setId(1L);
        Optional<Comment> optionalComment = Optional.of(testComment);
        when(commentRepository.findById(1L)).thenReturn(optionalComment);

        User testUser = new User("jDoe", "John", "Doe", "jDoe@gmail.com", "password", true, "moderator");
        testUser.setId(1L);
        Optional<User> optionalUser = Optional.of(testUser);
        when(userRepository.findById(1L)).thenReturn(optionalUser);

        Review testReview = new Review(1,2,3,4, "content", "01/01/23");
        testReview.setId(1L);
        Optional<Review> optionalReview = Optional.of(testReview);
        when(reviewRepository.findById(1L)).thenReturn(optionalReview);

        String returnString = commentController.linkCommentToUserAndReview(1L, 1L, 1L);

        assertEquals(success, returnString);


    }

}
