package info.ss12.audioalertsystem;

import info.ss12.audioalertsystem.notification.FlashNotification;
import info.ss12.audioalertsystem.notification.NotificationBarNotification;
import info.ss12.audioalertsystem.notification.VibrateNotification;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.ToggleButton;
import android.content.Context;

public class MainActivity extends Activity
{
	private final String TAG = "Main Activity";
	private boolean alarmActivated = false;

	
	private ToggleButton micSwitch;
	private Button testAlert;
	
	private ButtonController buttonControl;
	
	private Context context;
	private VibrateNotification vibrate;
	private FlashNotification flash;
	private NotificationBarNotification bar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		buttonControl = new ButtonController(this);
		micSwitch = (ToggleButton )findViewById(R.id.mic_switch);
		micSwitch.setOnClickListener(buttonControl);
		micSwitch.setOnTouchListener(buttonControl);

		testAlert = (Button)findViewById(R.id.test_alert);
		testAlert.setOnClickListener(buttonControl);
		
		context = this.getApplicationContext();
		vibrate = new VibrateNotification(this);
		flash = new FlashNotification(this);
		bar = new NotificationBarNotification();		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg) 
		{
			
			if(msg.arg1 == 1 && !alarmActivated) // Turn On
			{
				bar.startNotify();
				flash.startNotify();
				vibrate.startNotify();
				alarmActivated = true;
			}
			else if(msg.arg1 == 0 && alarmActivated)
			{
				alarmActivated = false;
				bar.stopNotify();
				flash.stopNotify();
				vibrate.stopNotify();	
			}
			Log.d(TAG, "FIRE ALARM DETECTED");	
		}
		
	};

	public Handler getHandler() 
	{
		return handler;
	}
	
	public void setHandler(Handler handler) 
	{
		this.handler = handler;
	}

}
