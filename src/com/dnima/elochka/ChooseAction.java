package com.dnima.elochka;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.Toast;

public class ChooseAction extends Activity  implements SurfaceHolder.Callback, View.OnClickListener, Camera.PictureCallback, Camera.PreviewCallback, Camera.AutoFocusCallback{
    
	
	public Gallery g;
	public long pos;
	public Camera cam;
	public DecorationFactory decorationFactory;
	private SurfaceView sfw;
	private SurfaceHolder sfh;

	// callbacks for surface holder
	@Override
	protected void onResume()
	{
		super.onResume();
		cam=Camera.open();
	}
	@Override
	protected void onPause() {
		super.onPause();
		if (cam!=null) {
			cam.setPreviewCallback(null);
			cam.stopPreview();
			cam.release();
			cam=null;
		}
	}

	
	public void takephoto (View who) {
		
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        decorationFactory=new DecorationFactory(this);
       
         setContentView(R.layout.chooser);
         cam=Camera.open();
 		sfw = (SurfaceView) findViewById(R.id.surfaceView1);
 		sfh = sfw.getHolder();
 		sfh.addCallback(this);
 		try {
 			cam.setPreviewDisplay(sfh);
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		cam.startPreview();
         g = (Gallery) findViewById(R.id.gallery1);
         g.setAdapter(new ImageAdapter(this));
         
         g.setOnItemClickListener(new OnItemClickListener() {
             public void onItemClick(@SuppressWarnings("rawtypes") AdapterView parent, View v, int position, long id) {
             
                 pos=id;
             }
         });
         Button b=(Button)findViewById(R.id.button1);
         b.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 // Perform action on click
            	 Bundle conData = new Bundle();
            	   conData.putString("param_return", ""+g.getSelectedItemId());
            	   Intent intent = new Intent();
            	   intent.putExtras(conData);
            	   setResult(RESULT_OK, intent);
            	   
            	 finish();
             }
         });
        
             
	 }
	public void onAutoFocus(boolean arg0, Camera arg1) {
		// TODO Auto-generated method stub
		
	}
	public void onPreviewFrame(byte[] arg0, Camera arg1) {
		// TODO Auto-generated method stub
		
	}
	public void onPictureTaken(byte[] arg0, Camera arg1) {
		// TODO Auto-generated method stub
		
	}
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		cam=Camera.open();
		sfw = (SurfaceView) findViewById(R.id.surfaceView1);
		sfh = sfw.getHolder();
		sfh.addCallback(this);
		try {
			cam.setPreviewDisplay(sfh);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cam.startPreview();
	}
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}
	public void surfaceChanged(SurfaceHolder holder,int format,int width, int height) {
		try {
			cam.setPreviewDisplay(sfh);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
