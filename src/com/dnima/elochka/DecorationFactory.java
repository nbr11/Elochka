package com.dnima.elochka;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;


public class DecorationFactory {
   public ArrayList<Decoration> deco=new ArrayList<Decoration>();
   public DecorationFactory(Context mContext) {
	   LoadFromFiles(mContext);
	   AddFromResource(mContext);
	   StoreToFiles(mContext);
   }
  
private void LoadFromFiles(Context mContext){
	   // load decorations from files
	   try {
		   int i=1;
		   while (true)  {
		   FileInputStream ifs=mContext.openFileInput(String.valueOf(i)+"_deco.dat");
		   BitmapDrawable dbm=new BitmapDrawable(null, ifs);
           deco.add(new Decoration(dbm)); 
           ifs.close();
           i=i+1;
		   } 
	   } catch (FileNotFoundException e) {
		// do not care - just create a new one from resource
		   ;
	   } catch (StreamCorruptedException e) {
		// do not care
	    ;
	} catch (IOException e) {
		// do not care
	} 
	   
	   
   }
   private void AddFromResource(Context mContext){
		for (Field el : R.drawable.class.getFields()) {
			if (el.getName().contains("sample")) { // names are sample_1 sample_2 etc
				Integer id = 0;
				try {
					id = (Integer) el.get(el);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

				try {
					Decoration nd = new Decoration((BitmapDrawable) mContext.getResources().getDrawable(id));
                    deco.add(nd);                    
				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
   }
   
   public void StoreToFiles(Context mContext){
	   try {
		   
	       int i=0;
		   for(Decoration el:deco) {
			   
		      
		       FileOutputStream fos=mContext.openFileOutput(String.valueOf(i)+"_deco.dat",Context.MODE_PRIVATE);
		       ObjectOutputStream out=new ObjectOutputStream(fos);		   
               el.f.getBitmap().compress(CompressFormat.JPEG, 100, out); 
               out.close();
               i=i+1;
		   }
		   }  
	   catch (Exception e) {
		   e.printStackTrace();
	   }
	
   }
  
}
