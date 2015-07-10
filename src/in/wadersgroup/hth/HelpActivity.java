package in.wadersgroup.hth;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gc.materialdesign.views.Button;

/**
 * @author Romil
 * 
 */
public class HelpActivity extends Activity {
	Button feed, sha, dona;
	RelativeLayout transBaby;
	TextView mainContent;
	TextView mainButton;
	int count = 0;
	Vibrator vibrator;

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

		setContentView(R.layout.activity_help);
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		feed = (Button) findViewById(R.id.bFeedb);
		feed.setBackgroundColor(getResources().getColor(R.color.purple));
		sha = (Button) findViewById(R.id.bSha);
		sha.setBackgroundColor(getResources().getColor(R.color.purple));
		dona = (Button) findViewById(R.id.bDona);
		dona.setBackgroundColor(getResources().getColor(R.color.purple));
		transBaby = (RelativeLayout) findViewById(R.id.llTranslation);
		mainContent = (TextView) findViewById(R.id.tvContent);
		mainButton = (TextView) findViewById(R.id.tvCommonButton);
		Typeface type2 = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoCondensed-Light.ttf");
		Typeface type3 = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Medium.ttf");
		TextView title = (TextView) findViewById(R.id.tvTitleHelp);
		title.setTypeface(type3);

		TextView tag = (TextView) findViewById(R.id.tvTagHelp);
		tag.setTypeface(type2);
		mainContent.setVisibility(View.INVISIBLE);
		mainButton.setVisibility(View.INVISIBLE);
		mainButton.setTypeface(type3);

		// LinearLayout parent = (LinearLayout) findViewById(R.id.llHelp);
		//
		// float parentCenterX = parent.getX() + parent.getWidth() / 2;
		// float parentCenterY = parent.getY() + parent.getHeight() / 2;

		// Animation anim = AnimationUtils.loadAnimation(this, R.anim.anime);
		// anim.setInterpolator((new AccelerateDecelerateInterpolator()));
		// anim.setFillAfter(true);
		// b.setAnimation(anim);

		// b.animate().translationX(250).translationY(0).setDuration(1000);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);

		// DisplayMetrics metrics = getResources().getDisplayMetrics();
		// int width = metrics.widthPixels;
		//
		// int buttonWidth = feed.getMeasuredWidth();

		// feed.animate().translationX((width / 2) - (buttonWidth / 2))
		// .translationY(0).setDuration(2000);
		// // b.setLayoutParams(new LinearLayout.LayoutParams(width / 2, 100));
		//
		// sha.animate().translationX((width / 2) - (sha.getWidth() / 2))
		// .translationY(0).setDuration(1500);
		// // a.setLayoutParams(new LinearLayout.LayoutParams(width / 2, 100));
		//
		// dona.animate().translationX((width / 2) - (dona.getWidth() / 2))
		// .translationY(0).setDuration(1000);
		// // c.setLayoutParams(new LinearLayout.LayoutParams(width / 2, 100));

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		// Popping Dialogs For Different Buttons

		mainButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (mainButton.getText().toString()
						.contentEquals("Donate Now!")) {

					Intent i = new Intent(HelpActivity.this, MainActivity.class);
					startActivity(i);
					finish();

				} else if (mainButton.getText().toString()
						.contentEquals("Spread the Word!")) {

					shareApp();

				} else if (mainButton.getText().toString()
						.contentEquals("Share Feedback!")) {

					Intent i = new Intent(HelpActivity.this,
							FeedbackActivity.class);
					startActivity(i);

				}

			}
		});

		dona.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				count++;
				if (vibrator.hasVibrator()) {
					vibrator.vibrate(20);
				}

				transBaby.animate().translationX(0).translationY(-380)
						.setDuration(1000);
				Typeface type1 = Typeface.createFromAsset(getAssets(),
						"fonts/RobotoCondensed-Light.ttf");
				mainContent.setTypeface(type1);
				mainContent
						.setText("The work of non-governmental organizations (NGOs) protecting the environment,"
								+ "helping the sick and needy, preserving arts and culture is "
								+ "by nature unprofitable. "
								+ "Traditionally, NGOs rely on the goodwill and generosity "
								+ "of others to cover the costs of "
								+ "their activities through grants and donations. Today, "
								+ "unfortunately, NGOs find that such "
								+ "traditional funding sources are often insufficient to "
								+ "meet growing needs and rising costs. "
								+ "Hence, one should help these organisations so that they "
								+ "can continue with the good work"
								+ " that they are doing. A donor, who is a resident of India "
								+ "and a non resident Indian "
								+ "holding a Indian Passport and also having taxable income in "
								+ "India, can claim income tax "
								+ "benefit from the donation made to a NGO, which registered "
								+ "with Income tax department "
								+ "through 12A, with tax exemption status under section 80 g, "
								+ "80 GGA, 35 ac (i & ii), (i & iii) "
								+ "of the Income Tax Act of 1961. PLEASE DONATE!");
				mainButton.setText("Donate Now!");
				mainButton
						.setTextColor(getResources().getColor(R.color.orange));

				Timer t = new Timer(false);
				if (count == 1) {
					t.schedule(new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								public void run() {
									mainContent.setVisibility(View.VISIBLE);
									mainButton.setVisibility(View.VISIBLE);

									final Animation in = new AlphaAnimation(
											0.0f, 1.0f);
									mainContent.setAnimation(in);
									mainButton.setAnimation(in);
									in.setDuration(1300);

								}
							});
						}
					}, 1300);
				} else {
					// t.schedule(new TimerTask() {
					// @Override
					// public void run() {
					// runOnUiThread(new Runnable() {
					// public void run() {
					// mainContent.setVisibility(View.VISIBLE);

					final Animation in = new AlphaAnimation(0.0f, 1.0f);
					mainContent.setAnimation(in);
					mainButton.setAnimation(in);
					in.setDuration(1000);

					// }
					// });
					// }
					// }, 100);
				}

				// TODO Auto-generated method stub

				// Creating a Alert Dialog

				// AlertDialog.Builder builder = new AlertDialog.Builder(
				// HelpActivity.this);
				//
				// // 2. Chain together various setter methods to set the dialog
				// // characteristics
				//
				// builder.setMessage("Testing the Dialog")
				// .setTitle("WadersGroup");
				//
				// // Add the buttons
				// builder.setPositiveButton("Donate",
				// new DialogInterface.OnClickListener() {
				// public void onClick(DialogInterface dialog, int id) {
				// // User clicked OK button
				// }
				// });
				// // builder.setNegativeButton("Cancel",
				// // new DialogInterface.OnClickListener() {
				// // public void onClick(DialogInterface dialog, int id) {
				// // // User cancelled the dialog
				// // }
				// // });
				// // Set other dialog properties
				//
				// // Create the AlertDialog
				// AlertDialog dialog = builder.create();
				// dialog.show();

				// Intent i = new Intent(HelpActivity.this,
				// DialogActivity.class);
				// i.putExtra("image_selector", "donate");
				// i.putExtra(
				// "text",
				// );
				// startActivity(i);
				//
			}
		});

		sha.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				count++;

				if (vibrator.hasVibrator()) {
					vibrator.vibrate(20);
				}

				transBaby.animate().translationX(0).translationY(-380)
						.setDuration(1000);
				Typeface type1 = Typeface.createFromAsset(getAssets(),
						"fonts/RobotoCondensed-Light.ttf");
				mainContent.setTypeface(type1);
				mainContent
						.setText("You can also help us by sharing our application. If the number of users will"
								+ "increase, so will the number of donations."
								+ "You can help us in a great way by spreading "
								+ "the word. You can share our app on your website, "
								+ "your blog, provide a download link. "
								+ "You can also share it by posting on Facebook, "
								+ "Google Plus, Twitter and other social media platforms. "
								+ "You can text your friends or rather send them a whatsapp "
								+ "message. One text message from your side will be of great "
								+ "help to us. Please Share the application and spread the word "
								+ "so that it reaches out to the people who will listen.");
				mainButton.setText("Spread the Word!");
				mainButton
						.setTextColor(getResources().getColor(R.color.orange));
				Timer t = new Timer(false);
				if (count == 1) {
					t.schedule(new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								public void run() {
									mainContent.setVisibility(View.VISIBLE);
									mainButton.setVisibility(View.VISIBLE);

									final Animation in = new AlphaAnimation(
											0.0f, 1.0f);
									mainContent.setAnimation(in);
									mainButton.setAnimation(in);
									in.setDuration(1300);

								}
							});
						}
					}, 1300);
				} else {
					// t.schedule(new TimerTask() {
					// @Override
					// public void run() {
					// runOnUiThread(new Runnable() {
					// public void run() {

					final Animation in = new AlphaAnimation(0.0f, 1.0f);
					mainContent.setAnimation(in);
					mainButton.setAnimation(in);
					in.setDuration(1000);

					// }
					// });
					// }
					// }, 100);
				}

				// Intent i = new Intent(HelpActivity.this,
				// DialogActivity.class);
				// i.putExtra("image_selector", "share");
				// i.putExtra(
				// "text",
				// );
				// startActivity(i);

			}
		});

		feed.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				count++;

				if (vibrator.hasVibrator()) {
					vibrator.vibrate(20);
				}

				transBaby.animate().translationX(0).translationY(-380)
						.setDuration(1000);
				Typeface type1 = Typeface.createFromAsset(getAssets(),
						"fonts/RobotoCondensed-Light.ttf");
				mainContent.setTypeface(type1);
				mainContent
						.setText("You must give us feedback. "
								+ "Your feedbacks will help us improve "
								+ "in a great way. Please share your ideas and "
								+ "suggestions. We will try and incorporate your "
								+ "ideas and make this application even more successful.");
				mainButton.setText("Share Feedback!");
				mainButton
						.setTextColor(getResources().getColor(R.color.orange));

				Timer t = new Timer(false);
				if (count == 1) {
					t.schedule(new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								public void run() {
									mainContent.setVisibility(View.VISIBLE);
									mainButton.setVisibility(View.VISIBLE);

									final Animation in = new AlphaAnimation(
											0.0f, 1.0f);
									mainContent.setAnimation(in);
									mainButton.setAnimation(in);
									in.setDuration(1300);

								}
							});
						}
					}, 1300);
				} else {
					// t.schedule(new TimerTask() {
					// @Override
					// public void run() {
					// runOnUiThread(new Runnable() {
					// public void run() {

					final Animation in = new AlphaAnimation(0.0f, 1.0f);
					mainContent.setAnimation(in);
					mainButton.setAnimation(in);
					in.setDuration(1000);

					// }
					// });
					// }
					// }, 100);
				}

				// Intent i = new Intent(HelpActivity.this,
				// DialogActivity.class);
				// i.putExtra("image_selector", "feedback");
				// i.putExtra(
				// "text",
				// );
				// startActivity(i);

			}
		});

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case R.id.action_clear:

			mainContent.setVisibility(View.INVISIBLE);
			mainButton.setVisibility(View.INVISIBLE);
			transBaby.animate().translationX(0).translationY(0)
					.setDuration(1000);
			Timer ti = new Timer(false);
			ti.schedule(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					finish();
				}
			}, 1100);

		}

		return super.onOptionsItemSelected(item);

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		mainContent.setVisibility(View.INVISIBLE);
		mainButton.setVisibility(View.INVISIBLE);
		transBaby.animate().translationX(0).translationY(0).setDuration(1000);
		Timer ti = new Timer(false);
		ti.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				finish();
			}
		}, 1100);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.cancel, menu);
		return super.onCreateOptionsMenu(menu);

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

}
