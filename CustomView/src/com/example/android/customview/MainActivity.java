/* Copyright (C) 2012 The Android Open Source Project

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.example.android.customview;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener, Runnable, SensorEventListener {
    
	private MieChart mie;
	private Handler handler = new Handler();
	private float inputAngle = 0;
	
	final int TIMESLIZE = 1;

	
	/**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        mie = (MieChart) this.findViewById(R.id.Mie);

        ((Button) findViewById(R.id.Reset)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Resources res = getResources();
                
                mie.mPlayGround.startCar(40, 40, res.getColor(R.color.seafoam), 0);
                mie.mPlayGround.startCar(40, 620, res.getColor(R.color.emerald), 90);
                mie.mPlayGround.startCar(620, 40, res.getColor(R.color.purple), 270);
                mie.mPlayGround.startCar(620, 620, res.getColor(R.color.lax), 180);
            }
        });
        
        ((Button) findViewById(R.id.Left)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mie.mPlayGround.steerLeft();
            }
        });
        
        ((Button) findViewById(R.id.Rite)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mie.mPlayGround.steerRite();
            }
        });
        
        //begin wahsinn
        
    	SensorManager sensorManager;
    	Sensor accelerometer;
    	
    	SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    	Sensor mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    	mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        

        
        //end wahnsinn
        

		handler.post(this);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		moveCars();
	}
	
	public void moveCars() {
		handler.postDelayed(this, TIMESLIZE);
		mie.mPlayGround.moveCars(inputAngle);
		
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent SensorEvent) {
		Sensor mySensor = SensorEvent.sensor;
		 
	    if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
	        // float x = SensorEvent.values[0];
	        inputAngle = SensorEvent.values[1];
	        // float z = SensorEvent.values[2];
	        
	        // Log.e("Y_value", String.valueOf(y));
	        
	    }
		
	}
}

