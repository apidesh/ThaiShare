package com.example.thaishare;

public class TableMembers {
	public static final String Table_Name = "members";
	public static final String Field_Id = "id";
	public static final String Field_FirstName = "firstname";
	public static final String Field_LastName = "lastname";
	public static final String Field_PhoneNumber = "phonenumber";
	public static final String Create_Table_Script = "create table "+Table_Name+" ("+Field_Id+" integer primary key autoincrement, "+Field_FirstName+" text not null, "+Field_LastName+" text, "+Field_PhoneNumber+" text)";
}
