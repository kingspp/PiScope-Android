package com.example.piscope_android;





import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);
			return true;
		}
		else if (id == R.id.action_about) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
			alertDialogBuilder.setTitle("About PiScope");
			try {
				alertDialogBuilder
						.setMessage(
								"Android Application\n"
										+ "Version   : "
										+ getPackageManager().getPackageInfo(
												getPackageName(), 0).versionName
										+ "\n"
										+ "Team : Prathyush SP\n"
										+ "               Vinay DC\n"
										+ "               Amaraprabhu C\n"
										+ "               Shashikiran MS\n"
										+ "Email     : kingspprathyush@gmail.com\n")
						.setCancelable(false)
						.setNeutralButton("Close",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
