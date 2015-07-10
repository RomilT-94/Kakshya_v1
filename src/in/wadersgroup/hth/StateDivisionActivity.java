package in.wadersgroup.hth;

import com.gc.materialdesign.views.Button;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class StateDivisionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.state_division);

		Button submit = (Button) findViewById(R.id.buttonSubmit);
		submit.setBackgroundColor(getResources().getColor(R.color.orange));

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
