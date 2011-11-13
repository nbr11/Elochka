/**
 * 
 */
package com.dnima.elochka;

import android.app.Application;

/**
 * @author denis
 *
 */
public class StorageApplication extends Application {
	public volatile DecorationFactory df; // DecorationStore for access from all activities from my app

}
