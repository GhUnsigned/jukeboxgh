package com.example.jukeboxgh;

import java.util.ArrayList;



import com.example.jukeboxghTask.ArtistDetails;


import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class MainActivity extends Activity implements OnTouchListener, OnCompletionListener, OnBufferingUpdateListener {
TabHost tb;
ListView songs;
ArrayList<ArtistDetails> details;
private SeekBar seekBarProgress;
private MediaPlayer mediaPlayer;
private int mediaFileLengthInMilliseconds; 
private final Handler handler = new Handler();
ImageButton bd;
int prevPos, currentPos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tb=(TabHost) findViewById(R.id.mainTabHost);
		songs=(ListView) findViewById(R.id.lvMainArtist);
		details=new ArrayList<ArtistDetails>();
		prevPos=currentPos=0;
		initView();
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
		
		
		//this is just an example to test the adapter but we will bind it later with the json u are goin to send
		binddata(R.drawable.dog, "Sakodie", "Dome wu", "http://192.168.1.66/social_music/Densu.mp3");
		binddata(R.drawable.dog, "Samini", "new song", "http://192.168.1.66/social_music/game.mp3");
		songs.setAdapter(new CustomAdapter(details, this));
	}

	private void initView() {
		// TODO Auto-generated method stu
		seekBarProgress = (SeekBar)findViewById(R.id.SeekBarTestPlay1);	
		seekBarProgress.setMax(99); // It means 100% .0-99
		seekBarProgress.setOnTouchListener(this);
		
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setOnBufferingUpdateListener(this);
		mediaPlayer.setOnCompletionListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



	
	private class CustomAdapter extends BaseAdapter{
		private ArrayList<ArtistDetails> _data;
	     Context _c;
	     
	     public CustomAdapter(ArrayList<ArtistDetails> data, Context c) {
			// TODO Auto-generated constructor stub
	    	 _data=data;
	    	 _c=c;
		}
	     @Override
			public int getCount() {
				// TODO Auto-generated method stub
				return _data.size();
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v=convertView;
			if(v==null){
				LayoutInflater vi=(LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v=vi.inflate(R.layout.arrangement, null);
			}
			
			ImageView image=(ImageView) v.findViewById(R.id.ivImage);
			TextView aName=(TextView) v.findViewById(R.id.tvArtistName);
			TextView sTitle=(TextView) v.findViewById(R.id.tvSongTitle);
			final ImageButton play=(ImageButton) v.findViewById(R.id.bPlay);
			ImageButton download= (ImageButton) v.findViewById(R.id.bDownload);
			
			ArtistDetails at=_data.get(position);
			image.setImageResource(at.icon);
			aName.setText(at.artistName);
			sTitle.setText(at.songTitle);
			
			
	play.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				stream(_data.get(position).getSongURL(),play);
				currentPos=position;
				}
			});
			
			download.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					copy(_data.get(position).getSongURL());
				}
			});
			return v;
		}
		
	}
	
	
	private void stream(String songUrl,ImageButton s){
		//streaming will be done here
		try {
			if(currentPos !=prevPos){
				mediaPlayer.stop();	
				currentPos=prevPos;
			}
			bd=s;
			mediaPlayer.setDataSource(songUrl); // setup song from http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
			mediaPlayer.prepare(); // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer. 
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL
		if(!mediaPlayer.isPlaying()){
			mediaPlayer.start();
			s.setImageResource(R.drawable.button_pause);
		}else {
			mediaPlayer.pause();
			s.setImageResource(R.drawable.button_play);
		}
		//prevPos=currentPos;
		primarySeekBarProgressUpdater();
	}
	
	private void copy(String songUrl){
		//copy item here
		
	}
	
	private void binddata(int icon, String artistName, String songTitle, String url){
		ArtistDetails Details;
		Details=new ArtistDetails();
		Details.setIcon(icon);
		Details.setArtistName(artistName);
		Details.setSongTitle(songTitle);
		Details.setSongURL(url);
		details.add(Details);
	}

	@Override
	public void onBufferingUpdate(MediaPlayer arg0, int percent) {
		// TODO Auto-generated method stub
		seekBarProgress.setSecondaryProgress(percent);
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		bd.setImageResource(R.drawable.button_play);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.SeekBarTestPlay1){
			/** Seekbar onTouch event handler. Method which seeks MediaPlayer to seekBar primary progress position*/
			if(mediaPlayer.isPlaying()){
		    	SeekBar sb = (SeekBar)v;
				int playPositionInMillisecconds = (mediaFileLengthInMilliseconds / 100) * sb.getProgress();
				mediaPlayer.seekTo(playPositionInMillisecconds);
			}
		}
		return false;
	}
	
@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
	mediaPlayer.release();
		super.onDestroy();
	}
	
	
	 private void primarySeekBarProgressUpdater() {
	    	seekBarProgress.setProgress((int)(((float)mediaPlayer.getCurrentPosition()/mediaFileLengthInMilliseconds)*100)); // This math construction give a percentage of "was playing"/"song length"
			if (mediaPlayer.isPlaying()) {
				Runnable notification = new Runnable() {
			        public void run() {
			        	primarySeekBarProgressUpdater();
					}
			    };
			    handler.postDelayed(notification,1000);
	    	}
	    }
}
