package com.knust.finals.tml.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import static android.provider.BaseColumns._ID;

import static com.knust.finals.tml.utils.DBConstants.CREATE_GEN_ID_TBL;
import static com.knust.finals.tml.utils.DBConstants.CREATE_LOCATIONS_TBL;
import static com.knust.finals.tml.utils.DBConstants.CREATE_MSGS_TBL;
import static com.knust.finals.tml.utils.DBConstants.DB_TML_NAME;
import static com.knust.finals.tml.utils.DBConstants.TBL_GEND_IDS;
import static com.knust.finals.tml.utils.DBConstants.TBL_LOCATIONS;
import static com.knust.finals.tml.utils.DBConstants.TBL_MSGS;
import com.knust.finals.tml.components.Coordinates;
import com.knust.finals.tml.components.DataMessage;
import static com.knust.finals.tml.utils.DBConstants.TAG;
import static com.knust.finals.tml.utils.DBConstants.MSGS_FROM;
import static com.knust.finals.tml.utils.DBConstants.LOCS_FROM; 
import static com.knust.finals.tml.utils.DBConstants.ORDER_BY_ID_DESC;

public class DBController {
	private static SQLiteDatabase db = null;
	private static Context context = null;
	
	public static void init(Context ctx){
		context = ctx;
		db = context.openOrCreateDatabase(DB_TML_NAME, Context.MODE_PRIVATE, null);
		Log.d(TAG, "DB open for access");
	}
	
	public static void setupDB(){
		try {
			Log.d(TAG, "About to set up DB");
			// Create tables for DB
			db.execSQL(CREATE_MSGS_TBL);
			db.execSQL(CREATE_LOCATIONS_TBL);
			db.execSQL(CREATE_GEN_ID_TBL);
			
			// set up gen ids table
			setupGendIdstable();
			Log.d(TAG, "Setup DB completed successfully");
		} catch (Exception e) {
			// TODO; Handle Exception
			Log.d(TAG, "Error occured setting up the database");
			e.printStackTrace();
		}
	}
	
	// Get last id used for a table
	public static int getLastIdUsedforaTable(String table_name){
		int last_id = -1;
		String querystring = "SELECT last_id_used FROM "+TBL_GEND_IDS+
				" WHERE table_name = '"+ table_name +"' ";
		Cursor cursor = db.rawQuery(querystring, null);
		while(cursor.moveToNext()){
			last_id = cursor.getInt(0);
		}
		
		return last_id;	
	}
	
	// update the last used id in the gen ids table
	public static void updateLastIdUsedforaTable(int new_id, String table_name){
		db.execSQL("UPDATE "+ TBL_GEND_IDS + 
				   " SET last_id_used = " + new_id + 
				   " WHERE table_name = '" + table_name + "'");
	}
	
	// Setup gend_ids table
	private static void setupGendIdstable(){
		Log.d(TAG, "About to setup the GEN ID table");
		db.execSQL("INSERT INTO "+ TBL_GEND_IDS + 
				" (table_name, last_id_used) " +
				"VALUES ('" + TBL_MSGS +"', 0)" );
		
		db.execSQL("INSERT INTO "+ TBL_GEND_IDS + 
				" (table_name, last_id_used) " +
				"VALUES ('" + TBL_LOCATIONS + "', 0)" );
		Log.i(TAG, "Setup of GENIDS table complete");
	}
	
	// save new message
	public static void saveNewCompleteMessage(DataMessage message){
		int loc_id = -1;
		
		if(null != message.getLocation()){
			loc_id = saveNewLocation(message.getLocation());
		}
		
		int id_to_use = getLastIdUsedforaTable(TBL_MSGS) + 1;
		updateLastIdUsedforaTable(id_to_use, TBL_MSGS);
		
		db.execSQL("INSERT INTO "+ TBL_MSGS + 
				" ( " + _ID +", message_text, is_editable, status, date_compose, date_sent, loc_id, archived)" +
				"VALUES (" + id_to_use + ", " +
						"'" + message.getMessage() +"', " +
						"'" + message.isEditable() +"', " +
						"'" + message.getStatus() + "', " +
						"'" + message.getDate_compose() + "', " +
						"'" + message.getDate_sent() + "', " +
						loc_id + ", " +
						"'" + message.isArchived() + "') ");
	}
	
	public static void saveNewMessageDraftNoLocation(DataMessage message){
		int id_to_use = getLastIdUsedforaTable(TBL_MSGS) + 1;
		
		try{
			db.execSQL("INSERT INTO "+ TBL_MSGS + 
					"( " + _ID + ", message_text, is_editable, status, date_compose, archived) " +
					"VALUES (" + id_to_use + ", " +
							"'" + message.getMessage() +"', " +
							"'" + message.isEditable() +"', " +
							"'" + message.getStatus() + "', " +
							"'" + message.getDate_compose() + "', " +
							"'" + message.isArchived() + "') ");
			
			updateLastIdUsedforaTable(id_to_use, TBL_MSGS);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	// Add new location
	public static int saveNewLocation(Coordinates coordinates){
		int id_to_use = getLastIdUsedforaTable(TBL_LOCATIONS) + 1;
		
		try{
			db.execSQL("INSERT INTO "+ TBL_LOCATIONS + 
					" (" + _ID + ", long_val, lat_val, name, date_saved) " +
					" VALUES (" + id_to_use + ", " +
					" '" + coordinates.getLong_val() + "', " +
					" '" + coordinates.getLat_val() + "', " +
					" '" + coordinates.getLoc_name() + "', " +
					" '" + coordinates.getDateSaved() + "')");
			
			updateLastIdUsedforaTable(id_to_use, TBL_LOCATIONS);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return id_to_use;
	}
	
	// Get all saved messages
	public static Cursor getAllSavedMessages(){
		Cursor cursor = db.query(TBL_MSGS, MSGS_FROM, null, null, null, null, ORDER_BY_ID_DESC);
		return cursor;
		
	}
	
	// Get all saved locations
	public static Cursor getAllSavedLocations(){
		
		Cursor cursor = db.query(TBL_LOCATIONS, LOCS_FROM, null, null, null, null, ORDER_BY_ID_DESC);
		return cursor;
	}
	
	// close the connection to the db
	public static void closeDBConnxn(){
		db.close();
		Log.i(TAG, "DB closed successfully");
	}
}
