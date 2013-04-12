package com.example.jukeboxgh;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.jukeboxghTask.DatabaseHandler;
import com.example.jukeboxghTask.UserFunctions;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignInActivity extends Activity {

	private ProgressDialog pDialog;
	
	//Edit Text
	private EditText Email, Password;
	
	// Button
	Button signInButton;
	
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		
		// Edit Texts
		
		Email = (EditText) findViewById(R.id.emaileditText);
		Password = (EditText) findViewById(R.id.passwordeditText);
		Email.setTextColor(Color.WHITE);
		Password.setTextColor(Color.WHITE);
		signInButton = (Button) findViewById(R.id.signInbutton);
		
		signInButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new SignInUser().execute();
			}
		});
	}
	
	class SignInUser extends AsyncTask<String, String, String>{
		// Show Progress Dialog
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(SignInActivity.this);
			pDialog.setMessage("Registering..");
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
			String email = Email.getText().toString();
			String password = Password.getText().toString();
			
			UserFunctions userFunction = new UserFunctions();
			// getting JSON object
			JSONObject json = userFunction.loginUser(email, password);
			
			// Check Log cat for response
			Log.d("Create Response", json.toString());
			
			// Check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);
				
				if (success == 1) {
					// successfully registered user
					// Clear all previous data in database
					userFunction.logoutUser(getApplicationContext());
					DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
					
					dbHandler.addUser(email, password);
					
					Intent i = new Intent(getApplicationContext(), WelcomeOneActivity.class);
					
					// Closing other views
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					
					// Launch Next Activity Screen
					
				} else {
					// failed to register user
					Log.e("falure", "asdfa");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
		}
	}

	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		finish();
		super.onPause();
	}
}
