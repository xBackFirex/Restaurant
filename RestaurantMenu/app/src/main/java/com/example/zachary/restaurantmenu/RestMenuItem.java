package com.example.zachary.restaurantmenu;

/**
 * Created by Zachary on 3/9/2016.
 * Project: RestaurantMenu
 */
public class RestMenuItem
{
	private int _prodID;
	private String _prodCat;
	private String _prodName;
	private double _prodPrice;
	private String _prodDesc;

	public RestMenuItem()
	{
	}

	public RestMenuItem(String _prodCat, String _prodName, double _prodPrice, String _prodDesc)
	{
		this._prodCat = _prodCat;
		this._prodName = _prodName;
		this._prodPrice = _prodPrice;
		this._prodDesc = _prodDesc;
	}

	public RestMenuItem(int _prodID, String _prodCat, String _prodName, double _prodPrice, String _prodDesc)
	{
		this._prodID = _prodID;
		this._prodCat = _prodCat;
		this._prodName = _prodName;
		this._prodPrice = _prodPrice;
		this._prodDesc = _prodDesc;
	}

	public int get_prodID()
	{
		return _prodID;
	}

	public void set_prodID(int _prodID)
	{
		this._prodID = _prodID;
	}

	public String get_prodCat()
	{
		return _prodCat;
	}

	public void set_prodCat(String _prodCat)
	{
		this._prodCat = _prodCat;
	}

	public String get_prodName()
	{
		return _prodName;
	}

	public void set_prodName(String _prodName)
	{
		this._prodName = _prodName;
	}

	public double get_prodPrice()
	{
		return _prodPrice;
	}

	public void set_prodPrice(double _prodPrice)
	{
		this._prodPrice = _prodPrice;
	}

	public String get_prodDesc()
	{
		return _prodDesc;
	}

	public void set_prodDesc(String _prodDesc)
	{
		this._prodDesc = _prodDesc;
	}
}
