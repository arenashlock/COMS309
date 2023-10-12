package com.example.flushd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.flushd.utils.SHARED;

import org.json.JSONObject;

/**
 * Jack - Edit Settings class allows the user to edit their specific account settings
 */
public class EditSettings extends AppCompatActivity {
    private Button cancelUpdate, updateSettings, deleteAccount;
    private EditText firstName, lastName, email, username, password, passwordCheck;
    private Intent switchScreens;

    /**
     * basic onCreate method that links buttons to their IDs and creates the screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set up the view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_settings);
        getSupportActionBar().hide();

        // Establish the input for most of the user fields
        firstName = (EditText) findViewById(R.id.firstName);
        firstName.setText(SHARED.getFirstName());
        lastName = (EditText) findViewById(R.id.lastName);
        lastName.setText(SHARED.getLastName());
        email = (EditText) findViewById(R.id.email);
        email.setText(SHARED.getEmail());
        username = (EditText) findViewById(R.id.username);
        username.setText(SHARED.getUsername());
        password = (EditText) findViewById(R.id.password);
        password.setText(SHARED.getPassword());
        passwordCheck = (EditText) findViewById(R.id.passwordCheck);
        passwordCheck.setText(SHARED.getPassword());


        // Establish the buttons for cancelling the edit process, updating the user's information, or deleting their account
        cancelUpdate = (Button) findViewById(R.id.cancelUpdate);
        updateSettings = (Button) findViewById(R.id.updateSettings);
        deleteAccount = (Button) findViewById(R.id.deleteAccount);

        // When the cancel button is pressed, switch back to the settings page
        cancelUpdate.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick method that switches screens to the settings screen
             * @param view - basic View parameter
             */
            @Override
            public void onClick(View view) {
                switchScreens = new Intent(EditSettings.this, Settings.class);
                startActivity(switchScreens);
            }
        });

        /*
            When the update button is pressed, check if the passwords match
            * Note: The old password auto-populates, so they may not change it, but in that case this should still work *
                - If they match, register the user and switch to the map screen
                - If they don't, deny the action and prompt the user to try again
         */
        updateSettings.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick method that switches screens to the settings screen, checks if passwords match, and updates settings
             * @param view - basic view parameter
             */
            @Override
            public void onClick(View view) {
                // Make the intent switch from this screen to the Settings screen (when the passwords match)
                switchScreens = new Intent(EditSettings.this, Settings.class);

                // If passwords match (the user has the password they want), proceed to update settings
                if(checkPassword(password.getText().toString(), passwordCheck.getText().toString())){
                    // Call update settings method
                    updateSettings();
                }

                // Passwords don't match (trying to prevent a typo that could lead to future problems for the user)
                else{
                    passwordCheck.setText("");
                    passwordCheck.setHint("Passwords don't match, try again");
                }
            }
        });

        // When the cancel button is pressed, delete the account from the database and switch back to the login page
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick method that switches activities to the login screen and deletes the account
             * @param view - basic view parameter
             */
            @Override
            public void onClick(View view) {
                // Make the intent switch from this screen to the Settings screen (when the passwords match)
                switchScreens = new Intent(EditSettings.this, MainActivity.class);

                // Call delete account method
                deleteAccount();
            }
        });
    }

    /**
     * Simple method that checks if the two passwords match
     * @param password - represents password one
     * @param passwordCheck represents password two
     * @return true/false - true is passwords match, false otherwise
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
     *  Method that updates the users settings and sets that information in place
     */
    private void updateSettings(){
        // Making the URL for the PUT request
        String updateSettingsURL = SHARED.getServerURL() + "/users/" + SHARED.getUserID();

        // Build the JSONObject for the PUT request
        JSONObject updateInfo = new JSONObject();
        try {
            updateInfo.put("username", username.getText().toString());
            updateInfo.put("firstName", firstName.getText().toString());
            updateInfo.put("lastName", lastName.getText().toString());
            updateInfo.put("email", email.getText().toString());
            updateInfo.put("password", password.getText().toString());
            updateInfo.put("accountType", SHARED.getAccountType());
            updateInfo.put("active", true);
        } catch (Exception E) {
            throw new RuntimeException();
        }

        // Calling the update settings PUT method
        updateSettingsRoundtrip(updateSettingsURL, updateInfo);
    }

    /**
     * Any account information is changed on both frontend and backend through a JSON round trip
     * @param updateSettingsURL - the URL string where the update is occurring
     * @param updateInfo - The JsonObject that updates the information
     */
    private void updateSettingsRoundtrip(String updateSettingsURL, JSONObject updateInfo){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest updateRequest = new JsonObjectRequest(Request.Method.PUT, updateSettingsURL, updateInfo, new Response.Listener<JSONObject>() {
            /**
             * Basic onResponse method that saves the user information for future use and switches the screen to the settings
             * @param response - Basic JSONObject response
             */
            @Override
            public void onResponse(JSONObject response) {
                // Store the user object (all the user's info) in the SHARED class for future reference
                SHARED.setUserInfo(response);
                // Switch to the Settings screen
                startActivity(switchScreens);
            }
        }, new Response.ErrorListener() {
            /**
             * onErrorResponse method that throws an exception when faced with a VolleyError
             * @param error - VolleyError
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                throw new RuntimeException(error);
            }
        });
        requestQueue.add(updateRequest);
    }

    /**
     * Helper method that creates the URL used in the deleteAccountRoundtrip method
     */
    private void deleteAccount(){
        // Making the URL for the DELETE request
        String deleteAccountURL = SHARED.getServerURL() + "/users/" + SHARED.getUserID();

        // Calling the update settings PUT method
        deleteAccountRoundtrip(deleteAccountURL);
    }

    /**
     * method that completes a JSON round trip and allows a user to delete their account
     * @param deleteAccountURL - specific URL that deletes the account
     */
    private void deleteAccountRoundtrip(String deleteAccountURL){
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest deleteAccountRequest = new JsonObjectRequest(Request.Method.DELETE, deleteAccountURL, null, new Response.Listener<JSONObject>() {
            /**
             * Basic onResponse method that switches the screen to the login screen
             * @param response - basic response parameter
             */
            public void onResponse(JSONObject response) {
                // Switch to the Login screen
                startActivity(switchScreens);
            }
        }, new Response.ErrorListener() {
            /**
             * onErrorResponse method that throws an exception when faced with a VolleyError
             * @param error - VolleyError
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                throw new RuntimeException(error);
            }
        });
        requestQueue.add(deleteAccountRequest);
    }
}