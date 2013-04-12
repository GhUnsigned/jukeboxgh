package com.example.jukeboxgh;







import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class Stream_activity extends Activity implements OnClickListener, OnTouchListener, OnCompletionListener, OnBufferingUpdateListener {
	private ImageButton buttonPlayPause;
	private SeekBar seekBarProgress;
	private MediaPlayer mediaPlayer;
	private int mediaFileLengthInMilliseconds; 
	
	
	private final Handler handler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stream);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		buttonPlayPause = (ImageButton)findViewById(R.id.ButtonTestPlayPause);
		buttonPlayPause.setOnClickListener(this);
		
		seekBarProgress = (SeekBar)findViewById(R.id.SeekBarTestPlay);	
		seekBarProgress.setMax(99); // It means 100% .0-99
		seekBarProgress.setOnTouchListener(this);
		
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setOnBufferingUpdateListener(this);
		mediaPlayer.setOnCompletionListener(this);
		
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try {
			mediaPlayer.setDataSource("http://192.168.1.66/social_music/Densu.mp3"); // setup song from http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
			mediaPlayer.prepare(); // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer. 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL
		
		if(!mediaPlayer.isPlaying()){
			mediaPlayer.start();
			buttonPlayPause.setImageResource(R.drawable.button_pause);
		}else {
			mediaPlayer.pause();
			buttonPlayPause.setImageResource(R.drawable.button_play);
		}
		
		primarySeekBarProgressUpdater();
	}

	@Override
	public boolean onTouch(View v, MotionEvent arg1) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.SeekBarTestPlay){
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
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		buttonPlayPause.setImageResource(R.drawable.button_play);
		
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub
		seekBarProgress.setSecondaryProgress(percent);
		
	}
}
