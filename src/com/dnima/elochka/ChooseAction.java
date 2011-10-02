package com.dnima.elochka;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;



public class ChooseAction extends Activity  {
    
	
	public Gallery g;
	public long pos;
	public static Camera cam;
	public DecorationFactory decorationFactory;
	
	
	// callbacks for surface holder
	@Override
	protected void onResume()
	{
		super.onResume();
//		cam=Camera.open();
	}
	@Override
	protected void onPause() {
		super.onPause();
//		if (cam!=null) {
//			cam.setPreviewCallback(null);
//			cam.stopPreview();
//			cam.release();
//			cam=null;
//		}
	}

	
	public void takephoto (View who) {
		decorationFactory.StoreToFiles(this);
		Intent intent = new Intent(Intent.ACTION_DEFAULT);
	
	 
	    intent.setClassName(this, com.dnima.elochka.TakePhoto.class.getName());
	    startActivityForResult(intent,1);
	}
	public void onActivityResult(int requestCode,int resultCode,Intent data) {
		// went from decoration photo add
		
		Bundle s1=new Bundle();
		onCreate(s1);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        decorationFactory=new DecorationFactory(this);
        setContentView(R.layout.chooser);
   
	 		
	 		

        
       
 		
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
	
}
