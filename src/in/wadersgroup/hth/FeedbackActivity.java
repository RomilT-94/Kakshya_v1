package in.wadersgroup.hth;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Romil
 * 
 */
public class FeedbackActivity extends Activity {

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

		// Changing ActionBar Title Color

		setContentView(R.layout.activity_feedback);

		Typeface type1 = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoCondensed-Light.ttf");
		Typeface type2 = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Medium.ttf");

		TextView tv1 = (TextView) findViewById(R.id.textView2);
		tv1.setTypeface(type1);

		Button email = (Button) findViewById(R.id.bEmail);
		Button feedback = (Button) findViewById(R.id.bFeedback);
		final EditText et = (EditText) findViewById(R.id.editText1);

		et.setTypeface(type2);

		email.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
						"mailto", "support@kakshya.in", null));
				startActivity(Intent.createChooser(intent,
						"Choose an Email client :"));

			}

		});

		feedback.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String message = et.getText().toString();
				if (message.contentEquals("")) {

					Toast.makeText(getApplicationContext(),
							"Please Say Something...", Toast.LENGTH_LONG)
							.show();

				} else {

					new EmailTask().execute(message);

				}
			}
		});

	}

	public boolean emailAddressValidator(String emailId) {
		Pattern pattern = Pattern.compile("\\w+([-+.]\\w+)*" + "\\@"
				+ "\\w+([-.]\\w+)*" + "\\." + "\\w+([-.]\\w+)*");

		Matcher matcher = pattern.matcher(emailId);
		if (matcher.matches())
			return true;
		else
			return false;
	}

	public class EmailTask extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pDialog = new ProgressDialog(FeedbackActivity.this, R.style.MyTheme);
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
						"Thank You for your Feedback!", Toast.LENGTH_LONG)
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
					.getDefaultSharedPreferences(FeedbackActivity.this);
			String uid = uniqueId.getString("uid", "");
			try {
				String msg = URLEncoder.encode(params[0], "utf-8");

				// Get email from SharedPrefs

				SharedPreferences email_feed = PreferenceManager
						.getDefaultSharedPreferences(FeedbackActivity.this);
				String email_to_send = email_feed.getString("email_feed", "");

				String email_encode = URLEncoder.encode(email_to_send, "utf-8");

				HttpPost httpPost = new HttpPost(
						"http://kakshya.in/feedback.php?message=" + msg
								+ "&email=" + email_encode + "&android");

				HttpResponse response = httpClient.execute(httpPost,
						localContext);
				res = EntityUtils.toString(response.getEntity());

				System.out.println("Message " + msg + " UID " + uid);

				System.out.println(response);
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
