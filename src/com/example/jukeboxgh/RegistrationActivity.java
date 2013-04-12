package com.example.jukeboxgh;

import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends Activity {

	private ProgressDialog pDialog;
	
	Button registerButton;
	// Text views
	TextView errorText;
	
	// Edit texts
	EditText inputEmail, inputPassword, inputPassword2;
	
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		
		// EditText Fields
		inputEmail = (EditText) findViewById(R.id.emaileditText);
		inputPassword = (EditText) findViewById(R.id.passwordeditText);
		inputPassword2 = (EditText) findViewById(R.id.password2editText);
		inputEmail.setTextColor(Color.WHITE);
		inputPassword.setTextColor(Color.WHITE);
		inputPassword2.setTextColor(Color.WHITE);
		// Register Button
		registerButton = (Button) findViewById(R.id.registerbutton);
		
		// Text Views
		errorText = (TextView) findViewById(R.id.errortextView);
		
		// Onclick Register Button
		registerButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				// registering user in background thread
				if (inputEmail.getText().equals(null)//.toString().isEmpty() 
						&& inputPassword.getText().equals(null)
						&& inputPassword2.getText().equals(null)){
					
					errorText.setText("Please Fill out all form fields");				
				} else {
					new RegisterUser().execute();	
				}
				
			}
		});
	}
	
	class RegisterUser extends AsyncTask<String, String, String>{
		// Show Progress Dialog
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(RegistrationActivity.this);
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
			String email = inputEmail.getText().toString();
			String password = inputPassword.getText().toString();
			String password2 = inputPassword2.getText().toString();
			
			UserFunctions userFunction = new UserFunctions();
			// getting JSON object
			JSONObject json = userFunction.registerUser(email, password, password2);
						
			// Check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);
				Intent i = new Intent();
				
				if (success == 1) {
					// successfully registered user
					i.setClass(RegistrationActivity.this, SignInActivity.class);
				} else {
					// failed to register user
					Toast.makeText(RegistrationActivity.this, 
							"Error in Registration", Toast.LENGTH_LONG).show();
					//Log.e("failure", "asdfa");
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
}
