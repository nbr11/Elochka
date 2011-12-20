package com.dnima.elochka;

import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;


// we hold here already drawn tree decorations
public class Decoration implements Cloneable {
	/**
	 *  class to hold drawable object and its methods and vars
	 */

	public float x,y,width,height,wh,hh;
 	private static final float padx=5;
	private static final float pady=5;
	public String filename;
	BitmapDrawable f;
    private boolean dropMark;
	// faces are a field of ElkaView
	public Decoration(BitmapDrawable face){
	
		f=face;
		x=(float) 50.0;
		y=(float) 50.0;
		width=face.getIntrinsicWidth();
		height=face.getIntrinsicHeight();
		wh=width/2;
		hh=height/2;
		setDropMark(false);
	}
	protected Object clone() throws CloneNotSupportedException {
		
		return super.clone();
		
	}
	
    
	
	public boolean liesIn(MotionEvent event) {
		//check if event hits this decoration
		
		float ey=event.getY();
		float ex=event.getX();
		if(ex < x-padx) return false;
		if(ey < y-pady) return false;
		if(ex > x+width+padx) return false;
		if(ey > y+height+pady) return false;
		return true;
 	
	}

	

	public void setDropMark(boolean dropMark) {
		this.dropMark = dropMark;
	}

	public boolean isDropMark() {
		return dropMark;
	}

	public void updCoords(MotionEvent event) {
		// click is not upper-left, click is center
		x=event.getX() - wh;
		y=event.getY() - hh;
	}
    
    
    
 }
