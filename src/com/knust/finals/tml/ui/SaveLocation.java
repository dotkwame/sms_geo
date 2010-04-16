package com.knust.finals.tml.ui;

import android.app.Activity;
import android.location.LocationManager;
import android.os.Bundle;

import com.knust.finals.tml.utils.DBController;
import com.knust.finals.tml.utils.ObtainCoordinates;
import com.knust.finals.tml.utils.Utils;

public class SaveLocation extends Activity {
	LocationManager locationManager;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        
        // initialize utilities
        ObtainCoordinates.init(this);
        DBController.init(this);
        Utils.init(this);
        
        // save the current location
        saveCurrentLocation();
    }
	
	@Override
    public void onPause(){
    	DBController.closeDBConnxn();
    	super.onPause();
    }
    
    @Override
    public void onDestroy(){
    	DBController.closeDBConnxn();
    	super.onDestroy();
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	DBController.init(this);
    	Utils.init(this);
    }
    
    @Override
    public void onRestart(){
    	super.onRestart();
    	DBController.init(this);
    	Utils.init(this);
    }
	
	public void saveCurrentLocation(){
    	boolean isLocObtained = ObtainCoordinates.getCurrLocation();
    	boolean isLocSaved = ObtainCoordinates.saveLocation();
    	
    	if(isLocObtained && isLocSaved){
    		Utils.showStatusDialog("success!", "Location obtained and saved successfully");
    	}else{
    		Utils.showStatusDialog("sorry", "a problem occured, \n your coordinated were not obtained to be saved");
    	}
    }
	
	public void addNameToLocation(){
		
	}

}
