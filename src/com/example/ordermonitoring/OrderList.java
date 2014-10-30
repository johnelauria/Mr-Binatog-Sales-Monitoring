package com.example.ordermonitoring;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

public class OrderList extends Fragment implements View.OnClickListener {
	
	DatePicker dateToSearch;
	Button startSearch;
	DateConverter dateconverter = new DateConverter();

	public OrderList() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.order_list, container, false);
		dateToSearch = (DatePicker) rootView.findViewById(R.id.etDate);
		startSearch = (Button) rootView.findViewById(R.id.bSearchDate);
		startSearch.setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onClick(View v) {
		Intent viewOrders = new Intent(getActivity(), OrderIndex.class);
		Bundle dateRequested = new Bundle();
		int month = dateToSearch.getMonth();
		int day = dateToSearch.getDayOfMonth();
		int year = dateToSearch.getYear();
		String theDate = month + "/" + day + "/" + year;
		String engDate = dateconverter.convertMonth(month) + " " + day + " " + year;
		dateRequested.putString("date", theDate);
		dateRequested.putString("engDate", engDate);
		viewOrders.putExtras(dateRequested);
		startActivity(viewOrders);
	}

}
