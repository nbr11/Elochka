package com.dnima.elochka;
// on intent this class 

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.KeyEvent.Callback;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class TakePhoto extends Activity implements Camera.PictureCallback,
android.view.View.OnTouchListener
{
	private Preview preview;
	private Camera cam;
	private boolean picture_taking;
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
		preview.setOnTouchListener(this);
    	setContentView(preview);
		
		
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Open the default i.e. the first rear facing camera.
        cam = Camera.open();
        Parameters comparam=cam.getParameters();
        comparam.setPictureFormat(PixelFormat.JPEG);
        comparam.setJpegQuality(100);
        cam.setParameters(comparam);
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
    private void takeShot() {
    	if(cam !=null && picture_taking ==false) {
    		picture_taking=true;
    	    cam.takePicture(null, null, this);//jpeg callback comes here
    	}
    }
	public void onPictureTaken(byte[] data, Camera camera) {
		
		DecorationFactory dc=new DecorationFactory(this);
		dc.deco.add(new Decoration(new BitmapDrawable(BitmapFactory.decodeByteArray(data, 0,data.length ))));
		dc.StoreToFiles(this);
		picture_taking=false;
		Intent intent=new Intent();
		setResult(RESULT_OK,intent);
		finish();
	}
	public boolean onTouch(View arg0, MotionEvent arg1) {
		takeShot();
		return true;
	}
	
  
}
