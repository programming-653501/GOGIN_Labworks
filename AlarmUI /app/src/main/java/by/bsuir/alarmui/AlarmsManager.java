package by.bsuir.alarmui;

import java.util.Calendar;
import java.util.List;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.app.AlarmManager;

public class AlarmsManager extends BroadcastReceiver {

	public static final String ID = "id";
	public static final String NAME = "nameOfAlarm";
	public static final String TIME_HOUR = "timeInHours";
	public static final String TIME_MINUTE = "timeInMinutes";
	public static final String SING = "alarmSong";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		setAlrms(context);
	}
	
	public static void setAlrms(Context context) {
		cancelAlrms(context);
		
		AlarmDataBase dbHelper = new AlarmDataBase(context);

		List<AlarmModel> alarmsList =  dbHelper.getAllAlarms();
		
		for (AlarmModel alarm : alarmsList) {
			if (alarm.isEnabled) {

				PendingIntent pendingIntent = createPendingIntent(context, alarm);

				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.HOUR_OF_DAY, alarm.timeInHours);
				calendar.set(Calendar.MINUTE, alarm.timeInMinutes);
				calendar.set(Calendar.SECOND, 00);

				//Знайсці наступны час, каб усталяваць
				final int thisDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
				final int thisHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
				final int thisMinute = Calendar.getInstance().get(Calendar.MINUTE);
				boolean alarmSet = false;
				
				//Спачатку праверце, калі гэта ў канцы тыдня
				for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; ++dayOfWeek) {
					if (alarm.getRepeatInDay(dayOfWeek - 1) && dayOfWeek >= thisDay &&
							!(dayOfWeek == thisDay && alarm.timeInHours < thisHour) &&
							!(dayOfWeek == thisDay && alarm.timeInHours == thisHour && alarm.timeInMinutes <= thisMinute)) {
						calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
						
						setAlarm(context, calendar, pendingIntent);
						alarmSet = true;
						break;
					}
				}
				
				//Інакш праверыць, калі гэта ў пачатку тыдня
				if (!alarmSet) {
					for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; ++dayOfWeek) {
						if (alarm.getRepeatInDay(dayOfWeek - 1) && dayOfWeek <= thisDay && alarm.repeatWeekly) {
							calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
							calendar.add(Calendar.WEEK_OF_YEAR, 1);
							
							setAlarm(context, calendar, pendingIntent);
							alarmSet = true;
							break;
						}
					}
				}
			}
		}
	}
	
	@SuppressLint("NewApi")
	private static void setAlarm(Context context, Calendar calendar, PendingIntent pIntent) {
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
			alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
		} else {
			alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
		}
	}
	
	public static void cancelAlrms(Context context) {
		AlarmDataBase dbHelper = new AlarmDataBase(context);
		
		List<AlarmModel> alarms =  dbHelper.getAllAlarms();
		
 		if (alarms != null) {
			for (AlarmModel alarm : alarms) {
				if (alarm.isEnabled) {
					PendingIntent pIntent = createPendingIntent(context, alarm);
	
					AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
					alarmManager.cancel(pIntent);
				}
			}
 		}
	}

	private static PendingIntent createPendingIntent(Context context, AlarmModel model) {
		Intent intent = new Intent(context, AlarmService.class);
		intent.putExtra(SING, model.alarmSong.toString());
		intent.putExtra(ID, model.id);
		intent.putExtra(NAME, model.nameOfAlarm);
		intent.putExtra(TIME_MINUTE, model.timeInMinutes);
		intent.putExtra(TIME_HOUR, model.timeInHours);
		
		return PendingIntent.getService(context, (int) model.id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}
}
