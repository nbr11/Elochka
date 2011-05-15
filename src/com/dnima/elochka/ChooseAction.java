package com.dnima.elochka;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.Toast;

public class ChooseAction extends Activity {
    
	
	public Gallery g;
	public long pos;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
   //      Collage parent=(Collage/)this.getContext();
         setContentView(R.layout.chooser);
         g = (Gallery) findViewById(R.id.gallery1);
         g.setAdapter(new ImageAdapter(this));

         g.setOnItemClickListener(new OnItemClickListener() {
             public void onItemClick(@SuppressWarnings("rawtypes") AdapterView parent, View v, int position, long id) {
                 Toast.makeText(ChooseAction.this, "" + position, Toast.LENGTH_SHORT).show();
                 pos=id;
             }
         });
         Button b=(Button)findViewById(R.id.button1);
         b.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 // Perform action on click
            	 Bundle conData = new Bundle();
            	   conData.putString("param_return", "decor # "+g.getSelectedItemId());
            	   Intent intent = new Intent();
            	   intent.putExtras(conData);
            	   setResult(RESULT_OK, intent);
            	   
            	 finish();
             }
         });
         
       

         
	 }
}
