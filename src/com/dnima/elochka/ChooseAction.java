package com.dnima.elochka;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.DataSetObserver;
import android.graphics.AvoidXfermode.Mode;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.Toast;

public class ChooseAction extends Activity {

	private static final int TAKE_PICTURE = 0;
	public Gallery g;
	public long pos=0;
	public static Camera cam;
	
//	private Uri imageUri;
	private StorageApplication ourapp;

	public void droptoy (View who) {
    	// Calling destruction of decoration.
    	if (ourapp.df.deco.size()<7) {
    		Toast t=Toast.makeText(this, "You cannot remove predefinded toys", 10);
    		t.show();
    		return;
    	}
    	Decoration drop=ourapp.df.deco.get((int)pos);
    	ourapp.df.remove(drop);

    	ourapp.df.StoreToFiles(getApplicationContext());
   
        
        ourapp.df.notifyDataSetChanged();
        g.setSelection(0);
    	g.invalidate();
    }
    
	public void takephoto(View who) {
/*
		String filename = "captured";
		String resfname = Environment.getExternalStorageDirectory()
				+ "/Android/data/com.dnima.elochka/files/" + filename + ".jpg";
		String mkdir1 = Environment.getExternalStorageDirectory()
				+ "/Android/data/com.dnima.elochka";
		String mkdir2 = Environment.getExternalStorageDirectory()
				+ "/Android/data/com.dnima.elochka/files";

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			File mkd1 = new File(mkdir1);
			mkd1.mkdir();
			File mkd2 = new File(mkdir2);
			mkd2.mkdir();
			// should fail or succeed, we don't care - exception will be later
			// but we should create the dir anyway

		} else {
			Toast.makeText(this, "You have no SD card - it is required",
					Toast.LENGTH_SHORT).show();
			return; // just do not do anything - no card here
		}
*/
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
	//	File photo = new File(resfname);
// we don't need large image so it's not needed		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
	//	imageUri = Uri.fromFile(photo);
		startActivityForResult(intent, TAKE_PICTURE);

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// went from decoration photo add
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case TAKE_PICTURE:
			if (resultCode == Activity.RESULT_OK) {
			//	Uri selectedImage = imageUri;
			//	getContentResolver().notifyChange(selectedImage, null);

			//	ContentResolver cr = getContentResolver();
				Bitmap bitmap,smallbitmap;
			//	try {
			//		bitmap = android.provider.MediaStore.Images.Media
			//				.getBitmap(cr, selectedImage);
                    bitmap=(Bitmap)data.getExtras().get("data");
            // lets do circle crop here
                  
                    
                    smallbitmap=Bitmap.createScaledBitmap(toyify(new BitmapDrawable(bitmap)), 50, 50,true);
                    bitmap.recycle();
                    bitmap=null;
                 

					ourapp.df.deco.add(new Decoration(new BitmapDrawable(smallbitmap)));
					ourapp.df.notifyDataSetChanged();
					ourapp.df.StoreToFiles(this.getApplicationContext());
					g.invalidate();
				
					

				/*} catch (Exception e) {
					Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
							.show();
					Log.e("Camera", e.toString());
					
				}*/
			}
		}
		// Bundle s1=new Bundle();
		// onCreate(s1);

	}

	private Bitmap toyify(BitmapDrawable bitmapDrawable) {
		// make border for bitmap
		Bitmap orig=bitmapDrawable.getBitmap();
		Bitmap output=Bitmap.createBitmap(orig.getWidth(),orig.getHeight(),Config.ARGB_8888);
		Canvas canvas=new Canvas(output);
		final int color=Color.YELLOW;
		final Paint paint=new Paint();
		final Rect rect=new Rect(0,0,orig.getWidth(),orig.getHeight());
		final RectF rectF=new RectF(rect);
		final float roundPx=40;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
	    canvas.drawRoundRect(rectF,roundPx,roundPx,paint);
	    PorterDuff.Mode mode=PorterDuff.Mode.SRC_IN;
	    PorterDuffXfermode xfermode = new PorterDuffXfermode(mode);
	    paint.setXfermode(xfermode);
	    canvas.drawBitmap(orig,rect,rect,paint);
	    return output;
	    
		
	}

	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	public boolean isAnyCameras() {

		return isIntentAvailable(this.getApplicationContext(),
				MediaStore.ACTION_IMAGE_CAPTURE);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	     getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
	     getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	    
        ourapp=(StorageApplication)this.getApplication();
	
		setContentView(R.layout.chooser);

		// getNumberOfCameras is available since api level 9 only!!!
		// dirty hack, thanks to doc_180

		if (isAnyCameras()) {
			setContentView(R.layout.chooser);
		} else {
			setContentView(R.layout.chooser_nocamera);
		}

		g = (Gallery) findViewById(R.id.gallery1);
		//imAdapter=new ImageAdapter(this);
		
		//imAdapter.registerDataSetObserver(observer);
		g.setAdapter(ourapp.df);

		g.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(
					@SuppressWarnings("rawtypes") AdapterView parent, View v,
					int position, long id) {

				pos = id;
			}
		});
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				Bundle conData = new Bundle();
				conData.putString("param_return", "" + g.getSelectedItemId());
				Intent intent = new Intent();
				intent.putExtras(conData);
				setResult(RESULT_OK, intent);

				finish();
			}
		});

	}

}

