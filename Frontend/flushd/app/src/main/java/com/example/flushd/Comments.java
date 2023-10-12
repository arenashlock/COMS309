package com.example.flushd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class Comments extends AppCompatActivity implements AdapterComments.ItemClickListener{
    private Button Back, CancelComment, SendComment, PostComment;

    private TextView reviewUser, dateReview, cleanlinessReview, smellReview, privacyReview, accessibilityReview, reasonReview, postedBy, cleanlinessReviewText, smellReviewText, privacyReviewText, accessibilityReviewText, reasonReviewText;

    private ImageView cleanlinessStar, smellStar, privacyStar, accessibilityStar;

    private EditText commentContent;

    /**
     * Adapter for the comments RecyclerView
     */
    // Adapter for RecyclerView
    private AdapterComments adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        getSupportActionBar().hide();

        // Set elements to their XML elements
        Back = (Button) findViewById(R.id.Back);
        CancelComment = (Button) findViewById(R.id.CancelComment);
        SendComment = (Button) findViewById(R.id.SendComment);
        PostComment = (Button) findViewById(R.id.PostComment);
        postedBy = (TextView) findViewById(R.id.postedBy);
        reviewUser = (TextView) findViewById(R.id.reviewUser);
        dateReview = (TextView) findViewById(R.id.dateReview);
        cleanlinessReviewText = (TextView) findViewById(R.id.cleanlinessReviewText);
        cleanlinessReview = (TextView) findViewById(R.id.cleanlinessReview);
        smellReviewText = (TextView) findViewById(R.id.smellReviewText);
        smellReview = (TextView) findViewById(R.id.smellReview);
        privacyReviewText = (TextView) findViewById(R.id.privacyReviewText);
        privacyReview = (TextView) findViewById(R.id.privacyReview);
        accessibilityReviewText = (TextView) findViewById(R.id.accessibilityReviewText);
        accessibilityReview = (TextView) findViewById(R.id.accessibilityReview);
        reasonReviewText = (TextView) findViewById(R.id.reasonReviewText);
        reasonReview = (TextView) findViewById(R.id.reasonReview);
        cleanlinessStar = (ImageView) findViewById(R.id.cleanlinessStar);
        smellStar = (ImageView) findViewById(R.id.smellStar);
        privacyStar = (ImageView) findViewById(R.id.privacyStar);
        accessibilityStar = (ImageView) findViewById(R.id.accessibilityStar);
        commentContent = (EditText) findViewById(R.id.commentContent);

        // Generate the desired review screen
        CancelComment.setVisibility(View.INVISIBLE);
        SendComment.setVisibility(View.INVISIBLE);
        commentContent.setVisibility(View.INVISIBLE);
        reviewView();
        commentsList();

        // Switch back to the "Bathroom View" screen
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToView = new Intent(Comments.this, BathroomView.class);
                startActivity(switchToView);
            }
        });

        CancelComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToOriginalLayout();
            }
        });

        SendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRequest();
            }
        });

        PostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToPostingComment();
            }
        });
    }

    /**
     * Generate the review's information by making a GET request for a JSONObject of the review and populating the respecting TextViews
     */
    // Generate the bathroom given the SHARED.bathroomID
    private void reviewView() {
        RequestQueue queue = Volley.newRequestQueue(this);

        // URL for individual bathroom adds "/#"
        JsonObjectRequest reviewViewReq = new JsonObjectRequest(Request.Method.GET, SHARED.getServerURL() + "/reviews/" + String.valueOf(SHARED.getReviewID()), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Set the XML elements with the grabbed data
                        try {
                            reviewUser.setText(response.getJSONObject("user").getString("username"));
                            dateReview.setText(response.getString("datePosted"));
                            cleanlinessReview.setText(String.valueOf(response.getInt("cleanlinessRating")));
                            smellReview.setText(String.valueOf(response.getInt("smellRating")));
                            privacyReview.setText(String.valueOf(response.get("privacyRating")));
                            accessibilityReview.setText(String.valueOf(response.getInt("accessibilityRating")));
                            reasonReview.setText(response.getString("content"));
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

        queue.add(reviewViewReq);
    }

    /**
     * Generate the list of comments by making a GET request for a JSONArray of the reviews and inflating the rows of the RecyclerView
     */
    // Generate the list of reviews from the database
    private void commentsList() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest commentsListReq = new JsonArrayRequest(Request.Method.GET, SHARED.getServerURL() + "/reviews/" + SHARED.getReviewID() + "/comments", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        RecyclerView recyclerView = findViewById(R.id.rvComments);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Comments.this));
                        adapter = new AdapterComments(Comments.this, response);
                        adapter.setClickListener(Comments.this);
                        recyclerView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Nothing
            }
        });
        queue.add(commentsListReq);
    }

    /**
     * Method for obtaining the item that the user clicked on within the RecyclerView (does nothing since this is the lowest level of interaction we want)
     * @param view - the view containing the RecyclerView
     * @param position - the position of the item within the RecyclerView
     */
    @Override
    public void onItemClick(View view, int position) {
        // Nothing
    }

    private void switchToPostingComment(){
        Back.setVisibility(View.INVISIBLE);
        PostComment.setVisibility(View.INVISIBLE);
        postedBy.setVisibility(View.INVISIBLE);
        reviewUser.setVisibility(View.INVISIBLE);
        dateReview.setVisibility(View.INVISIBLE);
        cleanlinessReviewText.setVisibility(View.INVISIBLE);
        cleanlinessReview.setVisibility(View.INVISIBLE);
        cleanlinessStar.setVisibility(View.INVISIBLE);
        smellReviewText.setVisibility(View.INVISIBLE);
        smellReview.setVisibility(View.INVISIBLE);
        smellStar.setVisibility(View.INVISIBLE);
        privacyReviewText.setVisibility(View.INVISIBLE);
        privacyReview.setVisibility(View.INVISIBLE);
        privacyStar.setVisibility(View.INVISIBLE);
        accessibilityReviewText.setVisibility(View.INVISIBLE);
        accessibilityReview.setVisibility(View.INVISIBLE);
        accessibilityStar.setVisibility(View.INVISIBLE);
        reasonReviewText.setVisibility(View.INVISIBLE);
        reasonReview.setVisibility(View.INVISIBLE);
        RecyclerView recyclerView = findViewById(R.id.rvComments);
        recyclerView.setLayoutManager(new LinearLayoutManager(Comments.this));
        adapter = new AdapterComments(Comments.this, new JSONArray());
        adapter.setClickListener(Comments.this);
        recyclerView.setAdapter(adapter);

        CancelComment.setVisibility(View.VISIBLE);
        SendComment.setVisibility(View.VISIBLE);
        commentContent.setVisibility(View.VISIBLE);
    }

    private void switchToOriginalLayout(){
        Back.setVisibility(View.VISIBLE);
        PostComment.setVisibility(View.VISIBLE);
        postedBy.setVisibility(View.VISIBLE);
        reviewUser.setVisibility(View.VISIBLE);
        dateReview.setVisibility(View.VISIBLE);
        cleanlinessReviewText.setVisibility(View.VISIBLE);
        cleanlinessReview.setVisibility(View.VISIBLE);
        cleanlinessStar.setVisibility(View.VISIBLE);
        smellReviewText.setVisibility(View.VISIBLE);
        smellReview.setVisibility(View.VISIBLE);
        smellStar.setVisibility(View.VISIBLE);
        privacyReviewText.setVisibility(View.VISIBLE);
        privacyReview.setVisibility(View.VISIBLE);
        privacyStar.setVisibility(View.VISIBLE);
        accessibilityReviewText.setVisibility(View.VISIBLE);
        accessibilityReview.setVisibility(View.VISIBLE);
        accessibilityStar.setVisibility(View.VISIBLE);
        reasonReviewText.setVisibility(View.VISIBLE);
        reasonReview.setVisibility(View.VISIBLE);
        commentsList();

        CancelComment.setVisibility(View.INVISIBLE);
        SendComment.setVisibility(View.INVISIBLE);
        commentContent.setVisibility(View.INVISIBLE);
    }

    /**
     * postRequest() method establishes a JSON request to the backend
     */
    private void postRequest(){
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject commentInfo = new JSONObject();

        // Building the JSONObject
        try {
            commentInfo.put("comment", commentContent.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
            String date = sdf.format(new Date());
            commentInfo.put("date", date);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest reviewCommentReq = new JsonObjectRequest(Request.Method.POST, SHARED.getServerURL() + "/comments", commentInfo,
                new Response.Listener<JSONObject>() {
                    /**
                     * basic onResponse() method that links a bathroom to an ID upon JSON response
                     * @param response - response parameter and acts as a JSONObject response
                     */
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // If successful, link the comment to the review
                            linkToUserAndReview(response.getInt("id"));
                        }
                        catch (JSONException error){
                            // Nothing
                        }
                    }
                },
                new Response.ErrorListener() {
                    /**
                     * Does nothing
                     * @param error - parameter for potentially any error
                     */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Nothing
                    }
                }
        );
        queue.add(reviewCommentReq);
    }

    /**
     * Helper method that links a comment to a specific user ID and review ID
     * @param commentID - the ID to be linked
     */
    private void linkToUserAndReview(int commentID){
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest linkCtoUR = new JsonObjectRequest(Request.Method.PUT, SHARED.getServerURL() + "/comments/" + commentID + "/user/" + SHARED.getUserID() + "/review/" + SHARED.getReviewID(), null, new Response.Listener<JSONObject>() {
            /**
             * basic onResponse() method that goes back to the original screen layout
             * @param response - basic JSONObject response
             */
            @Override
            public void onResponse(JSONObject response) {
                // If successful, regenerate the screen's layout
                switchToOriginalLayout();
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
        queue.add(linkCtoUR);
    }
}