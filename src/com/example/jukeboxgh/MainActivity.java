package com.example.jukeboxgh;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends Activity implements OnClickListener {
TabHost tb;
Button go;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tb=(TabHost) findViewById(R.id.mainTabHost);
		go=(Button) findViewById(R.id.goToStream);
		go.setOnClickListener(this);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.goToStream:
			startActivity(new Intent(this, Stream_activity.class));
			break;
		}
		
	}

}
