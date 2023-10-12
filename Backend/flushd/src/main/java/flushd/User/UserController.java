package flushd.User;

import java.util.*;

import flushd.Comment.Comment;
import flushd.Comment.CommentRepository;
import flushd.Review.Review;
import flushd.Review.ReviewRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import flushd.Job.*;
import flushd.Generic.ObjectResponse;

@Api(value = "user-controller", description = "REST APIs related to Users")
@RestController
@RequestMapping("/users")
public class UserController {

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    JobRepository jobRepository;


    @ApiOperation(value = "Get list of Users in the System ", response = Iterable.class, tags = "users")
    @GetMapping(path = "")
    public List<User> getAllUsers() { return userRepository.findAll(); }

    @ApiOperation(value = "Get specific of Users in the System by ID", response = User.class, tags = "users")
    @GetMapping(path = "/{id}")
    public User getUserById(@PathVariable Long id)
    {
        User result = userRepository.findById(id).get();

        if(result == null)
            return null;
        return result;
    }

    @ApiOperation(value = "Authorize a user by email/username and password", response = ObjectResponse.class, tags = "users")
    @GetMapping(path = "/login")
    public  Object findUserAndCheckPassword(@RequestParam("userid") Optional<String> userid,
                 @RequestParam("password") Optional<String> password) {
        ObjectResponse res = new ObjectResponse("user");

        // If params are missing, return with a message of "Missing Username or Password"
        res.setMessage("Missing Username or Password");
        if(userid.isPresent() && password.isPresent())
        {
            // Attempt to find user by username
            Optional<User> user = userRepository.findByUsername(userid.get());

            // Attempt to find user by email if username was unsuccessfull
            if(!user.isPresent())
                user = userRepository.findByEmail(userid.get());

            // If we couldn;t find the user in either case, return with a message of "Could not find user"
            res.setMessage("Could not find user " + userid.get());
            if(user.isPresent())
            {
                // If password is incorrect, return with a message of "Password incorrect"
                res.setMessage("Password incorrect");
                if(user.get().getPassword().equals(password.get()))
                {
                    // If passwords match, return with a message of "success" and user information
                    res.setMessage("success");
                    res.setObject(user.get());
                }
            }
        }
        return res;
    }

    @ApiOperation(value = "Create a new user", response = User.class, tags = "users")
    @PostMapping(path = "")
    public Object createUser(@RequestBody User user)
    {
        ObjectResponse res = new ObjectResponse("user");
        if(user == null || userRepository.findByUsername(user.getUsername()).isPresent())
        {
            res.setMessage("user exists");
            return res;
        }

        userRepository.save(user);
        res.setMessage("success");
        res.setObject(user);
        return res;
    }

    @ApiOperation(value = "Overwrite all params of a user besides ID", response = User.class, tags = "users")
    @PutMapping("/{id}")
    public User updateUser(@PathVariable long id, @RequestBody User request){
        User owner = userRepository.findById(id).get();
        if(owner == null)
            return null;
        request.setId(id);
        userRepository.save(request);
        return userRepository.findById(id).get();
    }

    @ApiOperation(value = "Delete a user by id", response = String.class, tags = "users")
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id)
    {
        if(userRepository.findById(id).get() == null)
            return failure;

        for (Comment c: commentRepository.findByUserId(id)) {
            commentRepository.deleteById(c.getId());
        }

        for (Review r: reviewRepository.findByUserId(id)) {
            reviewRepository.deleteById(r.getId());
        }

        for (Job j: jobRepository.findByOwnerId(id)) {
            jobRepository.deleteById(j.getId());
        }

        userRepository.deleteById(id);


        return success;
    }
}
