package in.wadersgroup.hth;

import com.gc.materialdesign.views.Button;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SearchActivity extends Activity {

	Button cancel, search;
	EditText searchString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_search);

		cancel = (Button) findViewById(R.id.buttonflatCancel);
		cancel.setBackgroundColor(getResources().getColor(R.color.black));

		search = (Button) findViewById(R.id.buttonflatSearch);
		search.setBackgroundColor(getResources().getColor(R.color.orange));

		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();

			}
		});

	}

}
