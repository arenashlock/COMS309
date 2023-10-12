package flushd.Review;

import flushd.Bathroom.Bathroom;
import flushd.User.User;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class Review {
    @ApiModelProperty(notes = "review id",name="id",required=false,value="1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ApiModelProperty(notes = "user foreign key, needs to be linked",name="user_id",required=false,value="1")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ApiModelProperty(notes = "bathroom foreign key, needs to be linked",name="bathroom_id",required=false,value="1")
    @ManyToOne
    @JoinColumn(name = "bathroom_id")
    private Bathroom bathroom;
    @ApiModelProperty(notes = "Cleanliness Rating of for the review",name="cleanlinessRating",required=true,value="5")
    private int cleanlinessRating;
    @ApiModelProperty(notes = "Smell Rating of for the review",name="smellRating",required=true,value="5")
    private int smellRating;
    @ApiModelProperty(notes = "Privacy Rating of for the review",name="privacyRating",required=true,value="5")
    private int privacyRating;
    @ApiModelProperty(notes = "Accessibility Rating of for the review",name="accessibilityRating",required=true,value="5")
    private int accessibilityRating;
    @ApiModelProperty(notes = "content written by the user",name="content",required=true,value="Bathroom very smelly")
    private String content;
    @ApiModelProperty(notes = "Date posted",name="datePosted",required=true,value="02/02/02")
    private String datePosted;

    public Review() {}

    public Review(int cleanlinessRating, int smellRating, int privacyRating, int accessibilityRating, String content, String datePosted) {
        this.cleanlinessRating = cleanlinessRating;
        this.smellRating = smellRating;
        this.privacyRating = privacyRating;
        this.accessibilityRating = accessibilityRating;
        this.content = content;
        this.datePosted = datePosted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bathroom getBathroom() {
        return bathroom;
    }

    public void setBathroom(Bathroom bathroom) {
        this.bathroom = bathroom;
    }

    public int getCleanlinessRating() {
        return cleanlinessRating;
    }

    public void setCleanlinessRating(int cleanlinessRating) {
        this.cleanlinessRating = cleanlinessRating;
    }

    public int getSmellRating() {
        return smellRating;
    }

    public void setSmellRating(int smellRating) {
        this.smellRating = smellRating;
    }

    public int getPrivacyRating() {
        return privacyRating;
    }

    public void setPrivacyRating(int privacyRating) {
        this.privacyRating = privacyRating;
    }

    public int getAccessibilityRating() {
        return accessibilityRating;
    }

    public void setAccessibilityRating(int accessibilityRating) {
        this.accessibilityRating = accessibilityRating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }
}
