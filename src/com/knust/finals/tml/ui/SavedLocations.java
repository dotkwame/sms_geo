package com.knust.finals.tml.ui;

import static com.knust.finals.tml.utils.DBConstants.TAG;
import com.knust.finals.tml.utils.DBController;

import static com.knust.finals.tml.utils.DBConstants.LOCS_FROM;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

public class SavedLocations extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.saved_locations);
		DBController.init(this);
		try {
			showsavedLocations();
		} catch (Exception e) {
			Log.d(TAG, "Error occured loading locations from DB");
			e.printStackTrace();
		}
		DBController.closeDBConnxn();
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
    }
    
    @Override
    public void onRestart(){
    	super.onRestart();
    	DBController.init(this);
    }
	
	private final int[] TO = {R.id.locrowid, R.id.datesaved, R.id.longval, R.id.latval, };
	private void showsavedLocations(){
		Cursor cursor = DBController.getAllSavedLocations();
		startManagingCursor(cursor);
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.locitems, cursor, LOCS_FROM, TO);
		setListAdapter(adapter);
		DBController.closeDBConnxn();
	}
}
