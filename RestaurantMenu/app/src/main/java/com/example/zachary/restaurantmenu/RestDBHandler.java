package com.example.zachary.restaurantmenu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Zachary on 3/9/2016.
 */
public class RestDBHandler extends SQLiteOpenHelper
{
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "restaurantmenu.db";
	private static final String TABLE_ITEMS = "items";

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_ITEMCAT = "_itemCat";
	public static final String COLUMN_ITEMNAME = "_itemName";
	public static final String COLUMN_ITEMPRICE = "_itemPrice";
	public static final String COLUMN_ITEMDESC = "_itemDesc";

	public RestDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
	{
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS
				+ " ("
					+ COLUMN_ID + " INTEGER PRIMARY KEY, "
					+ COLUMN_ITEMCAT + " TEXT, "
					+ COLUMN_ITEMNAME + " TEXT, "
					+ COLUMN_ITEMPRICE + " DOUBLE,"
					+ COLUMN_ITEMDESC + " TEXT"
				+ ")";

		db.execSQL(CREATE_ITEMS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		/*
			If this were a production DB, you would migrate your data to the new schema.
		 */

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
		onCreate(db);
	}

	public void addItem(RestMenuItem restmenuitem)
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_ITEMCAT, restmenuitem.get_prodCat());
		values.put(COLUMN_ITEMNAME, restmenuitem.get_prodName());
		values.put(COLUMN_ITEMPRICE, restmenuitem.get_prodPrice());
		values.put(COLUMN_ITEMDESC, restmenuitem.get_prodDesc());

		SQLiteDatabase db = this.getWritableDatabase();

		db.insert(TABLE_ITEMS, null, values);
		db.close();
	}

	public RestMenuItem findItem(String itemName)
	{
		String query = ("SELECT *"
							+ " FROM " + TABLE_ITEMS
							+ " WHERE " + COLUMN_ITEMNAME + " = \"" + itemName + "\"");

		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		RestMenuItem restmenuitem = new RestMenuItem();

		if (cursor.moveToFirst())
		{
			cursor.moveToFirst();
			restmenuitem.set_prodID(Integer.parseInt(cursor.getString(0)));
			restmenuitem.set_prodCat(cursor.getString(1));
			restmenuitem.set_prodName(cursor.getString(2));
			restmenuitem.set_prodPrice(Double.parseDouble(cursor.getString(3)));
			restmenuitem.set_prodDesc(cursor.getString(4));
		}
		else
		{
			restmenuitem = null;
		}

		cursor.close();

		db.close();
		return(restmenuitem);
	}

	/*
	public void updateItem(RestMenuItem restmenuitem)
	{

	}
	*/

	public boolean deleteItem (String itemName)
	{
		boolean result = false;

		String query = "SELECT *"
						+ " FROM " + TABLE_ITEMS
						+ " WHERE " + COLUMN_ITEMNAME + " = \"" + itemName + "\"";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		RestMenuItem restmenuitem = new RestMenuItem();

		if (cursor.moveToFirst())
		{
			restmenuitem.set_prodID(Integer.parseInt(cursor.getString(0)));

			db.delete(TABLE_ITEMS,
						COLUMN_ID + " = ?",
						new String[]{String.valueOf(restmenuitem.get_prodID())});

			result = true;
		}

		cursor.close();

		db.close();
		return result;
	}
}
