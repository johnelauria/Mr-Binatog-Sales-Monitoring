package com.example.ordermonitoring;

import java.io.FileNotFoundException;
import java.util.List;

import android.app.ListActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.os.Bundle;

public class OrderIndex extends ListActivity {
	
	DatabaseStorage myDb = new DatabaseStorage(this);
	String[] orders;
	Printer printer = new Printer();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String requestedDate = getIntent().getExtras().getString("date");
		myDb.open();
		try {
			if (requestedDate.contentEquals("")) {
				List<String> listedData = myDb.listAllData();
				orders = listedData.toArray(new String[listedData.size()]);
			}
			else {
				List<String> listedData = myDb.listData(requestedDate);
				orders = listedData.toArray(new String[listedData.size()]);
			}
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
		myDb.close();
		setListAdapter(new ArrayAdapter<String>(OrderIndex.this, android.R.layout.simple_list_item_1, orders));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.orders_menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		String requestedDate = getIntent().getExtras().getString("date");
		if (requestedDate.contentEquals("")) {
			myDb.open();
			try {
				printer.deleteExistingFile("Overall Sales");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			List<String> todaysOrders = myDb.listAllData();
			String[] orders = todaysOrders.toArray(new String[todaysOrders.size()]);
			for (int c = 0; c < todaysOrders.size(); c++) {
				printer.generatePrintableDoc("Overall Sales", orders[c], OrderIndex.this);
			}
			Toast.makeText(this, "File generated in your downloads folder", Toast.LENGTH_SHORT).show();
			myDb.close();
		} else {
			String engDate = getIntent().getExtras().getString("engDate");
			myDb.open();
			try {
				printer.deleteExistingFile(engDate);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			List<String> todaysOrders = myDb.listData(requestedDate);
			String[] orders = todaysOrders.toArray(new String[todaysOrders.size()]);
			for (int c = 0; c < todaysOrders.size(); c++) {
				printer.generatePrintableDoc(engDate, orders[c], OrderIndex.this);
			}
			Toast.makeText(this, "File generated in your documents folder", Toast.LENGTH_SHORT).show();
			myDb.close();
		}
		return false;
	}
	
	
}
