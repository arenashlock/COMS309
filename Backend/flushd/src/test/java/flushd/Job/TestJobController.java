package flushd.Job;


import flushd.Bathroom.Bathroom;
import flushd.Bathroom.BathroomRepository;
import flushd.Comment.CommentRepository;
import flushd.Review.Review;
import flushd.Review.ReviewRepository;
import flushd.User.User;
import flushd.User.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TestJobController {
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";


    @InjectMocks
    JobController jobController;

    @Mock
    BathroomRepository bathroomRepo;

    @Mock
    UserRepository userRepo;

    @Mock
    JobRepository repo;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getJobById() {
        Job job = new Job("plumbing", "urgent", "test description", "In-Progress", "test");
        job.setId(1);
        job.setOwner(new User());
        job.getOwner().setUsername("testOwner");
        when(repo.findById(1)).thenReturn(job);

        Job returnJob = jobController.getJobById(1);

        assertEquals(1, returnJob.getId());
        assertEquals("plumbing", returnJob.getType());
        assertEquals("urgent", returnJob.getSeverity());
        assertEquals("test description", returnJob.getDescription());
        assertEquals("In-Progress", returnJob.getStatus());
        assertEquals("test", returnJob.getDatePosted());
        assertEquals("testOwner", returnJob.getOwner().getUsername());
    }

    @Test
    public void getBathroomByJobId() {
        Job job = new Job("plumbing", "urgent", "test description", "In-Progress", "test");
        job.setId(1);

        Bathroom bathroom = new Bathroom("Carver", 2, "Near Door", 3, 4, "male");

        job.setBathroom(bathroom);
        when(repo.findById(1)).thenReturn(job);

        Bathroom returnBathroom = jobController.getBathroomByJobId(1);

        assertEquals(bathroom, returnBathroom);
    }

    @Test
    public void createJob() {
        Job job = new Job("plumbing", "urgent", "test description", "In-Progress", "test");
        job.setId(1);
        jobController.createJob(job);
        verify(repo, times(1)).save(job);
    }

    @Test
    public void updateJob() {
        Job job = new Job("plumbing", "urgent", "test description", "In-Progress", "test");
        User testUser = new User("jDoe1", "John1", "Doe1", "jDoe@gmail.com1", "password1", true, "moderator1");
        Bathroom testBathroom = new Bathroom("coover", 1, "near", 2, 3, "female");

        job.setId(1);
        job.setOwner(testUser);
        job.setBathroom(testBathroom);

        when(repo.findById(1)).thenReturn(job);

        Job newJob = new Job("electrical", "urgent", "test description", "In-Progress", "test");

        Job returnJob = jobController.updateJob(1, newJob);

        verify(repo, times(1)).save(returnJob);

        assertEquals(1, returnJob.getId());
        assertEquals("electrical", returnJob.getType());
        assertEquals("urgent", returnJob.getSeverity());
        assertEquals("test description", returnJob.getDescription());
        assertEquals("In-Progress", returnJob.getStatus());
        assertEquals("test", returnJob.getDatePosted());
        assertEquals("jDoe1", returnJob.getOwner().getUsername());
    }

    @Test
    public void linkJobToBathroom() {
        Job job = new Job("plumbing", "urgent", "test description", "In-Progress", "test");
        job.setId(1);
        Bathroom testBathroom = new Bathroom("coover", 1, "near", 2, 3, "female");

        when(bathroomRepo.findById(1)).thenReturn(testBathroom);
        when(repo.findById(1)).thenReturn(job);

        jobController.linkJobToBathroom(1, 1);

        job.setBathroom(testBathroom);

        verify(repo, times(1)).save(job);
    }

    @Test
    public void linkJobToUser() {
        Job job = new Job("plumbing", "urgent", "test description", "In-Progress", "test");
        job.setId(1);

        User testUser = new User("jDoe1", "John1", "Doe1", "jDoe@gmail.com1", "password1", true, "Maintenance");
        testUser.setId(new Long(1));
        Optional<User> optionalUser = Optional.of(testUser);

        when(userRepo.findById(new Long(1))).thenReturn(optionalUser);
        when(repo.findById(1)).thenReturn(job);

        jobController.linkJobToUser(1, 1);

        job.setOwner(testUser);

        verify(repo, times(1)).save(job);
    }

    @Test
    public void deleteJob() {
        Job job = new Job("plumbing", "urgent", "test description", "In-Progress", "test");
        job.setId(1);

        when(repo.findById(1)).thenReturn(job);

        String returnString = jobController.deleteJob(1);

        verify(repo, times(1)).deleteById(1);
        assertEquals(success, returnString);
    }

}
