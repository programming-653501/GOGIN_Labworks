package by.bsuir.alarmui;

import android.provider.BaseColumns;

public final class AlarmInfo {

	public static abstract class Alarm implements BaseColumns {
		public static final String TABLE_NAME = "application_alarms";
		public static final String ALRM_NAME = "nameOfAlarm";
		public static final String TIME_HOUR = "hour";
		public static final String TIME_MINUTE = "minute";
		public static final String REPEAT_DAYS = "days";
		public static final String REPEAT_WEEKLY = "weekly";
        public static final String ALRM_SONG = "song";
        public static final String ALRM_ENABLED = "enabled";
	}
	
}
