package com.example.flushd.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Aren - This class contains shared variables for use throughout the program. Variables include <code>userInfo (all fields), bathroomID, and the serverURL</code>.
 */
public class SHARED {
    /**
     * Private FINAL variable that holds the serverURL.
     */
    /*
        URL to the server is the same for all requests
        /users --> gives JSONArray of users
        /users/# --> gives JSONObject of user with ID#
        /users/login?field1=entry1&field2=entry2&... --> finds user with given fields
            returns "message":"success" AND the user information
            *instead of username as field, it is userid
        /bathrooms --> gives JSONArray of bathrooms
        /bathrooms/# --> gives JSONObject of bathroom with ID#
        /bathrooms/#/reviews --> gives JSONArray of reviews tied to that bathroom ID#
        /jobs --> gives JSONArray of jobs
        /jobs/# --> gives JSONObject of job with ID#
        /jobs/#/bathrooms/# --> gives JSONArray of jobs linked to the bathroom with that ID#
    */
    private static final String SERVERURL = "http://coms-309-041.class.las.iastate.edu:8080";

    /**
     * Getter method for the private variable SERVERURL
     * @return SERVERURL - the url for the server
     */
    public static String getServerURL(){
        return SERVERURL;
    }

    // ------------------------------ userInfo ------------------------------
    /**
     * Private variable with a JSON Object containing the user's information
     */
    private static JSONObject userInfo = new JSONObject();

    /**
     * Setter method for updating the private user variable with a given JSONObject containing user information
     * @param userInfoIn - JSONObject that will be placed in as a user
     */
    public static void setUserInfo(JSONObject userInfoIn){
        try {
            String[] fields = {"id", "username", "firstName", "lastName", "email", "password", "accountType", "active"};
            userInfo = new JSONObject(userInfoIn, fields);
        }
        catch (JSONException test){
            throw new RuntimeException(test);
        }
    }

    /**
     * Getter method for the private variable userInfo
     * @return userInfo - the entire JSON Object containing the user's information
     */
    public static JSONObject getJSONObject(){
        return userInfo;
    }

    // ------------------------------ userID ------------------------------
    /**
     * Setter method for the private variable userID
     * @param userIDIn - integer that will be replacing the user's current id
     */
    public static void setUserID(int userIDIn){
        try{
            userInfo.put("id", userIDIn);
        }
        catch (JSONException error){
            throw new RuntimeException(error);
        }
    }

    /**
     * Getter method for the private variable userID
     * @return userID - the integer representing the user's id
     */
    public static int getUserID(){
        try{
            return userInfo.getInt("id");
        }
        catch (JSONException error){
            return 1;
        }
    }

    // ------------------------------ username ------------------------------
    /**
     * Setter method for the private variable username
     * @param usernameIn - string that will be replacing the user's current username
     */
    public static void setUsername(String usernameIn){
        try{
            userInfo.put("username", usernameIn);
        }
        catch (JSONException error){
            throw new RuntimeException(error);
        }
    }

    /**
     * Getter method for the private variable username
     * @return username - the string representing the user's username
     */
    public static String getUsername(){
        try{
            return userInfo.getString("username");
        }
        catch (JSONException error){
            return "admin";
        }
    }

    // ------------------------------ firstName ------------------------------
    /**
     * Setter method for the private variable firstName
     * @param firstNameIn - string that will be replacing the user's current first name
     */
    public static void setFirstName(String firstNameIn){
        try{
            userInfo.put("firstName", firstNameIn);
        }
        catch (JSONException error){
            throw new RuntimeException(error);
        }
    }

    /**
     * Getter method for the private variable firstName
     * @return firstName - the string representing the user's first name
     */
    public static String getFirstName(){
        try{
            return userInfo.getString("firstName");
        }
        catch (JSONException error){
            return "UG";
        }
    }

    // ------------------------------ lastName ------------------------------
    /**
     * Setter method for the private variable lastName
     * @param lastNameIn - string that will be replacing the user's current last name
     */
    public static void setLastName(String lastNameIn){
        try{
            userInfo.put("lastName", lastNameIn);
        }
        catch (JSONException error){
            throw new RuntimeException(error);
        }
    }

