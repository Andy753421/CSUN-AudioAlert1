package info.ss12.audioalertsystem;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.Switch;
import android.os.Vibrator;

public class MainActivity extends Activity implements OnSignalsDetectedListener
{
	private final String TAG = "Main Activity";
	
	private Vibrator vibrator;
	private Switch micSwitch;
	private ButtonController buttonControl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		buttonControl = new ButtonController(this);
		micSwitch = (Switch )findViewById(R.id.mic_switch);
		micSwitch.setOnClickListener(buttonControl);
		micSwitch.setOnTouchListener(buttonControl);
		vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onFireAlarmDetected()
	{
		Log.d(TAG, "FIRE ALARM DETECTED");
		vibrator.vibrate(5000);
	}

}
