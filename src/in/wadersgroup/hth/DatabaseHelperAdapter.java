package in.wadersgroup.hth;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Romil
 * 
 */
public class DatabaseHelperAdapter {

	DatabaseHelper helper;
	Context c;
	ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();

	public DatabaseHelperAdapter(Context context) {

		// TODO Auto-generated constructor stub
		helper = new DatabaseHelper(context);
		c = context;

	}

	public long insertData(String _id, String name, String aos, String dof,
			String head, String address, String email, String phone,
			String website, String thumb, String donate, String lat, String lng) {

		SQLiteDatabase db = helper.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.UID, _id);
		contentValues.put(DatabaseHelper.NGO_NAME, name);
		contentValues.put(DatabaseHelper.AREA_SERVICE, aos);
		contentValues.put(DatabaseHelper.DATE_FORMATION, dof);
		contentValues.put(DatabaseHelper.HEAD, head);
		contentValues.put(DatabaseHelper.ADDRESS, address);
		contentValues.put(DatabaseHelper.EMAIL, email);
		contentValues.put(DatabaseHelper.PHONE, phone);
		contentValues.put(DatabaseHelper.WEBSITE, website);
		contentValues.put(DatabaseHelper.THUMB, thumb);
		contentValues.put(DatabaseHelper.DONATE_URL, donate);
		contentValues.put(DatabaseHelper.LATITUDE, lat);
		contentValues.put(DatabaseHelper.LONGITUDE, lng);

		long id = db.insert(DatabaseHelper.TABLE_NAME, null, contentValues);

		db.close();

		return id;

	}

	public int deleteRow(String email) {

		SQLiteDatabase db = helper.getWritableDatabase();

		String[] whereArgs = { email };

		int count = db.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.EMAIL
				+ " =? ", whereArgs);
		db.close();
		return count;

	}

	public String getData(String email) {

		SQLiteDatabase db = helper.getWritableDatabase();

		String columns[] = { DatabaseHelper.NGO_NAME };

		Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, columns,
				DatabaseHelper.EMAIL + "='" + email + "'", null, null, null,
				null);

		StringBuffer buffer = new StringBuffer();

		while (cursor.moveToNext()) {
			int index = cursor.getColumnIndex(DatabaseHelper.NGO_NAME);
			String ngo_name = cursor.getString(index);
			buffer.append(ngo_name);

		}
		db.close();
		return buffer.toString();
	}

	public ArrayList<HashMap<String, String>> getAllData() {

		SQLiteDatabase db = helper.getWritableDatabase();
		String[] columns = { DatabaseHelper.UID, DatabaseHelper.NGO_NAME,
				DatabaseHelper.AREA_SERVICE, DatabaseHelper.DATE_FORMATION,
				DatabaseHelper.HEAD, DatabaseHelper.ADDRESS,
				DatabaseHelper.EMAIL, DatabaseHelper.PHONE,
				DatabaseHelper.WEBSITE, DatabaseHelper.THUMB,
				DatabaseHelper.DONATE_URL, DatabaseHelper.LATITUDE,
				DatabaseHelper.LONGITUDE };
		Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, columns, null,
				null, null, null, null);
		StringBuffer buffer = new StringBuffer();
		while (cursor.moveToNext()) {

			int cid = cursor.getInt(0);

			String name = cursor.getString(1);
			String area = cursor.getString(2);
			String date = cursor.getString(3);
			String hea = cursor.getString(4);
			String addre = cursor.getString(5);
			String maile = cursor.getString(6);
			String ph = cursor.getString(7);
			String webs = cursor.getString(8);
			String thu = cursor.getString(9);
			String d_url = cursor.getString(10);
			String lati = cursor.getString(11);
			String longi = cursor.getString(12);

			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name", name);
			map.put("area", area);
			map.put("date", date);
			map.put("head", hea);
			map.put("address", addre);
			map.put("email", maile);
			map.put("phone", ph);
			map.put("website", webs);
			map.put("thumb", thu);
			map.put("donate_url", d_url);
			map.put("lat", lati);
			map.put("lng", longi);

			buffer.append(" " + cid + " " + name + " " + area + " " + date
					+ " " + hea + " " + addre + " " + maile + " " + ph + " "
					+ webs + thu + " " + "\n");
			dataList.add(map);

		}

		db.close();
		return dataList;
	}

	static class DatabaseHelper extends SQLiteOpenHelper {
		public DatabaseHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
			// TODO Auto-generated constructor stub
		}

		private static final int DB_VERSION = 7;
		private static final String DB_NAME = "favorite_database";
		private static final String TABLE_NAME = "favorite_table";
		private static final String KEY = "_uid";
		private static final String UID = "_id";
		private static final String NGO_NAME = "ngo_name";
		private static final String AREA_SERVICE = "area_service";
		private static final String DATE_FORMATION = "date_formation";
		private static final String HEAD = "head";
		private static final String ADDRESS = "address";
		private static final String EMAIL = "email";
		private static final String PHONE = "phone";
		private static final String WEBSITE = "website";
		private static final String THUMB = "thumb_url";
		private static final String DONATE_URL = "donate_url";
		private static final String LATITUDE = "latitude";
		private static final String LONGITUDE = "longitude";

		private static final String DROP_TABLE = "DROP TABLE IF EXISTS"
				+ TABLE_NAME;
		private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
				+ " (" + KEY + " INTEGER PRIMARY KEY," + UID
				+ " VARCHAR(255), " + NGO_NAME + " VARCHAR(255), "
				+ AREA_SERVICE + " VARCHAR(255), " + DATE_FORMATION
				+ " VARCHAR (255), " + HEAD + " VARCHAR(255), " + ADDRESS
				+ " VARCHAR(255), " + EMAIL + " VARCHAR(255), " + PHONE
				+ " VARCHAR(255), " + WEBSITE + " VARCHAR(255), " + THUMB
				+ " VARCHAR (255), " + DONATE_URL + " VARCHAR(255), "
				+ LATITUDE + " VARCHAR (255), " + LONGITUDE
				+ " VARCHAR (255));";

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub

			try {
				db.execSQL(CREATE_TABLE);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			try {
				db.execSQL(DROP_TABLE);
				onCreate(db);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
