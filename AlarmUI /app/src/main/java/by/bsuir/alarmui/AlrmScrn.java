package by.bsuir.alarmui;

import android.app.Activity;
import android.media.session.MediaController;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.os.Handler;
import android.view.View.OnClickListener;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.VideoView;

public class AlrmScrn extends Activity {
	
	public final String TAG = this.getClass().getSimpleName();

	private WakeLock mAlarmBlock;
	private MediaPlayer mPlayer;

	private static final int WAKELOCK_TIMEOUT = 60 * 1000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Макет ўстаноўкі
		this.setContentView(R.layout.alarm_screen);

		String name = getIntent().getStringExtra(AlarmsManager.NAME);
		int timeHour = getIntent().getIntExtra(AlarmsManager.TIME_HOUR, 0);
		int timeMinute = getIntent().getIntExtra(AlarmsManager.TIME_MINUTE, 0);
		String tone = getIntent().getStringExtra(AlarmsManager.SING);
		
		TextView tvName = (TextView) findViewById(R.id.alarm_screen_name);
		tvName.setText(name);
		
		TextView tvTime = (TextView) findViewById(R.id.alarm_screen_time);
		tvTime.setText(String.format("%02d : %02d", timeHour, timeMinute));
		
		Button dismissButton = (Button) findViewById(R.id.alarm_screen_button);
		dismissButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				mPlayer.stop();
				finish();
			}
		});

		//Прайграванне сігналу будзільніка
		mPlayer = new MediaPlayer();
		try {
			if (tone != null && !tone.equals("")) {
				Uri toneUri = Uri.parse(tone);
				if (toneUri != null) {
					mPlayer.setDataSource(this, toneUri);
					mPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
					mPlayer.setLooping(true);
					mPlayer.prepare();
					mPlayer.start();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Ensure wakelock release
		Runnable releaseWakelock = new Runnable() {

			@Override
			public void run() {
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

				if (mAlarmBlock != null && mAlarmBlock.isHeld()) {
					mAlarmBlock.release();
				}
			}
		};

		new Handler().postDelayed(releaseWakelock, WAKELOCK_TIMEOUT);
        String videoSource ="http://cs8.pikabu.ru/post_img/2017/04/22/7/1492861367129613788.webm";

        VideoView videoView = (VideoView) findViewById(R.id.videoView);

        videoView.setVideoURI(Uri.parse(videoSource));

        videoView.requestFocus(0);
        videoView.start();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();

		// Усталяваць акно, каб захаваць экран
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

		//Блакіяваць гадзіннік
		PowerManager powerManager = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
		if (mAlarmBlock == null) {
			mAlarmBlock = powerManager.newWakeLock((PowerManager.FULL_WAKE_LOCK | PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), TAG);
		}

		if (!mAlarmBlock.isHeld()) {
			mAlarmBlock.acquire();
			Log.i(TAG, "ЕЕЕЕ, Alarm blocked.");
		}

	}

	@Override
	protected void onPause() {
		super.onPause();

		if (mAlarmBlock != null && mAlarmBlock.isHeld()) {
			mAlarmBlock.release();
		}
	}
}
