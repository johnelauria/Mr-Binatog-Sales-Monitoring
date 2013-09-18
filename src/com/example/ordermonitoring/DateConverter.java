package com.example.ordermonitoring;

public class DateConverter {
	
	private String mes = null;
	
	public String convertMonth(int givenMonth) {
		switch(givenMonth) {
		case 0:
			mes = "January";
			break;
		case 1:
			mes = "February";
			break;
		case 2:
			mes = "March";
			break;
		case 3:
			mes = "April";
			break;
		case 4:
			mes = "May";
			break;
		case 5:
			mes = "June";
			break;
		case 6:
			mes = "July";
			break;
		case 7:
			mes = "August";
			break;
		case 8:
			mes = "September";
			break;
		case 9:
			mes = "October";
		case 10:
			mes = "November";
		case 11:
			mes = "December";
			break;
		}
		return mes;
	}

}
