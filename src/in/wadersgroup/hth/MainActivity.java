package in.wadersgroup.hth;

import in.wadersgroup.hth.SlidingTabLayout.TabColorizer;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * @author Romil
 * 
 */

public class MainActivity extends ActionBarActivity {

	NavigationDrawerFragment drawer;
	private Toolbar toolbar;
	private ViewPager mPager;
	private SlidingTabLayout mTabs;

	// For Facebook Logout
	//
	// private Session.StatusCallback sessionStatusCallback;
	// private Session currentSession;

	// For Facebook Logout

	// For Google Logout
	// private PlusClient mPlusClient;
	// For Google Logout

	// Tab titles
	private String[] tabs = { "ALL", "MY NGO(s)" };

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		int sdkVersion = android.os.Build.VERSION.SDK_INT;
		if (sdkVersion >= 21) {

			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(getResources().getColor(
					R.color.darker_orange));
		}

		setContentView(R.layout.activity_main);

		toolbar = (Toolbar) findViewById(R.id.app_bar);
		TextView toolbar_title = (TextView) toolbar
				.findViewById(R.id.toolbar_title);
		toolbar_title.setText("Kakshya");
		toolbar_title.setTextColor(getResources().getColor(R.color.black));

		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayShowTitleEnabled(false);

		mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
		mPager = (ViewPager) findViewById(R.id.pager);

		mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

		mTabs.setBackgroundColor(getResources().getColor(R.color.orange));
		mTabs.setDistributeEvenly(true);

		// mTabs.setCustomTabView(R.layout.custom_tab_view, R.id.tvTabText);
		mTabs.setCustomTabColorizer(new TabColorizer() {

			@Override
			public int getIndicatorColor(int position) {
				// TODO Auto-generated method stub
				return getResources().getColor(R.color.tab_colorizer);
			}
		});
		mTabs.setViewPager(mPager);

