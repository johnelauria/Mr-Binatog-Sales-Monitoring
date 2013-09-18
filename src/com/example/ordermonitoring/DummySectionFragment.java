package com.example.ordermonitoring;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class DummySectionFragment extends Fragment implements OnClickListener {
	
	/**
	 * This is the Activity to process orders from customers
	 */
	
	TextView smallTotal, medTotal, largeTotal, smallQuantity, medQuantity, largeQuantity, totalAmt, milkyQty,
		chocoQty, butterQty, cheeseQty, milkyTotal, chocoTotal, butterTotal, cheeseTotal;
	TableRow trs, trm, trl, trMilk, trButt, trChoc, trChse;
	Button smallOrder, medOrder, largeOrder, transact, addMilky, addCheese, addChoco, addButter;
	int sQty, mQty, lQty, smallPrice, medPrice, largePrice, orderTotal, milkyCounter, chocoCounter, butterCounter,
		cheeseCounter, milkyPrice, chocoPrice, butterPrice, cheesePrice;

	public DummySectionFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main_dummy,
				container, false);
		// REFER TO BUTTONS, TEXTVIEWS AND TABLE FROM LAYOUT
		sQty = mQty = lQty = smallPrice = medPrice = largePrice = milkyCounter = chocoCounter = butterCounter = cheeseCounter =
				milkyPrice = chocoPrice = butterPrice = cheesePrice = 0;
		smallOrder = (Button) rootView.findViewById(R.id.fragment1Small);
		medOrder = (Button) rootView.findViewById(R.id.fragment1Medium);
		largeOrder = (Button) rootView.findViewById(R.id.fragment1Large);
		transact = (Button) rootView.findViewById(R.id.bTransactOrder);
		addMilky = (Button) rootView.findViewById(R.id.bMilky);
		addCheese = (Button) rootView.findViewById(R.id.bCheese);
		addChoco = (Button) rootView.findViewById(R.id.bChoco);
		addButter = (Button) rootView.findViewById(R.id.bButter);
		smallTotal = (TextView) rootView.findViewById(R.id.tvSmallOrderPrice);
		medTotal = (TextView) rootView.findViewById(R.id.tvMedOrderPrice);
		largeTotal = (TextView) rootView.findViewById(R.id.tvLargeOrderPrice);
		milkyTotal = (TextView) rootView.findViewById(R.id.tvMilkyTotal);
		butterTotal = (TextView) rootView.findViewById(R.id.tvButterTotal);
		cheeseTotal = (TextView) rootView.findViewById(R.id.tvCheeseTotal);
		chocoTotal = (TextView) rootView.findViewById(R.id.tvChocoTotal);
		smallQuantity = (TextView) rootView.findViewById(R.id.tvSmallQty);
		medQuantity = (TextView) rootView.findViewById(R.id.tvMedQty);
		largeQuantity = (TextView) rootView.findViewById(R.id.tvLargeQty);
		milkyQty = (TextView) rootView.findViewById(R.id.tvMilkyQty);
		chocoQty = (TextView) rootView.findViewById(R.id.tvChocoQty);
		butterQty = (TextView) rootView.findViewById(R.id.tvButterQty);
		cheeseQty = (TextView) rootView.findViewById(R.id.tvCheeseQty);
		totalAmt = (TextView) rootView.findViewById(R.id.tvTotalAmount);
		trs = (TableRow) rootView.findViewById(R.id.trSmall);
		trm = (TableRow) rootView.findViewById(R.id.trMed);
		trl = (TableRow) rootView.findViewById(R.id.trLarge);
		trMilk = (TableRow) rootView.findViewById(R.id.trMilky);
		trButt = (TableRow) rootView.findViewById(R.id.trButter);
		trChse = (TableRow) rootView.findViewById(R.id.trCheese);
		trChoc = (TableRow) rootView.findViewById(R.id.trChoco);
		// SET ONCLICK LISTENERS TO BUTTONS
		smallOrder.setOnClickListener(this);
		medOrder.setOnClickListener(this);
		largeOrder.setOnClickListener(this);
		transact.setOnClickListener(this);
		addMilky.setOnClickListener(this);
		addCheese.setOnClickListener(this);
		addChoco.setOnClickListener(this);
		addButter.setOnClickListener(this);
		return rootView;
	}
	
	private void saveTransaction() {
		//SAVE THE TRANSACTION TO DATABASE
		trs.setVisibility(View.GONE);
		trm.setVisibility(View.GONE);
		trl.setVisibility(View.GONE);
		trMilk.setVisibility(View.GONE);
		trChse.setVisibility(View.GONE);
		trChoc.setVisibility(View.GONE);
		trButt.setVisibility(View.GONE);
		transact.setVisibility(View.GONE);
		Toast.makeText(getActivity(), "Transaction saved", Toast.LENGTH_SHORT).show();
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				sQty = mQty = lQty = smallPrice = medPrice = largePrice = milkyCounter = chocoCounter = butterCounter = 
						cheeseCounter = milkyPrice = chocoPrice = butterPrice = cheesePrice = orderTotal = 0;
				totalAmt.setText("P" + orderTotal + ".00");
				transact.setVisibility(View.GONE);
			}
		}, 1000);
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()) {
		case R.id.fragment1Small:
			trs.setVisibility(View.VISIBLE);
			smallPrice = smallPrice + 20;
			sQty++;
			smallQuantity.setText(sQty + "");
			smallTotal.setText("P" + smallPrice + ".00");
			break;
		case R.id.fragment1Medium:
			trm.setVisibility(View.VISIBLE);
			medPrice = medPrice + 25;
			mQty++;
			medQuantity.setText(mQty + "");
			medTotal.setText("P" + medPrice + ".00");
			break;
		case R.id.fragment1Large:
			trl.setVisibility(View.VISIBLE);
			largePrice = largePrice + 30;
			lQty++;
			largeQuantity.setText(lQty + "");
			largeTotal.setText("P" + largePrice + ".00");
			break;
		case R.id.bMilky:
			trMilk.setVisibility(View.VISIBLE);
			milkyCounter++;
			milkyPrice = milkyPrice + 5;
			milkyQty.setText(milkyCounter + "");
			milkyTotal.setText("P" + milkyPrice + ".00");
			break;
		case R.id.bCheese:
			trChse.setVisibility(View.VISIBLE);
			cheeseCounter++;
			cheesePrice = cheesePrice + 5;
			cheeseQty.setText(cheeseCounter + "");
			cheeseTotal.setText("P" + cheesePrice + ".00");
			break;
		case R.id.bChoco:
			trChoc.setVisibility(View.VISIBLE);
			chocoCounter++;
			chocoPrice = chocoPrice + 5;
			chocoQty.setText(chocoCounter + "");
			chocoTotal.setText("P" + chocoPrice + ".00");
			break;
		case R.id.bButter:
			trButt.setVisibility(View.VISIBLE);
			butterCounter++;
			butterPrice = butterPrice + 5;
			butterQty.setText(butterCounter + "");
			butterTotal.setText("P" + butterPrice + ".00");
			break;
		case R.id.bTransactOrder:
			if (orderTotal == 0) {
				//IF ATTEMPTED TO TRANSACT AN ORDER WITHOUT ANY REQUESTED ORDERS
				transact.setVisibility(View.GONE);
				Toast.makeText(getActivity(), "Cannot save a blank transaction", Toast.LENGTH_SHORT).show();
			} else {
				//TRANSACTION IN PROCESS
				DatabaseStorage db = new DatabaseStorage(getActivity());
				db.open();
				db.CommitTransaction(lQty, mQty, sQty, milkyCounter, chocoCounter, butterCounter, cheeseCounter, orderTotal);
				db.close();
				//ASK IF EMPLOYEE NEEDS CALCULATOR FOR CHANGE
				final AlertDialog changeCalculator = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT).create();
				changeCalculator.setTitle("Change Calculator");
				changeCalculator.setMessage("Would you like me to calculate for the change?");
				changeCalculator.setButton(AlertDialog.BUTTON_POSITIVE, "Calculate", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//OPEN UP A CALCULATOR THROUGH DIALOG THEN SHOW 
						changeCalculator.dismiss();
						AlertDialog calculator = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT).create();
						calculator.setTitle("Change Calculator");
						final EditText price = new EditText(getActivity());
						
						price.setInputType(InputType.TYPE_CLASS_NUMBER);
						calculator.setMessage("Enter amount paid by customer. I will calculate the change for P" + orderTotal);
						calculator.setView(price);
						calculator.setButton(AlertDialog.BUTTON_POSITIVE, "Compute", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								//DISPLAY CHANGE AMOUNT TO BE GIVEN TO CUSTOMER. THEN SAVE TRANSACTION TO DATABASE
								int amountPaid = Integer.parseInt(price.getText().toString());
								int change = amountPaid - orderTotal;
								String message = "The change for P" + amountPaid + " is P" + change + ". "
										+ "Don't forget to thank the customer with a smile!";
								Dialog changeDialog = new Dialog(getActivity());
								changeDialog.setTitle("Change is P" + change + ".00");
								TextView changeDialogMsg = new TextView(getActivity());
								changeDialogMsg.setText(message);
								changeDialog.setContentView(changeDialogMsg);
								changeDialog.show();
								saveTransaction();
							}
						});
						calculator.show();
					}
				});
				changeCalculator.setButton(AlertDialog.BUTTON_NEGATIVE, "No thanks", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//CALCULATOR NOT REQUIRED. SAVE TRANSACTION IMMEDIATELY TO DATABASE
						changeCalculator.cancel();
						saveTransaction();
					}
				});
				changeCalculator.show();
			}
			break;
		}
		orderTotal = (smallPrice + medPrice + largePrice + milkyPrice + chocoPrice + butterPrice + cheesePrice);
		totalAmt.setText("P" + orderTotal + ".00");
		if (orderTotal != 0)
			transact.setVisibility(View.VISIBLE);
	}
}