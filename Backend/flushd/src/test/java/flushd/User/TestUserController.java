package flushd.User;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import flushd.Comment.CommentRepository;
import flushd.Job.JobRepository;
import flushd.Review.ReviewRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import flushd.User.UserRepository;
import flushd.User.User;
import flushd.User.UserController;

import flushd.Generic.ObjectResponse;

public class TestUserController {

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @InjectMocks
    UserController userController;

    @Mock
    UserRepository repo;


    @Mock
    CommentRepository commentRepository;

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    JobRepository jobRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUserById() {
        Optional<User> optionalUser = Optional.of(new User("jDoe", "John", "Doe", "jDoe@gmail.com", "password", true, "moderator"));
        when(repo.findById(new Long(1))).thenReturn(optionalUser);

        User user = userController.getUserById(new Long(1));

        assertEquals("jDoe", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("jDoe@gmail.com", user.getEmail());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals(true, user.isActive());
        assertEquals("moderator", user.getAccountType());
    }

    @Test
    public void getAllUsers() {
        List<User> list = new ArrayList<User>();
        User testUser = new User("jDoe1", "John1", "Doe1", "jDoe@gmail.com1", "password1", true, "moderator1");

        list.add(testUser);
        list.add(testUser);
        list.add(testUser);

        when(repo.findAll()).thenReturn(list);

        List<User> userList = userController.getAllUsers();

        assertEquals(3, userList.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    public void createUser() {
        User testUser = new User("jDoe1", "John1", "Doe1", "jDoe@gmail.com1", "password1", true, "moderator1");
        userController.createUser(testUser);
        verify(repo, times(1)).save(testUser);
    }

    @Test
    public void updateUser() {
        User testUser = new User("jDoe1", "John1", "Doe1", "jDoe@gmail.com1", "password1", true, "moderator1");
        testUser.setId(new Long(1));
        Optional<User> optionalUser = Optional.of(testUser);
        when(repo.findById(new Long(1))).thenReturn(optionalUser);

        testUser.setUsername("new value");

        User returnUser = userController.updateUser(new Long(1), testUser);

        verify(repo, times(1)).save(testUser);
        assertEquals("new value", returnUser.getUsername());
    }

    @Test
    public void deleteUser() {
        User testUser = new User("jDoe1", "John1", "Doe1", "jDoe@gmail.com1", "password1", true, "moderator1");
        testUser.setId(new Long(1));
        Optional<User> optionalUser = Optional.of(testUser);

        when(repo.findById(new Long(1))).thenReturn(optionalUser);

        String returnString = userController.deleteUser(new Long(1));

        verify(repo, times(1)).deleteById(new Long(1));
        assertEquals(success, returnString);
    }

    @Test
    public void findUserAndCheckPassword() {
        User testUser = new User("jDoe", "John", "Doe", "jDoe@gmail.com", "password", true, "moderator");
        testUser.setId(new Long(1));
        Optional<User> optionalUser = Optional.of(testUser);

        when(repo.findByUsername("jDoe")).thenReturn(optionalUser);

        ObjectResponse response = (ObjectResponse) userController.findUserAndCheckPassword(Optional.of("jDoe"), Optional.of("password"));

        assertEquals("success", response.getMessage());
        assertEquals(testUser, (User)response.getObject());
    }

    @Test
    public void findUserAndCheckPassword_InvalidPassword() {
        User testUser = new User("jDoe", "John", "Doe", "jDoe@gmail.com", "password", true, "moderator");
        testUser.setId(new Long(1));
        Optional<User> optionalUser = Optional.of(testUser);

        when(repo.findByUsername("jDoe")).thenReturn(optionalUser);

        ObjectResponse response = (ObjectResponse) userController.findUserAndCheckPassword(Optional.of("jDoe"), Optional.of("invalid"));

        assertEquals("Password incorrect", response.getMessage());
        assertEquals(null, response.getObject());
    }

    @Test
    public void findUserAndCheckPassword_UserNotFound() {
        Optional<User> optionalUser = Optional.empty();
        when(repo.findByUsername("jDoe")).thenReturn(optionalUser);

        ObjectResponse response = (ObjectResponse) userController.findUserAndCheckPassword(Optional.of("jDoe"), Optional.of("password"));

        assertEquals("Could not find user jDoe", response.getMessage());
        assertEquals(null, response.getObject());
    }
}

