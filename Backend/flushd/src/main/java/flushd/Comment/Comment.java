package flushd.Comment;

import flushd.Review.Review;
import flushd.User.User;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;


@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @ApiModelProperty(notes = "ID of comment", name = "id", required = false, value="1")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @ApiModelProperty(notes = "user of the comment should be linked", name = "user_id", required = false, value= "1")
    private User user;


    @ManyToOne
    @JoinColumn(name = "review_id")
    @ApiModelProperty(notes = "review of the comment should be linked", name = "review_id", required = false, value= "1")
    private Review review;


    @ApiModelProperty(notes = "comment made by user", name = "comment", required = true, value="comment")
    private String comment;

    @ApiModelProperty(notes = "date of the comment", name = "date", required = true, value="1/1/2023")
    private String date;
    public Comment() {

    }
    public Comment(String comment, String date) {
        this.comment = comment;
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }

    public void setReview(Review review) {this.review = review;}

    public Review getReview() { return review; }


}
