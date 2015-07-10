package in.wadersgroup.hth;

import java.io.IOException;
import java.net.URLEncoder;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SuggestionActivity extends Activity {

	EditText ngo_name, locality, city, additional;
	Button submit;

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

		setContentView(R.layout.activity_suggestion);

		ngo_name = (EditText) findViewById(R.id.etOrgName);
		locality = (EditText) findViewById(R.id.etLocality);
		city = (EditText) findViewById(R.id.etCity);
		additional = (EditText) findViewById(R.id.etAdditional);

		Typeface type1 = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoCondensed-Light.ttf");

		ngo_name.setTypeface(type1);
		locality.setTypeface(type1);
		city.setTypeface(type1);
		additional.setTypeface(type1);

		submit = (Button) findViewById(R.id.bSuggestionSubmit);

		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (ngo_name.getText().toString().contentEquals("")) {
					Toast.makeText(getApplicationContext(),
							"Please fill all mandatory fields!",
							Toast.LENGTH_LONG).show();
				} else {
					if (locality.getText().toString().contentEquals("")) {
						Toast.makeText(getApplicationContext(),
								"Please fill all mandatory fields!",
								Toast.LENGTH_LONG).show();
					} else {
						if (city.getText().toString().contentEquals("")) {
							Toast.makeText(getApplicationContext(),
									"Please fill all mandatory fields!",
									Toast.LENGTH_LONG).show();
						} else {
							new EmailTask().execute(ngo_name.getText()
									.toString(), locality.getText().toString(),
									city.getText().toString(), additional
											.getText().toString());
						}
					}
				}

			}
		});
	}

	public class EmailTask extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pDialog = new ProgressDialog(SuggestionActivity.this,
					R.style.MyTheme);
			super.onPreExecute();
			// pDialog.setMessage("Posting Feedback....");
			pDialog.setCancelable(false);
			pDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
			pDialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != "") {
				pDialog.dismiss();
				Toast.makeText(getApplicationContext(),
						"Thank You for your Suggestion!", Toast.LENGTH_LONG)
						.show();
				finish();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String res = "";
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			SharedPreferences uniqueId = PreferenceManager
					.getDefaultSharedPreferences(SuggestionActivity.this);
			String uid = uniqueId.getString("uid", "");
			try {
				String name = URLEncoder.encode(params[0], "utf-8");
				String locality = URLEncoder.encode(params[1], "utf-8");
				String city = URLEncoder.encode(params[2], "utf-8");
				String additional = URLEncoder.encode(params[3], "utf-8");

				// Get email from SharedPrefs

				SharedPreferences email_feed = PreferenceManager
						.getDefaultSharedPreferences(SuggestionActivity.this);
				String email_to_send = email_feed.getString("email_feed", "");

				String email_encode = URLEncoder.encode(email_to_send, "utf-8");

				HttpPost httpPost = new HttpPost(
						"http://kakshya.in/suggest.php?name=" + name
								+ "&address=" + locality + city + "&email="
								+ email_encode + "&additional=" + additional);

				HttpResponse response = httpClient.execute(httpPost,
						localContext);
				res = EntityUtils.toString(response.getEntity());

				System.out.println("Message " + name + " UID " + uid);

				System.out.println("Response for Suggestion is " + res);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return res;
		}
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

}
