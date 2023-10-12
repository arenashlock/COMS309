package com.example.flushd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.flushd.databinding.ActivityViewJobBinding;
import com.example.flushd.utils.SHARED;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Jack - The ViewJob class allows maintenance to view the jobs in a list
 */
public class ViewJob extends sidebar implements AdapterJob.ItemClickListener {
    // Adapter for RecyclerView
    private AdapterJob adapter;

    //binding for nav bar
    ActivityViewJobBinding activityViewJobBinding;

    /**
     * onCreate method that creates the screen, navigation drawer, and calls job list method
     * @param savedInstanceState - basic saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job);

        //section for nav view code
        activityViewJobBinding = ActivityViewJobBinding.inflate(getLayoutInflater());
        setContentView(activityViewJobBinding.getRoot());
        allocateActivityTitle("Job List");

        // Populate the list of jobs
        jobList();
    }

    /**
     * Generate the list of bathrooms from the database
     */
    private void jobList() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jobListReq = new JsonArrayRequest(Request.Method.GET, SHARED.getServerURL() + "/jobs", null,
                new Response.Listener<JSONArray>() {
                    /**
                     * onResponse method that creates the recycler view for the list of jobs
                     * @param response - basic response parameter
                     */
                    @Override
                    public void onResponse(JSONArray response) {
                        RecyclerView recyclerView = findViewById(R.id.rvJobs);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ViewJob.this));
                        adapter = new AdapterJob(ViewJob.this, response);
                        adapter.setClickListener(ViewJob.this);
                        recyclerView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Nothing
            }
        });
        queue.add(jobListReq);
    }

    // Nothing needed for this method for now (might need to do something in the future, but just needs to be implemented for now)

    /**
     * Once a job is clicked in this method it will be accepted
     * @param view - basic view parameter
     * @param position - the position of the job in the list
     */
    @Override
    public void onItemClick(View view, int position) {
        try{
            if(adapter.getItem(position).getString("status").compareTo("Unassigned") == 0){
                linkJobToUser(adapter.getItem(position));
            }
            if((adapter.getItem(position).getJSONObject("owner").getInt("id") == SHARED.getUserID()) && (adapter.getItem(position).getString("status").compareTo("Active") == 0)) {
                completeJob(adapter.getItem(position));
            }
        } catch (JSONException error){
            // Nothing
        }
    }

    private void linkJobToUser(JSONObject job){
        RequestQueue queue = Volley.newRequestQueue(this);
        int jobID;

        try {
            jobID = job.getInt("id");
        } catch(Exception e){
            throw new RuntimeException();
        }

        JsonObjectRequest takeJob = new JsonObjectRequest(Request.Method.PUT, SHARED.getServerURL() + "/jobs/" + jobID + "/user/" + SHARED.getUserID(), null, new Response.Listener<JSONObject>() {
            /**
             * Sets the data from the JSON request
             * @param response - JSONObject response
             */
            @Override
            public void onResponse(JSONObject response) {
                try{
                    if(response.getString("message").compareTo("success") == 0){
                        acceptJob(job);
                    }
                    else {
                        // Nothing (person is not a maintenance type user, so don't do anything)
                    }
                } catch (JSONException e) {
                    // Nothing
                }
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
        queue.add(takeJob);
    }

    /**
     * this methods establishes a JSON request and accepts the job
     * @param job - job JSONObject
     */
    private void acceptJob(JSONObject job){
        RequestQueue queue = Volley.newRequestQueue(this);
        int jobID;

        try {
            job.put("status", "Active");
            jobID = job.getInt("id");
        } catch(Exception e){
            throw new RuntimeException();
        }

        JsonObjectRequest takeJob = new JsonObjectRequest(Request.Method.PUT, SHARED.getServerURL() + "/jobs/" + jobID, job, new Response.Listener<JSONObject>() {
            /**
             * Sets the data from the JSON request
             * @param response - JSONObject response
             */
            @Override
            public void onResponse(JSONObject response) {
                // Populate the list of jobs
                jobList();
                Toast.makeText(ViewJob.this, "Accepted this job!", Toast.LENGTH_LONG).show();
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
        queue.add(takeJob);
    }

    /**
     * this methods establishes a JSON request and completes the job
     * @param job - job JSONObject
     */
    private void completeJob(JSONObject job){
        RequestQueue queue = Volley.newRequestQueue(this);
        int jobID;

        try {
            job.put("status", "Complete");
            jobID = job.getInt("id");
        } catch(Exception e){
            throw new RuntimeException();
        }

        JsonObjectRequest completeJob = new JsonObjectRequest(Request.Method.PUT, SHARED.getServerURL() + "/jobs/" + jobID, job, new Response.Listener<JSONObject>() {
            /**
             * Sets the data from the JSON request
             * @param response - JSONObject response
             */
            @Override
            public void onResponse(JSONObject response) {
                // Populate the list of jobs
                jobList();
                Toast.makeText(ViewJob.this, "Completed this job!", Toast.LENGTH_LONG).show();
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
        queue.add(completeJob);
    }
}