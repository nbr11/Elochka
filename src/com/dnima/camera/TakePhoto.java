package com.dnima.camera;
// on intent this class 
import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;

public class TakePhoto extends Activity {
	private Preview preview;
	private static Camera cam;
	
	public TakePhoto() {
		// TODO Auto-generated constructor stub
		super();
		
	}
	@Override
	public void onCreate(Bundle savedstate) {
		super.onCreate(savedstate);
		preview=new Preview(this);
		cam=Camera.open();
		preview.setCamera(cam);
		
	    
	
	}

}
