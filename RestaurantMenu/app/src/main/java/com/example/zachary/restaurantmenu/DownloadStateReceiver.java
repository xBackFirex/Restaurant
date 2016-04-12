package com.example.zachary.restaurantmenu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Zachary on 4/12/2016.
 * Project: RestaurantMenu
 */
public class DownloadStateReceiver extends BroadcastReceiver
{
	public DownloadStateReceiver()
	{

	}

	public void onReceive(Context context, Intent intent)
	{
		if (intent.getExtras().getBoolean(Constants.EXTENDED_DATA_STATUS) == false) {
			Toast.makeText(context, "Load Failed.", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(context, "Load Completed.", Toast.LENGTH_LONG).show();

		}
	}
}
