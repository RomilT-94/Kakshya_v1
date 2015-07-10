package in.wadersgroup.hth;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class NavigationDrawerFragment extends Fragment {

	/**
	 * @author Romil
	 */
	private FragmentActivity activityContext;

	private ActionBarDrawerToggle mDrawerToggle;
	public static DrawerLayout mDrawerLayout;
	private View containerView;
	private RecyclerAdapter mRecyclerAdapter;
	private RecyclerView drawerList;
	private boolean mUserLearnedDrawer;
	private boolean mFromSavedInstanceState;

	public NavigationDrawerFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View layout = inflater.inflate(R.layout.navigation_fragment, container,
				false);

		TextView user_nav = (TextView) layout
				.findViewById(R.id.tvNavUserNameSet);

		// TextView username = (TextView) getActivity().findViewById(
		// R.id.tvNavName);
		//
		Typeface type1 = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/Roboto-Bold.ttf");
		user_nav.setTypeface(type1);
		// user_nav.setTextColor(getResources().getColor(R.color.white));

		// Take Emails Again

		Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
		Account[] accounts = AccountManager.get(getActivity()).getAccounts();
		String all_emails = "";
		for (Account account : accounts) {
			if (emailPattern.matcher(account.name).matches()) {
				String possibleEmail = account.name + ",";

				all_emails = all_emails + possibleEmail;

				// Toast.makeText(getApplicationContext(),
				// "Email: " + possibleEmail, Toast.LENGTH_LONG)
				// .show();

			}
		}

		String first_mail = all_emails.split(",")[0];
		user_nav.setText(first_mail);

		// TextView kakshya_link = (TextView) layout
		// .findViewById(R.id.tvWadersGroup);
		// kakshya_link.setVisibility(View.INVISIBLE);
		// kakshya_link.setTypeface(type1);
		// kakshya_link.setTextColor(getResources().getColor(R.color.black));
		// kakshya_link.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// Uri uri = Uri.parse("http://kakshya.in");
		// Intent inte = new Intent(Intent.ACTION_VIEW, uri);
		// startActivity(inte);
		//
		// }
		// });

		drawerList = (RecyclerView) layout.findViewById(R.id.drawerList);
		mRecyclerAdapter = new RecyclerAdapter(getActivity(), getData());
		drawerList.setAdapter(mRecyclerAdapter);
		final LinearLayoutManager layoutManager = new LinearLayoutManager(
				getActivity());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		drawerList.setLayoutManager(layoutManager);
		setHasOptionsMenu(true);

		return layout;

	}

	public static List<NavListItems> getData() {

		List<NavListItems> data = new ArrayList<>();

		// Extra icons: R.drawable.ic_nav_sort_domain,
		// R.drawable.ic_nav_sort_state,
		int[] icons = { R.drawable.ic_nav_event, R.drawable.ic_nav_suggestngo,
				R.drawable.ic_nav_register, R.drawable.ic_nav_help,
				R.drawable.ic_nav_rate, R.drawable.ic_nav_feedback,
				R.drawable.ic_nav_about };
		// Extra titles: "Sort by Categories", "Sort by States",
		String[] titles = { "Events", "Suggest an NGO", "Register your NGO",
				"Help", "Rate Us on Google Play", "Give Feedback", "About Us" };

		for (int i = 0; i < icons.length && i < titles.length; i++) {

			NavListItems current = new NavListItems();
			current.iconId = icons[i];
			current.title = titles[i];
			data.add(current);
		}

		return data;

	}

	public void setUp(int drawerID, DrawerLayout drawerLayout) {
		// TODO Auto-generated method stub

		containerView = getActivity().findViewById(drawerID);
		mDrawerLayout = drawerLayout;
		// getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		// getActivity().getActionBar().setHomeButtonEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout,
				R.string.drawer_open, R.string.drawer_close) {

			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerClosed(drawerView);

				getActivity().invalidateOptionsMenu();

			}

			@Override
			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerOpened(drawerView);

				getActivity().invalidateOptionsMenu();

			}

		};

		// mDrawerLayout.openDrawer(containerView);

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		mDrawerLayout.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub

		getActivity().getMenuInflater().inflate(R.menu.settings, menu);

		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		mDrawerToggle.syncState();

	}

	private void shareApp() {
		// TODO Auto-generated method stub

		final Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		String uri = ("http://kakshya.in/play");
		intent.putExtra(Intent.EXTRA_TEXT,
				"KAKSHYA - 'Your Effort Can Be The Change'. "
						+ "Spread the word and see the change. " + uri);

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
			Toast.makeText(getActivity(),
					"Please Install Google Play Store App", Toast.LENGTH_LONG)
					.show();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// TODO Auto-generated method stub
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {

		// case R.id.cmf:
		//
		// Toast.makeText(getActivity(), "Yo Yo", Toast.LENGTH_LONG)
		// .show();
		//
		// Intent i_latlng = new Intent(getActivity(),
		// CumulativeMapActivity.class);
		//
		// i_latlng.putStringArrayListExtra("lats",
		// (ArrayList<String>) lat);
		// i_latlng.putStringArrayListExtra("lngs",
		// (ArrayList<String>) lng);
		//
		// startActivity(i_latlng);
		//
		// break;

		case R.id.app_share:

			shareApp();

			break;

		case R.id.app_search:

			searchApp();

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

		return super.onOptionsItemSelected(item);

	}

	private void searchApp() {
		// TODO Auto-generated method stub

		Toast.makeText(getActivity(), "Search Coming Soon!", Toast.LENGTH_LONG)
				.show();

		// Intent i = new Intent(getActivity(), TrafficMonitorActivity.class);
		// startActivity(i);

	}

}
