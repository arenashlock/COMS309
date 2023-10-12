package com.example.flushd;


import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flushd.utils.SHARED;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class WebSockets extends AppCompatActivity {

    Button b1, b2, back;
    EditText e2;
    TextView t1;

    private WebSocketClient cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_sockets);
        getSupportActionBar().hide();
        b2 = (Button) findViewById(R.id.bt2);
        back = (Button) findViewById(R.id.Back);
        e2 = (EditText) findViewById(R.id.et2);
        t1 = (TextView) findViewById(R.id.tx1);

        t1.setMovementMethod(new ScrollingMovementMethod());

        try {
            cc.send(e2.getText().toString());
        } catch (Exception e) {
            Log.d("ExceptionSendMessage:", e.getMessage().toString());
        }

        Draft[] drafts = {
                new Draft_6455()
        };

        String w = "ws://coms-309-041.class.las.iastate.edu:8080/chat/" + String.valueOf(SHARED.getBathroomID()) + "/" + String.valueOf(SHARED.getUsername());

        try {
            Log.d("Socket:", "Trying socket");
            cc = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                @Override
                public void onMessage(String message) {
                    System.out.println(w);
                    Log.d("", "run() returned: " + message);
                    String s = t1.getText().toString();
                    t1.setText(s + message + "\n");
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", "onClose() returned: " + reason);
                }

                @Override
                public void onError(Exception e) {
                            Log.d("Exception:", e.toString());
                        }
                    };
                } catch (URISyntaxException e) {
                    Log.d("Exception:", e.getMessage().toString());
                    e.printStackTrace();
                }
                cc.connect();

            //}
        //});

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    cc.send(e2.getText().toString());
                } catch (Exception e) {
                    Log.d("ExceptionSendMessage:", e.getMessage().toString());
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent switchToList = new Intent(WebSockets.this, BathroomView.class);
              startActivity(switchToList);
          }
        });
    }
}
