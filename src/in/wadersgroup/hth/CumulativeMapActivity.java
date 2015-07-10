package in.wadersgroup.hth;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
public class CumulativeMapActivity extends Activity {

	GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_c_map);

		// Get a handle to the Map Fragment
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		Intent i_get = getIntent();

		ArrayList<String> lat = new ArrayList<String>();
		ArrayList<String> lng = new ArrayList<String>();

		lat = i_get.getStringArrayListExtra("lats");

		lng = i_get.getStringArrayListExtra("lngs");

		ArrayList<Double> lats = new ArrayList<Double>();
		ArrayList<Double> lngs = new ArrayList<Double>();

		LatLng def = new LatLng(23.987121, 83.095150);

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(def, 4));

		for (int i = 0; i < lat.size(); i++) {

			lats.add(Double.parseDouble(lat.get(i)));
			lngs.add(Double.parseDouble(lng.get(i)));

			LatLng ngo = new LatLng(Double.parseDouble(lat.get(i)),
					Double.parseDouble(lng.get(i)));

			map.addMarker(new MarkerOptions().icon(
					BitmapDescriptorFactory
							.fromResource(R.drawable.c_map_marker)).position(
					ngo));

		}

	}

}
