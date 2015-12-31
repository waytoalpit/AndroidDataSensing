package com.example.androiddatasensing;

import java.util.Random;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.GridLabelRenderer.GridStyle;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import android.R.color;
import android.support.v7.app.ActionBarActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	TextView time = null;
	TextView location = null;
	TextView channel = null;
	TextView power = null;
	TextView pilot = null;

	private static final Random RANDOM = new Random();
	private LineGraphSeries<DataPoint> series;
	private int lastX = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		time = (TextView) findViewById(R.id.timeval);
		time.setText("13:00 PM");

		location = (TextView) findViewById(R.id.locationval);
		location.setText("New York");

		channel = (TextView) findViewById(R.id.channelval);
		channel.setText("95");

		power = (TextView) findViewById(R.id.powerval);
		power.setText("122 MW");

		pilot = (TextView) findViewById(R.id.pilotval);
		pilot.setText("No");

		GraphView graph = (GraphView) findViewById(R.id.graph);
		series = new LineGraphSeries<DataPoint>();
		series.setBackgroundColor(color.holo_blue_light);
		
		graph.addSeries(series);
		graph.getGridLabelRenderer().setGridStyle(GridStyle.HORIZONTAL);
		graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
		
		Viewport viewport = graph.getViewport();
		viewport.setYAxisBoundsManual(true);
		viewport.setMinY(0);
	    viewport.setMaxY(10);
	    viewport.setScrollable(true);

	}

	@Override
	protected void onResume() {
		super.onResume();
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							addEntry();
						}
					});
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	private void addEntry() {
		Random rand = new Random();
		for (int i = 1; i <= 512; i++) {
			series.appendData(new DataPoint(lastX++, RANDOM.nextDouble()* 10d),
					false, 512);
		}		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
