package in.wadersgroup.hth;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.gc.materialdesign.views.Button;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Romil
 * 
 */
public class NGOFragment extends Fragment {

	// Location mCurrentLocation;
	// LocationClient mLocationClient;
	String service_provider, localTime, latitude, longitude;
	int start = 1, end = 8;

	int end_counter = 0;

	// XML node keys
	static final String KEY_SONG = "ngo_list"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_TITLE = "ngo_name";
	static final String KEY_ARTIST = "contact_add";
	static final String KEY_DURATION = "area_service";
	static final String KEY_DOF = "dof";
	static final String KEY_HEAD = "head_org";
	static final String KEY_DETAILS = "details";
	static final String KEY_EMAIL = "email";
	static final String KEY_PHONE = "phone";
	static final String KEY_WEBSITE = "website";
	static final String KEY_THUMB_URL = "thumb_url";
	static final String KEY_LATITUDE = "latitude";
	static final String KEY_LONGITUDE = "longitude";
	static final String KEY_DONATION = "donation";
	static final String KEY_DETAILED_DOMAIN = "service_area_details";

	String dof, head, details, mail, telephone, web_addr, identity, thumb;
	boolean loader = true;
	ListView list;
	LazyAdapter adapter;
	ProgressDialog pDialog;
	View rootView;

	View footerView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// StrictMode.enableDefaults(); // STRICT MODE ENABLED
		rootView = inflater.inflate(R.layout.main, container, false);
		setHasOptionsMenu(false);
		footerView = ((LayoutInflater) getActivity().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer,
				null, false);

		// Button load = (Button) rootView.findViewById(R.id.bLoad);
		// load.setVisibility(View.GONE);

		// FAB

		// FAB Over

