package com.example.zachary.restaurantmenu;

/**
 * Created by Zachary on 3/9/2016.
 * Project: RestaurantMenu
 */
public class RestMenuItem
{
	private int _itemID;
	private String _itemCat;
	private String _itemName;
	private double _itemPrice;
	private String _itemDesc;

	public RestMenuItem()
	{
	}

	public RestMenuItem(String _itemCat, String _itemName, double _itemPrice, String _itemDesc)
	{
		this._itemCat = _itemCat;
		this._itemName = _itemName;
		this._itemPrice = _itemPrice;
		this._itemDesc = _itemDesc;
	}

	public RestMenuItem(int _itemID, String _itemCat, String _itemName, double _itemPrice, String _itemDesc)
	{
		this._itemID = _itemID;
		this._itemCat = _itemCat;
		this._itemName = _itemName;
		this._itemPrice = _itemPrice;
		this._itemDesc = _itemDesc;
	}

	public int get_itemID()
	{
		return _itemID;
	}

	public void set_itemID(int _itemID)
	{
		this._itemID = _itemID;
	}

	public String get_itemCat()
	{
		return _itemCat;
	}

	public void set_itemCat(String _itemCat)
	{
		this._itemCat = _itemCat;
	}

	public String get_itemName()
	{
		return _itemName;
	}

	public void set_itemName(String _itemName)
	{
		this._itemName = _itemName;
	}

	public double get_itemPrice()
	{
		return _itemPrice;
	}

	public void set_itemPrice(double _itemPrice)
	{
		this._itemPrice = _itemPrice;
	}

	public String get_itemDesc()
	{
		return _itemDesc;
	}

	public void set_itemDesc(String _itemDesc)
	{
		this._itemDesc = _itemDesc;
	}
}
