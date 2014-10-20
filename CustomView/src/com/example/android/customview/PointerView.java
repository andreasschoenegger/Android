package com.example.android.customview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

    /**
     * View that draws the pointer on top of the pie chart
     */
    class PointerView extends View {

        private float mPointerX = 10.0f;
        private float mPointerY = 10.0f;
        private Paint PointerViewTextPaint;


       /**
         * Construct a PointerView object
         *
         * @param context
         */
        public PointerView(Context context) {
            super(context);

            // Set up the paint for the label text
            PointerViewTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

            Resources res = getResources();
            int TextColor = res.getColor(R.color.bluegrass);
            		
            PointerViewTextPaint.setColor(TextColor);
            PointerViewTextPaint.setTextSize(12);

            mPointerY = 130;
            mPointerX = 130;

        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawText(getContext().getString(R.string.car_wars), mPointerY, mPointerX, PointerViewTextPaint);
        }
    }

