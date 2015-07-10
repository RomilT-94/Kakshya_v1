package in.wadersgroup.hth;

import com.gc.materialdesign.views.Button;
import com.gc.materialdesign.views.CheckBox;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class DomainDivisionActivity extends Activity {

	CheckBox all, old, health, sanitation, children, poverty, women;
	Button submitOptions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.domain_division);

		all = (CheckBox) findViewById(R.id.checkBox1);
		children = (CheckBox) findViewById(R.id.checkBox2);
		health = (CheckBox) findViewById(R.id.checkBox3);
		old = (CheckBox) findViewById(R.id.checkBox4);
		poverty = (CheckBox) findViewById(R.id.checkBox5);
		sanitation = (CheckBox) findViewById(R.id.checkBox6);
		women = (CheckBox) findViewById(R.id.checkBox7);

		all.setBackgroundColor(getResources().getColor(R.color.purple));
		children.setBackgroundColor(getResources().getColor(R.color.purple));
		health.setBackgroundColor(getResources().getColor(R.color.purple));
		old.setBackgroundColor(getResources().getColor(R.color.purple));
		poverty.setBackgroundColor(getResources().getColor(R.color.purple));
		sanitation.setBackgroundColor(getResources().getColor(R.color.purple));
		women.setBackgroundColor(getResources().getColor(R.color.purple));

		submitOptions = (Button) findViewById(R.id.button);
		submitOptions.setBackgroundColor(getResources()
				.getColor(R.color.orange));

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

	public void onCheckboxClicked(View view) {
		// Is the view now checked?
//		boolean checked = ((CheckBox) view).isCheck();
		boolean pressed = ((CheckBox) view).isPressed();

		// Check which checkbox was clicked
		switch (view.getId()) {
		case R.id.checkBox1:
			if (pressed) {

				Toast.makeText(getApplicationContext(), "All is checked",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "All is unchecked",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.checkBox2:

			break;
		case R.id.checkBox3:

			break;
		case R.id.checkBox4:

			break;
		case R.id.checkBox5:

			break;
		case R.id.checkBox6:

			break;
		case R.id.checkBox7:

			break;
		// TODO: Veggie sandwich
		}
	}

}
