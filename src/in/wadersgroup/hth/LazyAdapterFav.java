package in.wadersgroup.hth;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.google.android.gms.internal.ov;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.method.ScrollingMovementMethod;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Romil
 * 
 */
public class LazyAdapterFav extends BaseAdapter {

	private Activity activity;
	MediaPlayer mp;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	private static LayoutInflater inflate_dialog = null;
	public ImageLoader imageLoader;

	// DatabaseHelperAdapter helper;

	public LazyAdapterFav(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate_dialog = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());

		notifyDataSetChanged();

	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.list_row_fav, null);

		TextView title = (TextView) vi.findViewById(R.id.title); // title
		// TextView artist = (TextView) vi.findViewById(R.id.artist); // artist
		// name

		ImageButton thumb_image = (ImageButton) vi
				.findViewById(R.id.list_image);

		HashMap<String, String> song = new HashMap<String, String>();
		song = data.get(position);
		final String url = song.get("website");
		final String phone = song.get("phone");
		final String emailer = song.get("email");
		final String donate_url = song.get("donate_url");
		final String thumb_url = song.get("thumb");
		final String address = song.get("address");
		final String lat = song.get("lat");
		final String lng = song.get("lng");
		final String ngo_name = song.get("name");
		final String area_service = song.get("area");

		final ImageButton overflow = (ImageButton) vi
				.findViewById(R.id.ibListFavOverflow);

		overflow.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(final View v) {
				// TODO Auto-generated method stub

				final Item[] itemsIcon = {
						new Item("Donate", R.drawable.ic_rupee_black),
						new Item("Visit on a Map", R.drawable.ic_action_map),
						new Item("Delete", R.drawable.ic_clear),
						new Item("Call", R.drawable.ic_action_call),
						new Item("Send an Email", R.drawable.ic_action_mail),
						new Item("Visit Website", R.drawable.ic_action_web_site) };

				ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(v
						.getContext(), android.R.layout.select_dialog_item,
						android.R.id.text1, itemsIcon) {
					public View getView(int position, View convertView,
							ViewGroup parent) {
						// User super class to create the View
						View v = super.getView(position, convertView, parent);
						TextView tv = (TextView) v
								.findViewById(android.R.id.text1);
						tv.setTextSize(15);
						Typeface type1 = Typeface.createFromAsset(v
								.getContext().getAssets(),
								"fonts/Roboto-Bold.ttf");
						tv.setTypeface(type1);

						Drawable d = v.getContext().getResources()
								.getDrawable(itemsIcon[position].icon);
						d.setBounds(0, 0, 48, 48);
						d.setColorFilter(v.getContext().getResources()
								.getColor(R.color.orange), Mode.SRC_ATOP);

						// Put the image on the TextView
						tv.setCompoundDrawables(d, null, null, null);

						// Add margin between image and text (support various
						// screen densities)
						int dp5 = (int) (30 * v.getContext().getResources()
								.getDisplayMetrics().density + 0.5f);
						tv.setCompoundDrawablePadding(dp5);

						return v;
					}
				};

				final CharSequence[] items = { "Donate",
						"View Location on a Map", "Delete", "Call",
						"Send an Email", "Visit Website" };

				AlertDialog.Builder builder = new AlertDialog.Builder(v
						.getContext());

				builder.setAdapter(adapter,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {

								if (item == 0) {
									try {
										Context c = v.getContext();
										mp = MediaPlayer.create(c, R.raw.click);
										mp.start();
										SharedPreferences uniqueId = PreferenceManager
												.getDefaultSharedPreferences(c);
										String uid = uniqueId.getString("uid",
												"");

										new EmailTask().execute("donate",
												emailer);

										Uri uri = Uri
												.parse("http://wadersgroup.in/kakshya/web.php?email="
														+ emailer);

										Intent intent = new Intent(
												Intent.ACTION_VIEW, uri)
												.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										c.startActivity(intent);
									} catch (Exception e) {
										e.printStackTrace();
									}
								} else if (item == 1) {
									try {

										Context c = v.getContext();
										mp = MediaPlayer.create(c, R.raw.click);
										mp.start();
										Double latitude = Double
												.parseDouble(lat);
										Double longitude = Double
												.parseDouble(lng);

										Intent intent = new Intent(c,
												MappingActivity.class);
										intent.putExtra("logo_url", thumb_url);
										intent.putExtra("address", address);
										intent.putExtra("latitude", latitude);
										intent.putExtra("longitude", longitude);
										intent.putExtra("title", ngo_name);
										intent.putExtra("area", area_service);
										SharedPreferences uniqueId = PreferenceManager
												.getDefaultSharedPreferences(c);
										String uid = uniqueId.getString("uid",
												"");

										new EmailTask().execute("map", emailer);

										c.startActivity(intent);
									} catch (Exception e) {
										e.printStackTrace();
									}
								} else if (item == 2) {
									try {
										Context c = v.getContext();
										mp = MediaPlayer.create(c, R.raw.click);
										mp.start();
										DatabaseHelperAdapter helper;
										helper = new DatabaseHelperAdapter(c);
										helper.deleteRow(emailer);

										data.remove(position);

										notifyDataSetChanged();

										// ((Activity) c).finish();
										//
										// Intent i = new Intent(c,
										// MainActivity.class);
										//
										// c.startActivity(i);
									} catch (Exception e) {
										e.printStackTrace();
									}
								} else if (item == 3) {
									try {
										Context c = v.getContext();
										mp = MediaPlayer.create(c, R.raw.click);
										mp.start();
										SharedPreferences uniqueId = PreferenceManager
												.getDefaultSharedPreferences(c);
										String uid = uniqueId.getString("uid",
												"");
										new EmailTask()
												.execute("call", emailer);

										Intent callIntent = new Intent(
												Intent.ACTION_DIAL);
										callIntent.setData(Uri.parse("tel:"
												+ phone));
										c.startActivity(callIntent);
									} catch (Exception e) {
										e.printStackTrace();
									}
								} else if (item == 4) {
									try {
										Context c = v.getContext();
										mp = MediaPlayer.create(c, R.raw.click);
										mp.start();
										SharedPreferences uniqueId = PreferenceManager
												.getDefaultSharedPreferences(c);
										String uid = uniqueId.getString("uid",
												"");
										new EmailTask().execute("email",
												emailer);
										Intent intent = new Intent(
												Intent.ACTION_SENDTO, Uri
														.fromParts("mailto",
																emailer, null));
										c.startActivity(Intent.createChooser(
												intent,
												"Choose an Email client :"));
										// // Intent emailIntent = new Intent(
										// //
										// android.content.Intent.ACTION_SEND);
										// // //
										// emailIntent.setData(Uri.parse("mailto:"
										// + email));
										// // emailIntent.setType("text/plain");
										// //
										// emailIntent.putExtra(Intent.EXTRA_EMAIL,
										// // new String[] { emailer });
										// // try {
										// //
										// c.startActivity(Intent.createChooser(emailIntent,
										// // "Send Email..."));
										// // } catch
										// (android.content.ActivityNotFoundException
										// ex) {
										// // Toast.makeText(c,
										// //
										// "There are no Email clients installed.",
										// // Toast.LENGTH_LONG).show();
										// // }
									} catch (Exception e) {
										e.printStackTrace();
									}
								} else if (item == 5) {
									try {
										Context c = v.getContext();
										mp = MediaPlayer.create(c, R.raw.click);
										mp.start();
										SharedPreferences uniqueId = PreferenceManager
												.getDefaultSharedPreferences(c);
										String uid = uniqueId.getString("uid",
												"");
										new EmailTask()
												.execute("site", emailer);

										Uri uri = Uri
												.parse("http://wadersgroup.in/kakshya/web.php?email="
														+ emailer);
										Intent intent = new Intent(
												Intent.ACTION_VIEW, uri)
												.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										c.startActivity(intent);
									} catch (Exception e) {
										e.printStackTrace();
									}
									;
								}

							}
						});
				AlertDialog alert = builder.create();
				alert.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
				alert.show();

			}
		});

		// Button web = (Button) vi.findViewById(R.id.bWeblink);
		// Button call = (Button) vi.findViewById(R.id.bCall);
		// Button email = (Button) vi.findViewById(R.id.bEmail);
		// Button donate = (Button) vi.findViewById(R.id.bDonation);
		// Button map = (Button) vi.findViewById(R.id.bMapping);

		// thumb_image.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// Context c = v.getContext();
		// AlertDialog.Builder dialog_item_detail = new AlertDialog.Builder(
		// c);
		//
		// View vi = inflate_dialog.inflate(R.layout.dialog_fav, null);
		// ImageView iv = (ImageView) vi.findViewById(R.id.ivDialogFav);
		// TextView tv = (TextView) vi.findViewById(R.id.tvDetailsFav);
		// ImageLoader imageLoader = new ImageLoader(c);
		// imageLoader.DisplayImage(thumb_url, iv);
		// tv.setText(SingleMenuItemActivity.details);
		// dialog_item_detail.setView(vi);
		// dialog_item_detail.create();
		// dialog_item_detail.setTitle("WadersGroup");
		// dialog_item_detail.show();
		//
		// }
		// });

		// web.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// try {
		// Context c = v.getContext();
		// mp = MediaPlayer.create(c, R.raw.click);
		// mp.start();
		// SharedPreferences uniqueId = PreferenceManager
		// .getDefaultSharedPreferences(c);
		// String uid = uniqueId.getString("uid", "");
		// new EmailTask().execute("site", emailer);
		//
		// Uri uri = Uri
		// .parse("http://wadersgroup.in/kakshya/web.php?email="
		// + emailer);
		// Intent intent = new Intent(Intent.ACTION_VIEW, uri)
		// .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// c.startActivity(intent);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// }
		// });
		//
		// call.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// try {
		// Context c = v.getContext();
		// mp = MediaPlayer.create(c, R.raw.click);
		// mp.start();
		// SharedPreferences uniqueId = PreferenceManager
		// .getDefaultSharedPreferences(c);
		// String uid = uniqueId.getString("uid", "");
		// new EmailTask().execute("call", emailer);
		//
		// Intent callIntent = new Intent(Intent.ACTION_DIAL);
		// callIntent.setData(Uri.parse("tel:" + phone));
		// c.startActivity(callIntent);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// }
		// });
		//
		// email.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// try {
		// Context c = v.getContext();
		// mp = MediaPlayer.create(c, R.raw.click);
		// mp.start();
		// SharedPreferences uniqueId = PreferenceManager
		// .getDefaultSharedPreferences(c);
		// String uid = uniqueId.getString("uid", "");
		// new EmailTask().execute("email", emailer);
		// Intent intent = new Intent(Intent.ACTION_SENDTO, Uri
		// .fromParts("mailto", emailer, null));
		// c.startActivity(Intent.createChooser(intent,
		// "Choose an Email client :"));
		// // Intent emailIntent = new Intent(
		// // android.content.Intent.ACTION_SEND);
		// // // emailIntent.setData(Uri.parse("mailto:" + email));
		// // emailIntent.setType("text/plain");
		// // emailIntent.putExtra(Intent.EXTRA_EMAIL,
		// // new String[] { emailer });
		// // try {
		// // c.startActivity(Intent.createChooser(emailIntent,
		// // "Send Email..."));
		// // } catch (android.content.ActivityNotFoundException ex) {
		// // Toast.makeText(c,
		// // "There are no Email clients installed.",
		// // Toast.LENGTH_LONG).show();
		// // }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// }
		// });
		//
		// donate.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// try {
		// Context c = v.getContext();
		// mp = MediaPlayer.create(c, R.raw.click);
		// mp.start();
		// SharedPreferences uniqueId = PreferenceManager
		// .getDefaultSharedPreferences(c);
		// String uid = uniqueId.getString("uid", "");
		//
		// new EmailTask().execute("donate", emailer);
		//
		// Uri uri = Uri
		// .parse("http://wadersgroup.in/kakshya/web.php?email="
		// + emailer);
		//
		// Intent intent = new Intent(Intent.ACTION_VIEW, uri)
		// .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// c.startActivity(intent);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// }
		// });
		//
		// details.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// try {
		// Context c = v.getContext();
		// mp = MediaPlayer.create(c, R.raw.click);
		// mp.start();
		// DatabaseHelperAdapter helper;
		// helper = new DatabaseHelperAdapter(c);
		// helper.deleteRow(emailer);
		//
		// ((Activity) c).finish();
		//
		// Intent i = new Intent(c, MainActivity.class);
		//
		// c.startActivity(i);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// }
		// });
		//
		// map.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// try {
		//
		// Context c = v.getContext();
		// mp = MediaPlayer.create(c, R.raw.click);
		// mp.start();
		// Double latitude = Double.parseDouble(lat);
		// Double longitude = Double.parseDouble(lng);
		//
		// Intent intent = new Intent(c, MappingActivity.class);
		// intent.putExtra("logo_url", thumb_url);
		// intent.putExtra("address", address);
		// intent.putExtra("latitude", latitude);
		// intent.putExtra("longitude", longitude);
		// intent.putExtra("title", ngo_name);
		// intent.putExtra("area", area_service);
		// SharedPreferences uniqueId = PreferenceManager
		// .getDefaultSharedPreferences(c);
		// String uid = uniqueId.getString("uid", "");
		//
		// new EmailTask().execute("map", emailer);
		//
		// c.startActivity(intent);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// }
		// });

		// Setting all values in listview
		title.setText(song.get("name"));

		Typeface type2 = Typeface.createFromAsset(vi.getContext().getAssets(),
				"fonts/RobotoCondensed-Light.ttf");

		// artist.setTypeface(type2);
		//
		// artist.setText(song.get("area"));
		//
		// artist.setSelected(true);

		imageLoader.DisplayImage(song.get("thumb"), thumb_image);
		return vi;
	}

	public class EmailTask extends AsyncTask<String, Void, Void> {

		protected Void doInBackground(String... event) {
			// TODO Auto-generated method stub

			try {
				String email_sent = URLEncoder.encode(event[1], "utf-8");

				HttpClient httpClient = new DefaultHttpClient();
				HttpContext localContext = new BasicHttpContext();
				HttpPost httpPost = new HttpPost(
						"http://wadersgroup.in/kakshya/hit.php?event="
								+ event[0] + "&email=" + email_sent);

				// http://wadersgroup.in/kakshya/hit.php?event=call&id=6&email=rajatb9

				HttpResponse response = httpClient.execute(httpPost,
						localContext);
				System.out.println("Event " + event[0] + email_sent);
				System.out.println(response);
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
}
