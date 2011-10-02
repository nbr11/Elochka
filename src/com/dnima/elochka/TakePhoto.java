package com.dnima.elochka;
// on intent this class 

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.widget.Button;

public class TakePhoto extends Activity {
	private Preview preview;
	private Camera cam;
	public TakePhoto() {
	 super();
		
	}
    @Override
    public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	preview=new Preview(this);
    	Button shutter=new Button(this);
		shutter.setText("Take Photo");
		preview.addView(shutter);
    	setContentView(preview);
		
		
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Open the default i.e. the first rear facing camera.
        cam = Camera.open();
        preview.setCamera(cam);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Because the Camera object is a shared resource, it's very
        // important to release it when the activity is paused.
        if (cam != null) {
            preview.setCamera(null);
            cam.release();
            cam = null;
        }
    }
  
}
