package in.wadersgroup.hth;

import com.gc.materialdesign.views.Button;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Romil
 * 
 */
public class DialogActivity extends Activity {

	ImageView header;
	Button click, cancel;
	TextView fill;
	TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// Changing ActionBar Title Color

		setContentView(R.layout.dialog_fav);
		header = (ImageView) findViewById(R.id.ivDialogFav);
		click = (Button) findViewById(R.id.bCommon);

		click.setBackgroundColor(getResources().getColor(R.color.orange));

		cancel = (Button) findViewById(R.id.bCancelD);

		cancel.setBackgroundColor(getResources().getColor(R.color.black));

		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();

			}
		});
		fill = (TextView) findViewById(R.id.tvDetailsFav);
		Intent i = getIntent();
		final String iv = i.getStringExtra("image_selector");
		String t = i.getStringExtra("text");
		System.out.print("Image " + iv + " Text " + t);
		// Toast.makeText(getApplicationContext(), "Image " + iv + " Text " + t,
		// Toast.LENGTH_LONG).show();
		Typeface type1 = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Thin.ttf");
		fill.setTypeface(type1);
		fill.setText(t);

		if (iv.contentEquals("donate")) {
			int actionBarTitleId = Resources.getSystem().getIdentifier(
					"action_bar_title", "id", "android");
			if (actionBarTitleId > 0) {
				title = (TextView) findViewById(actionBarTitleId);

			}
			header.setImageResource(R.drawable.ic_rupee);

			if (title != null) {
				title.setText("DONATE");
			}

		} else if (iv.contentEquals("share")) {

			header.setImageResource(R.drawable.ic_action_share1);

			int actionBarTitleId = Resources.getSystem().getIdentifier(
					"action_bar_title", "id", "android");
			if (actionBarTitleId > 0) {
				title = (TextView) findViewById(actionBarTitleId);

			}
			if (title != null) {

				title.setText("SHARE!!!");
			}

		} else if (iv.contentEquals("feedback")) {
			int actionBarTitleId = Resources.getSystem().getIdentifier(
					"action_bar_title", "id", "android");
			if (actionBarTitleId > 0) {
				title = (TextView) findViewById(actionBarTitleId);

			}
			header.setImageResource(R.drawable.ic_contacts);

			if (title != null) {
				title.setText("FEEDBACK");
			}

		}

		click.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (iv.contentEquals("donate")) {

					Intent i = new Intent(DialogActivity.this,
							MainActivity.class);
					HelpActivity h = new HelpActivity();
					h.finish();
					startActivity(i);

				} else if (iv.contentEquals("share")) {

					final Intent intent = new Intent(Intent.ACTION_SEND);
					intent.setType("text/plain");
					String uri = ("http://kakshya.in/play");
					intent.putExtra(Intent.EXTRA_TEXT,
							"KAKSHYA - 'Your Effort Can Be The Change'. "
									+ "Spread the word and see the change."
									+ uri);

					try {
						startActivity(Intent.createChooser(intent,
								"Select an action"));
					} catch (android.content.ActivityNotFoundException ex) {
						// (handle error)
					}

				} else if (iv.contentEquals("feedback")) {

					Intent i = new Intent(DialogActivity.this,
							FeedbackActivity.class);
					HelpActivity h = new HelpActivity();
					h.finish();
					finish();
					startActivity(i);

				}

			}
		});

	}

}
