package com.dnima.elochka;


import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;

import android.widget.Toast;







public class Collage extends Activity {

	/** Called when the activity is first created. 
	 Collage class is an Activity that shows elochka View in which 
	 Elochka's canvas can be populated with selected from another 
	 activity objects 
	 **/
	 
    public ElkaView elka;
   // public LinearLayout lay;
    public Button selectButton;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elochka);
        elka=(ElkaView)this.findViewById(R.id.elka);
        
        selectButton=(Button)this.findViewById(R.id.button1);
        
          
        
        
        
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.save_menu,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId() ) {
		case R.id.help:
		    showHelp();
			return true;
		case R.id.save:
		    try {
				doSave();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		case R.id.exit:
	        System.exit(0);
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void showHelp() {
	  try {
		try {
			Toast.makeText(this,getResources().getText(R.string.helpcontent) 	  , Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	public void doSave() throws FileNotFoundException {
      elka.saveToFile("elka");
	}
	public void attachDecoration(View who) {
		elka.dropDecoration();
	}
	public void startActivity2(View who) {
		// start decoration selection screen
		  attachDecoration(who);
		  Intent intent = new Intent(Intent.ACTION_DEFAULT);
	      intent.setClassName(this, com.dnima.elochka.ChooseAction.class.getName());
	        
	      startActivityForResult(intent,1);

	}
	
	protected void onActivityResult(int requestCode,int resultCode,Intent data) {
		// went from decoration selection screen
		if(data != null)
	       elka.selectDecoration(data.getStringExtra("param_return"));
	    
	    elka.invalidate();
	}
	

}