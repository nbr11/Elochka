package com.dnima.elochka;


// we hold here already drawn tree decorations
public class Decoration {
	public float x,y,ex,ey,dx,dy;
	public int  index;

	// faces are a field of ElkaView
	public Decoration(int indexInFaces,ElkaView ev, float startX, float startY){
		x=startX;
		y=startY;
		dx=ev.faces.get(indexInFaces).getMinimumWidth();

		
		dy=ev.faces.get(indexInFaces).getMinimumHeight();
		ex= startX+dx;
		ey= startY+dy;
		assert(dx>0);
		assert(dy>0);
		index=indexInFaces;
	}
    public void setXY(int newX, int newY) {
    	
    	x=newX;
    	y=newY;
        ex=x+dx;
        ey=y+dy;
    	return;
    }
    
    
 }
