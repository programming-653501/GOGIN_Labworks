package by.bsuir.alarmui;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

public class AlarmSettingsActivity extends Activity {
	
	private AlarmDataBase dbHelper = new AlarmDataBase(this);
	
	private AlarmModel alarmModel;
	
	private TimePicker timePicker;
	private EditText edtName;
	private ListItem cswWeekly;
	private ListItem cswSunday;
	private ListItem cswMonday;
	private ListItem cswTuesday;
	private ListItem cswWednesday;
	private ListItem cswThursday;
	private ListItem cswFriday;
	private ListItem cswSaturday;
	private TextView txtSongSelector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_details);
		getActionBar().setTitle("CREATE ALARM");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		timePicker = (TimePicker) findViewById(R.id.details_time_picker);
		edtName = (EditText) findViewById(R.id.details_name);
		cswMonday = (ListItem) findViewById(R.id.repeat_monday);
		cswTuesday = (ListItem) findViewById(R.id.repeat_tuesday);
		cswWednesday = (ListItem) findViewById(R.id.repeat_wednesday);
		cswThursday = (ListItem) findViewById(R.id.repeat_thursday);
		cswFriday = (ListItem) findViewById(R.id.repeat_friday);
		cswSaturday = (ListItem) findViewById(R.id.repeat_saturday);
		cswSunday = (ListItem) findViewById(R.id.repeat_sunday);
		cswWeekly = (ListItem) findViewById(R.id.repeat_weekly);
		txtSongSelector = (TextView) findViewById(R.id.alarm_song_selection);
		
		long id = getIntent().getExtras().getLong("id");
		
		if (id == -1) {
			alarmModel = new AlarmModel();
		} else {
			alarmModel = dbHelper.getAlarm(id);
			
			timePicker.setCurrentMinute(alarmModel.timeInMinutes);
			timePicker.setCurrentHour(alarmModel.timeInHours);
			edtName.setText(alarmModel.nameOfAlarm);
			cswMonday.setChecked(alarmModel.getRepeatInDay(AlarmModel.MONDAY));
			cswTuesday.setChecked(alarmModel.getRepeatInDay(AlarmModel.TUESDAY));
			cswWednesday.setChecked(alarmModel.getRepeatInDay(AlarmModel.WEDNESDAY));
			cswThursday.setChecked(alarmModel.getRepeatInDay(AlarmModel.THURSDAY));
			cswFriday.setChecked(alarmModel.getRepeatInDay(AlarmModel.FRDIAY));
			cswSaturday.setChecked(alarmModel.getRepeatInDay(AlarmModel.SATURDAY));
			cswSunday.setChecked(alarmModel.getRepeatInDay(AlarmModel.SUNDAY));
			cswWeekly.setChecked(alarmModel.repeatWeekly);
			txtSongSelector.setText(RingtoneManager.getRingtone(this, alarmModel.alarmSong).getTitle(this));
		}

		final LinearLayout ringToneContainer = (LinearLayout) findViewById(R.id.alarm_ringtone_container);
		ringToneContainer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
				startActivityForResult(intent , 1);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
	        switch (requestCode) {
		        case 1: {
                    txtSongSelector.setText(
                            RingtoneManager.getRingtone(this, alarmModel.alarmSong).getTitle(this));
		        	alarmModel.alarmSong =
                            data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
		            break;
		        }
		        default: {
		            break;
		        }
	        }
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.alarm_details, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case android.R.id.home: {
				finish();
				break;
			}
			case R.id.action_save_alarm_details: {
				updateModelFromLayout();
				
				AlarmsManager.cancelAlrms(this);
				
				if (alarmModel.id < 0) {
					dbHelper.create(alarmModel);
				} else {
					dbHelper.update(alarmModel);
				}
				
				AlarmsManager.setAlrms(this);
				
				setResult(RESULT_OK);
				finish();
			}
		}

		return super.onOptionsItemSelected(item);
	}
	
	private void updateModelFromLayout() {		
		alarmModel.timeInMinutes = timePicker.getCurrentMinute().intValue();
		alarmModel.timeInHours = timePicker.getCurrentHour().intValue();
		alarmModel.nameOfAlarm = edtName.getText().toString();
		alarmModel.repeatWeekly = cswWeekly.isChecked();
		alarmModel.setRepeatInDay(AlarmModel.SUNDAY, cswSunday.isChecked());
		alarmModel.setRepeatInDay(AlarmModel.MONDAY, cswMonday.isChecked());
		alarmModel.setRepeatInDay(AlarmModel.TUESDAY, cswTuesday.isChecked());
		alarmModel.setRepeatInDay(AlarmModel.WEDNESDAY, cswWednesday.isChecked());
		alarmModel.setRepeatInDay(AlarmModel.THURSDAY, cswThursday.isChecked());
		alarmModel.setRepeatInDay(AlarmModel.FRDIAY, cswFriday.isChecked());
		alarmModel.setRepeatInDay(AlarmModel.SATURDAY, cswSaturday.isChecked());
		alarmModel.isEnabled = true;
	}
	
}
