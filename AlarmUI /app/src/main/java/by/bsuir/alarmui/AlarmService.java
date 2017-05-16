package by.bsuir.alarmui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AlarmService extends Service {

	public static String TAG = AlarmService.class.getSimpleName();
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		Intent alarmIntent = new Intent(getBaseContext(), AlrmScrn.class);
		alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		alarmIntent.putExtras(intent);
		getApplication().startActivity(alarmIntent);
		
		AlarmsManager.setAlrms(this);
		
		return super.onStartCommand(intent, flags, startId);
	}
	
}