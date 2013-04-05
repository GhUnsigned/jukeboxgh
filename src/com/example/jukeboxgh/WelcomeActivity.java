package com.example.jukeboxgh;

import com.example.jukeboxghTask.UserFunctions;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.ClipData.Item;
import android.view.Menu;
import android.view.MenuItem;

public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
		
		return true;
	}
	
	/**
	 * Handling menu click events
	 * @param item
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case R.id.action_settings:
			
			break;
		
		case R.id.action_logout:
			UserFunctions userFunctions = new UserFunctions();
			userFunctions.logoutUser(getApplicationContext());
			finish();
			Intent i = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(i);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
