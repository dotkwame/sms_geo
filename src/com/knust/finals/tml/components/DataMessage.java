package com.knust.finals.tml.components;

//import java.sql.Date;

public class DataMessage {
	private int message_id;
	private String message;
	private String phone_num;
	private String date_compose;
	private String date_sent;
	private String is_editable;
	private String status;
	private Coordinates coordinates;
	private String is_archived;
	
	public int getMessage_id(){
		return message_id;
	}
	
	public void setMessage_id(int message_id){
		this.message_id = message_id;
	}
	
	public String getMessage(){
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getPhone_num(){
		return phone_num;
	}
	
	public void setPhone_num(String phone_num){
		this.phone_num = phone_num;
	}
	
	public String getDate_compose(){
		return date_compose;
	}
	
	public void setDate_compose(String date_compose){
		this.date_compose = date_compose;
	}
	
	public String getDate_sent(){
		return date_sent;
	}
	
	public void setDate_sent(String date_sent){
		this.date_sent = date_sent;
	}
	
	public Coordinates getLocation(){
		return coordinates;
	}
	
	public void setLocation(Coordinates coordinates){
		this.coordinates = coordinates;
	}
	
	public String isEditable(){
		return is_editable;
	}
	
	public void setIsEditable(String is_editable){
		this.is_editable = is_editable;
	}
	
	public String getStatus(){
		return status;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
	public String isArchived(){
		return is_archived;
	}
	
	public void setArchived(String is_archived){
		this.is_archived = is_archived;
	}
	
}
