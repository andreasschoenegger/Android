package com.example.android.customview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Build;
import android.view.View;

/**
 * Internal child class that draws the pie chart onto a separate hardware layer
 * when necessary.
 */
class PlayGround extends View {

    private List<Car> mData = new ArrayList<Car>();
    public RectF mBounds;

    /**
     * Construct a PlayGround
     *
     * @param context
     */
    public PlayGround(Context context) {
        super(context);
    }

    /**
     * Enable hardware acceleration (consumes memory)
     */
    public void accelerate() {
        setLayerToHW(this);
    }

    /**
     * Disable hardware acceleration (releases memory)
     */
    public void decelerate() {
        setLayerToSW(this);
    }

    
    private void setLayerToSW(View v) {
        if (!v.isInEditMode() && Build.VERSION.SDK_INT >= 11) {
           // setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    private void setLayerToHW(View v) {
        if (!v.isInEditMode() && Build.VERSION.SDK_INT >= 11) {
           // setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
    }    
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Car currentCar : mData) {
        	currentCar.drawMe(canvas);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mBounds = new RectF(0, 0, w, h);
    }


    

    public void startCar(float x, float y, int color, float angle) {
    	// Create a car
    	Car car = new Car(x, y, color, angle);
    	// Add it to my CarList
    	mData.add(car); 
    }

	public void moveCars(float inputAngle) {
        for (Car currentCar : mData) {
        	currentCar.move(inputAngle);
        }
    	invalidate();
	}

	public void steerRite() {
        for (Car currentCar : mData) {
        	currentCar.steerRite();
        }
	}
    
	public void steerLeft() {
        for (Car currentCar : mData) {
        	currentCar.steerLeft();
        }
	}
    
}

