package com.example.android.customview;

import android.view.GestureDetector;
import android.view.MotionEvent;


    /**
     * Extends {@link GestureDetector.SimpleOnGestureListener} to provide custom gesture
     * processing.
     */
    class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }