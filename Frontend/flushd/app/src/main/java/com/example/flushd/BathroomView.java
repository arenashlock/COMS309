package com.example.flushd;

import static com.example.flushd.utils.SHARED.getAccountType;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.flushd.utils.SHARED;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Aren - Page for viewing an individual bathroom's information and reviews
 */
public class BathroomView extends AppCompatActivity implements AdapterReview.ItemClickListener {

    /**
     * Button for switching to the Bathroom List screen
     */
    protected Button BathroomList;
    /**
     * Button for switching to the Create a Job screen
     */
    protected Button CreateJob;
    /**
     * Button for switching to the Create a Review screen
     */
    protected Button CreateReview;
    /**
     * Button for switching to the Chat screen
     */
    protected Button BathroomChat;
    /**
     * TextView displaying the main information of the bathroom
     */
    protected TextView mainInfo;
    /**
     * TextView displaying the bathroom's information regarding urinals and stalls
     */
    protected TextView toiletryInfo;
    /**
     * TextView displaying the locational description of the bathroom
     */
    protected TextView locDescription;
    /**
     * Temporary string for storing the building name of the bathroom
     */
    protected String building;
    /**
     * Temporary string for storing the floor the bathroom is on
     */
    protected String floor;
    /**
     * Temporary string for storing the gender(s) allowed in the bathroom
     */
    protected String gender;

    /**
     * Adapter for the reviews RecyclerView
     */
    // Adapter for RecyclerView
    protected AdapterReview adapter;

    protected ImageView bathroomPic;

    /**
     * Generating the design of the screen
     * @param savedInstanceState - the saved instance state for the screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bathroom_view);
        getSupportActionBar().hide();

        // Set elements to their XML elements
        BathroomList = (Button) findViewById(R.id.BathroomList);
        CreateJob = (Button) findViewById(R.id.CreateJob);
        mainInfo = (TextView) findViewById(R.id.mainInfo);
        toiletryInfo = (TextView) findViewById(R.id.toiletryInfo);
        locDescription = (TextView) findViewById(R.id.locDescription);
        CreateReview = (Button) findViewById(R.id.CreateReview);
        BathroomChat = (Button) findViewById(R.id.BathroomChat);
        bathroomPic = (ImageView) findViewById(R.id.bathroomPic);

        // Generate the desired bathroom screen
        bathroomView();
        setBathroomImage();

        // Hides certain buttons for certain account types
        if(getAccountType().equals("Maintenance")) {
            CreateReview.setVisibility(View.INVISIBLE);
            CreateJob.setVisibility(View.INVISIBLE);
        }

        else{
            reviewList();
        }

        // Switch to the "WebSockets" screen
        BathroomChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToChat = new Intent(BathroomView.this, WebSockets.class);
                startActivity(switchToChat);
            }
        });

        // Switch to the "Bathroom List" screen
        BathroomList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToList = new Intent(BathroomView.this, BathroomList.class);
                startActivity(switchToList);
            }
        });

        // Switch to the "Create a Job" screen
        CreateJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToCreateJob = new Intent(BathroomView.this, CreateJob.class);
                switchToCreateJob.putExtra("building", building);
                switchToCreateJob.putExtra("floor", floor);
                switchToCreateJob.putExtra("gender", gender);
                startActivity(switchToCreateJob);
            }
        });

        CreateReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToCreateReview = new Intent(BathroomView.this, CreateReview.class);
                switchToCreateReview.putExtra("building", building);
                switchToCreateReview.putExtra("floor", floor);
                switchToCreateReview.putExtra("gender", gender);
                startActivity(switchToCreateReview);
            }
        });
    }

    /**
     * Generate the bathroom's information by making a GET request for a JSONObject of the bathroom and populating the respecting TextViews
     */
    // Generate the bathroom given the SHARED.bathroomID
    private void bathroomView() {
        RequestQueue queue = Volley.newRequestQueue(this);

        // URL for individual bathroom adds "/#"
        JsonObjectRequest bathroomViewReq = new JsonObjectRequest(Request.Method.GET, SHARED.getServerURL() + "/bathrooms/" + String.valueOf(SHARED.getBathroomID()), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Set the XML elements with the grabbed data
                        try {
                            building = response.getString("building");
                            floor = String.valueOf(response.getInt("floor"));
                            gender = response.getString("gender");
                            String info1 = "Building: " + building;
                            mainInfo.setText(info1);
                            String info2 = "Floor: " + floor + ", Gender: " + gender + ", " + String.valueOf(response.getInt("numStalls")) + " stalls, " + String.valueOf(response.getInt("numUrinals")) + " urinals";
                            toiletryInfo.setText(info2);
                            locDescription.setText(response.getString("locDescription"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Nothing
                }
        });

        queue.add(bathroomViewReq);
    }

    /**
     * Generate the list of reviews by making a GET request for a JSONArray of the reviews and inflating the rows of the RecyclerView
     */
    // Generate the list of reviews from the database
    private void reviewList() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest reviewListReq = new JsonArrayRequest(Request.Method.GET, SHARED.getServerURL() + "/bathrooms/" + SHARED.getBathroomID() + "/reviews", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        RecyclerView recyclerView = findViewById(R.id.rvReviews);
                        recyclerView.setLayoutManager(new LinearLayoutManager(BathroomView.this));
                        adapter = new AdapterReview(BathroomView.this, response);
                        adapter.setClickListener(BathroomView.this);
                        recyclerView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Nothing
            }
        });
        queue.add(reviewListReq);
    }

    /**
     * Method for obtaining the item that the user clicked on within the RecyclerView
     * @param view - the view containing the RecyclerView
     * @param position - the position of the item within the RecyclerView
     */
    @Override
    public void onItemClick(View view, int position) {
        try{
            SHARED.setReviewID(adapter.getItem(position).getInt("id"));
            Intent switchToView = new Intent(BathroomView.this, Comments.class);
            startActivity(switchToView);
        } catch (JSONException error){
            // Nothing
        }
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