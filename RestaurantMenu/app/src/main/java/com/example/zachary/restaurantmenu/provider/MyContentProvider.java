package com.example.zachary.restaurantmenu.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.example.zachary.restaurantmenu.RestDBHandler;

public class MyContentProvider extends ContentProvider
{
	private static final String AUTHORITY = "com.example.zachary.restaurantmenu.provider.MyContentProvider";
	private static final String TABLE_ITEMS = "items";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_ITEMS);

	public static final int ITEMS = 1;
	public static final int ITEMS_ID = 2;

	private RestDBHandler myDB;

	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	static
	{
		sURIMatcher.addURI(AUTHORITY, TABLE_ITEMS, ITEMS);
		sURIMatcher.addURI(AUTHORITY, TABLE_ITEMS + "/#", ITEMS_ID);
	}
	public MyContentProvider()
	{
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs)
	{
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = myDB.getWritableDatabase();
		int rowsDeleted = 0;

		switch (uriType) {
			case ITEMS:
				rowsDeleted = sqlDB.delete(RestDBHandler.TABLE_ITEMS,
						selection,
						selectionArgs);
				break;

			case ITEMS_ID:
				String id = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsDeleted = sqlDB.delete(RestDBHandler.TABLE_ITEMS,
							RestDBHandler.COLUMN_ID + "=" + id,
							null);
				} else {
					rowsDeleted = sqlDB.delete(RestDBHandler.TABLE_ITEMS,
							RestDBHandler.COLUMN_ID + "=" + id
									+ " and " + selection,
							selectionArgs);
				}
				break;
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public String getType(Uri uri)
	{
		// TODO: Implement this to handle requests for the MIME type of the data
		// at the given URI.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public Uri insert(Uri uri, ContentValues values)
	{
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = myDB.getWritableDatabase();
		long id = 0;
		switch (uriType)
		{
			case ITEMS:
				id = sqlDB.insert(RestDBHandler.TABLE_ITEMS, null, values);
				break;
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(TABLE_ITEMS + "/" + id);
	}

	@Override
	public boolean onCreate()
	{
		myDB = new RestDBHandler(getContext(), null, null, 1);
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
						String[] selectionArgs, String sortOrder)
	{
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(RestDBHandler.TABLE_ITEMS);

		int uriType = sURIMatcher.match(uri);

		switch (uriType)
		{
			case ITEMS_ID:
				queryBuilder.appendWhere(RestDBHandler.COLUMN_ID + "=" + uri.getLastPathSegment());
				break;
			case ITEMS:
				break;
			default:
				throw new IllegalArgumentException("Unknown URI");
		}

		Cursor cursor = queryBuilder.query(myDB.getReadableDatabase(),projection, selection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
					  String[] selectionArgs)
	{
		/*
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = myDB.getWritableDatabase();
		int rowsUpdated = 0;

		switch (uriType) {
			case ITEMS:
				rowsUpdated =
						sqlDB.update(RestDBHandler.TABLE_ITEMS,
								values,
								selection,
								selectionArgs);
				break;
			case ITEMS_ID:
				String id = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsUpdated =
							sqlDB.update(RestDBHandler.TABLE_ITEMS,
									values,
									RestDBHandler.COLUMN_ID + "=" + id,
									null);
				} else {
					rowsUpdated =
							sqlDB.update(RestDBHandler.TABLE_ITEMS,
									values,
									RestDBHandler.COLUMN_ID + "=" + id
											+ " and "
											+ selection,
									selectionArgs);
				}
				break;
			default:
				throw new IllegalArgumentException("Unknown URI: " +
						uri);
		}
		getContext().getContentResolver().notifyChange(uri,
				null);
		return rowsUpdated;
		*/
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