    /**
     * Getter method for the private variable lastName
     * @return lastName - the string representing the user's last name
     */
    public static String getLastName(){
        try{
            return userInfo.getString("lastName");
        }
        catch (JSONException error){
            return "1";
        }
    }

    // ------------------------------ email ------------------------------
    /**
     * Setter method for the private variable email
     * @param emailIn - string that will be replacing the user's current email
     */
    public static void setEmail(String emailIn){
        try{
            userInfo.put("email", emailIn);
        }
        catch (JSONException error){
            throw new RuntimeException(error);
        }
    }

    /**
     * Getter method for the private variable email
     * @return email - the string representing the user's email
     */
    public static String getEmail(){
        try{
            return userInfo.getString("email");
        }
        catch (JSONException error){
            return "2_UG_1@iastate.edu";
        }
    }

    // ------------------------------ password ------------------------------
    /**
     * Setter method for the private variable password
     * @param passwordIn - string that will be replacing the user's current password
     */
    public static void setPassword(String passwordIn){
        try{
            userInfo.put("password", passwordIn);
        }
        catch (JSONException error){
            throw new RuntimeException(error);
        }
    }

    /**
     * Getter method for the private variable lastName
     * @return password - the string representing the user's password
     */
    public static String getPassword(){
        try{
            return userInfo.getString("password");
        }
        catch (JSONException error){
            return "password1";
        }
    }

    // ------------------------------ accountType ------------------------------
    /**
     * Setter method for the private variable accountType
     * @param accountTypeIn - string that will be replacing the user's account type
     */
    public static void setAccountType(String accountTypeIn){
        try{
            userInfo.put("accountType", accountTypeIn);
        }
        catch (JSONException error){
            throw new RuntimeException(error);
        }
    }

    /**
     * Getter method for the private variable accountType
     * @return accountType - the string representing the user's account type
     */
    public static String getAccountType(){
        try{
            return userInfo.getString("accountType");
        }
        catch (JSONException error){
            return "moderator";
        }
    }

    // ------------------------------ active ------------------------------
    /**
     * Setter method for the private variable active
     * @param activeIn - boolean that will be changing the user's active status
     */
    public static void setActive(boolean activeIn){
        try{
            userInfo.put("active", activeIn);
        }
        catch (JSONException error){
            throw new RuntimeException(error);
        }
    }

    /**
     * Getter method for the private variable active
     * @return active - the boolean representing the user's active status
     */
    public static boolean getActive(){
        try{
            return userInfo.getBoolean("active");
        }
        catch (JSONException error){
            return true;
        }
    }

    // ------------------------------ bathroomID ------------------------------
    /**
     * Private variable with an integer holding the current bathroom's id
     */
    private static int bathroomID = 44;

    /**
     * Setter method for the private variable bathroomID
     * @param bathroomIDIn - integer of the new current bathroom
     */
    // Method for setting private bathroomID variable
    public static void setBathroomID(int bathroomIDIn){
        bathroomID = bathroomIDIn;
    }

    /**
     * Getter method for the private variable bathroomID
     * @return bathroomID - the integer representing the current bathroom's id
     */
    // Method for accessing private bathroomID variable
    public static int getBathroomID(){
        return bathroomID;
    }

    // ------------------------------ reviewID ------------------------------
    /**
     * Private variable with an integer holding the current review's id
     */
    private static int reviewID = 28;

    /**
     * Setter method for the private variable reviewID
     * @param reviewIDIn - integer of the new current bathroom
     */
    // Method for setting private bathroomID variable
    public static void setReviewID(int reviewIDIn){
        reviewID = reviewIDIn;
    }

    /**
     * Getter method for the private variable reviewID
     * @return reviewID - the integer representing the current bathroom's id
     */
    // Method for accessing private bathroomID variable
    public static int getReviewID(){
        return reviewID;
    }
}
