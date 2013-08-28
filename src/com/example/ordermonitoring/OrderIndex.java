package com.example.ordermonitoring;

import java.util.List;

import android.app.ListActivity;
import android.widget.ArrayAdapter;
import android.os.Bundle;

public class OrderIndex extends ListActivity {
	
	DatabaseStorage myDb = new DatabaseStorage(this);
	String dateDemanded;
	String[] orders;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dateDemanded = getIntent().getExtras().getString("date");
		myDb.open();
		try {
			if (dateDemanded.contentEquals("")) {
				List<String> listedData = myDb.listAllData();
				orders = listedData.toArray(new String[listedData.size()]);
			}
			else {
				List<String> listedData = myDb.listData(dateDemanded);
				orders = listedData.toArray(new String[listedData.size()]);
			}
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
		myDb.close();
		setListAdapter(new ArrayAdapter<String>(OrderIndex.this, android.R.layout.simple_list_item_1, orders));
	}
	
}
