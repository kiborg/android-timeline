/**
 * @author Ben Pitts
 * Timeline Calendar project from CS495 (Android app development)
 * Won't do much in emulator, as it needs calendar data and multitouch.
 * email me if you do anything cool with this idea: methodermis@gmail.com
 */
package org.drgn.timeline;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.util.Log;

public class MainActivity extends Activity
{
	private static final String LogTag = "drgn";

	// TODO move timer thing to view itself
	Handler handler = new Handler();
	Runnable runnable = new Runnable()
	{
		public void run()
		{
			contentView.postInvalidate();
			handler.postDelayed(runnable, 250);
		}
	};

	private TLView contentView;
	private CalStuff calstuff;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		calstuff = new CalStuff(this);

		setContentView(R.layout.main);

		contentView = (TLView) findViewById(R.id.fullscreen_content);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);

		calstuff.LoadCalendars();
		Log.d(LogTag, "calendars loaded: " + calstuff.ourCalendars.size());
		
		calstuff.LoadEvents();
		Log.d(LogTag, "events loaded: " + calstuff.ourEvents.size());
		
		/*calstuff.LoadInstances();
		Log.d(LogTag, "instances loaded: " + calstuff.ourInstances.size());*/
		
		contentView.SetCalStuff(calstuff);
	}

	@Override
	protected void onPause()
	{
		Log.d(LogTag, "pause");
		super.onPause();

		handler.removeCallbacks(runnable);
	}

	@Override
	protected void onResume()
	{
		Log.d(LogTag, "resume");
		super.onResume();

		runnable.run();
	}
}
