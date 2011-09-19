package com.dnima.elochka;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.Toast;

public class ChooseAction extends Activity {
    
	
	public Gallery g;
	public long pos;
	public static Camera cam;
	public DecorationFactory decorationFactory;
	
	public void addphoto( View who) {
     // add photo to images decorations
		try {
		cam=Camera.open();
		Camera.Parameters cp=cam.getParameters();
	    cp.setPictureSize(80, 80);
	    //cam.setParameters(cp);
		SurfaceView sfw;
		sfw=(SurfaceView) findViewById(R.id.surfaceView1);
		SurfaceHolder sfh=sfw.getHolder();
		cam.setPreviewDisplay(sfh);
		cam.startPreview();
	
		}
		catch (Exception p) {
		    Toast.makeText(ChooseAction.this, p.getLocalizedMessage(), Toast.LENGTH_SHORT).show();	
		}
		finally {
			
		}
	}
	public void takephoto (View who) {
		
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
}
