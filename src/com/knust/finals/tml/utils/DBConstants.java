package com.knust.finals.tml.utils;

import android.provider.BaseColumns;

public interface DBConstants extends BaseColumns{
	public static final String TAG = "TML";
	
	// Database name
	public static final String DB_TML_NAME = "messages.db";
	
	// Tables in database
	public static final String TBL_MSGS = "messages";
	public static final String TBL_LOCATIONS = "locations";
	public static final String TBL_GEND_IDS = "gend_ids";
	
	// Create Statements for DB
	public static final String CREATE_MSGS_TBL = "CREATE TABLE IF NOT EXISTS messages " +
		"( " + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
		" message_text TEXT, " +
		" is_editable VARCHAR(2), " +
		" phone_num_to VARCHAR(15), " +
		" status VARCHAR(2), " +
		" date_compose VARCHAR(20), " +
		" date_sent VARCHAR(20), " +
		" loc_id INTEGER, " +
		" archived VARCHAR(2)) ";
	
	public static final String CREATE_LOCATIONS_TBL = "CREATE TABLE IF NOT EXISTS locations " + 
		"( " + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
		" long_val VARCHAR(10), " +
		" lat_val VARCHAR(10), " +
		" datesaved VARCHAR(20), " +
		" name VARCHAR(255), " +
		" date_saved VARCHAR(20))";
	
	public static final String CREATE_GEN_ID_TBL = "CREATE TABLE IF NOT EXISTS gend_ids " +
		"( table_name VARCHAR(15), " +
		" last_id_used INTEGER)";
	
	public static final String[] MSGS_FROM = {_ID, "date_compose", "message_text", };
	public static final String[] LOCS_FROM = {_ID, "date_saved", "long_val", "lat_val", };
	public static final String ORDER_BY_ID_DESC = _ID + " DESC";
}
