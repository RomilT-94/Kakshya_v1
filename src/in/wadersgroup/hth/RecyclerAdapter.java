package in.wadersgroup.hth;

import java.util.Collections;
import java.util.List;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecyclerAdapter extends
		RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

	public static NavigationDrawerFragment ndf;

	Context c;

	private LayoutInflater inflater;

	List<NavListItems> data = Collections.emptyList();

	public RecyclerAdapter(Context context, List<NavListItems> data) {

		this.c = context;

		inflater = LayoutInflater.from(context);
		this.data = data;

	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public void onBindViewHolder(RecyclerAdapter.MyViewHolder holder,
			final int position) {
		// TODO Auto-generated method stub

		NavListItems current = data.get(position);
		holder.title.setText(current.title);
		holder.icon.setImageResource(current.iconId);

	}

	@Override
	public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup arg0,
			int arg1) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.navigation_row, arg0, false);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}

	class MyViewHolder extends RecyclerView.ViewHolder implements
			OnClickListener {

		TextView title;
		ImageView icon;

		public MyViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub

			itemView.setOnClickListener(this);

			title = (TextView) itemView.findViewById(R.id.tvListTitle);
			Typeface type1 = Typeface.createFromAsset(c.getAssets(),
					"fonts/RobotoCondensed-Bold.ttf");
			title.setTypeface(type1);
			title.setTextColor(c.getResources().getColor(R.color.black));
			icon = (ImageView) itemView.findViewById(R.id.ivIconList);

		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			ndf = new NavigationDrawerFragment();
			ndf.mDrawerLayout.closeDrawer(Gravity.LEFT);

			// if (getPosition() == 0) {
			// // Domain
			// c.startActivity(new Intent(c, DomainDivisionActivity.class));
			// } else if (getPosition() == 1) {
			// // States
			// c.startActivity(new Intent(c, StateDivisionActivity.class));
			//
			// } else
			if (getPosition() == 0) {
				// Events

				Toast.makeText(c, "Events Coming Soon!", Toast.LENGTH_LONG)
						.show();

			} else if (getPosition() == 1) {
				// Suggest a NGO
				c.startActivity(new Intent(c, SuggestionActivity.class));

			} else if (getPosition() == 2) {
				// Register Your NGO
				Uri uri = Uri.parse("http://kakshya.in/register");
				Intent inte = new Intent(Intent.ACTION_VIEW, uri);
				c.startActivity(inte);

			} else if (getPosition() == 3) {
				// Help
				c.startActivity(new Intent(c, HelpActivity.class));

			} else if (getPosition() == 4) {
				// Rate
				launchMarket();

			} else if (getPosition() == 5) {
				// Feedback
				c.startActivity(new Intent(c, FeedbackActivity.class));

			} else if (getPosition() == 6) {
				// About Us
				c.startActivity(new Intent(c, AboutActivity.class));

			}

		}

		private void launchMarket() {
			Uri uri = Uri.parse("market://details?id=" + c.getPackageName());
			System.out.println(c.getPackageName());
			Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
			try {
				c.startActivity(myAppLinkToMarket);
			} catch (ActivityNotFoundException e) {
				Toast.makeText(c, "Please Install Google Play Store App",
						Toast.LENGTH_LONG).show();
			}
		}

	}

}
