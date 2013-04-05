package com.example.jukeboxgh;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends Activity {

	Button registerButton, logInButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		// register button, logIn Button
		registerButton = (Button) findViewById(R.id.registerbutton);
		logInButton = (Button) findViewById(R.id.logInbutton);
		
		// Register TextView onClick Event
		registerButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// Start Registration Page Activity
				Intent i = new Intent(getApplicationContext(), RegistrationActivity.class);
				startActivity(i);
			}
		});
		
		logInButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// Start Sign In activity
				Intent i = new Intent(getApplicationContext(), SignInActivity.class);
				
				startActivity(i);
			}
		});
	}

	/**
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}**/

}
