package com.example.jukeboxghTask;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class UserFunctions {
	
	private JSONParser jsonParser;
	// Url to register user 10.0.2.2
	private static String URL_REGISTER_USER= "http://192.168.1.66/social_music/and/register.php";

	// URL to login user
	private static String URL_LOGIN_USER= "http://192.168.1.66/social_music/and/login.php";
	
	// URL to get Artist
	private static String URL_GET_ARTIST = "http://192.168.1.66/social_music/and/scripts/getAllArtists.php";
	
	// URL to subscribe user
	private static String URL_SUBSCRIBE_ARTIST = "http://192.168.1.66/social_music/and/scripts/subscribe.php";
	
	public UserFunctions(){
		jsonParser = new JSONParser();
	}
	
	/**
	 * Function to register user
	 * @param email
	 * @param password
	 * @param password2
	 */
	
	public JSONObject registerUser (String email, String password, String password2) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("password2", password2));
		
		// getting JSON object
		JSONObject json = jsonParser.makeHttpRequest(URL_REGISTER_USER, "POST", params);
		
		return json;
	}
	
	/**
	 * Function to Login User
	 * @param email
	 * @param password
	 */
	
	public JSONObject loginUser(String email, String password) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		
		// getting JSON object
		JSONObject json = jsonParser.makeHttpRequest(URL_LOGIN_USER, "POST", params);
		
		return json;
	}
	
	public JSONObject subscribeArtists(String [] artist_id){
		// Building artist_id params
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		for (int i = 0; i < artist_id.length; i++) {
			params.add(new BasicNameValuePair("artist[]", artist_id[i]));
		}
		//params.add(new BasicNameValuePair("artist_id", artist_id));
		
		JSONObject json = jsonParser.makeHttpRequest(URL_SUBSCRIBE_ARTIST, "POST", params);
		return json;
	}
	
	public JSONObject getAllArtist() {
		// getting JSON object
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("null", "null"));
		
		JSONObject json = jsonParser.makeHttpRequest(URL_GET_ARTIST, "POST", params);
		return json;
	}
	
	/**
	 * Function get Login status
	 * */
	public boolean isUserLoggedIn(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		int count = db.getRowCount();
		if(count > 0){
			// user logged in
			return true;
		}
		return false;
	}
	
	/**
	 * Function to logout user
	 * Reset Database
	 * */
	public boolean logoutUser(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetTables();
		return true;
	}
}
