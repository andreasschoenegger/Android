package com.example.android.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Custom view with the playground (another view), the buttons and a label.
 */
public class MieChart extends ViewGroup {

    private float mTextWidth = 0.0f;

    public PlayGround mPlayGround;
    private GestureDetector mDetector;
    private PointerView mPointerView;

    private RectF mBounds = new RectF();

    /**
     * Class constructor taking only a context. Use this constructor to create
     * {@link MieChart} objects from your own code.
     *
     * @param context
     */
    public MieChart(Context context) {
        super(context);
        init();
    }

    /**
     * Class constructor taking a context and an attribute set. This constructor
     * is used by the layout engine to construct a {@link MieChart} from a set of
     * XML attributes.
     *
     * @param context
     * @param attrs   An attribute set which can contain attributes from
     *                {@link com.example.android.customview.R.styleable.MieChart} as well as attributes inherited
     *                from {@link android.view.View}.
     */
    public MieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
	@Override
    public boolean onTouchEvent(MotionEvent event) {
        // Let the GestureDetector interpret this event
        boolean result = mDetector.onTouchEvent(event);
        return result;
    }

    @Override
    public boolean performClick() {
    	return super.performClick();
    }    

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // Do nothing. Do not call the superclass method--that would start a layout pass
        // on this view's children. MieChart lays out its children in onSizeChanged().
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    //
    // Measurement functions. This example uses a simple heuristic: it assumes that
    // the pie chart should be at least as wide as its label.
    //
    @Override
    protected int getSuggestedMinimumWidth() {
        return (int) mTextWidth * 2;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return (int) mTextWidth;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //
        // Set dimensions for text, pie chart, etc
        //
        // Account for padding
        float xpad = (float) (getPaddingLeft() + getPaddingRight());
        float ypad = (float) (getPaddingTop() + getPaddingBottom());

        float ww = (float) w - xpad;
        float hh = (float) h - ypad;

        // Figure out how big we can make the quadratical playground.
        float minimum = Math.min(ww, hh);
        mBounds = new RectF(
                0.0f,
                0.0f,
                minimum,
                minimum);
        mBounds.offsetTo(getPaddingLeft(), getPaddingTop());


        // Lay out the child view that actually draws the game.
        mPlayGround.layout((int) mBounds.left,
                (int) mBounds.top,
                (int) mBounds.right,
                (int) mBounds.bottom);

        mPointerView.layout(0, 0, w, h);
    }

    /**
     * Initialize the control. This code is in a separate method so that it can be
     * called from both constructors.
     */
    private void init() {

        // Add a child view to draw the Game. Putting this in a child view
        // makes it possible to draw it on a separate hardware layer that rotates
        // independently
        mPlayGround = new PlayGround(getContext());
        addView(mPlayGround);

        // In order to show up
        // in front of the Game this also needs to be on a separate view.
        mPointerView = new PointerView(getContext());
        addView(mPointerView);

        // Create a gesture detector to handle onTouch messages
        mDetector = new GestureDetector(MieChart.this.getContext(), new GestureListener());

        // Turn off long press--this control doesn't use it, and if long press is enabled,
        // you can't scroll for a bit, pause, then scroll some more (the pause is interpreted
        // as a long press, apparently)
        mDetector.setIsLongpressEnabled(false);

    }


}
