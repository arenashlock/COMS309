package flushd.Job;

import com.fasterxml.jackson.annotation.JsonIgnore;
import flushd.Bathroom.Bathroom;
import flushd.User.User;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class Job {
    @ApiModelProperty(notes = "Id of Job",name="id",required=false,value="1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ApiModelProperty(notes = "Id of bathroom for job, should be linked",name="bathroom_id",required=false,value="1")
    @ManyToOne
    @JoinColumn(name = "bathroom_id")
    Bathroom bathroom;

    @ApiModelProperty(notes = "Id of job owner, should be linked",name="owner_id",required=false,value="1")
    @ManyToOne
    @JoinColumn(name="owner_id")
    User owner;

    @ApiModelProperty(notes = "Type of job",name="type",required=true,value="plumbing")
    private String type;
    @ApiModelProperty(notes = "Severity of the job",name="severity",required=true,value="plumbing")
    private String severity;
    @ApiModelProperty(notes = "The job description",name="description",required=true,value="Toliet overflowing")
    private String description;

    @ApiModelProperty(notes = "Status of the job",name="status",required=true,value="InProgress")
    private String status;
    @ApiModelProperty(notes = "Date Posted",name="datePosted",required=true,value="02/02/2002")
    private String datePosted;

    public Job(String type, String severity, String description, String status, String dataPosted) {
        this.type = type;
        this.severity = severity;
        this.description = description;
        this.status = status;
        this.datePosted = dataPosted;
        this.bathroom = null;
        this.owner = null;
    }

    public Job() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bathroom getBathroom() {
        return bathroom;
    }

    public void setBathroom(Bathroom bathroom) {
        this.bathroom = bathroom;
    }

    public User getOwner() {return owner;}

    public void setOwner(User owner) {this.owner = owner;}
    public void removeBathroom() {this.bathroom = null;}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", bathroom=" + bathroom +
                ", type='" + type + '\'' +
                ", severity='" + severity + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", datePosted='" + datePosted + '\'' +
                '}';
    }
}
