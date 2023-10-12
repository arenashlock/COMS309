package flushd.User;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "ID of User",name="id",required=false,value="1")
    private Long id;
    @ApiModelProperty(notes = "Username of User",name="username",required=true,value="username")
    private String username;
    @ApiModelProperty(notes = "Firstname of User",name="firstName",required=true,value="John")
    private String firstName;
    @ApiModelProperty(notes = "Lastname of User",name="lastName",required=true,value="Doe")
    private String lastName;
    @ApiModelProperty(notes = "Email of User",name="email",required=true,value="email@tag.com")
    private String email;
    @ApiModelProperty(notes = "Password of User",name="password",required=true,value="password123")
    private String password;
    @ApiModelProperty(notes = "Whether the User is active",name="isActive",required=true,value="true")
    private boolean isActive;
    /*
        Types:
            moderator
            maintenance
            user
     */
    @ApiModelProperty(notes = "Account Type of User",name="accountType",required=true,value="MODERATOR")
    private String accountType;

    public User() {
    }

    public User(String username, String firstName, String lastName, String email, String password, boolean isActive, String accountType) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.accountType = accountType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
