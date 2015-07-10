package in.wadersgroup.hth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author Romil
 * 
 */
public class MappingActivity extends Activity {

	GoogleMap map;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		int sdkVersion = android.os.Build.VERSION.SDK_INT;
		if (sdkVersion >= 21) {

			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(getResources().getColor(
					R.color.darker_orange));
		}

		setContentView(R.layout.activity_map);

		TextView tit = (TextView) findViewById(R.id.tvTitle);
		ImageView logo = (ImageView) findViewById(R.id.ivLogo);
		TextView add = (TextView) findViewById(R.id.tvAddress);

		// Get a handle to the Map Fragment
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		Intent i = getIntent();

		String url = i.getStringExtra("logo_url");
		String address = i.getStringExtra("address");
		Double latitude = i.getDoubleExtra("latitude", 58.987654);
		Double longitude = i.getDoubleExtra("longitude", 87.765432);
		String title = i.getStringExtra("title");
		String area = i.getStringExtra("area");

		ImageLoader imageLoader = new ImageLoader(getApplicationContext());
		imageLoader.DisplayImage(url, logo);

		LatLng ngo = new LatLng(latitude, longitude);

		tit.setText(title);
		Typeface type2 = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoCondensed-Light.ttf");
		add.setTypeface(type2);

		add.setText(address);
		if (latitude != 11.111111 & longitude != 11.111111) {

			map.moveCamera(CameraUpdateFactory.newLatLngZoom(ngo, 14));

			map.addMarker(new MarkerOptions()
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.map_marker)).title(title)
					.snippet("Area of Service: " + area).position(ngo));

			// Toast.makeText(getApplicationContext(),
			// "Latitude " + latitude + " Longitude " + longitude,
			// Toast.LENGTH_LONG).show();
		} else {
			// Toast.makeText(getApplicationContext(), "Location Unavailable",
			// Toast.LENGTH_LONG).show();
		}

	}

	@SuppressWarnings("finally")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		try {
			// TODO Auto-generated method stub

			switch (item.getItemId()) {
			case R.id.Normal:
				map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				return true;

			case R.id.Satellite:
				map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
				return true;

			case R.id.Terrain:
				map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
				return true;

			case R.id.Hybrid:
				map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
				return true;

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.map_bar, menu);
		return super.onCreateOptionsMenu(menu);

	}

}
