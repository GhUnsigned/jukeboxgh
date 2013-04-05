package com.example.jukeboxgh;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends Activity {
TabHost tb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tb=(TabHost) findViewById(R.id.mainTabHost);
		tb.setup();
		TabSpec spec=tb.newTabSpec("tag1");
		spec.setContent(R.id.tab1);
		spec.setIndicator("Artist");
		tb.addTab(spec);
		spec=tb.newTabSpec("tab2");
		spec.setContent(R.id.tab2);
		spec.setIndicator("Feed");
		tb.addTab(spec);
		
		spec=tb.newTabSpec("tab2");
		spec.setContent(R.id.tab3);
		spec.setIndicator("Subscribe");
		tb.addTab(spec);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
