package in.wadersgroup.hth;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gc.materialdesign.views.Button;



/**
 * @author Romil
 * 
 */
public class SingleMenuItemActivity extends AppCompatActivity {

	TextView name, address, area_service, date_formation, head_org,
			brief_details, mail, telephone, web_addr;
	ImageButton fav, web_link, mail_link, call_link;
	String phone, email, website, id, thumb_uri, ngo_name, addr, aos, dof,
			head, donation;
	public static String details;
	MediaPlayer mp;
	Button donate;
	private Toolbar toolbar;
	public static SharedPreferences settings;
	DatabaseHelperAdapter dbHelper;
	Double latitude, longitude;
	String lat, lng;
	ImageLoader imageLoader;
	boolean action_map = true;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			// getActionBar().setDisplayHomeAsUpEnabled(true);

			int sdkVersion = android.os.Build.VERSION.SDK_INT;
			if (sdkVersion >= 21) {

				Window window = getWindow();				
				window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
				window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				window.setStatusBarColor(getResources().getColor(
						R.color.darker_orange));
			}

			setContentView(R.layout.single_menu_item_layout);
			// Drawable layout = findViewById(R.id.layout_back).getBackground();
			// layout.setAlpha(30);
			
			toolbar = (Toolbar) findViewById(R.id.app_bar);
			setSupportActionBar(toolbar);

			ButtonFloat addToContacts = (ButtonFloat) findViewById(R.id.bAddToContacts);
			addToContacts.setBackgroundColor(getResources().getColor(
					R.color.purple));
			addToContacts.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					// Creates a new Intent to insert a contact
					Intent intent = new Intent(Intents.Insert.ACTION);
					// Sets the MIME type to match the Contacts Provider
					intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

					intent.putExtra(Intents.Insert.EMAIL, email)
							.putExtra(Intents.Insert.NAME, ngo_name)
							.putExtra(Intents.Insert.PHONE, phone)
							.putExtra(Intents.Insert.POSTAL, addr);

					startActivity(intent);

				}
			});

			name = (TextView) findViewById(R.id.tvTitle);
			address = (TextView) findViewById(R.id.tvArtist);
			area_service = (TextView) findViewById(R.id.tvAos);
			date_formation = (TextView) findViewById(R.id.tvDof);
			head_org = (TextView) findViewById(R.id.tvHoo);
			brief_details = (TextView) findViewById(R.id.tvDetails);
			mail = (TextView) findViewById(R.id.tvEmail);
			telephone = (TextView) findViewById(R.id.tvPhone);
			web_addr = (TextView) findViewById(R.id.tvWebsite);
			fav = (ImageButton) findViewById(R.id.ibFavo);
			web_link = (ImageButton) findViewById(R.id.ibWeb);
			mail_link = (ImageButton) findViewById(R.id.ibMail);
			call_link = (ImageButton) findViewById(R.id.ibCall);
			donate = (Button) findViewById(R.id.bDonate);
			dbHelper = new DatabaseHelperAdapter(this);
			Intent intent = getIntent();
			ngo_name = intent.getStringExtra("title");
			addr = intent.getStringExtra("artist");
			aos = intent.getStringExtra("detailed_service");
			dof = intent.getStringExtra("dof");
			head = intent.getStringExtra("head");
			email = intent.getStringExtra("email");
			phone = intent.getStringExtra("phone");
			website = intent.getStringExtra("website");
			id = intent.getStringExtra("identity");
			thumb_uri = intent.getStringExtra("thumb");
			donation = intent.getStringExtra("donation");
			imageLoader = new ImageLoader(getApplicationContext());

			latitude = intent.getDoubleExtra("latitude", 58.987654);
			longitude = intent.getDoubleExtra("longitude", 76.876543);

			lat = latitude.toString();
			lng = longitude.toString();

			// Toast.makeText(getApplicationContext(), lat + lng,
			// Toast.LENGTH_LONG).show();

			System.out.println(head);
			details = intent.getStringExtra("details");
