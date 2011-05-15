package com.dnima.elochka;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;







public class Collage extends Activity {
    /** Called when the activity is first created. */
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
	public void startActivity2(View who) {
		  Intent intent = new Intent(Intent.ACTION_DEFAULT);
	      intent.setClassName(this, com.dnima.elochka.ChooseAction.class.getName());
	        
	      startActivityForResult(intent,1);

	}
	protected void onActivityResult(int requestCode,int resultCode,Intent data) {
	    elka.drawDigi(data.getStringExtra("param_return"));
	    elka.invalidate();
	}
	

}