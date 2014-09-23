package com.example.thaishare;

public class TableMenus {
	public static final String Table_Name = "menus";
	public static final String Field_Id = "id";
	public static final String Field_Name = "name";
	public static final String Field_Price = "price";
	public static final String Create_Table_Script = "create table "+Table_Name+" ("+Field_Id+" integer primary key autoincrement, "+Field_Name+" text not null, "+Field_Price+" real not null)";
}
