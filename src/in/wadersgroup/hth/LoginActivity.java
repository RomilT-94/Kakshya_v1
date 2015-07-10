//package in.wadersgroup.hth;
//
//import java.io.IOException;
//import java.net.URLEncoder;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.protocol.BasicHttpContext;
//import org.apache.http.protocol.HttpContext;
//import org.apache.http.util.EntityUtils;
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentSender.SendIntentException;
//import android.content.SharedPreferences;
//import android.net.wifi.WifiInfo;
//import android.net.wifi.WifiManager;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Handler;
//import android.preference.PreferenceManager;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.Toast;
//import com.facebook.Request;
//import com.facebook.Response;
//import com.facebook.Session;
//import com.facebook.SessionState;
//import com.facebook.UiLifecycleHelper;
//import com.facebook.model.GraphUser;
//import com.facebook.widget.LoginButton;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
//import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.plus.PlusClient;
//import com.google.android.gms.plus.model.people.Person;
//import com.google.android.gms.plus.model.people.Person.Organizations;
//import com.google.android.gms.plus.model.people.Person.PlacesLived;
//
///**
// * @author Romil
// *
// */
//public class LoginActivity extends Activity implements View.OnClickListener,
//		ConnectionCallbacks, OnConnectionFailedListener {
//
//	public int currentimageindex = 0;
//	Timer timer;
//	TimerTask task;
//	ImageView slidingimage;
//
//	private int[] IMAGE_IDS = { R.drawable.arrow, R.drawable.arrow,
//			R.drawable.arrow, R.drawable.arrow };
//
//	String device_data;
//	String first_name, middle_name, last_name, username, email, profile_url,
//			birthday, sex, location, display_picture_url, about, lang,
//			organization, place, relation, logFlag, fbgFlag, macAddress,
//			device_name;
//
//	private static final String TAG = "MainActivity";
//
//	private Session.StatusCallback callback = new Session.StatusCallback() {
//
//		@Override
//		public void call(Session session, SessionState state,
//				Exception exception) {
//			onSessionStateChange(session, state, exception);
//		}
//	};
//
//	private UiLifecycleHelper uiHelper;
//
//	private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
//
//	private ProgressDialog mConnectionProgressDialog;
//
//	private PlusClient mPlusClient;
//
//	private ConnectionResult mConnectionResult;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//
//		super.onCreate(savedInstanceState);
//
//		mPlusClient = new PlusClient.Builder(this, this, this).setActions(
//				"http://schemas.google.com/AddActivity",
//				"http://schemas.google.com/BuyActivity").build();
//
//		setContentView(R.layout.activity_login);
//
//		// Image Slider Automatic
//
//		final Handler mHandler = new Handler();
//
//		final Runnable mUpdateResults = new Runnable() {
//			public void run() {
//
//				AnimateandSlideShow();
//
//			}
//		};
//
//		int delay = 1000;
//
//		int period = 1500;
//
//		Timer timer = new Timer();
//
//		timer.scheduleAtFixedRate(new TimerTask() {
//
//			public void run() {
//
//				mHandler.post(mUpdateResults);
//
//			}
//
//		}, delay, period);
//
//		uiHelper = new UiLifecycleHelper(LoginActivity.this, callback);
//
//		uiHelper.onCreate(savedInstanceState);
//
//		// Progress bar to be displayed if the connection failure is not
//		// resolved.
//
//		mConnectionProgressDialog = new ProgressDialog(this);
//		mConnectionProgressDialog.setMessage("Signing in...");
//
//		findViewById(R.id.sign_in_button).setOnClickListener(this); // G+
//																	// Button
//
//		LoginButton authButton = (LoginButton) findViewById(R.id.authButton);// FB
//		// Button
//		authButton.setReadPermissions(Arrays.asList("user_birthday", "email", // FB
//																				// Login
//																				// Permissions
//				"user_location", "public_profile"));
//
//	}
//
//	/**
//	 * Helper method to start the animation on the splash screen
//	 */
//	private void AnimateandSlideShow() {
//
//		slidingimage = (ImageView) findViewById(R.id.imageView1);
//		slidingimage.setImageResource(IMAGE_IDS[currentimageindex
//				% IMAGE_IDS.length]);
//
//		currentimageindex++;
//
//		// Animation rotateimage = AnimationUtils.loadAnimation(this,
//		// R.anim.slide_in);
//		//
//		// slidingimage.startAnimation(rotateimage);
//
//	}
//
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//
//		Session session = Session.getActiveSession();
//		if (session != null && (session.isOpened() || session.isClosed())) {
//			onSessionStateChange(session, session.getState(), null);
//		}
//		uiHelper.onResume();
//	}
//
//	@Override
//	protected void onStart() {
//		super.onStart();
//
//		mPlusClient.connect(); // G+ Client Connect
//
//	}
//
//	@Override
//	protected void onStop() {
//		super.onStop();
//
//		mPlusClient.disconnect(); // G+ Client Disconnect
//
//		finish();
//
//	}
//
//	@Override
//	public void onConnectionFailed(ConnectionResult result) {
//		if (mConnectionProgressDialog.isShowing()) {
//			// The user clicked the sign-in button already. Start to resolve
//			// connection errors. Wait until onConnected() to dismiss the
//			// connection dialog.
//			if (result.hasResolution()) {
//				try {
//					result.startResolutionForResult(this,
//							REQUEST_CODE_RESOLVE_ERR);
//				} catch (SendIntentException e) {
//					mPlusClient.connect();
//				}
//			}
//		}
//		// Save the result and resolve the connection failure upon a user
//		// click.
//		mConnectionResult = result;
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int responseCode,
//			Intent intent) {
//		if (requestCode == REQUEST_CODE_RESOLVE_ERR
//				&& responseCode == RESULT_OK) {
//			mConnectionResult = null;
//			mPlusClient.connect();
//		}
//
//		uiHelper.onActivityResult(requestCode, responseCode, intent);
//	}
//
//	@Override
//	public void onConnected(Bundle connectionHint) {
//
//		mConnectionProgressDialog.dismiss();
//
//		// TODO Auto-generated method stub
//		if (mPlusClient.isConnected()) {
//
//			Person currentPerson = mPlusClient.getCurrentPerson();
//
//			// User Details
//
//			// Name
//			first_name = currentPerson.getName().getGivenName();
//			middle_name = currentPerson.getName().getMiddleName();
//			last_name = currentPerson.getName().getFamilyName();
//
//			// User Name
//			username = "";
//
//			// Email
//			email = mPlusClient.getAccountName();
//
//			// Profile URL
//			profile_url = currentPerson.getUrl();
//
//			// Birthday
//			birthday = currentPerson.getBirthday();
//
//			// Sex
//			int g = currentPerson.getGender();
//
//			if (g == 0) {
//				sex = "Male";
//			} else if (g == 1) {
//				sex = "Female";
//			} else {
//				sex = "Other";
//			}
//
//			// Location
//			location = currentPerson.getCurrentLocation();
//
//			// Profile Picture URL
//			display_picture_url = currentPerson.getImage().getUrl();
//
//			// About Me of USER
//			about = currentPerson.getAboutMe();
//
//			// Language
//			lang = currentPerson.getLanguage();
//
//			// Organizations Worked In
//			List<Organizations> org = currentPerson.getOrganizations();
//
//			if (org != null) {
//				organization = org.toString();
//			} else {
//				organization = "";
//			}
//
//			// Places User Lived In
//			List<PlacesLived> plc = currentPerson.getPlacesLived();
//
//			if (plc != null) {
//				place = plc.toString();
//			} else {
//				place = "";
//			}
//
//			// Relationship Status
//			int rel = currentPerson.getRelationshipStatus();
//			if (rel == 0) {
//				relation = "Single";
//			} else if (rel == 1) {
//				relation = "In a Relationship";
//			} else if (rel == 2) {
//				relation = "Engaged";
//			} else if (rel == 3) {
//				relation = "Married";
//			} else if (rel == 4) {
//				relation = "Its Complicated";
//			} else if (rel == 5) {
//				relation = "Open Relationship";
//			} else if (rel == 6) {
//				relation = "Widowed";
//			} else if (rel == 7) {
//				relation = "In a Domestic Partnership";
//			} else if (rel == 8) {
//				relation = "In a Civil Union";
//			} else {
//				relation = "Unknown";
//			}
//
//			// Login/Logout Flag
//
//			logFlag = "1";
//
//			// FB/G+ Flag
//
//			fbgFlag = "G+";
//
//			// User Details
//
//			SharedPreferences settings = PreferenceManager
//					.getDefaultSharedPreferences(LoginActivity.this);
//			SharedPreferences.Editor editor = settings.edit();
//			editor.putString("email", email);
//			editor.commit();
//
//			// UNIVERSAL PROFILE URL
//
//			SharedPreferences user_profile = PreferenceManager
//					.getDefaultSharedPreferences(LoginActivity.this);
//			SharedPreferences.Editor editors = user_profile.edit();
//			editors.putString("user_profile", profile_url);
//			editors.commit();
//
//			// UNIVERSAL PROFILE URL SAVED
//
//			String user_data = "First Name: " + first_name + " Middle Name: "
//					+ middle_name + " Last Name: " + last_name + " Username: "
//					+ username + " Email: " + email + " Profile URL: "
//					+ profile_url + " Birthday: " + birthday + " Sex: " + sex
//					+ " Location: " + location + " Display Picture URL: "
//					+ display_picture_url + " About Me: " + about
//					+ " Language: " + lang + " Organizations: " + organization
//					+ " Places Lived: " + place + " Relationship Status: "
//					+ relation + " Login Flag: " + logFlag + " FB/G+ Flag: "
//					+ fbgFlag;
//			System.out.println(user_data);
//			Toast.makeText(
//					getApplicationContext(),
//					birthday + profile_url + sex + email + about
//							+ display_picture_url + lang + location + relation
//							+ place + organization, Toast.LENGTH_LONG).show();
//
//			// Device Data Fetching
//
//			// MAC
//
//			WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//			WifiInfo info = manager.getConnectionInfo();
//
//			if (info != null) {
//				macAddress = info.getMacAddress();
//			} else {
//				macAddress = "Not Found";
//			}
//
//			// MAC Over
//
//			// DEVICE NAME
//
//			String model = android.os.Build.MODEL;
//			String manufacturer = android.os.Build.MANUFACTURER;
//			// String product = android.os.Build.PRODUCT;
//
//			device_name = manufacturer + " " + model;
//
//			// DEVICE NAME Over
//
//			System.out
//					.println("Device: " + device_name + " MAC: " + macAddress);
//
//			device_data = "Device: " + device_name + " MAC: " + macAddress;
//
//			System.out.println(device_data);
//
//			// Device Data Fetching finished.
//
//			// HTTP COMMUNICATION
//
//			new EmailTask().execute();
//
//			// HTTP COMMUNICATION OVER
//
//			Intent i = new Intent(LoginActivity.this, MainActivity.class);
//			startActivity(i);
//			finish();
//		} else {
//			Toast.makeText(getApplicationContext(), "Please Sign In",
//					Toast.LENGTH_LONG).show();
//		}
//	}
//
//	@Override
//	public void onDisconnected() {
//		Toast.makeText(this, " Disconnected.", Toast.LENGTH_LONG).show();
//		Log.d(TAG, "disconnected");
//	}
//
//	@Override
//	public void onClick(View view) {
//		if (view.getId() == R.id.sign_in_button && !mPlusClient.isConnected()) {
//			if (mConnectionResult == null) {
//				mConnectionProgressDialog.show();
//
//			} else {
//				try {
//					mConnectionResult.startResolutionForResult(this,
//							REQUEST_CODE_RESOLVE_ERR);
//				} catch (SendIntentException e) {
//					// Try connecting again.
//					mConnectionResult = null;
//					mPlusClient.connect();
//				}
//			}
//		}
//	}
//
//	private void onSessionStateChange(Session session, SessionState state,
//			Exception exception) {
//		if (state.isOpened()) {
//			Log.i(TAG, "Logged in...");
//			// make request to the /me API
//			Request request = Request.newMeRequest(session,
//					new Request.GraphUserCallback() {
//						// callback after Graph API response with user
//						// object
//
//						@Override
//						public void onCompleted(GraphUser user,
//								Response response) {
//							// TODO Auto-generated method stub
//							if (user != null) {
//
//								// User Data Fetch
//
//								// Name
//								first_name = user.getFirstName();
//								middle_name = user.getMiddleName();
//								last_name = user.getLastName();
//
//								// User Name
//								username = user.getUsername();
//
//								// Email
//								email = "" + user.asMap().get("email");
//
//								// Profile URL
//								profile_url = user.getLink();
//
//								// Birthday
//								birthday = user.getBirthday();
//
//								// Sex
//								sex = "" + user.asMap().get("gender");
//
//								// Location
//								location = ""
//										+ user.getLocation()
//												.getProperty("name");
//
//								// Display Picture URL
//								display_picture_url = "http://graph.facebook.com/"
//										+ user.getId() + "/picture";
//
//								// User ID
//								String id = user.getId();
//
//								// About Me of User
//								about = "";
//
//								// Language
//								lang = "";
//
//								// Organizations
//								organization = "";
//
//								// Places Lived
//								place = "";
//
//								// Relationship Status
//								relation = "";
//
//								// Login/Logout Flag
//
//								logFlag = "1";
//
//								// FB/G+ Flag
//
//								fbgFlag = "FB";
//
//								// User Data Fetch Over
//
//								String user_data = "First Name: " + first_name
//										+ " Middle Name: " + middle_name
//										+ " Last Name: " + last_name
//										+ " Username: " + username + " Email: "
//										+ email + " Profile URL: "
//										+ profile_url + " Birthday: "
//										+ birthday + " Sex: " + sex
//										+ " Location: " + location
//										+ " Display Picture URL: "
//										+ display_picture_url + " About Me: "
//										+ about + " Language: " + lang
//										+ " Organizations: " + organization
//										+ " Places Lived: " + place
//										+ " Relationship Status: " + relation
//										+ " ID: " + id + " Login Flag: "
//										+ logFlag + " FB/G+ Flag: " + fbgFlag;
//
//								System.out.println(user_data);
//
//								// UNIVERSAL EMAIL
//
//								SharedPreferences user_profile = PreferenceManager
//										.getDefaultSharedPreferences(LoginActivity.this);
//								SharedPreferences.Editor editors = user_profile
//										.edit();
//								editors.putString("user_profile", profile_url);
//								editors.commit();
//
//								// UNIVERSAL EMAIL SAVED
//
//								SharedPreferences settings = PreferenceManager
//										.getDefaultSharedPreferences(LoginActivity.this);
//								SharedPreferences.Editor editor = settings
//										.edit();
//								editor.putString("email", first_name);
//								editor.commit();
//
//								Toast.makeText(getApplicationContext(),
//										user_data, Toast.LENGTH_LONG).show();
//
//								// Device Data Fetching
//
//								// MAC
//
//								WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//								WifiInfo info = manager.getConnectionInfo();
//
//								if (info != null) {
//									macAddress = info.getMacAddress();
//								} else {
//									macAddress = "Not Found";
//								}
//
//								// MAC Over
//
//								// DEVICE NAME
//
//								String model = android.os.Build.MODEL;
//								String manufacturer = android.os.Build.MANUFACTURER;
//								// String product =
//								// android.os.Build.PRODUCT;
//
//								device_name = manufacturer + " " + model;
//
//								// DEVICE NAME Over
//
//								System.out.println("Device: " + device_name
//										+ " MAC: " + macAddress);
//
//								device_data = "Device: " + device_name
//										+ " MAC: " + macAddress;
//
//								System.out.println(device_data);
//
//								// Device Data Fetching finished.
//
//								// HTTP COMMUNICATION
//
//								new EmailTask().execute();
//
//								// HTTP COMMUNICATION OVER
//
//								Intent i = new Intent(LoginActivity.this,
//										MainActivity.class);
//								startActivity(i);
//								finish();
//
//							}
//
//						}
//					});
//			Request.executeBatchAsync(request);
//		} else if (state.isClosed()) {
//			Log.i(TAG, "Logged out...");
//		}
//	}
//
//	public class EmailTask extends AsyncTask<Void, Void, Void> {
//
//		@Override
//		protected Void doInBackground(Void... params) {
//			// TODO Auto-generated method stub
//
//			// Data to be Sent
//			try {
//				String fName;
//				if (first_name != null) {
//					fName = URLEncoder.encode(first_name, "utf-8");
//				} else {
//					fName = "";
//				}
//				String mName;
//				if (middle_name != null) {
//					mName = URLEncoder.encode(middle_name, "utf-8");
//				} else {
//					mName = "";
//				}
//				String lName;
//				if (last_name != null) {
//					lName = URLEncoder.encode(last_name, "utf-8");
//				} else {
//					lName = "";
//				}
//				String uName;
//				if (username != null) {
//					uName = URLEncoder.encode(username, "utf-8");
//				} else {
//					uName = "";
//				}
//				String mail;
//				if (email != null) {
//					mail = URLEncoder.encode(email, "utf-8");
//				} else {
//					mail = "";
//				}
//				String bday;
//				if (birthday != null) {
//					bday = URLEncoder.encode(birthday, "utf-8");
//				} else {
//					bday = "";
//				}
//				String language;
//				if (lang != null) {
//					language = URLEncoder.encode(lang, "utf-8");
//				} else {
//					language = "";
//				}
//				String org;
//				if (organization != null) {
//					org = URLEncoder.encode(organization, "utf-8");
//				} else {
//					org = "";
//				}
//				String gen;
//				if (sex != null) {
//					gen = URLEncoder.encode(sex, "utf-8");
//				} else {
//					gen = "";
//				}
//				String rel;
//				if (relation != null) {
//					rel = URLEncoder.encode(relation, "utf-8");
//				} else {
//					rel = "";
//				}
//				String iUrl;
//				if (display_picture_url != null) {
//					iUrl = URLEncoder.encode(display_picture_url, "utf-8");
//				} else {
//					iUrl = "";
//				}
//				String pUrl;
//				if (profile_url != null) {
//					pUrl = URLEncoder.encode(profile_url, "utf-8");
//				} else {
//					pUrl = "";
//				}
//				String liveIn;
//				if (place != null) {
//					liveIn = URLEncoder.encode(place, "utf-8");
//				} else {
//					liveIn = "";
//				}
//				String clientFlag;
//				if (fbgFlag != null) {
//					clientFlag = URLEncoder.encode(fbgFlag, "utf-8");
//				} else {
//					clientFlag = "";
//				}
//				String mac;
//				if (macAddress != null) {
//					mac = URLEncoder.encode(macAddress, "utf-8");
//				} else {
//					mac = "";
//				}
//				String device;
//				if (device_name != null) {
//					device = URLEncoder.encode(device_name, "utf-8");
//				} else {
//					device = "";
//				}
//
//				HttpClient httpClient = new DefaultHttpClient();
//				HttpContext localContext = new BasicHttpContext();
//
//				HttpPost httpPost = new HttpPost(
//						"http://wadersgroup.in/kakshya/registration.php?+firstname="
//								+ fName + "&middlename=" + mName + "&lastname="
//								+ lName + "&username=" + uName + "&email="
//								+ mail + "&dob=" + bday + "&language="
//								+ language + "&workedin=" + org + "&gender="
//								+ gen + "&relationshipstatus=" + rel
//								+ "&imageurl=" + iUrl + "&profilelink=" + pUrl
//								+ "&livingin=" + liveIn + "&regclient="
//								+ clientFlag + "&macaddress=" + mac
//								+ "&devicename=" + device);
//
//				HttpResponse response = httpClient.execute(httpPost,
//						localContext);
//
//				String uniq = EntityUtils.toString(response.getEntity());
//				String uniqueId = uniq.trim();
//
//				SharedPreferences uid = PreferenceManager
//						.getDefaultSharedPreferences(LoginActivity.this);
//				SharedPreferences.Editor editor = uid.edit();
//				editor.putString("uid", uniqueId);
//				System.out.println("Response " + uniqueId);
//				editor.commit();
//
//				System.out.println(response);
//
//			} catch (ClientProtocolException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			return null;
//		}
//	}
//
//}
