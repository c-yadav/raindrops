package com.openE.framework;

import java.math.BigDecimal;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class AccelerometerHandler implements SensorEventListener {
	
    float x;
    float y;
    float z;
    
    float lastX;
    float lastY;
    float lastZ;
    
    private boolean initialized;
    private final float NOISE = 1f; 
    
    float deltaX;
    float deltaY;
    float deltaZ;
    
    float accelX;
    float accelY;
    float accelZ;
    
	private float[] gravity;
	private float[] linear_acceleration;

    public AccelerometerHandler(Context context) {
        SensorManager manager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
            Sensor accelerometer = manager.getSensorList(
                    Sensor.TYPE_ACCELEROMETER).get(0);
            manager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_GAME);
        }
        
        gravity = new float[3];
        linear_acceleration = new float[3];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // nothing to do here
    }

    @Override
	public void onSensorChanged(SensorEvent event) {
    	
        // alpha is calculated as t / (t + dT)
        // with t, the low-pass filter's time-constant
        // and dT, the event delivery rate
/*
        final float alpha = 0.8f;

        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        linear_acceleration[0] = round( (event.values[0] - gravity[0]), 2);
        linear_acceleration[1] = round( (event.values[1] - gravity[1]), 2);
        linear_acceleration[2] = round( (event.values[2] - gravity[2]), 2);
*/
    	

        linear_acceleration[0] = round( event.values[0], 2);
        linear_acceleration[1] = round( event.values[1], 2);
        linear_acceleration[2] = round( event.values[2], 2);
    	
		if (!initialized) {
			lastX = linear_acceleration[0];
			lastY = linear_acceleration[1];
			lastZ = linear_acceleration[2];

			initialized = true;
		}
		else {
			
			float deltaX = Math.abs(lastX - linear_acceleration[0]);
			float deltaY = Math.abs(lastY - linear_acceleration[1]);
			float deltaZ = Math.abs(lastZ - linear_acceleration[2]);

			//Log.d("Diff","DeltaX|" + deltaX + " lastX|" + lastX );
			if (deltaX < NOISE) {
				linear_acceleration[0] = lastX;
			}
			
			if (deltaY < NOISE) {
				linear_acceleration[1] = lastY;
			}
			
			if (deltaZ < NOISE) {
				linear_acceleration[2] = lastZ;
			}
			
			lastX = linear_acceleration[0];
			lastY = linear_acceleration[1];
			lastZ = linear_acceleration[2];
		}
		
    }

    public float getaccelX() {
        //return accelX;
    	return  round(linear_acceleration[0],2);
    }

    public float getaccelY() {
        return  round(linear_acceleration[1],2);
    }

    public float getaccelZ() {
        return  round(linear_acceleration[2],2);
    }
    
	public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}
