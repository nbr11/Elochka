package com.dnima.elochka;
// on intent this class 

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;

public class TakePhoto extends Activity {
	private Preview preview;
	public TakePhoto() {
	 super();
		
	}
    @Override
    public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	preview=new Preview(this);
    	setContentView(preview);
		Camera cam=Camera.open();
		preview.setCamera(cam);
		
    }
}
