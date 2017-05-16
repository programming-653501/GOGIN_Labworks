package by.bsuir.alarmui;

import android.net.Uri;

public class AlarmModel {

	public static final int MONDAY = 1;
	public static final int TUESDAY = 2;
	public static final int WEDNESDAY = 3;
	public static final int THURSDAY = 4;
	public static final int FRDIAY = 5;
	public static final int SATURDAY = 6;
	public static final int SUNDAY = 0;
	
	public long id = -1;
	public int timeInHours;
	public int timeInMinutes;
	private boolean repeatInDays[];
	public boolean repeatWeekly;
	public Uri alarmSong;
	public String nameOfAlarm;
	public boolean isEnabled;
	
	public AlarmModel() {
		repeatInDays = new boolean[7];
	}
    public boolean getRepeatInDay(int dayOfWeek) {
        return repeatInDays[dayOfWeek];
    }
	public void setRepeatInDay(int dayOfWeek, boolean value) {
		repeatInDays[dayOfWeek] = value;
	}

	
}
