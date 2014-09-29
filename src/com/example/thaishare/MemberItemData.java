package com.example.thaishare;

import java.io.Serializable;

public class MemberItemData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int _id;
	private String _firstName;
	private String _lastName;
	private String _phoneNumber;
	private int _rowsIndex;
	
	public int getId() {
		return _id;
	}
	public void setId(int id) {
		this._id = id;
	}
	public String getFirstName() {
		return _firstName;
	}
	public void setFirstName(String firstName) {
		this._firstName = firstName;
	}
	public String getLastName() {
		return _lastName;
	}
	public void setLastName(String lastName) {
		this._lastName = lastName;
	}
	public String getPhoneNUmber() {
		return _lastName;
	}
	public void setPhoneNumber(String phoneNumber) {
		this._phoneNumber = phoneNumber;
	}
	public int getRowsIndex() {
		return _rowsIndex;
	}
	public void setRowsIndex(int rowsIndex) {
		this._rowsIndex = rowsIndex;
	}
}
