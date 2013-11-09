package com.kysoe.android_type_significant_motion;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private  SensorManager mSensorManager;
    private  Sensor mSigMotion;
    private  TriggerEventListener mListener = new TriggerListener();
    
    private TextView tv;
    
    class TriggerListener extends TriggerEventListener {
        public void onTrigger(TriggerEvent event) {
             // Do Work.

        	Toast.makeText(getApplicationContext(), "significant motion detected",Toast.LENGTH_SHORT).show();
        // As it is a one shot sensor, it will be canceled automatically.
        // SensorManager.requestTriggerSensor(this, mSigMotion); needs to
        // be called again, if needed.
        	long currentTimeStamp = System.currentTimeMillis();
        	tv.setText("Last movement triggered at "+String.valueOf(currentTimeStamp));
        }
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		  tv = (TextView) findViewById(R.id.tv); 
		 
		 mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
	     mSigMotion = mSensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    protected void onResume() {
        super.onResume();
        if (mSensorManager != null) {
        	mSensorManager.requestTriggerSensor(mListener, mSigMotion);
        }
        
    }

    protected void onPause() {
        super.onPause();
        // Call disable to ensure that the trigger request has been canceled.
        mSensorManager.cancelTriggerSensor(mListener, mSigMotion);
    }


}
