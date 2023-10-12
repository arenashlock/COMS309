package flushd.Bathroom;

import io.swagger.annotations.ApiModelProperty;
import flushd.Job.Job;

import javax.persistence.*;
import java.util.List;

@Entity
public class Bathroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "ID of Bathroom",name="id",required=false,value="1")
    private int id;

    @ApiModelProperty(notes = "Building of Bathroom",name="building",required=true,value="building")
    private String building;

    @ApiModelProperty(notes = "Floor of Bathroom",name="floor",required=true,value="1")
    private int floor;

    @ApiModelProperty(notes = "Locational Description of Bathroom",name="locDescription",required=true,value="near entrance")
    private String locDescription;

    @ApiModelProperty(notes = "Number of stalls in Bathroom",name="numStalls",required=true,value="1")
    private int numStalls;

    @ApiModelProperty(notes = "Number of urinals in Bathroom",name="numUrinals",required=true,value="1")
    private int numUrinals;

    @ApiModelProperty(notes = "Gender of Bathroom",name="gender",required=true,value="male")
    private String gender;

    public Bathroom(String building, int floor, String locDescription, int numStalls, int numUrinals, String gender) {
        this.building = building;
        this.floor = floor;
        this.locDescription = locDescription;
        this.numStalls = numStalls;
        this.numUrinals = numUrinals;
        this.gender = gender;
    }

    public Bathroom() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getLocDescription() {
        return locDescription;
    }

    public void setLocDescription(String locDescription) {
        this.locDescription = locDescription;
    }

    public int getNumStalls() {
        return numStalls;
    }

    public void setNumStalls(int numStalls) {
        this.numStalls = numStalls;
    }

    public int getNumUrinals() {
        return numUrinals;
    }

    public void setNumUrinals(int numUrinals) {
        this.numUrinals = numUrinals;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
