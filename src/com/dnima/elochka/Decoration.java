package com.dnima.elochka;

import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;


// we hold here already drawn tree decorations
public class Decoration {
	public float x,y,width,height;
	
	BitmapDrawable f;

	// faces are a field of ElkaView
	public Decoration(BitmapDrawable face){
	
		f=face;
		x=(float) 50.0;
		y=(float) 50.0;
		width=face.getBounds().width();
		height=face.getBounds().height();
	}

	public boolean liesIn(MotionEvent event) {
		//check if event hits this decoration
		
		float ey=event.getY();
		float ex=event.getX();
		if(ex < x) return false;
		if(ey < y) return false;
		if(ex > x+width) return false;
		if(ey > y+height) return false;
		return true;
 	
	}
   
    
    
 }
