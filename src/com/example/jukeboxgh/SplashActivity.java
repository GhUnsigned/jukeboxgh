package com.example.jukeboxgh;

import com.example.jukeboxghTask.UserFunctions;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class SplashActivity extends Activity {
	
	// Time for splash display 5000
	protected int _splashTime = 1000;
	private Thread splashThread;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        final SplashActivity sPlashScreen = this;
        
        // thread for displaying the SplashScreen
	    splashThread = new Thread() {
	        @SuppressWarnings("deprecation")
			@Override
	        public void run() {
	            try {	            	
	            	synchronized(this){
	            		//wait 5 sec
	            		wait(_splashTime);
	            	}

	            } catch(InterruptedException e) {} 
	            finally {
	                finish();

	                //start a new activity
	                Intent i = new Intent();
	                UserFunctions userFunctions = new UserFunctions();
	                
	                if(userFunctions.isUserLoggedIn(getApplicationContext())) {
	                	i.setClass(sPlashScreen, MainActivity.class);
	                } else {
	                	i.setClass(sPlashScreen, LoginActivity.class);
	                }
	        		startActivity(i);

	                //stop();
	              //  startActivity(new Intent(SplashActivity.this, MainActivity.class));
	            }
	        }
	    };
	    
	    splashThread.start();
    }
    
}
