package com.example.thaishare;

import java.io.Serializable;

public class MenuItemData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int _id;
	private String _name;
	private double _price;
	private int _columnsIndex;
	
	public int getId() {
		return _id;
	}
	public void setId(int id) {
		this._id = id;
	}
	public String getName() {
		return _name;
	}
	public void setName(String name) {
		this._name = name;
	}
	public double getPrice() {
		return _price;
	}
	public void setPrice(double price) {
		this._price = price;
	}
	public int getColumnsIndex() {
		return _columnsIndex;
	}
	public void setColumnsIndex(int columnsIndex) {
		this._columnsIndex = columnsIndex;
	}
}
