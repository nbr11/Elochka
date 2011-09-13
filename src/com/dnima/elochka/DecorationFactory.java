package com.dnima.elochka;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.BitmapDrawable;


public class DecorationFactory {
   public ArrayList<Decoration> deco=new ArrayList<Decoration>();
   public DecorationFactory(Context mContext) {
	   LoadFromFiles();
	   AddFromResource(mContext);
	   StoreToFiles();
   }
   @SuppressWarnings("unchecked")
private void LoadFromFiles(){
	   // load decorations from files
	   try {
		   
		   ObjectInputStream in=new ObjectInputStream(new FileInputStream("decorations.string"));		   
           deco=(ArrayList<Decoration>)in.readObject(); 
	   } catch (FileNotFoundException e) {
		// do not care - just create a new one from resource
		   ;
	   } catch (StreamCorruptedException e) {
		// do not care
	    ;
	} catch (IOException e) {
		// do not care
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
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
   
   public void StoreToFiles(){
	   try {
		   
		   ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("decorations.string"));		   
           out.writeObject(deco); 
	   }  
	   catch (Exception e) {
		   e.printStackTrace();
	   }
	
   }
  
}
