package com.example.zachary.restaurantmenu;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.zachary.restaurantmenu.provider.MyContentProvider;

/**
 * Created by Zachary on 3/9/2016.
 */
public class RestDBHandler extends SQLiteOpenHelper
{
	private ContentResolver myCR;

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "restaurantmenu.db";
	public static final String TABLE_ITEMS = "items";

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_ITEMCAT = "_itemCat";
	public static final String COLUMN_ITEMNAME = "_itemName";
	public static final String COLUMN_ITEMPRICE = "_itemPrice";
	public static final String COLUMN_ITEMDESC = "_itemDesc";

	public static final String[] PROJECTION =
			{
					COLUMN_ID,
					COLUMN_ITEMCAT,
					COLUMN_ITEMNAME,
					COLUMN_ITEMPRICE,
					COLUMN_ITEMDESC
			};

	public RestDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
	{
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
		myCR = context.getContentResolver();
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
		values.put(COLUMN_ITEMCAT, restmenuitem.get_itemCat());
		values.put(COLUMN_ITEMNAME, restmenuitem.get_itemName());
		values.put(COLUMN_ITEMPRICE, restmenuitem.get_itemPrice());
		values.put(COLUMN_ITEMDESC, restmenuitem.get_itemDesc());

		myCR.insert(MyContentProvider.CONTENT_URI, values);
	}

	public RestMenuItem findItem(String itemName)
	{
		String[] projection = {COLUMN_ID, COLUMN_ITEMCAT, COLUMN_ITEMNAME, COLUMN_ITEMPRICE, COLUMN_ITEMDESC};

		String selection = COLUMN_ITEMNAME + " = \"" + itemName + "\"";

		Cursor cursor = myCR.query(MyContentProvider.CONTENT_URI, projection, selection, null, null);

		RestMenuItem restmenuitem = new RestMenuItem();

		if (cursor.moveToFirst())
		{
			cursor.moveToFirst();
			restmenuitem.set_itemID(Integer.parseInt(cursor.getString(0)));
			restmenuitem.set_itemCat(cursor.getString(1));
			restmenuitem.set_itemName(cursor.getString(2));
			restmenuitem.set_itemPrice(Double.parseDouble(cursor.getString(3)));
			restmenuitem.set_itemDesc(cursor.getString(4));
		}
		else
		{
			restmenuitem = null;
		}

		return restmenuitem;
	}

	/*
	public void updateItem(RestMenuItem restmenuitem)
	{

	}
	*/

	public boolean deleteItem (String itemName)
	{
		boolean result = false;

		String selection = COLUMN_ITEMNAME + " = \"" + itemName + "\"";

		int rowsDeleted = myCR.delete(MyContentProvider.CONTENT_URI, selection, null);

		if (rowsDeleted > 0)
		{
			result = true;
		}
		return result;
	}
}
