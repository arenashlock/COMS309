package com.example.flushd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.flushd.utils.SHARED;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Aren - Page for creating a job
 */
public class CreateJob extends AppCompatActivity {
    /**
     * Button for cancelling the creation of a job
     */
    private Button Cancel;
    /**
     * Button for creating the job
     */
    private Button CreateJob;
    /**
     * EditText for obtaining the type of job
     */
    private Spinner type;
    /**
     * EditText for obtaining the severity of the job
     */
    private Spinner severity;
    /**
     * EditText for obtaining the description of the job
     */
    private EditText description;
    /**
     * textView for obtaining displaying the bathroom's info for which the user is making a job for
     */
    private TextView bathroomInfo;
    private ImageView bathroomPic;
    /**
     * Temporary string for storing the bathroom's building
     */
    private String building;
    /**
     * Temporary string for storing the floor the bathroom is on
     */
    private String floor;
    /**
     * Temporary string for storing the gender(s) allowed in the bathroom
     */
    private String gender;

    /**
     * Generating the design of the screen
     * @param savedInstanceState - the saved instance state for the screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);
        getSupportActionBar().hide();

        Cancel = (Button) findViewById(R.id.Cancel);
        CreateJob = (Button) findViewById(R.id.CreateJob);
        description = (EditText) findViewById(R.id.description);
        bathroomInfo = (TextView) findViewById(R.id.bathroomInfo);
        bathroomPic = (ImageView) findViewById(R.id.bathroomPic);

        // Making the dropdown menu for the type
        type = (Spinner) findViewById(R.id.type);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.issue, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        type.setAdapter(typeAdapter);

        // Making the dropdown menu for the severity
        severity = (Spinner) findViewById(R.id.severity);
        ArrayAdapter<CharSequence> severityAdapter = ArrayAdapter.createFromResource(this, R.array.severity, android.R.layout.simple_spinner_item);
        severityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        severity.setAdapter(severityAdapter);

        Intent getBathroomInfo = getIntent();
        building = getBathroomInfo.getStringExtra("building");
        floor = getBathroomInfo.getStringExtra("floor");
        gender = getBathroomInfo.getStringExtra("gender");

        bathroomInfo.setText("Building: " + building + "\nFloor: " + floor + ", Gender: " + gender);

        setBathroomImage();

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToBathroomView = new Intent(CreateJob.this, BathroomView.class);
                startActivity(switchToBathroomView);
            }
        });

        CreateJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateRequest();
            }
        });

    }

    /**
     * Method for constructing the JSONObject containing the job's information and completing a POST request
     */
    private void CreateRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject job = new JSONObject();

        try {
            job.put("type", type.getSelectedItem().toString());
            job.put("severity", severity.getSelectedItem().toString());
            job.put("description", description.getText().toString());
            job.put("status", "Unassigned");
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
            String date = sdf.format(new Date());
            job.put("datePosted", date);
        }
        catch (Exception E) {
            throw new RuntimeException();
        }

        JsonObjectRequest jobCreate = new JsonObjectRequest(Request.Method.POST, SHARED.getServerURL() + "/jobs", job,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            // If successful, link the review to the bathroom
                            linkToBathroom(response.getInt("id"));
                        }
                        catch (JSONException error){
                            // Nothing
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse (VolleyError error) {
                        //Nothing
                    }
                }
        );
        queue.add(jobCreate);
    }

    /**
     * Method for linking a job to a bathroom
     * @param reviewID - the ID of the job (returned after the POST request)
     */
    private void linkToBathroom(int reviewID){
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest linkJtoB = new JsonObjectRequest(Request.Method.PUT, SHARED.getServerURL() + "/jobs/" + String.valueOf(reviewID) + "/bathroom/" + String.valueOf(SHARED.getBathroomID()), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // If successful, go back to the bathroom
                Intent switchToBathroomView = new Intent(CreateJob.this, BathroomView.class);
                startActivity(switchToBathroomView);
            }
        }, new Response.ErrorListener(){
            public void onErrorResponse(VolleyError error){
                // Nothing
            }
        });
        queue.add(linkJtoB);
    }

    /**
     * Temporary method for displaying a bathroom's picture since we didn't set up the table to hold image URLs
     */
    public void setBathroomImage(){
        if(SHARED.getBathroomID() == 35){
            bathroomPic.setImageResource(R.drawable.atanasoff_1_mens);
        }
        else if(SHARED.getBathroomID() == 36){
            bathroomPic.setImageResource(R.drawable.carver_1_mens);
        }
        else if(SHARED.getBathroomID() == 37){
            bathroomPic.setImageResource(R.drawable.carver_1_womens);
        }
        else if(SHARED.getBathroomID() == 38){
            bathroomPic.setImageResource(R.drawable.hoover_1_mens);
        }
        else if(SHARED.getBathroomID() == 39){
            bathroomPic.setImageResource(R.drawable.howe_0_mens);
        }
        else if(SHARED.getBathroomID() == 40){
            bathroomPic.setImageResource(R.drawable.marston_2_mens);
        }
        else if(SHARED.getBathroomID() == 41){
            bathroomPic.setImageResource(R.drawable.mu_0_neutral);
        }
        else if(SHARED.getBathroomID() == 42){
            bathroomPic.setImageResource(R.drawable.mu_1_mens);
        }
        else if(SHARED.getBathroomID() == 43){
            bathroomPic.setImageResource(R.drawable.state_1_womens);
        }
        else if(SHARED.getBathroomID() == 44){
            bathroomPic.setImageResource(R.drawable.state_2_mens);
        }
        else{
            bathroomPic.setImageResource(R.drawable.general_bathroom);
        }
    }
}