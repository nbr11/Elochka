package com.dnima.camera;
// on intent this class 
import android.app.Activity;

public class TakePhoto extends Activity {
	private Preview preview;
	public TakePhoto() {
		// TODO Auto-generated constructor stub
		preview=new Preview(this);
		
		
	}

}
