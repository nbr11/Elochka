package com.dnima.elochka;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.KeyEvent.Callback;
import android.view.MotionEvent;
import android.view.View;

public class PhotoView extends View implements Callback{
public PhotoView(Context context) {
		super(context);
		
		// TODO Auto-generated constructor stub
	}

	public PhotoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	
	}

	
	public void onDraw (Canvas elkaCanvas) {
	   
	}
	public boolean onTouchEvent(MotionEvent event) {
		
     
		invalidate();
		return true;
	}
	public boolean onKeyDown(int arg0, KeyEvent arg1) {
		// TODO Auto-generated method stub
		
		 
		
		return true;
	}

	/* (non-Javadoc)
	 * @see android.view.KeyEvent.Callback#onKeyLongPress(int, android.view.KeyEvent)
	 */
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
       ;
       
		return true;
	}

	/* (non-Javadoc)
	 * @see android.view.KeyEvent.Callback#onKeyMultiple(int, int, android.view.KeyEvent)
	 */
	public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see android.view.KeyEvent.Callback#onKeyUp(int, android.view.KeyEvent)
	 */
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}


}
