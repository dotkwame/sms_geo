package com.knust.finals.tml.ui;

import com.knust.finals.tml.utils.DBController;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import static com.knust.finals.tml.utils.DBConstants.MSGS_FROM;

public class SavedMessages extends ListActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.saved_messages);
		DBController.init(this);
		
		showSavedMessages();
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
	
	private final int[] TO = {R.id.msgrowid, R.id.datecomposed, R.id.msgtext, };
	private void showSavedMessages(){
		Cursor cursor = DBController.getAllSavedMessages();
		startManagingCursor(cursor);
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.msgsitems, cursor, MSGS_FROM, TO);
		setListAdapter(adapter);
		DBController.closeDBConnxn();
	}
}
