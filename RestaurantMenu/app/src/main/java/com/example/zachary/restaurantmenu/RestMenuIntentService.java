package com.example.zachary.restaurantmenu;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.zachary.restaurantmenu.provider.MyContentProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestMenuIntentService extends IntentService
{
	private static final String DEBUG_TAG = "RESTAURANT MENU APP";
	private static final String STUDENT_ID = "s0949124";
	private static final String BASE_URL = "http://opus.monmouth.edu/";
	private static final String FILE_NAME = "items.json";
	//private static final String DATA_URL = "http://www.jantron.com/json-products.php";
	private static final String DATA_URL = BASE_URL + "~" + STUDENT_ID + "/" + FILE_NAME;
	//private static final String DATA_URL = "http://opus.monmouth.edu/~s0949124/products_test.json";

	public RestMenuIntentService()
	{
		super("RestMenuIntentService");
	}


	@Override
	protected void onHandleIntent(Intent intent)
	{
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected())
		{
			// Get the data
			try
			{
				downloadUrl(DATA_URL);
			} catch (IOException e) {
				Log.d(DEBUG_TAG, "Unable to retrieve web page. URL may be invalid.");
			}
		} else {
			Log.d(DEBUG_TAG, "No network connections available");
		}
	}

	private void downloadUrl(String myURL) throws IOException
	{
		HttpURLConnection conn = null;
		InputStream inputStream = null;

		try
		{
			URL url = new URL(myURL);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000);                         // milliseconds
			conn.setConnectTimeout(15000);                      // milliseconds
			conn.setRequestMethod("GET");
			conn.setDoInput(true);

			// starts the query
			conn.connect();
			int response = conn.getResponseCode();
			Log.d(DEBUG_TAG, "The response is: " + response);
			inputStream = conn.getInputStream();
			String contentAsString = readIt(inputStream);
			Log.d(DEBUG_TAG, "The json is: " + contentAsString);


			// Parse the data
			try
			{
				parseIt(contentAsString);
			} catch (IOException e) {
				Log.d(DEBUG_TAG, "Error parsing JSON");
			}

		} finally {
			// This was not in the Connecting to the Network tutorial code sample
			// but the HttpURLConnection manual page indicates we should probably do this
			if (conn != null)
			{
				conn.disconnect();
			}
			// This can actually throw an IOException,
			// so close the HttpURLConnection first.
			if (inputStream != null)
			{
				try
				{
					inputStream.close();

				} catch (IOException e) {
					Log.e(DEBUG_TAG, "Error closing InputStream");
				}
			}
		}
	}

	public String readIt(InputStream inputStream) throws IOException
	{
        /*
         * There are many ways this can be accomplished.
         */
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
		{
			result += line;
		}

		inputStream.close();
		return result;
	}


	public void parseIt(String result) throws IOException
	{
		RestDBHandler dbHandler = new RestDBHandler(this, null, null, 1);

		final boolean SUCCESS = true;
		final boolean FAILURE = false;

		String category = "";
		String name = "";
		double price = 0;
		String description = "";
		String data = "";

		try
		{
			JSONObject jsonRootObject = new JSONObject(result);
			JSONArray jsonArray = jsonRootObject.optJSONArray("items");

			for(int i=0; i < jsonArray.length(); i++)
			{
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				category = jsonObject.optString("category").toString();
				name = jsonObject.optString("name").toString();
				price = Double.parseDouble(jsonObject.optString("price").toString());
				description = jsonObject.optString("description").toString();

				RestMenuItem item = new RestMenuItem(category, name, price, description);

				dbHandler.addItem(item);

				data += "Node"+i+" : \n Category= "+ category +" \n Name= "+ name +" \n Price= "+ price +" \n Description= "+ description +" \n ";
			}
			Log.d(DEBUG_TAG, data);
			Log.d(DEBUG_TAG, "Load Data Completed");

			// Notify the Content Provider of the change

			getContentResolver().notifyChange(MyContentProvider.CONTENT_URI, null);

			Intent localIntent = new Intent(Constants.BROADCAST_ACTION).putExtra(Constants.EXTENDED_DATA_STATUS, SUCCESS);

			LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);

		} catch (JSONException e) {
			Intent localIntent = new Intent(Constants.BROADCAST_ACTION).putExtra(Constants.EXTENDED_DATA_STATUS, FAILURE);

			LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);

			String message = e.getLocalizedMessage();
			Log.d(DEBUG_TAG, "JSON Error:" + message);
			Log.d(DEBUG_TAG, "JSON Error Additional Data:" + result);
		}
	}
}
