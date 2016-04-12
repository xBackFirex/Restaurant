package com.example.zachary.restaurantmenu;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.zachary.restaurantmenu.provider.MyContentProvider;

public class ListerActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor>
{
	private static final int LIST_LOADER = 0;
	private SimpleCursorAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.items_list);

		String[] mFromColumns = {
				RestDBHandler.COLUMN_ID,
				RestDBHandler.COLUMN_ITEMCAT,
				RestDBHandler.COLUMN_ITEMNAME,
				RestDBHandler.COLUMN_ITEMPRICE,
				RestDBHandler.COLUMN_ITEMDESC
		};

		int[] mToFields = {
				R.id.idOfItem,
				R.id.catOfItem,
				R.id.nameOfItem,
				R.id.priceOfItem,
				R.id.descOfItem
		};

		mAdapter = new SimpleCursorAdapter(
				this,
				R.layout.item_row,
				null,
				mFromColumns,
				mToFields,
				0
		);
		ListView mListView = (ListView) findViewById(R.id.ListView);
		mListView.setAdapter (mAdapter);

		getLoaderManager().initLoader(LIST_LOADER, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args)
	{
		switch (id)
		{
			case LIST_LOADER:
				return new CursorLoader(
						this,
						MyContentProvider.CONTENT_URI,
						RestDBHandler.PROJECTION,
						null,
						null,
						null
				);
			default:
				return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
	{
		if (cursor != null && cursor.getCount() > 0) {
			mAdapter.changeCursor(cursor);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader)
	{
		mAdapter.changeCursor(null);
	}
}
