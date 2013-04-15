package com.example.jukeboxgh;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.jukeboxghTask.Artists_Items;
import com.example.jukeboxghTask.DatabaseHandler;
import com.example.jukeboxghTask.UserFunctions;
import com.example.jukeboxghadapters.SubscribeArtistAdapter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FollowActivity extends Activity {

	private static final String TAG_SUCCESS = "success";

	ArrayList<Artists_Items> artistsList;
	
	private ProgressDialog pDialog;
	private SubscribeArtistAdapter dataAdapter = null;
	
	private ListView artistListView;
	String[] artist;
	
	// JSON Node names
	private static final String TAG_NAME = "name";
	private static final String TAG_GENRE = "genre";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_follow);
		
		artistListView =(ListView) findViewById(R.id.lvArtist);
		
		new GetAllArtists().execute();
		
		
	}
	
	public void nextButtonClick (View view) {
		StringBuffer responseText = new StringBuffer();
		
		responseText.append("The following were selected...\n");
		
		ArrayList<Artists_Items> artistsItems = new ArrayList<Artists_Items>();
		ArrayList<String> artistsArray = new ArrayList<String>();
		
		for(int i=0;i<artistsList.size();i++){
			Artists_Items artist = artistsList.get(i);
			int a=0;
			if(artist.isChecked()){
				artistsArray.add(a, String.valueOf(artist.getID()));				// Later to get the ID
				a++;
				
				responseText.append("\n" + artist.getName());
			}
		}		
		
		UserFunctions userFunction = new UserFunctions();
		
		
		//String [] postArtist = (String[]) artistsArray.toArray();
		String[]postArtist=new String[artistsArray.size()];
		for(int i=0;i<artistsArray.size();i++){
			postArtist[i]=artistsArray.get(i);
		}
		
		JSONObject json = userFunction.subscribeArtists(postArtist);

		Toast.makeText(getApplicationContext(),
				responseText, Toast.LENGTH_LONG).show();	
		// Check for request status message
		try {
			int success = json.getInt(TAG_SUCCESS);
			Intent i = new Intent();
			
			if (success == 1) {
				// query success
				i.setClass(FollowActivity.this, MainActivity.class);
				startActivity(i);
				finish();
			} else {
				// failed query
				Toast.makeText(FollowActivity.this, 
						"Sorry an error occured during subscription", 
						Toast.LENGTH_LONG);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.follow, menu);
		return true;
	}

	private void bind(ArrayList<Artists_Items> artistsList){// accept Artist_listItems as params
		//create an ArrayAdaptar from the String Array
		dataAdapter = new SubscribeArtistAdapter(this, artistsList);
		
		// Assign adapter to ListView
		artistListView.setAdapter(dataAdapter);
		
		//ArrayAdapter<String> aa=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array);
	}
	
	class GetAllArtists extends AsyncTask<String, String, String>{
		// Show Progress Dialog
		JSONObject json;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(FollowActivity.this);
			pDialog.setMessage("Please Wait..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/*
		 * (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 * Registering users
		 */
		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			
			UserFunctions userFunction = new UserFunctions();
			
			// Getting Json Objects
			json = userFunction.getAllArtist();
			
			// Creating json array
			try {
				JSONArray artistsArray = json.getJSONArray("Artists");
				//String artistsStrName[] = new String[artistsArray.length()];
				artistsList = new ArrayList<Artists_Items>();
				
				for(int i=0;i<artistsArray.length();i++) {
					JSONObject artistArray = artistsArray.getJSONObject(i);
					
					int artist_id = Integer.parseInt(artistArray.getString("id"));
					String artistName = artistArray.getString("name");
					String genre = artistArray.getString("genre");
					
					Artists_Items artistsItems = new Artists_Items(artist_id, artistName, genre, false);
					
					
					artistsList.add(artistsItems);
					//artistsStrName[i] = artistName;
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Check Log cat for response			
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
			bind(artistsList);
		}
	}
}
