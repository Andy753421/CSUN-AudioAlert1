package info.ss12.audioalertsystem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class ButtonController implements OnClickListener, OnTouchListener
{
	private final String TAG = "Button Controller";
	private MainActivity mainActivity;
	private LocalService localService;
	private Intent intent;
	private Messenger messenger;
	
	public ButtonController(MainActivity mainActivity)
	{
		this.mainActivity = mainActivity;
		localService = new LocalService();
		intent = new Intent(mainActivity, LocalService.class);
		messenger = new Messenger(mainActivity.getHandler());
	}
	
	@Override
	public void onClick(View v)
	{
		Log.d(TAG, "On CLICK CALLED");
		handleButtonToggle(v);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
//		Log.d(TAG, "On TOUCH CALLED");
//		int eventId = event.getAction();
//
//		if (eventId == MotionEvent.ACTION_MOVE)
//		{
//			//For UI purposes
//			if(v instanceof ToggleButton)
//			{
//				ToggleButton temp = (ToggleButton) v;
//				if(temp.isChecked())
//					temp.setChecked(false);
//				else
//					temp.setChecked(true);
//			}
//			handleButtonToggle(v);
//			return true;
//		}
		return false;
	}
	private boolean swap = false;
	public void handleButtonToggle(View v)
	{
		int id = v.getId();

		
		if(id == R.id.mic_switch)
		{
			Log.d(TAG, "press");
			ToggleButton micS = (ToggleButton) v;
			ImageView micImage = (ImageView) mainActivity.findViewById(R.id.mic_image);
			if (micS.isChecked())
			{
				Log.d(TAG, "switch on");
				micImage.setImageBitmap(BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.mic_icon_on));			
				intent.putExtra("HANDLER", messenger);
				mainActivity.startService(intent);
			}
			else
			{
				Log.d(TAG, "switch off");
				micImage.setImageBitmap(BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.mic_icon_off));
				mainActivity.stopService(intent);
				turnOffAllNoti();
			}
			
		} 
		else if(id == R.id.test_alert)
		{
			turnOffAllNoti();
		}
		else 
		{
			Log.d("swtich button test", "default");
		}
		
		
		
		
	}
	
	public void turnOffAllNoti()
	{
		Message msg = Message.obtain();
		msg.arg1 = !swap ? 1 : 0;
		swap ^= true;
		try 
		{
			messenger.send(msg);
		} 
		catch (RemoteException e) 
		{
			e.printStackTrace();
		}
	}

}
