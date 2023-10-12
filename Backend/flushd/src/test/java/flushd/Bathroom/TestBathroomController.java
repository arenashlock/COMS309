package flushd.Bathroom;


import flushd.Bathroom.BathroomRepository;
import flushd.Bathroom.Bathroom;
import flushd.Bathroom.BathroomController;
import flushd.Comment.CommentRepository;
import flushd.Job.JobRepository;
import flushd.Job.Job;
import flushd.Review.Review;
import flushd.Review.ReviewRepository;
import flushd.User.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TestBathroomController {
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";


    @InjectMocks
    BathroomController bathroomController;

    @Mock
    BathroomRepository repo;

    @Mock
    JobRepository jobRepo;

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    CommentRepository commentRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getBathroomById() {
        Bathroom optionalBathroom = new Bathroom("Carver", 2, "Near Door", 3, 4, "male");
        when(repo.findById(1)).thenReturn(optionalBathroom);

        Bathroom bathroom = bathroomController.getBathroomById(1);
        
        assertEquals("Carver", bathroom.getBuilding());
        assertEquals(2, bathroom.getFloor());
        assertEquals("Near Door", bathroom.getLocDescription());
        assertEquals(3, bathroom.getNumStalls());
        assertEquals(4, bathroom.getNumUrinals());
        assertEquals("male", bathroom.getGender());
    }

    @Test
    public void createBathroom() {
        Bathroom optionalBathroom = new Bathroom("Carver", 2, "Near Door", 3, 4, "male");
        Bathroom bathroom = bathroomController.createBathroom(optionalBathroom);

        optionalBathroom.setId(1);

        verify(repo, times(1)).save(optionalBathroom);

        assertEquals(optionalBathroom, bathroom);
    }


    @Test
    public void getJobsFromBathroomId() {
        Bathroom optionalBathroom = new Bathroom("Carver", 2, "Near Door", 3, 4, "male");

        List<Job> jobList = new ArrayList<Job>();
        jobList.add(new Job());
        jobList.add(new Job());
        jobList.add(new Job());

        when(jobRepo.findByBathroomId(1)).thenReturn(jobList);

        List<Job> jobs = bathroomController.getJobsFromBathroomId(1);

        assertEquals(3, jobs.size());
    }

    @Test
    public void deleteBathroom() {
        Bathroom optionalBathroom = new Bathroom("Carver", 2, "Near Door", 3, 4, "male");
        optionalBathroom.setId(1);
        when(repo.findById(1)).thenReturn(optionalBathroom);

        List<Job> jobList = new ArrayList<Job>();
        Job job = new Job();
        job.setId(1);
        jobList.add(job);
        when(jobRepo.findByBathroomId(1)).thenReturn(jobList);
        
        List<Job> jobs = bathroomController.getJobsFromBathroomId(1);

        String returnString = bathroomController.deleteBathroom(1);

        verify(jobRepo, times(1)).deleteById(1);
        verify(repo, times(1)).deleteById(1);
        assertEquals(success, returnString);
    }

    @Test
    public void updateBathroom() {
        Bathroom optionalBathroom = new Bathroom("Carver", 2, "Near Door", 3, 4, "male");
        optionalBathroom.setId(1);

        when(repo.findById(1)).thenReturn(optionalBathroom);

        Bathroom newBathroom = new Bathroom("Hoover", 2, "Near Door", 3, 4, "male");

        Bathroom bathroom = bathroomController.updateBathroom(1, newBathroom);

        verify(repo, times(1)).save(bathroom);

        assertEquals("Hoover", bathroom.getBuilding());
        assertEquals(2, bathroom.getFloor());
        assertEquals("Near Door", bathroom.getLocDescription());
        assertEquals(3, bathroom.getNumStalls());
        assertEquals(4, bathroom.getNumUrinals());
        assertEquals("male", bathroom.getGender());
    }
}
