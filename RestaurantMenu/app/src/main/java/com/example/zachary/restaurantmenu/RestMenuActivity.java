package com.example.zachary.restaurantmenu;

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
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

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
		double price = Double.parseDouble(itemPrice.getText().toString());

		if (itemCatSelected == null || itemPrice.getText().toString() == null || itemName.getText().toString() == null || itemDesc.getText().toString() == null)
		{
			Toast.makeText(RestMenuActivity.this, "Please enter values for all fields", Toast.LENGTH_LONG).show();
		}
		else
		{
			RestMenuItem restmenuitem = new RestMenuItem(
					itemCatSelected,
					itemName.getText().toString(),
					price,
					itemDesc.getText().toString());

			dbHandler.addItem(restmenuitem);
			itemID.setText(R.string.notRecAdd);
			itemCat.setSelection(0);
			itemName.setText("");
			itemPrice.setText("");
			itemDesc.setText("");
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

	public void updateItem (View view)
	{

	}

	public void deleteItem (View view)
	{

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
