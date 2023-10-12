package flushd.Job;

import java.util.List;

import flushd.Bathroom.Bathroom;
import flushd.Bathroom.BathroomRepository;
import flushd.User.User;
import flushd.User.UserController;
import flushd.User.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import flushd.Job.*;

@Api(value = "job-controller", description = "REST APIs related to Users")
@RestController
@RequestMapping("/jobs")
public class JobController {

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";
    @Autowired
    JobRepository jobRepository;
    @Autowired
    BathroomRepository bathroomRepository;
    @Autowired
    UserRepository userRepository;
    @ApiOperation(value = "Get list of jobs in the System ", response = Iterable.class, tags = "jobs")
    @GetMapping(path = "")
    List<Job> getAllJobs() { return jobRepository.findAll(); }

    @ApiOperation(value = "Get specific job by id", response = Job.class, tags = "jobs")
    @GetMapping(path = "/{id}")
    Job getJobById(@PathVariable int id)
    {
        Job result = jobRepository.findById(id);
        if(result == null)
            return null;
        return result;
    }

    @ApiOperation(value = "Get specific jobs bathroom", response = Bathroom.class, tags = "jobs")
    @GetMapping(path = "/{id}/bathroom")
    Bathroom getBathroomByJobId(@PathVariable int id)
    {
        Job result = jobRepository.findById(id);
        if(result == null)
            return null;
        return result.getBathroom();
    }

    @ApiOperation(value = "Create a new Job", response = Job.class, tags = "jobs")
    @PostMapping(path = "")
    Job createJob(@RequestBody Job job)
    {
        if(job == null)
            return null;
        jobRepository.save(job);
        return job;
    }

    @ApiOperation(value = "Update job besides Id, Bathroom Foreign Key, and User Foreign Key", response = Job.class, tags = "jobs")
    @PutMapping("/{id}")
    Job updateJob(@PathVariable int id, @RequestBody Job request){
        Job job = jobRepository.findById(id);
        if(job == null)
            return null;

        request.setId(job.getId());
        request.setBathroom(job.getBathroom());
        request.setOwner(job.getOwner());

        jobRepository.save(request);
        return request;
    }

    @ApiOperation(value = "Link job to Bathroom Foreign Key", response = String.class, tags = "jobs")
    @PutMapping("/{id}/bathroom/{bathroomId}")
    String linkJobToBathroom(@PathVariable int id, @PathVariable int bathroomId)
    {
        Bathroom bathroom = bathroomRepository.findById(bathroomId);
        if(bathroom == null)
            return failure;
        Job job = jobRepository.findById(id);
        if(job == null)
            return failure;

        job.setBathroom(bathroom);
        jobRepository.save(job);
        return success;
    }

    @ApiOperation(value = "Link job to User Foreign Key", response = String.class, tags = "jobs")
    @PutMapping("/{id}/user/{ownerId}")
    String linkJobToUser(@PathVariable int id, @PathVariable long ownerId)
    {
        User user = userRepository.findById(ownerId).get();
        if(user == null)
            return failure;

        if(!user.getAccountType().equals("Maintenance"))
            return failure;

        Job job = jobRepository.findById(id);
        if(job == null)
            return failure;

        job.setOwner(user);
        jobRepository.save(job);
        return success;
    }

    @ApiOperation(value = "Delete a job by id", response = String.class, tags = "jobs")
    @DeleteMapping("/{id}")
    String deleteJob(@PathVariable int id)
    {
        if(jobRepository.findById(id) == null)
            return failure;
        jobRepository.deleteById(id);
        return success;
    }
}
