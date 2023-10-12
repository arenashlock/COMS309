package com.example.flushd;

import static android.view.View.GONE;
import static com.example.flushd.utils.SHARED.getAccountType;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.example.flushd.databinding.ActivityBathroomListBinding;
import com.example.flushd.utils.SHARED;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Aren - Page that contains a list of all the bathrooms
 */
//every activity needs "extends sidebar" for navigation bar
public class BathroomList extends sidebar implements AdapterBathroom.ItemClickListener {

    /**
     * Button to take users to the Create a Bathroom page
     */
    // Button for taking users to the "Create a Bathroom" page
    private Button BathroomCreate;

    /**
     * Adapter for this specific activity
     */
    // Adapter for RecyclerView
    private AdapterBathroom adapter;

    /**
     * Binding for the navigation drawer that ties to this activity
     */
    //binding for nav bar
    ActivityBathroomListBinding activityBathroomListBinding;

    /**
     * Generating the design of the screen
     * @param savedInstanceState - the saved instance state for the screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bathroom_list);

        //section for nav view code
        activityBathroomListBinding = ActivityBathroomListBinding.inflate(getLayoutInflater());
        setContentView(activityBathroomListBinding.getRoot());
        allocateActivityTitle("Bathroom List");

        // Set elements to their XML elements
        BathroomCreate = (Button) findViewById(R.id.BathroomCreate);

        // Populate the list of bathrooms
        bathroomList();

        //hides the bathroom create button for certain account types
        if (getAccountType().equals("User") || getAccountType().equals("Maintenance")) {
            BathroomCreate.setVisibility(GONE);
        }

        // Switch to the "Bathroom Create" screen
        BathroomCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToCreate = new Intent(BathroomList.this, BathroomCreate.class);
                startActivity(switchToCreate);
            }
        });
    }

    /**
     * Generate the list of bathrooms by making a GET request for a JSONArray of the bathrooms and inflating the rows of the RecyclerView
     */
    // Generate the list of bathrooms from the database
    private void bathroomList() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest bathroomListReq = new JsonArrayRequest(Request.Method.GET, SHARED.getServerURL() + "/bathrooms", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        RecyclerView recyclerView = findViewById(R.id.rvBathrooms);
                        recyclerView.setLayoutManager(new LinearLayoutManager(BathroomList.this));
                        adapter = new AdapterBathroom(BathroomList.this, response);
                        adapter.setClickListener(BathroomList.this);
                        recyclerView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Nothing
            }
        });
        queue.add(bathroomListReq);
    }

    /**
     * Method for obtaining the item that the user clicked on within the RecyclerView
     * @param view - the view containing the RecyclerView
     * @param position - the position of the item within the RecyclerView
     */
    // Sets the bathroomID and goes to the "Bathroom View" page (which will generate the view for the selected bathroom)
    @Override
    public void onItemClick(View view, int position) {
        try{
            SHARED.setBathroomID(adapter.getItem(position).getInt("id"));
            Intent switchToView = new Intent(BathroomList.this, BathroomView.class);
            startActivity(switchToView);
        } catch (JSONException error){
            // Nothing
        }
    }
}