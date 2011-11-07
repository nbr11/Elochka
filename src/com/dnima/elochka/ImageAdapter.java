package com.dnima.elochka;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;

public class ImageAdapter implements SpinnerAdapter {
	    int mGalleryItemBackground;
	    private Context mContext;
	    private int our_len;
		private StorageApplication ourapp;
       


	public ImageAdapter(ChooseAction chooseAction) {
		// TODO Auto-generated constructor stub
		mContext = chooseAction;
        TypedArray a = mContext.obtainStyledAttributes(R.styleable.DecoGallery);
        mGalleryItemBackground = a.getResourceId(
                R.styleable.DecoGallery_android_galleryItemBackground, 0);
        a.recycle();
     	 ourapp=(StorageApplication)mContext.getApplicationContext();
 	        
        our_len=ourapp.df.deco.size();

	}

	public int getCount() {
		// TODO Auto-generated method stub
		return our_len;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		
		return position;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
	
		return position;
	}

	

	public View getView(int position, View convertView, ViewGroup parent) {
		// 
		 ImageView i = new ImageView(mContext);
		 i.setImageDrawable(
				 
				 ourapp.df.deco.get(position).f);
           
	        i.setLayoutParams(new Gallery.LayoutParams(150, 100));
	        i.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
	        i.setBackgroundResource(mGalleryItemBackground);

	        return i;


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
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	public View getDropDownView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}


}
