package com.knust.finals.tml.utils;

import java.sql.Date;

import com.knust.finals.tml.components.Coordinates;
import com.knust.finals.tml.ui.InputDialogBox;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class ObtainCoordinates {
	static LocationManager locationManager;
	static Context context;
	static String long_val = "0.0000";
	static String lat_val = "0.0000";
	static String loc_name = "-not set-";
	static Coordinates coordinates;
	
	public static void init(Context ctx){
		context = ctx;
	}
	
	public static boolean getCurrLocation(){
		boolean isLocationObtained = false;
		String location_context = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) context.getSystemService(location_context);
		
		if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) return false;
		
		try{
			String provider = LocationManager.GPS_PROVIDER;
			
			locationManager.requestLocationUpdates(provider, 5000, 0, new LocationListener(){
				public void onLocationChanged(Location location) {}
				public void onProviderDisabled(String provider) {}
				public void onProviderEnabled(String provider) {}
				public void onStatusChanged(String provider, int status, Bundle extras) {}
			});
			
			Location location = locationManager.getLastKnownLocation(provider);
			
			if (location != null) {
				Log.d("TML", "latitude val is: " + location.getLatitude());
				
				lat_val = Utils.formatCoordinate(location.getLatitude());
				long_val = Utils.formatCoordinate(location.getLongitude());
				
				String date_saved = (new Date((new java.util.Date()).getTime())).toString();
				//addaNametoLocation();
				//loc_name = InputDialogBox.getValueEntered();
				coordinates = new Coordinates(lat_val, long_val, loc_name, date_saved);
				isLocationObtained = true;
			} else {
				isLocationObtained = false;
			}
		}catch (Exception e) {
			isLocationObtained = false;
			e.printStackTrace();
		}
		
		return isLocationObtained;
	}
	
	public static Coordinates getCoordinates(){
		return coordinates;
	}
	
	public static boolean saveLocation(){
		boolean isLocationSaved = false;
		try{
			DBController.init(context);
			DBController.saveNewLocation(coordinates);
			isLocationSaved = true;
		}catch(Exception e){
			isLocationSaved = false;
			e.printStackTrace();
		}finally{
			DBController.closeDBConnxn();
		}
		return isLocationSaved;
	}
	
	public static void addaNametoLocation() {
		Intent input_dialog = new Intent(context, InputDialogBox.class);
		context.startActivity(input_dialog);
	}
	
}
