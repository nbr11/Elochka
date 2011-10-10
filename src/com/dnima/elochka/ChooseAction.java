package com.dnima.elochka;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.*;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.Toast;



public class ChooseAction extends Activity  {
    
	
	private static final int TAKE_PICTURE = 0;
	public Gallery g;
	public long pos;
	public static Camera cam;
	public DecorationFactory decorationFactory;
	private Uri imageUri;
	
	
	

	
	public void takephoto (View who) {
		decorationFactory.StoreToFiles(this);
		String filename="Captured.jpg";
		String resfname=Environment.getExternalStorageDirectory()+"/Android/data/com.dnima.elochka/files/"+filename+".jpg";
		String mkdir1=Environment.getExternalStorageDirectory()+"/Android/data/com.dnima.elochka";
		String mkdir2=Environment.getExternalStorageDirectory()+"/Android/data/com.dnima.elochka/files";
			    
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			 File mkd1=new File(mkdir1);
			 mkd1.mkdir();
			 File mkd2=new File(mkdir2);
			 mkd2.mkdir();
			 // should fail or succeed, we don't care - exception will be later
			 // but we should create the dir anyway
		
		} else {
			 Toast.makeText(this, "You have no SD card - it is required", Toast.LENGTH_SHORT)
             .show();
			return; // just do not do anything - no card here
		}
			
	    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
	    File photo = new File(resfname);
	    intent.putExtra(MediaStore.EXTRA_OUTPUT,
	            Uri.fromFile(photo));
	    imageUri = Uri.fromFile(photo);
	    startActivityForResult(intent,TAKE_PICTURE);
	    
	    
	    
	    
	}
	public void onActivityResult(int requestCode,int resultCode,Intent data) {
		// went from decoration photo add
		super.onActivityResult(requestCode, resultCode, data);
	    switch (requestCode) {
	    case TAKE_PICTURE:
	        if (resultCode == Activity.RESULT_OK) {
	            Uri selectedImage = imageUri;
	            getContentResolver().notifyChange(selectedImage, null);
	           
	            ContentResolver cr = getContentResolver();
	            Bitmap bitmap;
	            try {
	                 bitmap = android.provider.MediaStore.Images.Media
	                 .getBitmap(cr, selectedImage);

	                 decorationFactory.deco.add(new Decoration(new BitmapDrawable(bitmap)));
	                 decorationFactory.StoreToFiles(this.getApplicationContext());
	                
	            } catch (Exception e) {
	                Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
	                        .show();
	                Log.e("Camera", e.toString());
	            }
	        }
	    }
		//Bundle s1=new Bundle();
		//onCreate(s1);
	    setContentView(null);
	    setContentView(R.layout.chooser);
	}
	public static boolean isIntentAvailable(Context context, String action) {
	    final PackageManager packageManager = context.getPackageManager();
	    final Intent intent = new Intent(action);
	    List<ResolveInfo> list =
	            packageManager.queryIntentActivities(intent,
	                    PackageManager.MATCH_DEFAULT_ONLY);
	    return list.size() > 0;
	}
    public boolean isAnyCameras() {
    	
    	return isIntentAvailable(this.getApplicationContext(),MediaStore.ACTION_IMAGE_CAPTURE);
    }
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        decorationFactory=new DecorationFactory(this);
        setContentView(R.layout.chooser);
   
	 		// getNumberOfCameras is available since api level 9 only!!!
            // dirty hack, thanks to doc_180
        
	 		
if  (isAnyCameras()) {
	 setContentView(R.layout.chooser);
} else {
	 setContentView(R.layout.chooser_nocamera);
}
        
       
 		
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
