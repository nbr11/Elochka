/**
 * 
 */
package com.dnima.elochka;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Vector;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
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

	/* (non-Javadoc)
	 * @see android.view.KeyEvent.Callback#onKeyDown(int, android.view.KeyEvent)
	 */
	// with decoration on cursor
	private static final int MODE_DRAG=1;
	// without decoration on cursor
	private static final int MODE_FREE=0;
	
	private Canvas c;
	private Paint p;
	private Context mycontext;
	private MotionEvent eventTouch=null;

	public BitmapDrawable face;
	public ArrayList<BitmapDrawable> faces=new ArrayList<BitmapDrawable>();
	private String eventSelected;
	
	public ElkaView(Context context, AttributeSet as) {
		
		super(context,as);
		setFocusableInTouchMode(true);
		mycontext=context;
		face=(BitmapDrawable)context.getResources().getDrawable(R.drawable.icon);
        for (Field el : R.drawable.class.getFields()) {
        	if (el.getName().contains("sample")) {
        		Integer id=0;
				try {
					id = (Integer)el.get(el);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
            	try {
					faces.add((BitmapDrawable)context.getResources().getDrawable(id));
				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
        	}
        }
        
        
    
		p=new Paint();
	}
	public ElkaView(Context context, AttributeSet as,int defaultStyle) {
		super(context,as,defaultStyle);
		setFocusableInTouchMode(true);
		mycontext=context;
		face=(BitmapDrawable)context.getResources().getDrawable(R.drawable.icon);
	    for (Field el : R.drawable.class.getFields()) {
        	if (el.getName().contains("sample")) {
        		Integer id=0;
				try {
					id = (Integer)el.get(el);
					
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	BitmapDrawable img=
            		((BitmapDrawable)context.getResources().getDrawable(id));
            	faces.add(img);
        	}
        }
		p=new Paint();
		
	}
	
	public void onDraw (Canvas elkaCanvas) {
	    c=elkaCanvas;
	    
		p.setARGB(200,0,0,0);
	
		
		if (eventTouch !=null) {
//		 c.drawText("Hush Puppies",  eventTouch.getX(),eventTouch.getY(), p);
     	 c.drawBitmap(face.getBitmap(), eventTouch.getX(),eventTouch.getY(), p); 
    	 
     	 eventTouch=null;
		}
		if (eventSelected != null) {
			 c.drawText("Selected:"+eventSelected, 20, 20, p);
			 face=faces.get(Integer.parseInt(eventSelected));
		     eventSelected=null;		   
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
		
		eventSelected=stringExtra;
	}

}
