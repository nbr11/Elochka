package com.dnima.elochka; 

import java.util.ArrayList;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;

public class ImageAdapter extends ArrayAdapter<Decoration> implements SpinnerAdapter {
	    static int mGalleryItemBackground;
	    private Context mContext;
	   
		private StorageApplication ourapp;
       


	public ImageAdapter(ChooseAction chooseAction) {
	    super(chooseAction, mGalleryItemBackground);
		mContext = chooseAction;
        TypedArray a = mContext.obtainStyledAttributes(R.styleable.DecoGallery);
        mGalleryItemBackground = a.getResourceId(
                R.styleable.DecoGallery_android_galleryItemBackground, 0);
        a.recycle();
     	ourapp=(StorageApplication)mContext.getApplicationContext();
 	        
        

	}

	public int getCount() {
		//size of gallery
		return ourapp.df.deco.size();
	}

	public Decoration getItem(int position) {
		if (position >= ourapp.df.deco.size())
			return null;
		else
			return ourapp.df.deco.get(position);
	}

	public long getItemId(int position) {
	
	
		return position;
	}

	

	public View getView(int position, View convertView, ViewGroup parent) {
		// 
		 ImageView imView = new ImageView(mContext);
		 imView.setImageDrawable(
				 
				 ourapp.df.deco.get(position).f);
           
	        imView.setLayoutParams(new Gallery.LayoutParams(150, 100));
	        imView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
	        imView.setBackgroundResource(mGalleryItemBackground);

	        return imView;


	}

	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean hasStableIds() {

		return true;
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
    private ArrayList<DataSetObserver> observers=new ArrayList<DataSetObserver>();
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		observers.add(observer);
	}

	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		observers.remove(observer);
	}

	public View getDropDownView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}


}