//			Bitmap bmp = (Bitmap) intent.getParcelableExtra("image");
			ImageView logo = (ImageView) findViewById(R.id.ivLogo);
			imageLoader.DisplayImage(thumb_uri, logo);
			// logo.setImageBitmap(bmp);
			Typeface type2 = Typeface.createFromAsset(getAssets(),
					"fonts/RobotoCondensed-Light.ttf");

			name.setText(ngo_name);

			registerForContextMenu(name);
			registerForContextMenu(address);
			registerForContextMenu(area_service);
			registerForContextMenu(date_formation);
			registerForContextMenu(head_org);
			registerForContextMenu(brief_details);
			registerForContextMenu(mail);
			registerForContextMenu(telephone);
			registerForContextMenu(web_addr);

			address.setTypeface(type2);
			address.setText(addr);
			area_service.setTypeface(type2);
			area_service.setText(aos);
			date_formation.setTypeface(type2);
			date_formation.setText(dof);
			head_org.setTypeface(type2);
			head_org.setText(head);
			brief_details.setTypeface(type2);
			brief_details.setText(details);
			mail.setTypeface(type2);
			mail.setText(email);
			telephone.setTypeface(type2);
			telephone.setText(phone);
			web_addr.setTypeface(type2);
			web_addr.setText(website);

			mp = MediaPlayer.create(this, R.raw.click);

			new EmailTask().execute("click");

			String ngo_ka_naam = dbHelper.getData(email);
			if (ngo_ka_naam != "") {

				// Disable Favorite Button
				fav.setEnabled(false);
				Drawable d = getResources().getDrawable(
						R.drawable.ic_action_star);

				d.setColorFilter(getResources().getColor(R.color.purple),
						Mode.SRC_ATOP);
				fav.setImageDrawable(d);
				// (R.drawable.ic_action_starred);

			} else {

				// Enable Favorite Button
				fav.setEnabled(true);
				fav.setImageResource(R.drawable.ic_action_star);

			}

			call_link.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						Drawable d = getResources().getDrawable(
								R.drawable.ic_action_call);

						d.setColorFilter(getResources()
								.getColor(R.color.purple), Mode.SRC_ATOP);

						call_link.setImageDrawable(d);
						mp.start();

						new EmailTask().execute("call");

						Intent callIntent = new Intent(Intent.ACTION_DIAL);
						callIntent.setData(Uri.parse("tel:" + phone));
						startActivity(callIntent);

					} catch (Exception e) {

						e.printStackTrace();

					}

				}
			});
			mail_link.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					try {
						Drawable d = getResources().getDrawable(
								R.drawable.ic_action_mail);

						d.setColorFilter(getResources()
								.getColor(R.color.purple), Mode.SRC_ATOP);

						mail_link.setImageDrawable(d);

						mp.start();

						new EmailTask().execute("email");

						Intent intent = new Intent(Intent.ACTION_SENDTO, Uri
								.fromParts("mailto", email, null));
						startActivity(Intent.createChooser(intent,
								"Choose an Email client :"));
						// Intent emailIntent = new Intent(
						// android.content.Intent.ACTION_SEND);
						// // emailIntent.setData(Uri.parse("mailto:" + email));
						// emailIntent.setType("text/plain");
						// emailIntent.putExtra(Intent.EXTRA_EMAIL,
						// new String[] { email });
						// try {
						// startActivity(Intent.createChooser(emailIntent,
						// "Send Email..."));
						// } catch (android.content.ActivityNotFoundException
						// ex) {
						// Toast.makeText(SingleMenuItemActivity.this,
						// "There are no Email clients installed.",
						// Toast.LENGTH_LONG).show();
						// }

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			});
			web_link.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						Drawable d = getResources().getDrawable(
								R.drawable.ic_action_web_site);

						d.setColorFilter(getResources()
								.getColor(R.color.purple), Mode.SRC_ATOP);

						web_link.setImageDrawable(d);

						mp.start();

						new EmailTask().execute("site");

						Uri uri = Uri.parse("http://kakshya.in/web.php?email="
								+ email);
						Intent inte = new Intent(Intent.ACTION_VIEW, uri);
						startActivity(inte);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			fav.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					try {

						mp.start();
						new EmailTask().execute("fav");

						long ids = dbHelper.insertData(id, ngo_name, aos, dof,
								head, addr, email, phone, website, thumb_uri,
								donation, lat, lng);

						if (ids < 0) {
							Toast.makeText(getApplicationContext(), "ERROR",
									Toast.LENGTH_LONG).show();
							mp.stop();
						} else {
							Toast.makeText(getApplicationContext(),
									"SAVED AS A FAVORITE!", Toast.LENGTH_LONG)
									.show();

							Intent i = new Intent(SingleMenuItemActivity.this,
									MainActivity.class);
							startActivity(i);
							finish();

						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		donate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				try {

					mp.start();
					new EmailTask().execute("donate");

					Uri uri = Uri.parse("http://kakshya.in/web.php?email="
							+ email);
					Intent intent_donate = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(intent_donate);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);

		menu.add(0, v.getId(), 0, "Copy the Details");

		// cast the received View to TextView so that you can get its text
		TextView yourTextView = (TextView) v;

		// place your TextView's text in clipboard
		ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

		ClipData clip = ClipData.newPlainText("copied_text",
				yourTextView.getText());
		clipboard.setPrimaryClip(clip);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar

		if (action_map == false) {

			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.no_action_bar, menu);

		} else {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.action_bar, menu);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		Drawable dc = getResources().getDrawable(R.drawable.ic_action_call);
		dc.setColorFilter(getResources().getColor(R.color.black), Mode.SRC_ATOP);

		Drawable dm = getResources().getDrawable(R.drawable.ic_action_mail);
		dm.setColorFilter(getResources().getColor(R.color.black), Mode.SRC_ATOP);

		Drawable dw = getResources().getDrawable(R.drawable.ic_action_web_site);
		dw.setColorFilter(getResources().getColor(R.color.black), Mode.SRC_ATOP);

		call_link.setImageDrawable(dc);
		mail_link.setImageDrawable(dm);
		web_link.setImageDrawable(dw);

		// Toast.makeText(getApplicationContext(), lat + lng, Toast.LENGTH_LONG)
		// .show();

		if (lat.contentEquals("11.111111") && lng.contentEquals("11.111111")) {
			invalidateOptionsMenu();
			action_map = false;
		}

	}

	@Override
	public void onBackPressed() {
		try {
			// TODO Auto-generated method stub
			super.onBackPressed();
			overridePendingTransition(R.anim.slide_in_y, R.anim.slide_out_y);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("finally")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		try {
			// TODO Auto-generated method stub

			switch (item.getItemId()) {
			// case android.R.id.home:
			// NavUtils.navigateUpFromSameTask(this);
			// overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
			// return true;

			// case R.id.action_contacts:
			//
			// // Creates a new Intent to insert a contact
			// Intent intent = new Intent(Intents.Insert.ACTION);
			// // Sets the MIME type to match the Contacts Provider
			// intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
			//
			// intent.putExtra(Intents.Insert.EMAIL, email)
			// .putExtra(Intents.Insert.NAME, ngo_name)
			// .putExtra(Intents.Insert.PHONE, phone)
			// .putExtra(Intents.Insert.POSTAL, addr);
			//
			// startActivity(intent);
			//
			// break;

			case R.id.action_map:

				try {

					mp.start();
					new EmailTask().execute("map");

					startMapFeature();

					return true;
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return super.onOptionsItemSelected(item);
		}
	}

	private void startMapFeature() {
		// TODO Auto-generated method stub

		Intent i = new Intent(SingleMenuItemActivity.this,
				MappingActivity.class);
		i.putExtra("latitude", latitude);
		i.putExtra("longitude", longitude);
		i.putExtra("title", ngo_name);
		i.putExtra("area", aos);
		i.putExtra("logo_url", thumb_uri);
		i.putExtra("address", addr);
		startActivity(i);

	}

	public class EmailTask extends AsyncTask<String, Void, Void> {

		protected Void doInBackground(String... event) {
			// TODO Auto-generated method stub

			SharedPreferences uniqueId = PreferenceManager
					.getDefaultSharedPreferences(SingleMenuItemActivity.this);
			String uid = uniqueId.getString("uid", "");
			try {
				String email_sent = URLEncoder.encode(email, "utf-8");

				HttpClient httpClient = new DefaultHttpClient();
				HttpContext localContext = new BasicHttpContext();
				HttpPost httpPost = new HttpPost(
						"http://kakshya.in/hit.php?event=" + event[0]
								+ "&email=" + email_sent);

				// HttpPost httpPost = new HttpPost(
				// "http://kakshya.in/hit.php?event=fav&email=h");

				// http://wadersgroup.in/kakshya/hit.php?event=call&id=6&email=rajatb9

				HttpResponse response = httpClient.execute(httpPost,
						localContext);
				System.out
						.println("Event " + event[0] + " Email " + email_sent);
				System.out.println(response);
			} catch (ClientProtoco