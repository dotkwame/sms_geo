package com.knust.finals.tml.ui;

import com.knust.finals.tml.ui.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class InputDialogBox extends Activity {
	public static String valueEntered;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		showInputDialog();
	}
	
	public void showInputDialog(){
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Enter Name for Location");
		
		alert.setPositiveButton("OK", new DialogInterface.OnClickListener(){
			
			public void onClick(DialogInterface dialog, int which) {
				valueEntered = ((EditText)findViewById(R.id.input_data_txt_fld)).getText().toString();
			}});
	
		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {}
		});
		
		LayoutInflater li = LayoutInflater.from(this);
		View alertdialogview = li.inflate(R.layout.inputdialog, null);
		
		alert.setView(alertdialogview);
		
		alert.show();
	}
	
	public static String getValueEntered(){
		return valueEntered;
	}
}

