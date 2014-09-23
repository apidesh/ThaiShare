package com.example.thaishare;

public class TableActivities {
	public static final String Table_Name = "activities";
	public static final String Field_Id = "id";
	public static final String Field_Menus_Id = "menusid";
	public static final String Field_Members_Id = "membersid";
	public static final String Field_IsOrder = "isorder";
	public static final String Create_Table_Script = "create table "+Table_Name+" ("+Field_Id+" integer primary key autoincrement, "+Field_Menus_Id+" integer not null, "+Field_Members_Id+" integer not null, "+Field_IsOrder+" boolean)";
}
