package com.example.zachary.restaurantmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RestMenuActivity extends AppCompatActivity
{
	// Get input
	EditText itemID;
	Spinner itemCat;
	EditText itemName;
	EditText itemPrice;
	EditText itemDesc;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rest_menu);

		// Set values
		itemID = (EditText) findViewById(R.id.itemID);
		itemCat = (Spinner) findViewById(R.id.itemCat);
		itemName = (EditText) findViewById(R.id.itemName);
		itemPrice = (EditText) findViewById(R.id.itemPrice);
		itemDesc = (EditText) findViewById(R.id.itemDesc);
	}

	public void addItem (View view)
	{
		RestDBHandler dbHandler = new RestDBHandler(this, null, null, 1);

		String itemCatSelected = itemCat.getSelectedItem().toString();

		if (itemCatSelected == "Not Assigned" || String.valueOf(itemPrice.getText().toString()) == "" || String.valueOf(itemPrice.getText().toString()) == "Not Assigned" || String.valueOf(itemName.getText().toString()) == "" || String.valueOf(itemName.getText().toString()) == "Not Assigned" || String.valueOf(itemDesc.getText().toString()) == "" || String.valueOf(itemDesc.getText().toString()) == "Not Assigned")
		{
			Toast.makeText(RestMenuActivity.this, "Please enter values for all fields", Toast.LENGTH_LONG).show();
		}
		else
		{
			try {
				double price = Double.parseDouble(itemPrice.getText().toString());

				RestMenuItem restmenuitem = new RestMenuItem(
						itemCatSelected,
						itemName.getText().toString(),
						price,
						itemDesc.getText().toString());

				dbHandler.addItem(restmenuitem);
				//itemID.setText("");
				//itemCat.setSelection(0);
				//itemName.setText("");
				//itemPrice.setText("");
				//itemDesc.setText("");

				Toast.makeText(RestMenuActivity.this, R.string.notRecAdd, Toast.LENGTH_LONG).show();
			} catch (Exception e)
			{
				Toast.makeText(RestMenuActivity.this, "Error adding item, please check values", Toast.LENGTH_LONG).show();
			}
		}
	}

	public void findItem (View view)
	{
		RestDBHandler dbHandler = new RestDBHandler(this, null, null, 1);

		RestMenuItem restmenuitem = dbHandler.findItem(itemName.getText().toString());

		if (restmenuitem != null)
		{
			itemID.setText(String.valueOf(restmenuitem.get_prodID()));

			itemName.setText(String.valueOf(restmenuitem.get_prodName()));
			itemPrice.setText(String.valueOf(restmenuitem.get_prodPrice()));
			itemDesc.setText(String.valueOf(restmenuitem.get_prodDesc()));

			switch (String.valueOf(restmenuitem.get_prodCat()))
			{
				case "Appetizers":
					itemCat.setSelection(1);
					break;
				case "Entrees":
					itemCat.setSelection(2);
					break;
				case "Sides":
					itemCat.setSelection(3);
					break;
				case "Desserts":
					itemCat.setSelection(4);
					break;
				case "Drinks":
					itemCat.setSelection(5);
					break;
				default:
					itemCat.setSelection(0);
					break;
			}
		}
		else
		{
			Toast.makeText(RestMenuActivity.this, R.string.errNMF, Toast.LENGTH_LONG).show();
		}
	}

	public void findAllItem (View view)
	{
		Intent intent = new Intent(this, ListerActivity.class);
		startActivity(intent);
	}

	/*
	public void updateItem (View view)
	{

	}
	*/

	public void deleteItem (View view)
	{
		RestDBHandler dbHandler = new RestDBHandler(this, null, null, 1);

		boolean result = dbHandler.deleteItem(itemName.getText().toString());

		if (result)
		{
			itemID.setText("");
			itemCat.setSelection(0);
			itemName.setText("");
			itemPrice.setText("");
			itemDesc.setText("");

			Toast.makeText(RestMenuActivity.this, R.string.notRecDel, Toast.LENGTH_LONG).show();
		}
		else {
			itemID.setText("");
			itemCat.setSelection(0);

			itemPrice.setText("");
			itemDesc.setText("");

			Toast.makeText(RestMenuActivity.this, R.string.errNMF, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_rest_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
