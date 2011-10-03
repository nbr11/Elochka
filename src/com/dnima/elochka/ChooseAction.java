package com.dnima.elochka;

import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;

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
