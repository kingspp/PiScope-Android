package com.example.piscope_android;



import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends ActionBarActivity {
	
	TextView ipaddr;
	String url="";
	Button ipsave;
	final Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		ipaddr= (TextView) findViewById(R.id.textView3);
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