		ConnectionDetector cd = new ConnectionDetector(getActivity());
		// mLocationClient = new LocationClient(getActivity(), this, this);
		Boolean isInternetPresent = cd.isConnectingToInternet(); // true or
		if (isInternetPresent) { // false

			// load.setOnClickListener(new View.OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// // TODO Auto-generated method stub
			// end = end + 5;
			// new LoadListView().execute();
			//
			// }
			// });

			// Getting total Count from Server

			new CountTask().execute();

			if (loader) {

				new LoadListView().execute();
			} else {
				rootView = inflater.inflate(R.layout.main, container, false);
				new LoadListView().execute();
				adapter.notifyDataSetChanged();

			}

		} else {
			// load.setOnClickListener(new View.OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// // TODO Auto-generated method stub
			// Toast.makeText(getActivity(), "Turn On Data Traffic",
			// Toast.LENGTH_LONG).show();
			//
			// }
			// });
			try {
				// 1. Instantiate an AlertDialog.Builder with its
				// constructor

				// Dialog dialogInternet = new Dialog(
				// getActivity(),
				// "No Internet",
				// "ALAS! This Section of KAKSHYA will not without Internet. Turn On Data Traffic?");
				//
				// dialogInternet.show();
				//
				// ButtonFlat acceptButton = dialogInternet.getButtonAccept();
				// acceptButton.setBackgroundColor(getResources().getColor(
				// R.color.orange));
				// ButtonFlat cancelButton = dialogInternet.getButtonCancel();
				// cancelButton.setBackgroundColor(getResources().getColor(
				// R.color.black));

				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				// 2. Chain together various setter methods to set the
				// dialog
				// characteristics

				builder.setMessage(
						"ALAS! This Section of KAKSHYA will not without Internet. Turn On Data Traffic?")
						.setTitle("No Internet");

				// dialogInternet
				// .setOnAcceptButtonClickListener(new View.OnClickListener() {
				//
				// @Override
				// public void onClick(View v) {
				// // TODO Auto-generated method stub
				//
				// startActivityForResult(
				// new Intent(
				// android.provider.Settings.ACTION_SETTINGS),
				// 0);
				//
				// }
				// });
				// Add the buttons
				builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// // User clicked OK button
								//
								// // Intent intent = new
								// // Intent(Intent.ACTION_MAIN);
								// // intent.setClassName("com.android.phone",
								// // "com.android.phone.Settings");
								// // startActivity(intent);
								//
								startActivityForResult(
										new Intent(
												android.provider.Settings.ACTION_SETTINGS),
										0);

							}
						});

				// dialogInternet
				// .setOnCancelButtonClickListener(new View.OnClickListener() {
				//
				// @Override
				// public void onClick(View v) {
				// // TODO Auto-generated method stub
				//
				// Toast.makeText(
				// getActivity(),
				// "You can still use other features of this application.",
				// Toast.LENGTH_LONG).show();
				//
				// }
				// });

				builder.setNegativeButton("CANCEL",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// User cancelled the dialog
								// getActivity().finish();
							}
						});
				// 3. Get the AlertDialog from create()
				AlertDialog dialog = builder.create();

				dialog.show();
				// dialogInternet.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return rootView;

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		// View v = getView();
		// PopupMenu popup = new PopupMenu(getActivity(), v);
		MenuInflater inflater_menu = getActivity().getMenuInflater();
		inflater.inflate(R.menu.settings, menu);
		// popup.show();
		super.onCreateOptionsMenu(menu, inflater);

	}

	@SuppressWarnings("finally")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		try {
			// TODO Auto-generated method stub

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

			case R.id.About:

				Intent start_about = new Intent(getActivity(),
						AboutActivity.class);
				startActivity(start_about);

				break;

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
			Toast.makeText(getActivity(),
					"Please Install Google Play Store App", Toast.LENGTH_LONG)
					.show();
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		try {
			super.onResume();
			Button fab = (Button) getActivity().findViewById(R.id.buttonFloat);
			fab.setBackgroundColor(getResources().getColor(R.color.purple));
			fab.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent i = new Intent(getActivity(),
							SuggestionActivity.class);
					startActivity(i);

				}
			});
			// FloatingActionButton fabButton = new
			// FloatingActionButton.Builder(
			// getActivity())
			// .withDrawable(
			// getResources().getDrawable(R.drawable.fab_search))
			// .withButtonColor(getResources().getColor(R.color.orange))
			// .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
			// .withMargins(0, 0, 16, 16).create();
			//
			// fabButton.setOnClickListener(new View.OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// // TODO Auto-generated method stub
			//
			// DisplayMetrics dm = new DisplayMetrics();
			// getActivity().getWindowManager().getDefaultDisplay()
			// .getMetrics(dm);
			// double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
			// double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
			// double height = Math.sqrt(y);
			// double screenInches = Math.sqrt(x + y);
			//
			// Toast.makeText(
			// getActivity(),
			// "Screen Height: " + height + " Screen Size: "
			// + screenInches, Toast.LENGTH_LONG).show();
			//
			// }
			// });

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// mLocationClient.connect();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		// mLocationClient.disconnect();

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		loader = false;
		super.onPause();
	}

	public class CountTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			HttpPost httpPost = new HttpPost("http://kakshya.in/count.php");

			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();

			try {
				HttpResponse response = httpClient.execute(httpPost,
						localContext);
				String res = "";
				res = EntityUtils.toString(response.getEntity());

				end_counter = Integer.parseInt(res);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	}

	List<String> lat, lng;

	public class LoadListView extends
			AsyncTask<Void, Void, ArrayList<HashMap<String, String>>> {
		ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

		@Override
		protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
			try {
				// TODO Auto-generated method stub
				super.onPostExecute(result);

				// Toast.makeText(getActivity(), "Loader Not Working",
				// Toast.LENGTH_LONG).show();

				// closing progress dialog
				pDialog.dismiss();

				// Initializing List
				list = (ListView) getView().findViewById(R.id.list);

				// Getting adapter by passing xml data ArrayList
				// songsList = null;
				// list = null;
				if (list != null) {
					adapter = new LazyAdapter(getActivity(), songsList);

					int checker_null = list.getLastVisiblePosition()
							- list.getFirstVisiblePosition();
					int firstPosition = list.getFirstVisiblePosition();

					if (checker_null != 0) {

						list.setAdapter(adapter);
						list.setSelection(firstPosition);

						list.setDivider(null);
						list.setDividerHeight(0);

						// Click event for single list row
						list.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {

								Vibrator vibrator = (Vibrator) getActivity()
										.getSystemService(
												Context.VIBRATOR_SERVICE);

								if (vibrator.hasVibrator()) {
									vibrator.vibrate(20);
								}

								String ngo_name = ((TextView) view
										.findViewById(R.id.title)).getText()
										.toString();

								String address = songsList.get(position).get(
										KEY_ARTIST);

								String area_service = songsList.get(position)
										.get(KEY_DURATION);

								String detailed_service = songsList.get(
										position).get(KEY_DETAILED_DOMAIN);

								head = songsList.get(position).get(KEY_HEAD);
								dof = songsList.get(position).get(KEY_DOF);
								details = songsList.get(position).get(
										KEY_DETAILS);
								mail = songsList.get(position).get(KEY_EMAIL);
								telephone = songsList.get(position).get(
										KEY_PHONE);
								web_addr = songsList.get(position).get(
										KEY_WEBSITE);
								identity = songsList.get(position).get(KEY_ID);
								thumb = songsList.get(position).get(
										KEY_THUMB_URL);
								String lat = songsList.get(position).get(
										KEY_LATITUDE);
								String lng = songsList.get(position).get(
										KEY_LONGITUDE);
								String donation_url = songsList.get(position)
										.get(KEY_DONATION);
								Double lati;
								Double longi;
								if (lat != "") {
									lati = Double.parseDouble(lat);
								} else {
									// Toast.makeText(getActivity(),
									// "Location Not Available for this NGO",
									// Toast.LENGTH_LONG).show();
									lati = 11.111111;
								}
								if (lng != "") {
									longi = Double.parseDouble(lng);
								} else {
									// Toast.makeText(getActivity(),
									// "Location Not Available for this NGO",
									// Toast.LENGTH_LONG).show();
									longi = 11.111111;
								}

								View v = getView()
										.findViewById(R.id.list_image);
								v.setDrawingCacheEnabled(true);
								v.buildDrawingCache();
								Bitmap bmp = v.getDrawingCache();

								// Starting new intent
								Intent in = new Intent(getActivity(),
										SingleMenuItemActivity.class);
								in.putExtra("title", ngo_name);
								in.putExtra("artist", address);
								in.putExtra("duration", area_service);
								in.putExtra("detailed_service",
										detailed_service);
								in.putExtra("dof", dof);
								in.putExtra("head", head);
								in.putExtra("details", details);
								in.putExtra("email", mail);
								in.putExtra("phone", telephone);
								in.putExtra("website", web_addr);
								in.putExtra("identity", identity);
								in.putExtra("image", bmp);
								in.putExtra("thumb", thumb);
								in.putExtra("latitude", lati);
								in.putExtra("longitude", longi);
								in.putExtra("donation", donation_url);

								startActivity(in);
								getActivity().overridePendingTransition(
										R.anim.slide_in, R.anim.slide_out);

							}
						});
					} else {

						TextView oops = (TextView) rootView
								.findViewById(R.id.tvOops);
						Typeface type2 = Typeface.createFromAsset(getActivity()
								.getAssets(), "fonts/Roboto-Thin.ttf");
						oops.setTypeface(type2);
						oops.setText("Oops! Something went wrong.  PLEASE RELOAD!");

					}
					// code to set adapter to populate list

					// list.addFooterView(footerView);

					list.setOnScrollListener(new OnScrollListener() {

						@Override
						public void onScrollStateChanged(AbsListView view,
								int scrollState) {
							// TODO Auto-generated method stub

							int threshold = 1;
							int count = list.getCount();

							if (scrollState == SCROLL_STATE_IDLE) {
								if (list.getLastVisiblePosition() >= count
										- threshold) {
									// Execute LoadMoreDataTask AsyncTask
									end = end + 3;

									if (end_counter >= end) {

										new LoadMoreListView().execute();
										adapter.notifyDataSetChanged();
									} else {
										Toast.makeText(getActivity(),
												"That's all for Now!",
												Toast.LENGTH_SHORT).show();
									}

								}
							}

						}

						@Override
						public void onScroll(AbsListView view,
								int firstVisibleItem, int visibleItemCount,
								int totalItemCount) {
							// TODO Auto-generated method stub

						}
					});
				} else {

					TextView oops = (TextView) rootView
							.findViewById(R.id.tvOops);
					Typeface type2 = Typeface.createFromAsset(getActivity()
							.getAssets(), "fonts/Roboto-Thin.ttf");
					oops.setTypeface(type2);
					oops.setText("Oops! Something went wrong.  PLEASE RELOAD!");

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected void onPreExecute() {
			try {
				// TODO Auto-generated method stub
				super.onPreExecute();

				// Showing progress dialog before sending http request
				pDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
				// pDialog.setMessage("Please wait..");
				pDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
				pDialog.setIndeterminate(true);
				pDialog.setCancelable(false);
				pDialog.show();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@SuppressWarnings("finally")
		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(
				Void... params) {
			try {
				// TODO Auto-generated method stub

				// All static variables
				final String URL = "http://kakshya.in/ngo_list.php?start="
						+ start + "&end=" + end;
				// http://wadersgroup.in/kakshya/ngo_list.php?start=1&end=2

				XMLParser parser = new XMLParser(getActivity());
				String xml = parser.getXmlFromUrl(URL); // getting XML from URL
				if (xml.contentEquals("error")) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {

							Toast t = Toast
									.makeText(
											getActivity(),
											"OOPS! Something went wrong! Please Retry.",
											Toast.LENGTH_SHORT);

							t.show();

						}
					});
				}
				Document doc = parser.getDomElement(xml); // getting DOM element

				NodeList nl = doc.getElementsByTagName(KEY_SONG);
				// looping through all song nodes <song>
				songsList = new ArrayList<HashMap<String, String>>();

				lat = new ArrayList<String>();
				lng = new ArrayList<String>();

				for (int i = 0; i < nl.getLength(); i++) {
					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();
					Element e = (Element) nl.item(i);
					// adding each child node to HashMap key => value
					// map.put(KEY_ID, parser.getValue(e, KEY_ID));
					map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
					map.put(KEY_ARTIST, parser.getValue(e, KEY_ARTIST));
					map.put(KEY_DETAILED_DOMAIN,
							parser.getValue(e, KEY_DETAILED_DOMAIN));
					map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
					map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));
					map.put(KEY_DOF, parser.getValue(e, KEY_DOF));
					map.put(KEY_HEAD, parser.getValue(e, KEY_HEAD));
					map.put(KEY_DETAILS, parser.getValue(e, KEY_DETAILS));
					map.put(KEY_EMAIL, parser.getValue(e, KEY_EMAIL));
					map.put(KEY_PHONE, parser.getValue(e, KEY_PHONE));
					map.put(KEY_WEBSITE, parser.getValue(e, KEY_WEBSITE));
					map.put(KEY_LATITUDE, parser.getValue(e, KEY_LATITUDE));

					if (parser.getValue(e, KEY_LATITUDE) != "") {

						lat.add(parser.getValue(e, KEY_LATITUDE));
					}

					map.put(KEY_LONGITUDE, parser.getValue(e, KEY_LONGITUDE));

					if (parser.getValue(e, KEY_LATITUDE) != "") {

						lng.add(parser.getValue(e, KEY_LONGITUDE));

					}

					map.put(KEY_DONATION, parser.getValue(e, KEY_DONATION));
					// adding HashList to ArrayList

					songsList.add(map);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block

				Toast.makeText(getActivity(),
						"OOPS! Something went wrong! Please Retry.",
						Toast.LENGTH_LONG).show();

				e.printStackTrace();
			} finally {
				return songsList;
			}
		}

	}

	public class LoadMoreListView extends
			AsyncTask<Void, Void, ArrayList<HashMap<String, String>>> {
		ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

		@Override
		protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
			try {
				// TODO Auto-generated method stub
				super.onPostExecute(result);

				// Toast.makeText(getActivity(), "Loader Not Working",
				// Toast.LENGTH_LONG).show();

				// closing progress dialog
				// pDialog.dismiss();

				// Initializing List
				list = (ListView) getView().findViewById(R.id.list);

				// Getting adapter by passing xml data ArrayList
				// songsList = null;
				// list = null;
				if (list != null) {
					adapter = new LazyAdapter(getActivity(), songsList);

					int checker_null = list.getLastVisiblePosition()
							- list.getFirstVisiblePosition();
					int firstPosition = list.getFirstVisiblePosition();

					if (checker_null != 0) {
						list.setAdapter(adapter);
						list.setSelection(firstPosition);

						list.removeFooterView(footerView);

						// Click event for single list row
						list.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {

								Vibrator vibrator = (Vibrator) getActivity()
										.getSystemService(
												Context.VIBRATOR_SERVICE);
								if (vibrator.hasVibrator()) {

									vibrator.vibrate(20);
								}

								String ngo_name = ((TextView) view
										.findViewById(R.id.title)).getText()
										.toString();

								String address = songsList.get(position).get(
										KEY_ARTIST);

								String area_service = songsList.get(position)
										.get(KEY_DURATION);

								String detailed_service = songsList.get(
										position).get(KEY_DETAILED_DOMAIN);

								head = songsList.get(position).get(KEY_HEAD);
								dof = songsList.get(position).get(KEY_DOF);
								details = songsList.get(position).get(
										KEY_DETAILS);
								mail = songsList.get(position).get(KEY_EMAIL);
								telephone = songsList.get(position).get(
										KEY_PHONE);
								web_addr = songsList.get(position).get(
										KEY_WEBSITE);
								identity = songsList.get(position).get(KEY_ID);
								thumb = songsList.get(position).get(
										KEY_THUMB_URL);
								String lat = songsList.get(position).get(
										KEY_LATITUDE);
								String lng = songsList.get(position).get(
										KEY_LONGITUDE);
								String donation_url = songsList.get(position)
										.get(KEY_DONATION);
								Double lati;
								Double longi;
								if (lat != "") {
									lati = Double.parseDouble(lat);
								} else {
									// Toast.makeText(getActivity(),
									// "Location Not Available for this NGO",
									// Toast.LENGTH_LONG).show();
									lati = 11.111111;
								}
								if (lng != "") {
									longi = Double.parseDouble(lng);
								} else {
									// Toast.makeText(getActivity(),
									// "Location Not Available for this NGO",
									// Toast.LENGTH_LONG).show();
									longi = 11.111111;
								}

								View v = getView()
										.findViewById(R.id.list_image);
								v.setDrawingCacheEnabled(true);
								v.buildDrawingCache();
								Bitmap bmp = v.getDrawingCache();

								// Starting new intent
								Intent in = new Intent(getActivity(),
										SingleMenuItemActivity.class);
								in.putExtra("title", ngo_name);
								in.putExtra("artist", address);
								in.putExtra("duration", area_service);
								in.putExtra("detailed_service",
										detailed_service);
								in.putExtra("dof", dof);
								in.putExtra("head", head);
								in.putExtra("details", details);
								in.putExtra("email", mail);
								in.putExtra("phone", telephone);
								in.putExtra("website", web_addr);
								in.putExtra("identity", identity);
								in.putExtra("image", bmp);
								in.putExtra("thumb", thumb);
								in.putExtra("latitude", lati);
								in.putExtra("longitude", longi);
								in.putExtra("donation", donation_url);

								startActivity(in);
								getActivity().overridePendingTransition(
										R.anim.slide_in, R.anim.slide_out);

							}
						});
					} else {

						TextView oops = (TextView) rootView
								.findViewById(R.id.tvOops);
						Typeface type2 = Typeface.createFromAsset(getActivity()
								.getAssets(), "fonts/Roboto-Thin.ttf");
						oops.setTypeface(type2);
						oops.setText("Oops! Something went wrong.  PLEASE RELOAD!");

					}
					// code to set adapter to populate list

					list.setOnScrollListener(new OnScrollListener() {

						@Override
						public void onScrollStateChanged(AbsListView view,
								int scrollState) {
							// TODO Auto-generated method stub

							int threshold = 1;
							int count = list.getCount();

							if (scrollState == SCROLL_STATE_IDLE) {
								if (list.getLastVisiblePosition() >= count
										- threshold) {
									// Execute LoadMoreDataTask AsyncTask
									end = end + 3;

									if (end_counter >= end) {

										new LoadMoreListView().execute();
										adapter.notifyDataSetChanged();
									} else {
										Toast.makeText(getActivity(),
												"That's all for Now!",
												Toast.LENGTH_SHORT).show();
									}

								}
							}

						}

						@Override
						public void onScroll(AbsListView view,
								int firstVisibleItem, int visibleItemCount,
								int totalItemCount) {
							// TODO Auto-generated method stub

						}
					});
				} else {

					TextView oops = (TextView) rootView
							.findViewById(R.id.tvOops);
					Typeface type2 = Typeface.createFromAsset(getActivity()
							.getAssets(), "fonts/Roboto-Thin.ttf");
					oops.setTypeface(type2);
					oops.setText("Oops! Something went wrong.  PLEASE RELOAD!");

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected void onPreExecute() {
			try {
				// TODO Auto-generated method stub
				super.onPreExecute();

				// Showing progress dialog before sending http request
				// pDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
				// // pDialog.setMessage("Please wait..");
				// pDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
				// pDialog.setIndeterminate(true);
				// pDialog.setCancelable(true);
				// pDialog.show();
				int footerCount = list.getFooterViewsCount();

				if (footerCount < 1) {
					list.addFooterView(footerView);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@SuppressWarnings("finally")
		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(
				Void... params) {
			try {
				// TODO Auto-generated method stub

				// All static variables
				final String URL = "http://kakshya.in/ngo_list.php?start="
						+ start + "&end=" + end;
				// http://wadersgroup.in/kakshya/ngo_list.php?start=1&end=2

				XMLParser parser = new XMLParser(getActivity());
				String xml = parser.getXmlFromUrl(URL); // getting XML from URL
				if (xml.contentEquals("error")) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {

							Toast t = Toast
									.makeText(
											getActivity(),
											"OOPS! Something went wrong! Please Retry.",
											Toast.LENGTH_SHORT);

							t.show();

						}
					});
				}
				Document doc = parser.getDomElement(xml); // getting DOM element

				NodeList nl = doc.getElementsByTagName(KEY_SONG);
				// looping through all song nodes <song>
				songsList = new ArrayList<HashMap<String, String>>();

				lat = new ArrayList<String>();
				lng = new ArrayList<String>();

				for (int i = 0; i < nl.getLength(); i++) {
					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();
					Element e = (Element) nl.item(i);
					// adding each child node to HashMap key => value
					// map.put(KEY_ID, parser.getValue(e, KEY_ID));
					map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
					map.put(KEY_ARTIST, parser.getValue(e, KEY_ARTIST));
					map.put(KEY_DETAILED_DOMAIN,
							parser.getValue(e, KEY_DETAILED_DOMAIN));
					map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
					map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));
					map.put(KEY_DOF, parser.getValue(e, KEY_DOF));
					map.put(KEY_HEAD, parser.getValue(e, KEY_HEAD));
					map.put(KEY_DETAILS, parser.getValue(e, KEY_DETAILS));
					map.put(KEY_EMAIL, parser.getValue(e, KEY_EMAIL));
					map.put(KEY_PHONE, parser.getValue(e, KEY_PHONE));
					map.put(KEY_WEBSITE, parser.getValue(e, KEY_WEBSITE));
					map.put(KEY_LATITUDE, parser.getValue(e, KEY_LATITUDE));

					if (parser.getValue(e, KEY_LATITUDE) != "") {

						lat.add(parser.getValue(e, KEY_LATITUDE));
					}

					map.put(KEY_LONGITUDE, parser.getValue(e, KEY_LONGITUDE));

					if (parser.getValue(e, KEY_LATITUDE) != "") {

						lng.add(parser.getValue(e, KEY_LONGITUDE));

					}

					map.put(KEY_DONATION, parser.getValue(e, KEY_DONATION));
					// adding HashList to ArrayList

					songsList.add(map);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block

				Toast.makeText(getActivity(),
						"OOPS! Something went wrong! Please Retry.",
						Toast.LENGTH_LONG).show();

				e.printStackTrace();
			} finally {
				return songsList;
			}
		}

	}

	public class EmailTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {
				String sProvider = URLEncoder.encode(service_provider, "utf-8");

				String tZone = URLEncoder.encode(localTime, "utf-8");

				String lat = URLEncoder.encode(latitude, "utf-8");

				String lng = URLEncoder.encode(longitude, "utf-8");

				SharedPreferences uniqueId = PreferenceManager
						.getDefaultSharedPreferences(getActivity());
				String uid = uniqueId.getString("uid", "");

				HttpClient httpClient = new DefaultHttpClient();
				HttpContext localContext = new BasicHttpContext();
				System.out.println("Email Task Executing");
				HttpPost httpPost = new HttpPost(
						"http://kakshya.in/environment.php?serviceprovider="
								+ sProvider + "&timezone=" + tZone
								+ "&latitude=" + lat + "&longitude=" + lng
								+ "&id=" + uid);
				System.out.println("Email Task Executing 2");

				HttpResponse response = httpClient.execute(httpPost,
						localContext);
				System.out.println(response);
				System.out.println("Service " + sProvider + " TimeZone "
						+ tZone + " Latitude " + lat + " Longitude " + lng
						+ " UID " + uid);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
	}

	// @Override
	// public void onConnectionFailed(ConnectionResult result) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onConnected(Bundle connectionHint) {
	// // TODO Auto-generated method stub
	//
	// // SERVICE PROVIDER
	// TelephonyManager telephonyManager = (TelephonyManager) getActivity()
	// .getSystemService(Context.TELEPHONY_SERVICE);
	// service_provider = telephonyManager.getSimOperatorName();
	// // SERVICE PROVIDER OVER
	//
	// // TIMEZONE
	// Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"),
	// Locale.getDefault());
	// Date currentLocalTime = calendar.getTime();
	// SimpleDateFormat date = new SimpleDateFormat("Z");
	// localTime = date.format(currentLocalTime);
	// // TIMEZONE OVER
	//
	// // CURRENT LOCATION
	// mCurrentLocation = mLocationClient.getLastLocation();
	// Double lat = mCurrentLocation.getLatitude();
	// Double longi = mCurrentLocation.getLongitude();
	// latitude = lat.toString();
	// longitude = longi.toString();
	//
	// String location = "Latitude: " + latitude + " Longitude: " + longitude;
	// // CURRENT LOCATION OVER
	//
	// System.out.println("Start EmailTask Execution");
	// // new EmailTask().execute();
	// System.out.println("Email Task Executed");
	//
	// String device_details = "Service: " + service_provider + " TimeZone: "
	// + localTime + " Location: " + location;
	// System.out.println(device_details);
	//
	// }
	//
	// @Override
	// public void onDisconnected() {
	// // TODO Auto-generated method stub
	//
	// }

}
