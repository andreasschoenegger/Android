package com.example.android.customview;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

    /**
     * Maintains the state for a car
     */
    class Car {
        public String mLabel;
        public int mColor;
        public float mSpeed = 1f;
        private Paint mCarPaint;

        // computed values
        public float mXPos;
        public float mYPos;

        public float mAngle;
        
        private List<Point> mVectorsDefault = new ArrayList<Point>();
        private List<Point> mVectorsAngled = new ArrayList<Point>();

        public Car(float x, float y, int color, float angle) {
        	
        	// Mittelpunkt
        	mXPos = x;
        	mYPos = y;
        	mAngle = angle;
  	
        	// Set up the paint for the cars
        	mColor = color;
        	mCarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        	mCarPaint.setStyle(Paint.Style.STROKE);
        	mCarPaint.setStrokeWidth(3);
        	mCarPaint.setColor(mColor);
        	
        	// Currently the car is line drawn
        	// Set up a list of vectors that make up a car when connected with a line
            int width = 10;
            int length = 18;
            mVectorsDefault.add(new Point(-length/2, -width/2));
            mVectorsDefault.add(new Point(-length/2, +width/2));
            mVectorsDefault.add(new Point(+length/2, +width/2));
            mVectorsDefault.add(new Point(+length/2, -width/2));
            mVectorsAngled = getRotatedVectors(mVectorsDefault);
        }


        public Point getRotatedVector(Point vector) {

        	double r;
        	double alpha;
        	double x;
        	double y;
        	Point outputVector = new Point();
        	
        	x = vector.x;
        	y = vector.y;
        	
    		r = Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));

    		if (x == 0) { 
        		if (y>0)
        			alpha = Math.PI/2;
        		else 
        			alpha = - Math.PI/2;
        	} else if (x > 0) {
        		alpha = Math.atan(y / x);
        	} else if (y < 0) {
        		alpha = Math.atan(y / x) - Math.PI;
        	} else {
        		alpha = Math.atan(y / x) + Math.PI;
        	}
    		
        	alpha = alpha + Math.toRadians(mAngle);
        	x = (Math.cos(alpha))*r;
        	y = (Math.sin(alpha))*r*-1;
        	outputVector.x =(int)x;
        	outputVector.y =(int)y;
        	
        	return outputVector;
        }
        
        public List<Point> getRotatedVectors(List<Point> vectors ) {
            
            List<Point> outputVectors = new ArrayList<Point>();

        	
        	for (Point vector : vectors) {
        		outputVectors.add(getRotatedVector(vector));
            }
            return outputVectors;
        }
        
        public void move(float inputAngle) {
        	
        	int ourFaktor = 2;
        	steer((int)inputAngle*ourFaktor);
        	
        	
        	mXPos += Math.cos(Math.toRadians(mAngle))*mSpeed;
        	mYPos -= Math.sin(Math.toRadians(mAngle))*mSpeed;
        }

        public void steerRite() {
        	steer(-10);
        }

        public void steerLeft() {
        	steer(10);
        }

        public void steer(int degree) {
        	mAngle += degree + 360;
        	mAngle = mAngle % 360;
            mVectorsAngled = getRotatedVectors(mVectorsDefault);
        }

        public void drawMe(Canvas canvas) {
            boolean first = true;
            Point old = new Point();
            Point firstPoint = new Point();
            
            for (Point vector : mVectorsAngled) {
            	if (!first) {
                	canvas.drawLine(mXPos+old.x, mYPos+old.y, mXPos+vector.x, mYPos+vector.y, mCarPaint);
            	} else {
            		firstPoint = vector;
            		first = false;
            	}
            	old = vector;
            }
        	canvas.drawLine(mXPos+firstPoint.x, mYPos+firstPoint.y, mXPos+old.x, mYPos+old.y, mCarPaint);
        	
        }
        
    }
