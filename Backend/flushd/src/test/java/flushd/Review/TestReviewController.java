package flushd.Review;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import flushd.User.UserRepository;
import flushd.User.User;

import flushd.Bathroom.BathroomRepository;
import flushd.Bathroom.Bathroom;

import flushd.Review.ReviewRepository;
import flushd.Review.Review;
import flushd.Review.ReviewController;

public class TestReviewController {

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @InjectMocks
    ReviewController reviewController;

    @Mock
    ReviewRepository repo;

    @Mock
    BathroomRepository bathroomRepo;

    @Mock
    UserRepository userRepo;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getReviewById() {
        Optional<Review> optionalReview = Optional.of(new Review(1,2,3,4, "content", "01/01/23"));
        when(repo.findById(new Long(1))).thenReturn(optionalReview);

        Review review = reviewController.getReviewById(new Long(1));

        assertEquals(1, review.getCleanlinessRating());
        assertEquals(2, review.getSmellRating());
        assertEquals(3, review.getPrivacyRating());
        assertEquals(4, review.getAccessibilityRating());
        assertEquals("content", review.getContent());
        assertEquals("01/01/23", review.getDatePosted());
    }

    @Test
    public void getAllReveiws() {
        List<Review> list = new ArrayList<Review>();
        Review testReview = new Review(1,2,3,4, "content", "01/01/23");

        list.add(testReview);
        list.add(testReview);
        list.add(testReview);

        when(repo.findAll()).thenReturn(list);

        List<Review> reviewList = reviewController.getAllReviews();

        assertEquals(3, reviewList.size());
        assertEquals(testReview, reviewList.get(0));
        verify(repo, times(1)).findAll();
    }

    @Test
    public void createReview() {
        Review testUser = new Review(1,2,3,4, "content", "01/01/23");
        reviewController.createReview(testUser);
        verify(repo, times(1)).save(testUser);
    }

    @Test
    public void updateReview() {
        Review testReview = new Review(1,2,3,4, "content", "01/01/23");
        User testUser = new User("jDoe1", "John1", "Doe1", "jDoe@gmail.com1", "password1", true, "moderator1");
        Bathroom testBathroom = new Bathroom("coover", 1, "near", 2, 3, "female");

        testReview.setId(new Long(1));
        testReview.setUser(testUser);
        testReview.setBathroom(testBathroom);

        when(repo.findById(new Long(1))).thenReturn(Optional.of(testReview));

        Review newReview = new Review(5,6,7,8, "new content", "02/01/23");

        Review returnReview = reviewController.updateReview(new Long(1), newReview);

        verify(repo, times(1)).save(returnReview);
        assertEquals(new Long(1), returnReview.getId());
        assertEquals(testUser, returnReview.getUser());
        assertEquals(testBathroom, returnReview.getBathroom());
        assertEquals(5, returnReview.getCleanlinessRating());
        assertEquals(6, returnReview.getSmellRating());
        assertEquals(7, returnReview.getPrivacyRating());
        assertEquals(8, returnReview.getAccessibilityRating());
        assertEquals("new content", returnReview.getContent());
        assertEquals("02/01/23", returnReview.getDatePosted());
    }

    @Test
    public void deleteReview() {
        Optional<Review> optionalReview = Optional.of(new Review(1,2,3,4, "content", "01/01/23"));
        optionalReview.get().setId(new Long(1));

        when(repo.findById(new Long(1))).thenReturn(optionalReview);

        String returnString = reviewController.deleteReview(new Long(1));

        verify(repo, times(1)).deleteById(new Long(1));
        assertEquals(success, returnString);
    }



}

