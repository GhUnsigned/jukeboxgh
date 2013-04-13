package com.example.jukeboxgh;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.app.Activity;
import android.content.Intent;

public class WelcomeOneActivity extends Activity {

	RelativeLayout layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome_one);
		
		layout = (RelativeLayout) findViewById(R.id.welcomeone);
		
		layout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
				Intent i = new Intent(getApplicationContext(), FollowActivity.class);
				
				startActivity(i);
			}
		});
	}

}
