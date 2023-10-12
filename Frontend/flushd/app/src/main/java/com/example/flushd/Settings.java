package com.example.flushd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.flushd.databinding.ActivitySettingsBinding;
import com.example.flushd.utils.SHARED;

import org.w3c.dom.Text;

/**
 * Jack - Settings screen, where a user can view their account settings. Also contains a navigation drawer
 */
public class Settings extends sidebar {
    private Button editSettings;
    private TextView firstName, lastName, email, username;
    private Intent switchScreens;
    ActivitySettingsBinding activitySettingsBinding;

    /**
     * onCreate method that creates the screen, navigation drawer, and sets the buttons to their IDs
     * @param savedInstanceState - basic savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySettingsBinding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(activitySettingsBinding.getRoot());
        allocateActivityTitle("Settings");

        editSettings = (Button) findViewById(R.id.editSettings);
        firstName = (TextView) findViewById(R.id.firstName);
            firstName.setText(firstName.getText().toString() + SHARED.getFirstName());
        lastName = (TextView) findViewById(R.id.lastName);
            lastName.setText(lastName.getText().toString() + SHARED.getLastName());
        email = (TextView) findViewById(R.id.email);
            email.setText(email.getText().toString() + SHARED.getEmail());
        username = (TextView) findViewById(R.id.username);
            username.setText(username.getText().toString() + SHARED.getUsername());

        editSettings.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick method that switches the screen to the Edit Settings screen
             * @param view - basic view parameter
             */
            @Override
            public void onClick(View view) {
                switchScreens = new Intent(Settings.this, EditSettings.class);
                startActivity(switchScreens);
            }
        });
    }
}