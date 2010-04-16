package com.knust.finals.tml.components;

public class Coordinates {
	private int loc_id;
	private String long_val;
	private String lat_val;
	private String loc_name;
	private String date_saved;
	
	public Coordinates(String lat_val, String long_val, String name, String date_saved){
		this.lat_val = lat_val;
		this.long_val = long_val;
		this.loc_name = name;
		this.date_saved = date_saved;
	}
	
	public int getLoc_id(){
		return loc_id;
	}
	
	public void setLoc_id(int loc_id){
		this.loc_id = loc_id;
	}
	
	public String getLong_val(){
		return long_val;
	}
	
	public void setLong_val(String long_val){
		this.long_val = long_val;
	}
	
	public String getLat_val(){
		return lat_val;
	}
	
	public void setLat_val(String lat_val){
		this.lat_val = lat_val;
	}
	
	public String getLoc_name(){
		return loc_name;
	}
	
	public void setLoc_name(String loc_name){
		this.loc_name = loc_name;
	}
	
	public void setDateSaved(String date_saved){
		this.date_saved = date_saved;
	}
	
	public String getDateSaved(){
		return date_saved;
	}
}
