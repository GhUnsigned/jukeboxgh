package com.example.jukeboxgh;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.jukeboxghTask.Artists_Items;
import com.example.jukeboxghTask.DatabaseHandler;
import com.example.jukeboxghTask.UserFunctions;

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
	

	ArrayList<Artists_Items> artistsList;
	
	private ProgressDialog pDialog;
	private MyCustomAdapter dataAdapter = null;
	
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
		for(int i=0;i<artistsList.size();i++){
			Artists_Items artist = artistsList.get(i);
			if(artist.isChecked()){
				responseText.append("\n" + artist.getName());
			}
		}
		
		Toast.makeText(getApplicationContext(),
				responseText, Toast.LENGTH_LONG).show();		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.follow, menu);
		return true;
	}

	private void bind(ArrayList<Artists_Items> artistsList){// accept Artist_listItems as params
		//create an ArrayAdaptar from the String Array
		dataAdapter = new MyCustomAdapter(this,	R.layout.artist_list_row, artistsList);
		
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
					String artistName = artistArray.getString("name");
					//String genre = artistArray.getString("genre");
					Artists_Items artistsItems = new Artists_Items(artistName, false);
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
	
	private class MyCustomAdapter extends ArrayAdapter<Artists_Items>{
		
		private ArrayList<Artists_Items> artistsList;
		public MyCustomAdapter(Context context, int textViewResourceId, 
				ArrayList<Artists_Items> artistsList) {
			super(context, textViewResourceId, artistsList);
			this.artistsList = new ArrayList<Artists_Items>();
			this.artistsList.addAll(artistsList);
			
			// TODO Auto-generated constructor stub
		}
		
		private class ViewHolder {
			TextView textView;
			CheckBox checkBox;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			
			Log.v("ConvertView", String.valueOf(position));
				 
			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
				
				convertView = vi.inflate(R.layout.artist_list_row, null);
				
				holder = new ViewHolder();
				holder.textView = (TextView) convertView.findViewById(R.id.rowTextView);
				holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
				convertView.setTag(holder);
				 
				holder.checkBox.setOnClickListener( new View.OnClickListener() {
					public void onClick(View v) {
						CheckBox cb = (CheckBox) v ;
						Artists_Items artistsItems = (Artists_Items) cb.getTag();
						artistsItems.setChecked(cb.isChecked());
					}
				}); 
				
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			Artists_Items artistsItems = artistsList.get(position);
			holder.textView.setText(artistsItems.getName());
			holder.checkBox.setChecked(artistsItems.isChecked());
			holder.checkBox.setTag(artistsItems);
			
			return convertView;
		}
		
	}
}
