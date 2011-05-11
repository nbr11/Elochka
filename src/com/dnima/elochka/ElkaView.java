/**
 * 
 */
package com.dnima.elochka;

import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.*;
import android.view.KeyEvent.Callback;

/**
 * @author ggrushina
 *
 */
public class ElkaView extends View implements Callback {

	private static final int REQUEST_IMAGE = 1;
	/* (non-Javadoc)
	 * @see android.view.KeyEvent.Callback#onKeyDown(int, android.view.KeyEvent)
	 */
	
	private Canvas c;
	private Paint p;
	private Context mycontext;
	private MotionEvent eventTouch=null;

	public BitmapDrawable face;
	private Object eventSelected;
	
	public ElkaView(Context context, AttributeSet as) {
		
		super(context,as);
		setFocusableInTouchMode(true);
		mycontext=context;
		face=(BitmapDrawable)context.getResources().getDrawable(R.drawable.icon);
		p=new Paint();
	}
	public ElkaView(Context context, AttributeSet as,int defaultStyle) {
		super(context,as,defaultStyle);
		setFocusableInTouchMode(true);
		mycontext=context;
		face=(BitmapDrawable)context.getResources().getDrawable(R.drawable.icon);
		p=new Paint();
	}
	
	public void onDraw (Canvas elkaCanvas) {
	    c=elkaCanvas;
	    
		p.setARGB(200,0,0,0);
	
		
		if (eventTouch !=null) {
//		 c.drawText("Hush Puppies",  eventTouch.getX(),eventTouch.getY(), p);
     	 c.drawBitmap(face.getBitmap(), eventTouch.getX(),eventTouch.getY(), p); 
    		if (eventSelected != null) {
 			 c.drawText("Selected:"+eventSelected, eventTouch.getX(), eventTouch.getY(), p);
 			 eventSelected=null;
 			}

     	 eventTouch=null;
		}
		
	}
	public boolean onTouchEvent(MotionEvent event) {
		
        eventTouch=event;
      
      
        
        
		return true;
	}
	public boolean onKeyDown(int arg0, KeyEvent arg1) {
		// TODO Auto-generated method stub
		  ((Collage) mycontext).startActivity2();
		 
		
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
	public void drawDigi(String stringExtra) {
		// TODO Auto-generated method stub
		
	}

}
