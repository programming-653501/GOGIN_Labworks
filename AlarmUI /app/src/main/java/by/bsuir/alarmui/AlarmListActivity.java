package by.bsuir.alarmui;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class AlarmListActivity extends ListActivity {

	private AlarmListAdapter mListAdapter;
	private AlarmDataBase alrmDB = new AlarmDataBase(this);
	private Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mContext = this;
		
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		
		setContentView(R.layout.list_activity);

		mListAdapter = new AlarmListAdapter(this, alrmDB.getAllAlarms());
		
		setListAdapter(mListAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.alarm_list, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if(item.getItemId() == R.id.action_add_new_alarm) {
            startAlarmDetailsActivity(-1);
        }
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
	        mListAdapter.setAlarms(alrmDB.getAllAlarms());
	        mListAdapter.notifyDataSetChanged();
	    }
	}
	
	public void setAlarmEnabled(long id, boolean isEnabled) {
		AlarmsManager.cancelAlrms(this);
		
		AlarmModel alarmModel = alrmDB.getAlarm(id);
		alarmModel.isEnabled = isEnabled;
		alrmDB.update(alarmModel);
		
		AlarmsManager.setAlrms(this);
	}

	public void startAlarmDetailsActivity(long id) {
		Intent intent = new Intent(this, AlarmSettingsActivity.class);
		intent.putExtra("id", id);
		startActivityForResult(intent, 0);
	}
	
	public void deleteAlarm(long id) {
		final long alarmId = id;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("You are really want")
		.setTitle("Delete this alarm?")
		.setCancelable(true)
		.setNegativeButton("Cancel", null)
		.setPositiveButton("Ok", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//скончыць гадзинники
				AlarmsManager.cancelAlrms(mContext);
				alrmDB.delete(alarmId);
				mListAdapter.setAlarms(alrmDB.getAllAlarms());
				mListAdapter.notifyDataSetChanged();
				AlarmsManager.setAlrms(mContext);
			}
		}).show();
	}
}
