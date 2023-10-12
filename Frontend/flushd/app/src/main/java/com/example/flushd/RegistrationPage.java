package com.example.flushd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
 * Jack - This is the registration page and allows the user to register an account
 */
public class RegistrationPage extends AppCompatActivity {
    private Spinner userType;
    private Button cancelRegistration, register;
    private EditText firstName, lastName, email, username, password, passwordCheck;
    private Intent switchScreens;

    /**
     * creates the screen and assigns the button to their IDs
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set up the view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        getSupportActionBar().hide();

        // Establish the input for most of the user fields
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        passwordCheck = (EditText) findViewById(R.id.passwordCheck);

        // Making the dropdown menu for the accountType
        userType = (Spinner) findViewById(R.id.userType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.userTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        userType.setAdapter(adapter);

        // Establish the buttons for cancelling registration or completing registration
        cancelRegistration = (Button) findViewById(R.id.cancelRegistration);
        register = (Button) findViewById(R.id.register);

        // When the cancel button is pressed, switch back to the login page
        cancelRegistration.setOnClickListener(new View.OnClickListener() {
            /**
             * Switches screens to the login screen
             * @param view - basic view parameter
             */
            @Override
            public void onClick(View view) {
                switchScreens = new Intent(RegistrationPage.this, MainActivity.class);
                startActivity(switchScreens);
            }
        });

        /*
            When the registration button is pressed, check if the passwords match
                - If they match, register the user and switch to the map screen
                - If they don't, deny the action and prompt the user to try again
         */
        register.setOnClickListener(new View.OnClickListener() {
            /**
             * switches the screen to the map screen and checks that the passwords are the same
             * @param view
             */
            @Override
            public void onClick(View view) {
                // Make the intent switch from this screen to the Map screen (when the passwords match)
                switchScreens = new Intent(RegistrationPage.this, Map.class);

                // If passwords match (the user has the password they want), proceed to registration
                if(checkPassword(password.getText().toString(), passwordCheck.getText().toString())){
                    // Call register method
                    register();
                }

                // Passwords don't match (trying to prevent a typo that could lead to future problems for the user)
                else{
                    passwordCheck.setText("");
                    passwordCheck.setHint("Incorrect, try again");
                }
            }
        });
    }

    /**
     * check that the passwords are the same
     * @param password - password one
     * @param passwordCheck - password two
     * @return false if password check fails
     */
    private boolean checkPassword(String password, String passwordCheck){
        if(password.length() != passwordCheck.length()){
            return false;
        }

        for(int i = 0; i < password.length(); i++){
            if(password.charAt(i) != passwordCheck.charAt(i)){
                return false;
            }
        }

        return true;
    }

    /**
     * JSON Request for the user to register an account
     */
    private void register(){
        // Making the URL for the POST request
        String registrationURL = SHARED.getServerURL() + "/users";

        // Build the JSONObject for the POST request
        JSONObject registrationInfo = new JSONObject();
        try {
            registrationInfo.put("username", username.getText().toString());
            registrationInfo.put("firstName", firstName.getText().toString());
            registrationInfo.put("lastName", lastName.getText().toString());
            registrationInfo.put("email", email.getText().toString());
            registrationInfo.put("password", password.getText().toString());
            registrationInfo.put("accountType", userType.getSelectedItem().toString());
            registrationInfo.put("active", true);
        } catch (Exception E) {
            throw new RuntimeException();
        }

        // Calling the registration POST method
        registrationRoundtrip(registrationURL, registrationInfo);
    }

    /**
     * JSON registration round trip that allows the user to register an account
     * @param registrationURL - URL for the get request
     * @param registrationInfo - Users information for the JSON round trip
     */
    private void registrationRoundtrip(String registrationURL, JSONObject registrationInfo){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest registerRequest = new JsonObjectRequest(Request.Method.POST, registrationURL, registrationInfo, new Response.Listener<JSONObject>() {
            /**
             * sets information and switches screens on response to the map screen
             * @param response - basic response parameter
             */
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("message").compareTo("user exists") == 0) {
                        username.setText("");
                        username.setHint("Username already exists");
                    } else {
                        // Store the user object (all the user's info) in the SHARED class for future reference
                        SHARED.setUserInfo(response);
                        // Switch to the Map screen
                        startActivity(switchScreens);
                    }
                }
                catch (JSONException e){
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            /**
             * throws an error if an error is found
             * @param error - parameter for potentially any error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                throw new RuntimeException(error);
            }
        });
        requestQueue.add(registerRequest);
    }
}