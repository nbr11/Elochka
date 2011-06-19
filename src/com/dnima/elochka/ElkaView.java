/**
 * 
 */
package com.dnima.elochka;

import java.lang.reflect.Field;

import java.util.ArrayList;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.*;
import android.view.KeyEvent.Callback;
import android.view.View.OnLongClickListener;

/**
 * @author ggrushina
 * 
 */
public class ElkaView extends View implements Callback,OnLongClickListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.KeyEvent.Callback#onKeyDown(int, android.view.KeyEvent)
	 */

	private Canvas c;
	private Paint p;
	private MotionEvent eventTouch = null;

	public BitmapDrawable face = null;
	private int selectedFaceIndex;
	private MotionEvent selectedFacePlace = null;
	public ArrayList<BitmapDrawable> faces = new ArrayList<BitmapDrawable>();
	public ArrayList<Decoration> drawnDecorations = new ArrayList<Decoration>();
	private String eventSelected;

	public ElkaView(Context context, AttributeSet as) {

		super(context, as);
		setFocusableInTouchMode(true);
		setDrawingCacheEnabled(true);
		for (Field el : R.drawable.class.getFields()) {
			if (el.getName().contains("sample")) { // names are sample_1 sample_2 etc
				Integer id = 0;
				try {
					id = (Integer) el.get(el);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

				try {
					faces.add((BitmapDrawable) context.getResources()
							.getDrawable(id));
				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

		p = new Paint();
	}

	public ElkaView(Context context, AttributeSet as, int defaultStyle) {
		super(context, as, defaultStyle);
		setFocusableInTouchMode(true);// to be able to receive kbd events
		setDrawingCacheEnabled(true);// to be able to save canvas to file
		for (Field el : R.drawable.class.getFields()) {
			if (el.getName().contains("sample")) {
				Integer id = 0;
				try {
					id = (Integer) el.get(el);

				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				BitmapDrawable img = ((BitmapDrawable) context.getResources().getDrawable(id));
				faces.add(img);
			}
		}
		p = new Paint();

	}

	public Bitmap getImage() {
		return getDrawingCache(true);

	}

	public void onDraw(Canvas elkaCanvas) {
		c = elkaCanvas;

		p.setARGB(200, 0, 0, 0);
       if ((eventTouch != null) && (face != null)) {
			// c.drawText("Hush Puppies", eventTouch.getX(),eventTouch.getY(),
			// p);
				selectedFacePlace = eventTouch; // only if in drawing zone, not
				c.drawBitmap(face.getBitmap(), eventTouch.getX(),
						eventTouch.getY(), p);
					
												// in drawn drop button zone
			eventTouch = null;
		}
		if (eventSelected != null) {
			selectedFaceIndex = Integer.parseInt(eventSelected);

			face = faces.get(selectedFaceIndex);
			eventSelected = null;
		}
		for (Decoration d : drawnDecorations) {
			c.drawBitmap(faces.get(d.index).getBitmap(), (float) d.x,
					(float) d.y, p);

		}

	}

	public boolean onTouchEvent(MotionEvent event) {

	    eventTouch = event;
	    if (eventTouch.getPointerCount()==2) dropDecoration();
		invalidate();

		return true;
	}
    public boolean onClickEvent(MotionEvent event){
    	
    	return false;
    }
	public void dropDecoration() {
		// we drop a toy which is on cursor to drawnDecorations list
		if (face != null) {
			Decoration thing = new Decoration(selectedFaceIndex, this,
					selectedFacePlace.getX(), selectedFacePlace.getY());
			drawnDecorations.add(thing);
			// and clean current face until one selected
			face = null;
		}
	}

	public boolean onKeyDown(int arg0, KeyEvent arg1) {
		// TODO Auto-generated method stub

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.KeyEvent.Callback#onKeyLongPress(int,
	 * android.view.KeyEvent)
	 */
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		;

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.KeyEvent.Callback#onKeyMultiple(int, int,
	 * android.view.KeyEvent)
	 */
	public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.KeyEvent.Callback#onKeyUp(int, android.view.KeyEvent)
	 */
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// drop on any key
		dropDecoration();
		return true;
	}

	public void selectDecoration(String stringExtra) {
		// takes attribute from selection activity (which is string)

		eventSelected = stringExtra;
	
	}

	public boolean onLongClick(View arg0) {
		// TODO Auto-generated method stub
		dropDecoration();
		return true;
	}

}
