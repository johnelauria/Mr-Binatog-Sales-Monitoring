package com.example.ordermonitoring;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

import android.app.ListActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.os.Bundle;
import android.util.Log;

public class OrderIndex extends ListActivity implements DialogInterface.OnClickListener {
	
	DatabaseStorage myDb = new DatabaseStorage(this);
	String[] orders;
	Printer printer = new Printer();
	private ArrayList<String> al;
	private ArrayAdapter orderListAdapter;
	private int listPositionClicked;
	private String requestedDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestedDate = getIntent().getExtras().getString("date");
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
			al = new ArrayList<String>();
			orderListAdapter = new ArrayAdapter<String>(OrderIndex.this, android.R.layout.simple_list_item_1, al);
			for (int c = 0; c < orders.length; c++) {
				al.add(c, orders[c]);
			}
			
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
		myDb.close();
		setListAdapter(orderListAdapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if (position < l.getLastVisiblePosition() && !requestedDate.isEmpty()) {
			final AlertDialog confirmDelete = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT).create();
			listPositionClicked = position;
			confirmDelete.setTitle("Confirm delete?");
			confirmDelete.setMessage("Are you sure you want to delete this record?");
			confirmDelete.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", this);
			confirmDelete.setButton(AlertDialog.BUTTON_NEGATIVE, "No", this);
			confirmDelete.show();
		}
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
	
	@Override
	public void onClick(DialogInterface btn, int position) {
		if (position == btn.BUTTON_POSITIVE) {
			myDb.open();
			myDb.deleteById(myDb.orderIds.get(listPositionClicked));
			myDb.close();
			
			al.remove(listPositionClicked);
			orderListAdapter.notifyDataSetChanged();
			
			Toast.makeText(OrderIndex.this, "Record successfully deleted", Toast.LENGTH_LONG).show();
		}
	}
	
	
}
