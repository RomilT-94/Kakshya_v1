package in.wadersgroup.hth;

import com.google.android.gms.plus.PlusOneButton;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Romil
 * 
 */
public class AboutActivity extends Activity {

	PlusOneButton mPlusOneButton;

	// The request code must be 0 or greater.
	private static final int PLUS_ONE_REQUEST_CODE = 0;

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

		setContentView(R.layout.activity_about);

		mPlusOneButton = (PlusOneButton) findViewById(R.id.plus_one_button);

		Typeface type1 = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoCondensed-Light.ttf");
//		Typeface type2 = Typeface.createFromAsset(getAssets(),
//				"fonts/Roboto-Medium.ttf");

		Typeface type3 = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoCondensed-Light.ttf");

		TextView partT = (TextView) findViewById(R.id.textView2);
		TextView partT1 = (TextView) findViewById(R.id.textView3);
		TextView partT2 = (TextView) findViewById(R.id.textView4);

		partT.setTypeface(type3);
		partT1.setTypeface(type3);
		partT2.setTypeface(type3);

		// partT.setText(Html.fromHtml(getString(R.string.about_one)));

		Button link = (Button) findViewById(R.id.button1);

		link.setTypeface(type1);
		link.setTextColor(getResources().getColor(R.color.orange));

		link.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Uri uri = Uri.parse("http://kakshya.in");
				Intent inte = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(inte);

			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case R.id.action_clear:
			finish();

		}

		return super.onOptionsItemSelected(item);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.cancel, menu);
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		String URL = "https://market.android.com/details?id=in.wadersgroup.hth";

		// Refresh the state of the +1 button each time the activity receives
		// focus.
		mPlusOneButton.initialize(URL, PLUS_ONE_REQUEST_CODE);

	}

}
