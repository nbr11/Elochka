/**
 * 
 */
package com.dnima.elochka;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.KeyEvent.Callback;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * @author Denis Medvedev
 * 
 */
public class ElkaView extends View implements Callback {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.KeyEvent.Callback#onKeyDown(int, android.view.KeyEvent)
	 */

	private Canvas c;
	private Paint p;
	public BitmapDrawable face = null;
	// public DecorationFactory decorationFactory;
	public ArrayList<Decoration> drawnDecorations = new ArrayList<Decoration>();
	private String toySelected; // getting 0 1 2 etc
	private boolean saveToFileFlag = false;// save not in event processing, but
											// in onDraw.
	private Decoration thing;

	public ElkaView(Context context, AttributeSet as) {

		super(context, as);
		setFocusableInTouchMode(true);
		setDrawingCacheEnabled(true);
		p = new Paint();

		((Collage) context).ourapp.df = new DecorationFactory(this.getContext()
				.getApplicationContext());
	}

	public ElkaView(Context context, AttributeSet as, int defaultStyle) {
		super(context, as, defaultStyle);
		setFocusableInTouchMode(true);
		setDrawingCacheEnabled(true);
		p = new Paint();
		// decorationFactory=new DecorationFactory(context);

		((Collage) context).ourapp.df = new DecorationFactory(this.getContext()
				.getApplicationContext());
	}

	public Uri saveToFile(String filename) throws FileNotFoundException {
		File mkd1, mkd2;
		Uri up = null;

		try {
			String resfname = Environment.getExternalStorageDirectory()
					+ "/Android/data/com.dnima.elochka/files/" + filename
					+ ".jpg";
			String mkdir1 = Environment.getExternalStorageDirectory()
					+ "/Android/data/com.dnima.elochka";
			String mkdir2 = Environment.getExternalStorageDirectory()
					+ "/Android/data/com.dnima.elochka/files";

			if (Environment.MEDIA_MOUNTED.equals(Environment
					.getExternalStorageState())) {
				mkd1 = new File(mkdir1);
				mkd1.mkdir();
				mkd2 = new File(mkdir2);
				mkd2.mkdir();
				// should fail or succeed, we don't care - exception will be
				// later
				// but we should create the dir anyway
				FileOutputStream os = new FileOutputStream(resfname);
				// considering ourselves as cacheable view
				Bitmap bm = this.getDrawingCache();
				bm.compress(Bitmap.CompressFormat.valueOf("JPEG"), 80, os);
				bm = null;

				os.close();
				File fp = new File(resfname);
				up = Uri.fromFile(fp);
				return up;

			}
		} catch (Exception e) {
			Toast.makeText(this.getContext(),
					R.string.slomato + e.getLocalizedMessage(), 30);
			return (Uri) null;
		}
		return up;

	}

	public void onDraw(Canvas elkaCanvas) {
		c = elkaCanvas;

		p.setARGB(200, 0, 0, 0);

		if (thing != null) {
			c.drawBitmap(thing.f.getBitmap(), thing.x, thing.y, p);
			if (thing.isDropMark())
				dropDecoration();
		}

		for (Decoration d : drawnDecorations) {
			c.drawBitmap(d.f.getBitmap(), (float) d.x, (float) d.y, p);

		}
		// we should save to file only after everything is drawn
		if (saveToFileFlag) {
			try {
				saveToFile("Elochka");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block

				c.drawText(e.getMessage(), 10, 10, p);

			}
			saveToFileFlag = false;
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// we need to figure out if this touch is for canvas!
		switch (event.getAction()) {

		case MotionEvent.ACTION_MOVE:
			if (event.getY() < 40.0)
				return false;
			if (thing != null) {
				thing.updCoords(event);
				invalidate();
			}
			return true;
		case MotionEvent.ACTION_UP:
			if (thing != null) {
				thing.updCoords(event);
				thing.setDropMark(true);
				invalidate();
				return true;
			}
		case MotionEvent.ACTION_DOWN:
			if (thing != null) {
				thing.updCoords(event);

				invalidate();
				return true;
			} else {

				thing = getAnyThingHere(event);
				if (thing == null)
					return false;

				invalidate();
				return true;

			}

		default:

			invalidate();
			return false;
		}

	}

	private Decoration getAnyThingHere(MotionEvent event) {
		// on this event we pick up from DrawnDecoration the decoration under
		// cursor and return it. Else null
		Decoration returnable = null;
		for (Decoration d : drawnDecorations) {
			if (d.liesIn(event)) {
				// we ignore z-index, take the oldest one up.
				returnable = d;
				drawnDecorations.remove(d);

				break;
			}

		}
		return returnable;
	}

	public void dropDecoration() {
		// we drop a toy which is on cursor to drawnDecorations list
		if (thing != null) {
			// pre-previous event to mitigate button click
			thing.setDropMark(false);
			drawnDecorations.add(thing);

			thing = null;
		}
	}

	public void selectDecoration(String selectedDecorationString)
			throws CloneNotSupportedException {
		// takes attribute from selection activity (which is string)

		toySelected = selectedDecorationString;

		if (toySelected != null) {
			int selectedFaceIndex = Integer.parseInt(toySelected);// index from
																	// 0

			thing = (Decoration) ((Collage) (this.getContext())).ourapp.df.deco
					.get(selectedFaceIndex).clone();
			// do deep copy - we need to keep the main decoration silo intact.
			toySelected = null;
		}

	}

}
