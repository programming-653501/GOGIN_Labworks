package by.bsuir.alarmui;




import java.util.ArrayList;
import java.util.List;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;




public class AlarmDataBase extends SQLiteOpenHelper {

	public static final int DB_VERSION = 3; //типа крутой, аж 3 версия
    public static final String DB_NAME = "alarm_data.db";
	
	private static final String CREATE_SQL = "CREATE TABLE " +
            AlarmInfo.Alarm.TABLE_NAME + " (" +
			AlarmInfo.Alarm._ID +
			" INTEGER PRIMARY KEY AUTOINCREMENT," +
			AlarmInfo.Alarm.ALRM_NAME +
			" TEXT," +
			AlarmInfo.Alarm.TIME_HOUR +
			" INTEGER," +
			AlarmInfo.Alarm.TIME_MINUTE +
			" INTEGER," +
			AlarmInfo.Alarm.REPEAT_DAYS +
			" TEXT," +
			AlarmInfo.Alarm.REPEAT_WEEKLY +
			" BOOLEAN," +
			AlarmInfo.Alarm.ALRM_SONG +
			" TEXT," +
			AlarmInfo.Alarm.ALRM_ENABLED +
			" BOOLEAN" +
	    " )";
	
	private static final String DELETE_ALARM_FROM_DB =
		    "DROP TABLE IF EXISTS " + AlarmInfo.Alarm.TABLE_NAME;
    
	public AlarmDataBase(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DELETE_ALARM_FROM_DB);
        onCreate(db);
	}

    private ContentValues fillContent(AlarmModel model) {
        ContentValues values = new ContentValues();
        values.put(AlarmInfo.Alarm.ALRM_NAME, model.nameOfAlarm);
        values.put(AlarmInfo.Alarm.REPEAT_WEEKLY, model.repeatWeekly);
        values.put(AlarmInfo.Alarm.TIME_HOUR, model.timeInHours);
        values.put(AlarmInfo.Alarm.TIME_MINUTE, model.timeInMinutes);
        values.put(AlarmInfo.Alarm.ALRM_ENABLED, model.isEnabled);
        values.put(AlarmInfo.Alarm.ALRM_SONG,
                ((model.alarmSong != null) ?
                        (model.alarmSong.toString()) : ("")));

        String repeatingDays = "";
        for (int i = 0; i < 7; i++) {
            repeatingDays += model.getRepeatInDay(i) + ",";
        }
        values.put(AlarmInfo.Alarm.REPEAT_DAYS, repeatingDays);

        return values;
    }

	private AlarmModel fillModel(Cursor c) {
		AlarmModel model = new AlarmModel();
		model.id = c.getLong(c.getColumnIndex(AlarmInfo.Alarm._ID));
		model.nameOfAlarm = c.getString(c.getColumnIndex(AlarmInfo.Alarm.ALRM_NAME));
		model.timeInHours = c.getInt(c.getColumnIndex(AlarmInfo.Alarm.TIME_HOUR));
		model.timeInMinutes = c.getInt(c.getColumnIndex(AlarmInfo.Alarm.TIME_MINUTE));
		model.repeatWeekly = c.getInt(c.getColumnIndex(AlarmInfo.Alarm.REPEAT_WEEKLY)) == 0 ? false : true;
		model.alarmSong = c.getString(c.getColumnIndex(AlarmInfo.Alarm.ALRM_SONG)) != "" ? Uri.parse(c.getString(c.getColumnIndex(AlarmInfo.Alarm.ALRM_SONG))) : null;
		model.isEnabled = c.getInt(c.getColumnIndex(AlarmInfo.Alarm.ALRM_ENABLED)) == 0 ? false : true;

		String[] repeatingDays = c.getString(c.getColumnIndex(AlarmInfo.Alarm.REPEAT_DAYS)).split(",");
		for (int i = 0; i < repeatingDays.length; ++i) {
			model.setRepeatInDay(i, repeatingDays[i].equals("false") ? false : true);
		}

		return model;
	}


	public long create(AlarmModel model) {
		ContentValues values = fillContent(model);
        return getWritableDatabase().insert(AlarmInfo.Alarm.TABLE_NAME, null, values);
	}

    public int delete(long id) {
        return getWritableDatabase().delete(AlarmInfo.Alarm.TABLE_NAME,
                AlarmInfo.Alarm._ID + " = ?",
                new String[] {
                        String.valueOf(id)
                });
    }

	public long update(AlarmModel model) {
		ContentValues values = fillContent(model);
        return getWritableDatabase().update(AlarmInfo.Alarm.TABLE_NAME,
                                            values,
                                            AlarmInfo.Alarm._ID + " = ?",
                                            new String[] {
                                                String.valueOf(model.id)
                                            });
	}

    public List<AlarmModel> getAllAlarms() {
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String select = "SELECT * FROM " + AlarmInfo.Alarm.TABLE_NAME;
        Cursor cursor = sqldb.rawQuery(select, null);
        List<AlarmModel> alarmList = new ArrayList<AlarmModel>();

        while (cursor.moveToNext()) {
            alarmList.add(fillModel(cursor));
        }

        if (!alarmList.isEmpty()) {
            return alarmList;
        }
        return null;
    }

	public AlarmModel getAlarm(long id) {
		SQLiteDatabase sqldb = this.getReadableDatabase();
        String select = "SELECT * FROM " +
                        AlarmInfo.Alarm.TABLE_NAME +
                        " WHERE " + AlarmInfo.Alarm._ID +
                        " = " + id;
		Cursor cursor = sqldb.rawQuery(select, null);

		if (cursor.moveToNext()) {
			return fillModel(cursor);
		}

		return null;
	}

}
