/**
 * 
 */
package com.dnima.elochka;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Field;


import java.util.ArrayList;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import android.view.KeyEvent.Callback;
import android.view.View.OnLongClickListener;
import android.view.View.OnClickListener;

/**
 * @author Denis Medvedev
 * 
 */
public class ElkaView extends View implements Callback {

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.KeyEvent.Callback#onKeyDown(int, android.view.KeyEvent)
	 */

	private static final String TAG = "ElkaView";
	private Canvas c;
	private Paint p;
	private MotionEvent eventTouch = null;
	private MotionEvent onChangeUpEvent  = null;

	public BitmapDrawable face = null;
	private int selectedFaceIndex;
	private MotionEvent selectedFacePlace = null;
	public ArrayList<BitmapDrawable> faces = new ArrayList<BitmapDrawable>();
	public ArrayList<Decoration> drawnDecorations = new ArrayList<Decoration>();
	private String toySelected; // getting 0 1 2 etc
 	private Context sc;
	private boolean saveToFileFlag=false;// save not in event  processing, but in onDraw.
	private Decoration thing;


	public ElkaView(Context context, AttributeSet as) {

		super(context, as);
		sc=context;
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
		sc=context;
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
	//return results as image
		return getDrawingCache(true);

	}
   public void saveToFile(String filename) throws FileNotFoundException {
	   
	   String resfname=Environment.getExternalStorageDirectory()+"/Android/data/com.dnima.elochka/files/"+filename+".jpg";
	   String mkdir1=Environment.getExternalStorageDirectory()+"/Android/data/com.dnima.elochka";
	   String mkdir2=Environment.getExternalStorageDirectory()+"/Android/data/com.dnima.elochka/files";
		    
	   if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
		 File mkd1=new File(mkdir1);
		 mkd1.mkdir();
		 File mkd2=new File(mkdir2);
		 mkd2.mkdir();
		 // should fail or succeed, we don't care - exception will be later
		 // but we should create the dir anyway
		 FileOutputStream os= new FileOutputStream(resfname);
	     this.getImage().compress(Bitmap.CompressFormat.valueOf("JPEG"), 80, os);
	   }
   }
	public void onDraw(Canvas elkaCanvas)  {
		c = elkaCanvas;

		p.setARGB(200, 0, 0, 0);
		
			
			if (thing!=null) {
				c.drawBitmap(thing.f.getBitmap(), thing.x,
						thing.y, p);
		           if (thing.isDropMark()) dropDecoration();
		    }
		
		
		for (Decoration d : drawnDecorations) {
			c.drawBitmap(d.f.getBitmap(), (float) d.x,
					(float) d.y, p);
		  
		}
// we should save to file only after everything is drawn
		if(saveToFileFlag) {
			   try {
				saveToFile("Elochka");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				
				c.drawText(e.getMessage(), 10,10, p);
				
			}
			   saveToFileFlag=false;
		   }

	}
    @Override
    
	public boolean onTouchEvent(MotionEvent event) {
        // we need to figure out if this touch is for canvas!
    	  switch (event.getAction()) {
    	    
    	  case MotionEvent.ACTION_MOVE:	 
             if (event.getY() < 40.0) return false;
             if (thing != null) {
               
		       thing.updCoords(event);
		       invalidate();
             } else {
            	   
          	         thing=getAnyThingHere(event);
              	       if(thing==null)
              	         return false;
          		       
              	
             }
		     return true;
    	  case MotionEvent.ACTION_UP:
			 if (thing !=null) {
				 thing.updCoords(event);
			   thing.setDropMark(true);
			   invalidate();
			   return true;
			 }
    	  case MotionEvent.ACTION_DOWN:
    		  if (thing != null) {
                  thing.updCoords(event);
   		        
   		          invalidate();
   		          return true;
                } else {
               	   
             	         thing=getAnyThingHere(event);
                 	       if(thing==null)
                 	         return false;
             		     
             		      invalidate();
             		      return true;
                 	
                }
			 
	      default:
	    	
		      invalidate();
	    	  return false;
    	  }
    
    }
	private Decoration getAnyThingHere(MotionEvent event) {
		// on this event we pick up from DrawnDecoration the decoration under cursor and return it. Else null
		for(Decoration d: drawnDecorations) {
			if (d.liesIn(event)) {
				// we ignore z-index, take the oldest one up.
				drawnDecorations.remove(d);
				return d;
			}
			
			
		}
		return null;
	}

	public void dropDecoration() {
		// we drop a toy which is on cursor to drawnDecorations list
		if (thing != null) {
			// pre-previous event to mitigate button click
		
			drawnDecorations.add(thing);
			
			thing = null;
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
		// do save canvas to file
		
		saveToFileFlag=true;
		// and initiate the saving in redraw 
		invalidate();
		
		return false;
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
		return false;
	}

	public void selectDecoration(String selectedDecorationString) {
		// takes attribute from selection activity (which is string)

		toySelected = selectedDecorationString;

		if (toySelected != null) {
			int selectedFaceIndex = Integer.parseInt(toySelected);//index from 0

			BitmapDrawable face = faces.get(selectedFaceIndex);
			thing = new Decoration(face);
			toySelected=null;
		}
			
	  
	
	}

	public boolean onLongClick(View arg0) {
		// TODO Auto-generated method stub
		dropDecoration();
		return true;
	}


}
