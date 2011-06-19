package com.dnima.elochka;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.LinearLayout;







public class Collage extends Activity {

	/** Called when the activity is first created. 
	 Collage class is an Activity that shows elochka View in which 
	 Elochka's canvas can be populated with selected from another 
	 activity objects 
	 **/
	 

	public ElkaView elka;
    public PhotoView photo;
    public LinearLayout lay;
    public Button selectButton;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elochka);
        elka=(ElkaView)this.findViewById(R.id.elka);
        
        selectButton=(Button)this.findViewById(R.id.button1);
        
          
        
        
        
    }
	public void attachDecoration(View who) {
		elka.dropDecoration();
	}
	public void startActivity2(View who) {
		// start decoration selection screen
		  Intent intent = new Intent(Intent.ACTION_DEFAULT);
	      intent.setClassName(this, com.dnima.elochka.ChooseAction.class.getName());
	        
	      startActivityForResult(intent,1);

	}
	
	protected void onActivityResult(int requestCode,int resultCode,Intent data) {
		// went from decoration selection screen
		
	    elka.selectDecoration(data.getStringExtra("param_return"));
	    
	    elka.invalidate();
	}
	

}