package com.example.flushd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

/**
 * Jack - Acts as the login screen of the project and allows the user to login to the app
 */
public class MainActivity extends AppCompatActivity {
    protected EditText username, password;
    protected TextView invalidLogin;
    protected Button login, register;
    protected Intent switchScreens;

    /**
     * basic onCreate that links buttons to their IDs and creates the screen
     * @param savedInstanceState - basic savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set up the view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        // Establish the input for the username and password fields
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        // Establish the connection to the text that appears with an invalid login
        invalidLogin = (TextView) findViewById(R.id.invalidLogin);

        // Establish the buttons for logging in or going to register
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);

        /*
            When the login button is pressed, check if there is a user in the system
                - If there is, capture the user's information and switch to the map screen
                - If there is not, deny the action and explain some information is incorrect
         */
        login.setOnClickListener(new View.OnClickListener() {
            /**
             * Basic onClick method that switches screens to the Map screen and call login to get the URL
             * @param view - basic view parameter
             */
            @Override
            public void onClick(View view) {
                // Make the intent switch from this screen to the Map screen (when there is a successful login)
                switchScreens = new Intent(MainActivity.this, Map.class);

                // Calls login method
                login();
            }
        });

        // When the register button is pressed, switch to the registration page
        register.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick method that switches screens to the registration screen
             * @param view - basic view parameter
             */
            @Override
            public void onClick(View view) {
                // Make the intent switch from this screen to the RegistrationPage screen
                switchScreens = new Intent(MainActivity.this, RegistrationPage.class);
                startActivity(switchScreens);
            }
        });
    }

    /**
     * makes the URL for the GET request
     */
    private void login(){
        // Making the URL for the GET request (to validate user is in database)
        String username = this.username.getText().toString();
        String password = this.password.getText().toString();
        String loginURL = SHARED.getServerURL() + "/users/login?userid=" + username + "&password=" + password;

        // Calling the login validation method
        loginRoundtrip(loginURL);
    }

    /**
     * Makes the JSON round trip for the users login
     * @param loginURL - the URL for the JSON GET request
     */
    private void loginRoundtrip(String loginURL){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.GET, loginURL, null, new Response.Listener<JSONObject>() {
            /**
             * onResponse method that sets the parameter to the users info and switches the screen to the map screen
             * @param response - JSONObject response
             */
            @Override
            public void onResponse(JSONObject response) {
                try{
                    // User is in database
                    if(response.getString("message").compareTo("success") == 0) {
                        // Store the user object (all the user's info) in the SHARED class for future reference
                        SHARED.setUserInfo(response.getJSONObject("user"));
                        // Switch to the Map screen
                        startActivity(switchScreens);
                    }
                    // User is not in database
                    else {
                        // Have text appear saying "Invalid username or password"
                        invalidLogin.setVisibility(View.VISIBLE);
                    }
                }
                // Server is likely not on, so just explain that in a Toast (temporary pop-up) message
                catch (JSONException error){
                    Toast.makeText(MainActivity.this, "Server not responding. Try again shortly", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            /**
             * Does nothing
             * @param error - parameter for potentially any error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                throw new RuntimeException(error);
            }
        });
        requestQueue.add(loginRequest);
    }
}