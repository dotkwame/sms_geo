 package com.knust.finals.tml.ui;

import com.knust.finals.tml.utils.DBController;
import com.knust.finals.tml.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import static com.knust.finals.tml.utils.DBConstants.DB_TML_NAME;
import static com.knust.finals.tml.utils.DBConstants.TAG;

public class Home extends Activity implements OnClickListener {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //dropDB();
        lookupForDB();
        
        // setup click listeners for buttons
        View new_msg_btn = findViewById(R.id.new_msg_btn);
        new_msg_btn.setOnClickListener(this);
        
        View sent_msgs_btn = findViewById(R.id.sent_msgs_btn);
        sent_msgs_btn.setOnClickListener(this);
        
        View about_btn = findViewById(R.id.about_btn);
        about_btn.setOnClickListener(this);
        
        View help_btn = findViewById(R.id.help_btn);
        help_btn.setOnClickListener(this);
        
        View exit_application_btn = findViewById(R.id.exit_app_btn);
        exit_application_btn.setOnClickListener(this);
        
        // initialize utilities
        DBController.init(this);
		Utils.init(this);        
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

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.new_msg_btn:
			goComposeANewMsg();
			break;
			
		case R.id.sent_msgs_btn:
			goLoadAllMessages();
			break;
		
		case R.id.about_btn:
			goLoadAbout();
			break;
			
		case R.id.help_btn:
			goLoadHelp();
			break;
		
		case R.id.exit_app_btn:
			shutdownApp();
			break;
		}
		
	}
    
    public void goComposeANewMsg(){
    	Log.i(TAG, "compose new message");
    	Intent comp_new_msg = new Intent(Home.this, ComposeMsg.class);
    	startActivity(comp_new_msg);
    }
    
    public void goLoadAllMessages(){
    	Log.i(TAG, "load all saved messages");
    	Intent saved_msgs = new Intent(Home.this, SavedMessages.class);
    	startActivity(saved_msgs);
    }
    
    public void goLoadHelp(){
    	Intent help = new Intent(this, Help.class);
    	startActivity(help);
    }
    
    public void goLoadAbout(){
    	Intent about = new Intent(this, About.class);
    	startActivity(about);
    }
    
    public void lookupForDB(){
    	String[] listofdb = this.databaseList();
    	boolean dbfound = false;
    	
		DBController.init(this);
    	for (int i = 0; i < listofdb.length; i++) {
			if(DB_TML_NAME.equals(listofdb[i])){
				Log.i(TAG, "DB Found, no need to create");
				dbfound = true;
				break;
			}
		}
    	
    	if(!dbfound){
    		DBController.setupDB();
    	}
    }
	
    /*private void dropDB(){
    	Log.d(TAG, "about to delete DB");
    	this.deleteDatabase(DB_TML_NAME);
    }*/
    
    private void shutdownApp(){
    	DBController.closeDBConnxn();
		finish();
    }
    
}