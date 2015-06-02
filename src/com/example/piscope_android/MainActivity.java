package com.example.piscope_android;







import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

	
	private WebView browser;
	public String url = "http://www.google.com";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Check for Internet
				if (!isNetworkAvailable()) {
					Toast.makeText(getApplicationContext(),
							"No Internet Connection. Please try again . . ",
							Toast.LENGTH_LONG).show();
					Toast.makeText(getApplicationContext(),
							"You are viewing a cached version", Toast.LENGTH_LONG)
							.show();
				}
				
				// Web view block
				browser = (WebView) findViewById(R.id.webview);
				browser.getSettings().setJavaScriptEnabled(true);		
				browser.getSettings()
						.setUserAgentString(
								"Mozilla/5.0 (Linux; Android 4.0.4; Galaxy Nexus Build/IMM76B) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.133 Mobile Safari/535.19");
				browser.setWebViewClient(new MyBrowser());
				browser.setWebViewClient(new MyWebViewClient());
				browser.loadUrl("http://" + url);
	}
	
	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	private class MyWebViewClient extends WebViewClient {
		@Override
		public void onReceivedHttpAuthRequest(WebView view,
				HttpAuthHandler handler, String host, String realm) {
			

		}
	}
	
	private class MyBrowser extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
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
