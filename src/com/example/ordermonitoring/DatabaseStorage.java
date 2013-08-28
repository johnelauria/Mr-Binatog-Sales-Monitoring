package com.example.ordermonitoring;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseStorage {
	
	private static final String DATABASE_NAME = "OrderRecord";
	private static final String DATABASE_TABLE = "Orders";
	private static final int DATABASE_VERSION = 1;
	public static final String KEY_ROWID = "_id";
	public static final String KEY_DATE = "date";
	public static final String KEY_TIME = "time";
	public static final String KEY_LARGE = "large_order";
	public static final String KEY_MEDIUM = "medium_order";
	public static final String KEY_SMALL = "small_order";
	public static final String KEY_MILKY = "milky_order";
	public static final String KEY_BUTTER = "butter_order";
	public static final String KEY_CHEESE = "cheese_order";
	public static final String KEY_CHOCO = "choco_order";
	public static final String KEY_TOTAL = "total_amount";
	
	private Context myContext;
	private DbHelper myHelper;
	private SQLiteDatabase myDb;
	
	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID +
					" INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_DATE + " STRING NOT NULL, " + 
					KEY_TIME + " STRING NOT NULL, " + KEY_LARGE + " INTEGER DEFAULT '0', " + 
					KEY_MEDIUM + " INTEGER DEFAULT '0', " + KEY_SMALL + " INTEGER DEFAULT '0', " +
					KEY_MILKY + " INTEGER DEFAULT '0', " + KEY_BUTTER + " INTEGER DEFAULT '0', " +
					KEY_CHOCO + " INTEGER DEFAULT '0', " + KEY_CHEESE + " INTEGER DEFAULT '0', " +
					KEY_TOTAL + " INTEGER DEFAULT '0');");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}
	
	public DatabaseStorage(Context c) {
		myContext = c;
	}
	
	public DatabaseStorage open() {
		myHelper = new DbHelper(myContext);
		myDb = myHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		myHelper.close();
	}
	
	public long CommitTransaction(int large, int medium, int small, int milkQty, int chocoQty, int butterQty, int cheeseQty, int total) {
		Calendar c = Calendar.getInstance();
		String daylight = null;
		int am_pm = c.get(Calendar.AM_PM);
		if (am_pm == 0)
			daylight = "am";
		else
			daylight = "pm";
		String minutes = Integer.toString(c.get(Calendar.MINUTE));
		String hour = Integer.toString(c.get(Calendar.HOUR));
		String month = Integer.toString(c.get(Calendar.MONTH));
		String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
		String year = Integer.toString(c.get(Calendar.YEAR));
		String time = hour + ":" + minutes + " " + daylight;
		String date = month + "/" + day + "/" + year;
		ContentValues cv = new ContentValues();
		cv.put(KEY_DATE, date);
		cv.put(KEY_TIME, time);
		cv.put(KEY_LARGE, large);
		cv.put(KEY_MEDIUM, medium);
		cv.put(KEY_SMALL, small);
		cv.put(KEY_TOTAL, total);
		cv.put(KEY_MILKY, milkQty);
		cv.put(KEY_CHOCO, chocoQty);
		cv.put(KEY_BUTTER, butterQty);
		cv.put(KEY_CHEESE, cheeseQty);
		return myDb.insert(DATABASE_TABLE, null, cv);
	}
	
	public List<String> listData(String dateRequested) {
		//LIST SALES ACCORDING TO SELECTED DATE
		int totalSales = 0;
		List<String> data = new ArrayList<String>();
		String[] selectedDate = {dateRequested};
		String[] columns = new String[] {KEY_DATE, KEY_TIME, KEY_SMALL, KEY_MEDIUM, KEY_LARGE, KEY_MILKY, KEY_CHOCO, 
				KEY_BUTTER, KEY_CHEESE, KEY_TOTAL};
		Cursor c = myDb.query(DATABASE_TABLE, columns, KEY_DATE + " = ?", selectedDate, null, null, null);
		int iDate = c.getColumnIndex(KEY_DATE);
		int iTime = c.getColumnIndex(KEY_TIME);
		int iLarge = c.getColumnIndex(KEY_LARGE);
		int iMed = c.getColumnIndex(KEY_MEDIUM);
		int iSmall = c.getColumnIndex(KEY_SMALL);
		int iMilky = c.getColumnIndex(KEY_MILKY);
		int iCheese = c.getColumnIndex(KEY_CHEESE);
		int iButter = c.getColumnIndex(KEY_BUTTER);
		int iChoco = c.getColumnIndex(KEY_CHOCO);
		int iTotal = c.getColumnIndex(KEY_TOTAL);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			data.add(c.getString(iDate) + ", " + c.getString(iTime) + "\n" + c.getInt(iLarge) + " large, " + c.getInt(iMed) + " medium, " +
					c.getInt(iSmall) + " small\n" + c.getInt(iMilky) + " Milky way, " + c.getInt(iChoco) + " Choco, " + c.getInt(iButter) +
					" Butter, " + c.getInt(iCheese) + " Cheese\n" + "Total: P" + c.getInt(iTotal));
			totalSales = totalSales + c.getInt(iTotal);
		}
		data.add("Total Sales: P" + totalSales + ".00");
		return data;
	}
	
	public List<String> listAllData() {
		//LIST ALL SALES FROM BEGINNING
		List<String> data = new ArrayList<String>();
		String[] columns = new String[] {KEY_DATE, KEY_TIME, KEY_SMALL, KEY_MEDIUM, KEY_LARGE, KEY_MILKY, KEY_CHOCO, 
				KEY_BUTTER, KEY_CHEESE, KEY_TOTAL};
		Cursor c = myDb.query(DATABASE_TABLE, columns, null, null, null, null, null);
		int iDate = c.getColumnIndex(KEY_DATE);
		int iTime = c.getColumnIndex(KEY_TIME);
		int iLarge = c.getColumnIndex(KEY_LARGE);
		int iMed = c.getColumnIndex(KEY_MEDIUM);
		int iSmall = c.getColumnIndex(KEY_SMALL);
		int iMilky = c.getColumnIndex(KEY_MILKY);
		int iCheese = c.getColumnIndex(KEY_CHEESE);
		int iButter = c.getColumnIndex(KEY_BUTTER);
		int iChoco = c.getColumnIndex(KEY_CHOCO);
		int iTotal = c.getColumnIndex(KEY_TOTAL);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			data.add(c.getString(iDate) + ", " + c.getString(iTime) + "\n" + c.getInt(iLarge) + " large, " + c.getInt(iMed) + " medium, " +
					c.getInt(iSmall) + " small\n" + c.getInt(iMilky) + " Milky way, " + c.getInt(iChoco) + " Choco, " + c.getInt(iButter) +
					" Butter, " + c.getInt(iCheese) + " Cheese\n" + "Total: P" + c.getInt(iTotal) + ".00");
		}
		return data;
	}

}
