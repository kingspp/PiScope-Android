package com.piscope_android;


import com.example.piscope_android.R;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends ActionBarActivity {
	boolean setChanged = false;
	TextView ipaddr;
	String url="";
	Button ipsave;
	EditText username;
	EditText password;
	String uname;
	String passwd;
	final Context context = this;
	String about;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		ipaddr= (TextView) findViewById(R.id.textView3);
		username=(EditText)findViewById(R.id.editText2);
		password=(EditText)findViewById(R.id.editText1);
		
		try {
			about = "Android Application\n"
					+ "Version   : "
					+ getPackageManager().getPackageInfo(
							getPackageName(), 0).versionName
					+ "\n"
					+ "Team : Prathyush SP\n"
					+ "             Amaraprabhu C\n"
					+ "             Shashikiran MS\n"
					+ "             Vinay DC\n"
					+ "Email :  kingspprathyush@gmail.com\n";
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final EditText ipaddress = new EditText(this);
		
		SharedPreferences prefs = getSharedPreferences("Preferences.xml",
				MODE_PRIVATE);
		Boolean key = prefs.getBoolean("checkbox", false);
		url=prefs.getString("ip", "192.168.0.99");
		uname=prefs.getString("uname", "admin");
		passwd=prefs.getString("passwd", "1234");
		username.setText(uname);
		password.setText(passwd);
		ipaddr.setText(url);
		ipsave = (Button)findViewById(R.id.button1);
		ipsave.setOnClickListener(new View.OnClickListener() {
	        @Override
	        //On click function
	        public void onClick(View view) {
	        	/* Alert Dialog Code Start*/ 	
	        	AlertDialog.Builder alert = new AlertDialog.Builder(context);
	        	alert.setTitle("Raspberry Pi IP Address"); //Set Alert dialog title here
	        	alert.setMessage("Please enter Raspberry Pi IP Address\n"); //Message here

	            // Set an EditText view to get user input 
	            final EditText input = new EditText(context);
	            alert.setView(input);

	        	alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	        	public void onClick(DialogInterface dialog, int whichButton) {
	        	 //You will get as string input data in this variable.
	        	 // here we convert the input to a string and show in a toast.
	        	 url = input.getEditableText().toString();
	        	 Toast.makeText(context,"IP address changed to"+url,Toast.LENGTH_LONG).show(); 
	        	 writeURL(url);
	        	 ipaddr.setText(url);
	        	} // End of onClick(DialogInterface dialog, int whichButton)

				private void writeURL(String url) {
					// TODO Auto-generated method stub
					SharedPreferences prefs = getSharedPreferences(
							"Preferences.xml", MODE_PRIVATE);
					SharedPreferences.Editor editor = prefs.edit();
					editor.putString("ip", url);
					editor.commit(); //
					
				}
	        }); //End of alert.setPositiveButton
	        	alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
	        	  public void onClick(DialogInterface dialog, int whichButton) {
	        	    // Canceled.
	        		  dialog.cancel();
	        	  }
	        }); //End of alert.setNegativeButton
	        	AlertDialog alertDialog = alert.create();
	        	alertDialog.show();
	       /* Alert Dialog Code End*/ 
	        }
	    });
		
		url = prefs.getString("ip", "192.168.0.99");
		
		final CheckBox ch = (CheckBox) findViewById(R.id.checkBox);
		ch.setChecked(key);
		ch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ch.isChecked()) {
					writesetOn();
				} else if (!ch.isChecked()) {
					writesetoff();
				}
			}

			private void writesetoff() {
				setChanged = true;
				SharedPreferences prefs = getSharedPreferences(
						"Preferences.xml", MODE_PRIVATE);
				SharedPreferences.Editor editor = prefs.edit();
				editor.putBoolean("checkbox", false);
				editor.commit(); //
			}
			
			

			private void writesetOn() {
				setChanged = true;
				Toast.makeText(getApplicationContext(), "Enjoy Full screen",
						Toast.LENGTH_LONG).show();
				SharedPreferences prefs = getSharedPreferences(
						"Preferences.xml", MODE_PRIVATE);
				SharedPreferences.Editor editor = prefs.edit();
				editor.putBoolean("checkbox", true);
				editor.commit(); //
			}
		});
	}

	void savePrefs(){
		uname = username.getText().toString();
		passwd = password.getText().toString();
		SharedPreferences prefs = getSharedPreferences(
				"Preferences.xml", MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("uname", uname);
		editor.putString("passwd", passwd);
		editor.commit(); 
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Toast.makeText(context,"Saving Configuration",Toast.LENGTH_LONG).show();
			savePrefs();
			return true;			
		}
		else if (id == R.id.action_about) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
			alertDialogBuilder.setTitle("About MAHADASARA");
			alertDialogBuilder
					.setMessage(about)
					.setCancelable(false)
					.setNeutralButton("Close",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
			return true;
		}

		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		if (setChanged = true) {
			Intent i = getBaseContext().getPackageManager()
					.getLaunchIntentForPackage(
							getBaseContext().getPackageName());
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		}
	}
}