package com.example.piscope_android;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.HttpAuthHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.webkit.JsResult;

public class MainActivity extends ActionBarActivity {
	private WebView browser;
	public String url = "http://192.168.0.99";
	public String uname;
	public String passwd;
	public String about;
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
		// Shared Pref check
		SharedPreferences prefs = getSharedPreferences("Preferences.xml",
				MODE_PRIVATE);
		url = prefs.getString("ip", "192.168.0.99");
		uname=prefs.getString("uname", "admin");
		passwd=prefs.getString("passwd", "1234");
		Boolean key = prefs.getBoolean("checkbox", false);
		if (key == true) {
			ActionBar actionBar = getSupportActionBar();
			actionBar.hide();
		} else {
			ActionBar actionBar = getSupportActionBar();
			actionBar.show();
		}
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
		// Ads block

	}

	private class MyWebViewClient extends WebViewClient {
		@Override
		public void onReceivedHttpAuthRequest(WebView view,
				HttpAuthHandler handler, String host, String realm) {
			handler.proceed(uname, passwd);

		}
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public abstract class AdListener {
		public void onAdLoaded() {
		}

		public void onAdFailedToLoad(int errorCode) {
		}

		public void onAdOpened() {
		}

		public void onAdClosed() {
		}

		public void onAdLeftApplication() {
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);
			return true;
		} else if (id == R.id.action_about) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
			alertDialogBuilder.setTitle("About PiScope");
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

		else if (id == R.id.action_reload) {
			browser.clearCache(true);
			Toast.makeText(getApplicationContext(), "Clearing cache... ",
					Toast.LENGTH_LONG).show();
			browser.loadUrl(url);
			Toast.makeText(getApplicationContext(), "Loading...",
					Toast.LENGTH_LONG).show();
		}
		return super.onOptionsItemSelected(item);
	}

	private class MyBrowser extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	// Exit function
	private Boolean exit = false;

	@Override
	public void onBackPressed() {
		if (exit) {
			finish(); // finish activity
		} else {
			Toast.makeText(this, "Press Back again to Exit.",
					Toast.LENGTH_SHORT).show();
			exit = true;
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					exit = false;
				}
			}, 3 * 1000);
		}
	}
}