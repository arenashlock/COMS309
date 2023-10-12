package com.example.flushd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.flushd.databinding.ActivityMapBinding;
import com.example.flushd.utils.SHARED;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Jack - The "Main" screen, contains the map and temp imageview along with the navigation drawer
 */
public class Map extends sidebar implements AdapterBathroom.ItemClickListener {
    // Adapter for RecyclerView
    private AdapterBathroom adapter;

    private ImageView mapPlaceholder;

    ActivityMapBinding activitymapBinding;

    /**
     * onCreate method that creates the screen and adds the navigation drawer
     * @param savedInstanceState - basic savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitymapBinding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(activitymapBinding.getRoot());
        allocateActivityTitle("Map");

        mapPlaceholder = findViewById(R.id.mapPlaceholder);

        bathroomList();
    }

    /**
     * Generates the list of bathrooms from the database
     */
    private void bathroomList() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest bathroomListReq = new JsonArrayRequest(Request.Method.GET, SHARED.getServerURL() + "/bathrooms", null,
                new Response.Listener<JSONArray>() {
                    /**
                     * onResponse method that creates the recycler view for the list of bathrooms
                     * @param response - JSONArray
                     */
                    @Override
                    public void onResponse(JSONArray response) {
                        RecyclerView recyclerView = findViewById(R.id.rvBathrooms);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Map.this));
                        adapter = new AdapterBathroom(Map.this, response);
                        adapter.setClickListener(Map.this);
                        recyclerView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            /**
             * Does nothing
             * @param error - parameter for potentially any error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                // Nothing
            }
        });
        queue.add(bathroomListReq);
    }

    /**
     * Sets the bathroomID and generates a "map location" of the bathroom using the ImageView
     * @param view - basic view parameter
     * @param position - position on the map
     */
    @Override
    public void onItemClick(View view, int position) {
        try{
            SHARED.setBathroomID(adapter.getItem(position).getInt("id"));
            setMapImage();
        } catch (JSONException error){
            // Nothing
        }
    }

    /**
     * Temporary method for displaying a bathroom's location since Map API is not an option at the moment
     */
    public void setMapImage(){
        if(SHARED.getBathroomID() == 35){
            mapPlaceholder.setImageResource(R.drawable.atanasoff_1_mens_map);
        }
        else if(SHARED.getBathroomID() == 36){
            mapPlaceholder.setImageResource(R.drawable.carver_1_mens_map);
        }
        else if(SHARED.getBathroomID() == 37){
            mapPlaceholder.setImageResource(R.drawable.carver_1_womens_map);
        }
        else if(SHARED.getBathroomID() == 38){
            mapPlaceholder.setImageResource(R.drawable.hoover_1_mens_map);
        }
        else if(SHARED.getBathroomID() == 39){
            mapPlaceholder.setImageResource(R.drawable.howe_0_mens_map);
        }
        else if(SHARED.getBathroomID() == 40){
            mapPlaceholder.setImageResource(R.drawable.marston_2_mens_map);
        }
        else if(SHARED.getBathroomID() == 41){
            mapPlaceholder.setImageResource(R.drawable.mu_0_netural_map);
        }
        else if(SHARED.getBathroomID() == 42){
            mapPlaceholder.setImageResource(R.drawable.mu_1_mens_map);
        }
        else if(SHARED.getBathroomID() == 43){
            mapPlaceholder.setImageResource(R.drawable.state_1_womens_map);
        }
        else if(SHARED.getBathroomID() == 44){
            mapPlaceholder.setImageResource(R.drawable.state_2_mens_map);
        }
        else{
            mapPlaceholder.setImageResource(R.drawable.bathroom_map_placeholder);
        }
    }
}