		drawer = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fNav);
		// Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		drawer.setUp(R.id.fNav, (DrawerLayout) findViewById(R.id.drawer_layout));

		// Initialization

		// actionBar = getActionBar();
		// actionBar.setIcon(new ColorDrawable(getResources().getColor(
		// android.R.color.transparent)));

		// mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		// For Facebook Logout

		// sessionStatusCallback = new Session.StatusCallback() {
		//
		// @Override
		// public void call(Session session, SessionState state,
		// Exception exception) {
		// // TODO Auto-generated method stub
		//
		// onSessionStateChange(session, state, exception);
		//
		// }
		// };
		//
		// currentSession = new Session.Builder(this).build();
		// currentSession.addCallback(sessionStatusCallback);

		// Session.OpenRequest openRequest = new Session.OpenRequest(
		// MainActivity.this);
		// openRequest.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO);
		// openRequest.setRequestCode(Session.DEFAULT_AUTHORIZE_ACTIVITY_CODE);
		//
		// currentSession.openForPublish(openRequest);

		// For Facebook Logout

		// For Google Logout
		// mPlusClient = new PlusClient.Builder(this, this, this).setActions(
		// "http://schemas.google.com/AddActivity",
		// "http://schemas.google.com/BuyActivity").build();
		// For Google Logout

	}

	// private void onSessionStateChange(Session session, SessionState state,
	// Exception exception) {
	// if (session != currentSession) {
	// return;
	// }
	//
	// if (state.isOpened()) {
	// // Log in just happened.
	// Toast.makeText(getApplicationContext(), "session opened",
	// Toast.LENGTH_SHORT).show();
	// } else if (state.isClosed()) {
	// // Log out just happened. Update the UI.
	// Toast.makeText(getApplicationContext(), "session closed",
	// Toast.LENGTH_SHORT).show();
	// }
	// }

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// mPlusClient.connect();

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // TODO Auto-generated method stub
	// MenuInflater inflater = getMenuInflater();
	// inflater.inflate(R.menu.settings, menu);
	// return super.onCreateOptionsMenu(menu);
	// }
	//
	// @SuppressWarnings("finally")
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	//
	// if (mDrawerToggle.onOptionsItemSelected(item)) {
	// return true;
	// }
	//
	// try {
	//
	// switch (item.getItemId()) {
	//
	// case R.id.app_share:
	//
	// shareApp();
	//
	// break;
	//
	// case R.id.Feedback:
	// Intent i = new Intent(MainActivity.this, FeedbackActivity.class);
	// startActivity(i);
	// break;
	//
	// case R.id.Rate:
	//
	// launchMarket();
	// break;
	//
	// // case R.id.Share:
	// // //
	// // //
	// // //
	// // // break;
	//
	// case R.id.Help:
	//
	// Intent ih = new Intent(MainActivity.this, HelpActivity.class);
	// startActivity(ih);
	//
	// break;
	// //
	// case R.id.Register:
	//
	// Uri uri = Uri.parse("http://kakshya.in/register");
	// Intent inte = new Intent(Intent.ACTION_VIEW, uri);
	// startActivity(inte);
	//
	// break;
	// // //
	// // // // // case R.id.Logout:
	// // // // // // Intent logger = new Intent(MainActivity.this,
	// // // // // // LoginActivity.class);
	// // // // // // logger.putExtra("logger", "logout");
	// // // // // SharedPreferences settings = PreferenceManager
	// // // .getDefaultSharedPreferences(MainActivity.this);
	// // // SharedPreferences.Editor editor = settings.edit();
	// // // editor.putString("email", "");
	// // // editor.commit();
	// // // startActivity(logger);
	// // // finish();
	// // // System.exit(0);
	// // //
	// // // // Facebook Part of it
	// // //
	// // // currentSession.closeAndClearTokenInformation();
	// // //
	// // // //Google Part of it
	// // //
	// // // mPlusClient.clearDefaultAccount();
	// // // mPlusClient.revokeAccessAndDisconnect(this);
	// // //
	// // // finish();
	// // //
	// // // break;
	// // //
	// }
	//
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } finally {
	// return super.onOptionsItemSelected(item);
	// }
	//
	// }

	// private void shareApp() {
	// // TODO Auto-generated method stub
	//
	// final Intent intent = new Intent(Intent.ACTION_SEND);
	// intent.setType("text/plain");
	// String uri = ("http://kakshya.in/play");
	// intent.putExtra(Intent.EXTRA_TEXT,
	// "KAKSHYA - 'Your Effort Can Be The Change'. "
	// + "Spread the word and see the change." + uri);
	//
	// try {
	// startActivity(Intent.createChooser(intent, "Select an action"));
	// } catch (android.content.ActivityNotFoundException ex) {
	// // (handle error)
	// }
	//
	// }

	// private void launchMarket() {
	// Uri uri = Uri.parse("market://details?id=" + getPackageName());
	// System.out.println(getPackageName());
	// Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
	// try {
	// startActivity(myAppLinkToMarket);
	// } catch (ActivityNotFoundException e) {
	// Toast.makeText(this, "Not Available on Google Play Store",
	// Toast.LENGTH_LONG).show();
	// }
	// }

	class MyPagerAdapter extends FragmentPagerAdapter {

		int icons[] = { R.drawable.ic_action_call, R.drawable.ic_action_star };

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub

			switch (arg0) {
			case 0:
				// NGO fragment activity
				return new NGOFragment();

			case 1:
				// Favorites fragment activity
				return new FavoriteFragment();

			}

			return null;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub

			Drawable drawable = getResources().getDrawable(icons[position]);
			drawable.setBounds(0, 0, 36, 36);
			// ImageSpan iSpan = new ImageSpan(drawable);
			SpannableString sString = new SpannableString("");

			sString.setSpan(
					new ForegroundColorSpan(getResources().getColor(
							R.color.black)), 0, sString.length(),
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

			return tabs[position];
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 2;
		}

	}

}
