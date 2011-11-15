package com.dnima.elochka;



import java.io.BufferedInputStream;
import java.io.File;
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
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;


public class DecorationFactory {
   public ArrayList<Decoration> deco=new ArrayList<Decoration>();
   public DecorationFactory(Context mContext) {
	   LoadFromFiles(mContext);
	 
     	if(deco.size()==0)   AddFromResource(mContext);
	   
	   StoreToFiles(mContext);
   }
  
private void LoadFromFiles(Context mContext) {
	// load decorations from files
	try {
		int i=0;
		while (true)  {
			
		    File bitmap_file=mContext.getFileStreamPath(String.valueOf(i)+"_deco.png");

            // override bug in android: unable to normally parse bitmap files
            BufferedInputStream is = new BufferedInputStream(new FileInputStream(bitmap_file));

            Bitmap tX=BitmapFactory.decodeStream(is);
            if (tX==null) throw new StreamCorruptedException("Wrong format");
            BitmapDrawable dbm=new BitmapDrawable(tX);
//          tX.recycle();
            deco.add(new Decoration(dbm));
            i=i+1;

			
			
			
			
		
		} 
	} catch (Exception e) {
		// do not care
		;
	} 
	   
	   
   }
   private void AddFromResource(Context mContext){
	   destroyFiles();
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
   
   private void destroyFiles() {
	// TODO Auto-generated method stub
	 File dir=new File(".");
	 String l[]=dir.list();
	 for( String el:l) {
		 File d= new File(el);
	     d.delete();
	 }
   }

public void StoreToFiles(Context mContext){
	   try {
		   
	       int i=0;
		   for(Decoration el:deco) {
			        
		       FileOutputStream fos=mContext.openFileOutput(String.valueOf(i)+"_deco.png",Context.MODE_PRIVATE);
		       		   
               el.f.getBitmap().compress(CompressFormat.PNG, 100, fos); 
               fos.close();
               i=i+1;
		   }
		   }  
	   catch (Exception e) {
		   e.printStackTrace();
	   }
	
   }
  
}
