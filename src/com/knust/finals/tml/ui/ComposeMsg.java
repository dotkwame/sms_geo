package com.knust.finals.tml.ui;

import java.sql.Date;

import com.knust.finals.tml.components.Coordinates;
import com.knust.finals.tml.components.DataMessage;
import com.knust.finals.tml.utils.DBController;
import com.knust.finals.tml.utils.ObtainCoordinates;
import com.knust.finals.tml.utils.Utils;
import static com.knust.finals.tml.utils.DBConstants.TAG;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class ComposeMsg extends Activity implements OnClickListener{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_msg);
		
		// setup click listeners
		//View save_msg_btn = findViewById(R.id.save_msg_btn);
		//save_msg_btn.setOnClickListener(this);
		
		View send_msg_btn = findViewById(R.id.send_msg_btn);
		send_msg_btn.setOnClickListener(this);
		
		View back_home_btn = findViewById(R.id.back_home_btn);
		back_home_btn.setOnClickListener(this);
		
		// Set up utilities
		DBController.init(this);
		Utils.init(this);
		ObtainCoordinates.init(this);
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
	
	public void onClick(View v){
		switch (v.getId()) {
		//case R.id.save_msg_btn:
			//saveNewMessageDraft();
			//break;
			
		case R.id.send_msg_btn:
			sendMessageSMS();
			break;
			
		case R.id.back_home_btn:
			this.finish();
			break;
		}
	}
	
	public void sendMessageSMS(){
		// Try sending message then save
		try{
			ObtainCoordinates.init(this);
			// Get current location coordinates
			Coordinates coordinates = (ObtainCoordinates.getCurrLocation()) ? ObtainCoordinates.getCoordinates() : null;
	
			// Get and prepare message for sending
			DataMessage datamsg = new DataMessage();
			datamsg = prepareMessageForSavingAndSending(datamsg);
			
			if(datamsg == null) return; 
			
			datamsg.setDate_sent("not yet");
			datamsg.setIsEditable("N");
			datamsg.setLocation(coordinates);
			datamsg.setStatus("not sent");
			
			StringBuilder buildmsg = new StringBuilder();
			buildmsg
					.append("smsgeo ")
					.append(datamsg.getMessage())
					.append("\n")
					.append("!lt" + coordinates.getLat_val() + "\n")
					.append("!lg" + coordinates.getLong_val());
			
			String message_to_send = buildmsg.toString();
		
		
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(datamsg.getPhone_num(), null, message_to_send, null, null);
			
			datamsg.setStatus("sent");
			datamsg.setDate_sent((new Date((new java.util.Date()).getTime())).toString());
			
			DBController.saveNewCompleteMessage(datamsg);
			Utils.showStatusDialog("success!", "message has been sent successfully & saved.");
			clearText();
		}catch(Exception e){
			Utils.showStatusDialog("oops", "An error occured either sending or saving the message. Please check that your GPS is enabled.");
			e.printStackTrace();
		}
	}
	
	public void saveNewMessageDraft(){
		DataMessage message = new DataMessage();
		message = prepareMessageForSavingAndSending(message);
		
		if(message == null) return;
		
		message.setIsEditable("Y");
		message.setStatus("draft");
		
		Log.d(TAG, "About to save a new message draft");
		try{
			DBController.saveNewMessageDraftNoLocation(message);
			Log.d(TAG, "New message saved successfully");
			Utils.showStatusDialog("message saved", "message draft saved successfully.");
			clearText();
		}catch(Exception e){
			Utils.showStatusDialog("oops", "could not save message.");
			e.printStackTrace();
		}
	}
	
	private DataMessage prepareMessageForSavingAndSending(DataMessage msg){
		String msg_text = ((EditText)findViewById(R.id.new_msg_text)).getText().toString().trim();
		String phone_num = ((EditText)findViewById(R.id.phone_num_to_send_to)).getText().toString().trim();
		String comp_date = (new Date((new java.util.Date()).getTime())).toString();
		
		msg.setMessage(msg_text);
		msg.setPhone_num(phone_num);
		msg.setDate_compose(comp_date);
		msg.setArchived("N");
		
		if((null == msg_text) || (null == phone_num) || msg_text.equals("")  || phone_num.equals("")){
			Utils.showStatusDialog("oops", "either phone number has not been entered or \n message to send is is blank.");
			return null;
		}
		
		return msg;
	}
	
	public void clearText(){
		//EditText phonenum = (EditText)findViewById(R.id.phone_num_to_send_to);
		//phonenum.setText("");
		EditText msgtext = (EditText)findViewById(R.id.new_msg_text);
		msgtext.setText("");
	}

}
