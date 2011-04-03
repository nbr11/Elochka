/**
 * 
 */
package com.dnima.elochka;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.*;
import android.view.KeyEvent.Callback;

/**
 * @author ggrushina
 *
 */
public class elka extends View implements Callback {

	/* (non-Javadoc)
	 * @see android.view.KeyEvent.Callback#onKeyDown(int, android.view.KeyEvent)
	 */
	
	private Canvas c;
	private Paint p;
	private MotionEvent eventTouch=null;
	public BitmapDrawable face;
	
	public elka(Context context, AttributeSet as) {
		
		super(context,as);
		face=(BitmapDrawable)context.getResources().getDrawable(R.drawable.icon);
		p=new Paint();
	}
	public elka(Context context, AttributeSet as,int defaultStyle) {
		super(context,as,defaultStyle);
		face=(BitmapDrawable)context.getResources().getDrawable(R.drawable.icon);
		p=new Paint();
	}
	
	public void onDraw (Canvas elkaCanvas) {
	    c=elkaCanvas;
	    
		p.setARGB(200,0,0,0);
	
		
		if (eventTouch !=null) {
	//	 c.drawText("Hush Puppies",  eventTouch.getX(),eventTouch.getY(), p);
		 c.drawBitmap(face.getBitmap(), eventTouch.getX(),eventTouch.getY(), p); 
		 eventTouch=null;
		}
		
	}
	public boolean onTouchEvent(MotionEvent event) {
		
        eventTouch=event;		
		invalidate();
		return true;
	}
	public boolean onKeyDown(int arg0, KeyEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see android.view.KeyEvent.Callback#onKeyLongPress(int, android.view.KeyEvent)
	 */
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
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
