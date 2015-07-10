package in.wadersgroup.hth;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * @author Romil
 * 
 */
public class FavoriteFragment extends Fragment {

	MediaPlayer mp;
	View rootView;
	ListView list;
	LazyAdapterFav adapter;
	DatabaseHelperAdapter dbHelper;
	ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();

	// All static variables
	static final String URL = "http://www.wadersgroup.in/ngo_res/ngo_info.xml";
	static final String KEY_ID = "id";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		// StrictMode.enableDefaults(); // STRICT MODE ENABLED

		dbHelper = new DatabaseHelperAdapter(getActivity());

		rootView = inflater.inflate(R.layout.main1, container, false);

		setHasOptionsMenu(false);

		list = (ListView) rootView.findViewById(R.id.list);

		dataList = dbHelper.getAllData();

		// Getting adapter by passing xml data ArrayList
		adapter = new LazyAdapterFav(getActivity(), dataList);

		list.setAdapter(adapter);

		list.setDivider(null);
		list.setDividerHeight(0);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					final int position, long id) {
				// TODO Auto-generated method stub
				final String ngo_name = dataList.get(position).get("name");
				final String area_service = dataList.get(position).get("area");
				String date_formation = dataList.get(position).get("date");
				String head_organisation = dataList.get(position).get("head");
				final String lat = dataList.get(position).get("lat");
				final String lng = dataList.get(position).get("lng");
				final String address = dataList.get(position).get("address");
				final String email = dataList.get(position).get("email");
				String phone = dataList.get(position).get("phone");
				String website = dataList.get(position).get("website");
				final String thumb_url = dataList.get(position).get("thumb");

			}

		});

		return rootView;

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub

		MenuInflater inflater_menu = getActivity().getMenuInflater();
		inflater.inflate(R.menu.settings_fav, menu);
		super.onCreateOptionsMenu(menu, inflater);

	}

	@SuppressWarnings("finally")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		try {
			// TODO Auto-generated method stub

			switch (item.getItemId()) {

			case R.id.app_share:

				shareApp();

				break;

			case R.id.Feedback:
				Intent i = new Intent(getActivity(), FeedbackActivity.class);
				startActivity(i);
				break;

			case R.id.Rate:

				launchMarket();
				break;

			// case R.id.Share:
			//
			//
			//
			// break;

			case R.id.Help:

				Intent ih = new Intent(getActivity(), HelpActivity.class);
				startActivity(ih);

				break;

			case R.id.Register:

				Uri uri = Uri.parse("http://kakshya.in/register");
				Intent inte = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(inte);

				break;

			case R.id.About:

				Intent start_about = new Intent(getActivity(),
						AboutActivity.class);
				startActivity(start_about);

				break;

			// case R.id.Logout:
			// // Intent logger = new Intent(MainActivity.this,
			// // LoginActivity.class);
			// // logger.putExtra("logger", "logout");
			// SharedPreferences settings = PreferenceManager
			// .getDefaultSharedPreferences(MainActivity.this);
			// SharedPreferences.Editor editor = settings.edit();
			// editor.putString("email", "");
			// editor.commit();
			// // startActivity(logger);
			// // finish();
			// // // System.exit(0);
			//
			// // Facebook Part of it
			//
			// // currentSession.closeAndClearTokenInformation();
			//
			// // Google Part of it
			//
			// mPlusClient.clearDefaultAccount();
			// mPlusClient.revokeAccessAndDisconnect(this);
			//
			// finish();
			//
			// break;

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return super.onOptionsItemSelected(item);
		}
	}

	private void shareApp() {
		// TODO Auto-generated method stub

		final Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		String uri = ("http://kakshya.in/play");
		intent.putExtra(Intent.EXTRA_TEXT,
				"KAKSHYA - 'Your Effort Can Be The Change'. "
						+ "Spread the word and see the change." + uri);

		try {
			startActivity(Intent.createChooser(intent, "Select an action"));
		} catch (android.content.ActivityNotFoundException ex) {
			// (handle error)
		}

	}

	private void launchMarket() {
		Uri uri = Uri.parse("market://details?id="
				+ getActivity().getPackageName());
		System.out.println(getActivity().getPackageName());
		Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
		try {
			startActivity(myAppLinkToMarket);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(getActivity(), "Not Available on Google Play Store",
					Toast.LENGTH_LONG).show();
		}
	}

}
