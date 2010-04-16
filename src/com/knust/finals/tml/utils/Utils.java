package com.knust.finals.tml.utils;

import java.text.NumberFormat;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;

public class Utils {
	private static Context context;
	
	public static void init(Context ctx){
		context = ctx;
	}
	
	public static void showStatusDialog(String title, String message){
		Builder alert = new AlertDialog.Builder(context);
		alert.setTitle(title)
			.setMessage(message)
			.setNeutralButton("OK", new DialogInterface.OnClickListener(){
										public void onClick(DialogInterface dialog, int which) {}
									})
			.show();
	}
	
	public static String formatCoordinate(Double coord){
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(5);
		nf.setMaximumIntegerDigits(10);
		return nf.format(coord);
	}
}
