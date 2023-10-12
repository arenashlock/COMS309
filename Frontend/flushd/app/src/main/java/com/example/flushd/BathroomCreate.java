package com.example.flushd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

/**
 * Aren - Page for creating a bathroom
 */
public class BathroomCreate extends AppCompatActivity {

    /**
     * Button for cancelling the creation
     */
    protected Button Cancel;
    /**
     * Button for creating the bathroom
     */
    protected Button Create;
    /**
     * EditText for obtaining the building the bathroom is in
     */
    protected EditText building;
    /**
     * EditText for obtaining the floor the bathroom is on
     */
    protected EditText floor;
    /**
     * EditText for obtaining the location description of the bathroom
     */
    protected EditText locDescription;
    /**
     * EditText for obtaining the number of stalls in the bathroom
     */
    protected EditText numStalls;
    /**
     * EditText for obtaining the number of urinals in the bathroom
     */
    protected EditText numUrinals;
    /**
     * EditText for obtaining the gender(s) allowed in the bathroom
     */
    protected Spinner gender;

    /**
     * Generating the design of the screen
     * @param savedInstanceState - the saved instance state for the screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bathroom_create);
        getSupportActionBar().hide();

        //Set elements to their XML elements
        Cancel = (Button) findViewById(R.id.Cancel);
        Create = (Button) findViewById(R.id.Create);
        building = (EditText) findViewById(R.id.building);
        floor = (EditText) findViewById(R.id.floor);
        locDescription = (EditText) findViewById(R.id.locDescription);
        numStalls = (EditText) findViewById(R.id.numStalls);
        numUrinals = (EditText) findViewById(R.id.numUrinals);

        // Making the dropdown menu for the gender
        gender = (Spinner) findViewById(R.id.gender);
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        gender.setAdapter(genderAdapter);

        /**
         * onClickListener for the cancel button so that it switches back to the Bathroom List screen
         */
        // Switch back to the "Bathroom List" screen
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToList = new Intent(BathroomCreate.this, BathroomList.class);
                startActivity(switchToList);
            }
        });

        /**
         * onClickListener for the create button to make a new POST request with the bathroom's information to the bathroom table
         */
        // POST a new bathroom to the database
        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRequest();
            }
        });
    }

    /**
     * Method for constructing the JSONObject containing the bathroom's information and completing a POST request
     */
    //Create a new bathroom in the database
    private void postRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject bathroomData = new JSONObject();

        //Building the JSONObject
        try {
            bathroomData.put("building", building.getText().toString());
            bathroomData.put("floor", Integer.valueOf(floor.getText().toString()));
            bathroomData.put("locDescription", locDescription.getText().toString());
            bathroomData.put("numStalls", Integer.valueOf(numStalls.getText().toString()));
            bathroomData.put("numUrinals", Integer.valueOf(numUrinals.getText().toString()));
            bathroomData.put("gender", gender.getSelectedItem().toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest bathroomCreateReq = new JsonObjectRequest(Request.Method.POST, SHARED.getServerURL() + "/bathrooms", bathroomData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //If successful, grab the id from the response and switch to the "Bathroom View" that you just made
                            SHARED.setBathroomID(response.getInt("id"));
                            Intent switchToCreatedBathroom = new Intent(BathroomCreate.this, BathroomView.class);
                            startActivity(switchToCreatedBathroom);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        };
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BathroomCreate.this, "Fail = " + error, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        queue.add(bathroomCreateReq);
    }
}