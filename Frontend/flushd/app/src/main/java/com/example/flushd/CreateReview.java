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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.flushd.utils.SHARED;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Jack - CreateReview class allows the user to create a review for an individual bathroom
 */
public class CreateReview extends AppCompatActivity {
    // Elements that appear on our screen
    private Button Cancel, PostReview;
    private Spinner cleanlinessRating, smellRating, privacyRating, accessibilityRating;
    private TextView bathroomInfo;
    private EditText content;
    private String building, floor, gender;
    private ImageView bathroomPic;

    /**
     * Basic onCreate method that connects button variables to their IDs and sets some text
     * @param savedInstanceState - basic savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_review);
        getSupportActionBar().hide();

        Cancel = (Button) findViewById(R.id.Cancel);
        PostReview = (Button) findViewById(R.id.PostReview);
        bathroomInfo = (TextView) findViewById(R.id.bathroomInfo);
        content = (EditText) findViewById(R.id.content);
        bathroomPic = (ImageView) findViewById(R.id.bathroomPic);

        // Making the dropdown menu for the cleanlinessRating
        cleanlinessRating = (Spinner) findViewById(R.id.cleanlinessRating);
        ArrayAdapter<CharSequence> cleanlinessAdapter = ArrayAdapter.createFromResource(this, R.array.ratings, android.R.layout.simple_spinner_item);
        cleanlinessAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        cleanlinessRating.setAdapter(cleanlinessAdapter);

        // Making the dropdown menu for the smellRating
        smellRating = (Spinner) findViewById(R.id.smellRating);
        ArrayAdapter<CharSequence> smellAdapter = ArrayAdapter.createFromResource(this, R.array.ratings, android.R.layout.simple_spinner_item);
        smellAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        smellRating.setAdapter(smellAdapter);

        // Making the dropdown menu for the privacyRating
        privacyRating = (Spinner) findViewById(R.id.privacyRating);
        ArrayAdapter<CharSequence> privacyAdapter = ArrayAdapter.createFromResource(this, R.array.ratings, android.R.layout.simple_spinner_item);
        privacyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        privacyRating.setAdapter(privacyAdapter);

        // Making the dropdown menu for the accessibilityRating
        accessibilityRating = (Spinner) findViewById(R.id.accessibilityRating);
        ArrayAdapter<CharSequence> accessibilityAdapter = ArrayAdapter.createFromResource(this, R.array.ratings, android.R.layout.simple_spinner_item);
        accessibilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        accessibilityRating.setAdapter(accessibilityAdapter);

        Intent getBathroomInfo = getIntent();
        building = getBathroomInfo.getStringExtra("building");
        floor = getBathroomInfo.getStringExtra("floor");
        gender = getBathroomInfo.getStringExtra("gender");

        bathroomInfo.setText("Building: " + building + "\nFloor: " + floor + ", Gender: " + gender);

        setBathroomImage();

        Cancel.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick method that switches the activity to the bathroom view and starts the activity
             * @param view - basic view parameter
             */
            @Override
            public void onClick(View view) {
                Intent switchToBathroomView = new Intent(CreateReview.this, BathroomView.class);
                startActivity(switchToBathroomView);
            }
        });

        // POST a new review to the database
        PostReview.setOnClickListener(new View.OnClickListener() {
            /**
             * simple onClick method that calls helper method postRequest()
             */
            @Override
            public void onClick(View view) {
                postRequest();
            }
        });
    }

    /**
     * postRequest() method establishes a JSON request to the backend
     */
    private void postRequest(){
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject reviewInfo = new JSONObject();

        // Building the JSONObject
        try {
            reviewInfo.put("cleanlinessRating", Integer.valueOf(cleanlinessRating.getSelectedItem().toString()));
            reviewInfo.put("smellRating", Integer.valueOf(smellRating.getSelectedItem().toString()));
            reviewInfo.put("privacyRating", Integer.valueOf(privacyRating.getSelectedItem().toString()));
            reviewInfo.put("accessibilityRating", Integer.valueOf(accessibilityRating.getSelectedItem().toString()));
            reviewInfo.put("content", content.getText().toString());
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
            String date = sdf.format(new Date());
            reviewInfo.put("datePosted", date);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest bathroomReviewReq = new JsonObjectRequest(Request.Method.POST, SHARED.getServerURL() + "/reviews", reviewInfo,
                new Response.Listener<JSONObject>() {
                    /**
                     * basic onResponse() method that links a bathroom to an ID upon JSON response
                     * @param response - response parameter and acts as a JSONObject response
                     */
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // If successful, link the review to the bathroom
                            linkToBathroom(response.getInt("id"));
                        }
                        catch (JSONException error){
                            // Nothing
                        }
                    }
                },
                new Response.ErrorListener() {
                    /**
                     * Does nothing
                     * @param error - parameter for potentially any error
                     */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Nothing
                    }
                }
        );
        queue.add(bathroomReviewReq);
    }

    /**
     * Helper method that links a review to a specific bathroom ID
     * @param reviewID - the ID to be linked
     */
    private void linkToBathroom(int reviewID){
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest linkRtoB = new JsonObjectRequest(Request.Method.PUT, SHARED.getServerURL() + "/reviews/" + String.valueOf(reviewID) + "/bathroom/" + String.valueOf(SHARED.getBathroomID()), null, new Response.Listener<JSONObject>() {
            /**
             * basic onResponse() method that switches activity to the Bathroom view activity when given the response parameter
             * @param response - basic JSONObject response
             */
            @Override
            public void onResponse(JSONObject response) {
                // If successful, link the review to the user
                linkToUser(reviewID);
            }
        }, new Response.ErrorListener(){
            /**
             * Does nothing
             * @param error - parameter for potentially any error
             */
            public void onErrorResponse(VolleyError error){
                // Nothing
            }
        });
        queue.add(linkRtoB);
    }

    /**
     * Helper method that links a review to a specific user ID
     * @param reviewID - the ID to be linked
     */
    private void linkToUser(int reviewID){
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest linkRtoU = new JsonObjectRequest(Request.Method.PUT, SHARED.getServerURL() + "/reviews/" + String.valueOf(reviewID) + "/user/" + String.valueOf(SHARED.getUserID()), null, new Response.Listener<JSONObject>() {
            /**
             * basic onResponse() method that switches activity to the Bathroom view activity when given the response parameter
             * @param response - basic JSONObject response
             */
            @Override
            public void onResponse(JSONObject response) {
                // If successful, go back to the bathroom page you were viewing
                Intent switchToReviewedBathroom = new Intent(CreateReview.this, BathroomView.class);
                startActivity(switchToReviewedBathroom);
            }
        }, new Response.ErrorListener(){
            /**
             * Does nothing
             * @param error - parameter for potentially any error
             */
            public void onErrorResponse(VolleyError error){
                // Nothing
            }
        });
        queue.add(linkRtoU);
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