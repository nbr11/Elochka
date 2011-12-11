package com.dnima.elochka;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StreamCorruptedException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;

public class DecorationFactory extends ArrayAdapter<Decoration> implements
		SpinnerAdapter {
	public ArrayList<Decoration> deco = new ArrayList<Decoration>();
	public int size = 0;
	private Context myContext;
	private int galleryItemBackground;

	public DecorationFactory(Context mContext) {
		super(mContext, 0);
		myContext = mContext;
		LoadFromFiles(mContext);
		TypedArray a = mContext.obtainStyledAttributes(R.styleable.DecoGallery);
		galleryItemBackground = a.getResourceId(
				R.styleable.DecoGallery_android_galleryItemBackground, 0);
		a.recycle();
		if (deco.size() == 0) {
			AddFromResource(mContext);
			StoreToFiles(mContext);
		}

		// StoreToFiles(mContext);
	}
   @Override
	public void remove(Decoration p) {

		String f = p.filename;
		File filedir = myContext.getFileStreamPath(f);
		filedir.delete();
		// deco.get(p).f.getBitmap().recycle();
		deco.remove(p);

		deco.trimToSize();

	}
    
    
	private void LoadFromFiles(Context mContext) {
		// load decorations from files
		try {
			int i = 0;
			size = 0;
			while (true) {

				File bitmap_file = mContext.getFileStreamPath(String.valueOf(i)
						+ "_deco.png");

				// override bug in android: unable to normally parse bitmap
				// files
				BufferedInputStream is = new BufferedInputStream(
						new FileInputStream(bitmap_file));

				Bitmap tX = BitmapFactory.decodeStream(is);
				if (tX == null)
					throw new StreamCorruptedException("Wrong format");
				BitmapDrawable dbm = new BitmapDrawable(tX);
				// tX.recycle();
				Decoration newDeco = new Decoration(dbm);
				newDeco.filename = bitmap_file.getName();
				deco.add(newDeco);
				newDeco = null;
				i = i + 1;
				size = size + 1;

			}
		} catch (Exception e) {
			// do not care
			;
		}

	}

	@Override
	public Decoration getItem(int position) {
		if (position >= deco.size())
			return null;
		else
			return deco.get(position);
	}

	@Override
	public int getCount() {
		// size of gallery
		return deco.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//
		ImageView imView = new ImageView(myContext);
		imView.setImageDrawable(

		deco.get(position).f);

		imView.setLayoutParams(new Gallery.LayoutParams(150, 100));
		imView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		imView.setBackgroundResource(galleryItemBackground);
		return imView;

	}

	private void AddFromResource(Context mContext) {
		destroyFiles();
		for (Field el : R.drawable.class.getFields()) {
			if (el.getName().contains("sample")) { // names are sample_1
													// sample_2 etc
				Integer id = 0;
				try {
					id = (Integer) el.get(el);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

				try {
					Decoration nd = new Decoration((BitmapDrawable) mContext
							.getResources().getDrawable(id));

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
		File dir = new File(".");
		String l[] = dir.list();
		for (String el : l) {
			File d = new File(el);
			d.delete();
		}
	}

	public void StoreToFiles(Context mContext) {
		try {

			int i = 0;
			for (Decoration el : deco) {
				el.filename = String.valueOf(i) + "_deco.png";
				FileOutputStream fos = mContext.openFileOutput(
						String.valueOf(i) + "_deco.png", Context.MODE_PRIVATE);

				el.f.getBitmap().compress(CompressFormat.PNG, 100, fos);
				fos.close();
				i = i + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
