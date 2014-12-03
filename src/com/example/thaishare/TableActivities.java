package com.example.thaishare;

public class TableActivities {
	public static final String Table_Name = "activities";
	public static final String Field_Id = "id";
	public static final String Field_Menus_Id = "menusid";
	public static final String Field_Members_Id = "membersid";
	public static final String Field_IsOrder = "isorder";
	public static final String Create_Table_Script = "create table "+Table_Name+" ("+Field_Id+" integer primary key autoincrement, "+Field_Menus_Id+" integer not null, "+Field_Members_Id+" integer not null, "+Field_IsOrder+" boolean)";
	public static final String SelectAlls_Script = "SELECT"+
			"activities.id AS activitiesid,"+
			"activities.isorder AS isorder,"+ 
			"members.id AS membersid, "+
			"members.firstname AS firstname, "+
			"members.lastname AS lastname, "+
			"menus.id AS menusid, "+
			"menus.name AS menusname, "+
			"menus.price AS menusprice"+
	"FROM "+
	"activities "+
	" LEFT JOIN members ON activities.membersid = members.id"+
	" LEFT JOIN menus ON activities.menusid = menus.id";
	
	public static final String Find_By_Id_Script = "SELECT"+
			"activities.id AS activitiesid,"+
			"activities.isorder AS isorder,"+ 
			"members.id AS membersid, "+
			"members.firstname AS firstname, "+
			"members.lastname AS lastname, "+
			"menus.id AS menusid, "+
			"menus.name AS menusname, "+
			"menus.price AS menusprice"+
	"FROM "+
	"activities "+
	" LEFT JOIN members ON activities.membersid = members.id"+
	" LEFT JOIN menus ON activities.menusid = menus.id"+
	" WHERE activities.id = ?";
	
	public static final String Find_By_Latest_Script = "SELECT"+
			"activities.id AS activitiesid,"+
			"activities.isorder AS isorder,"+ 
			"members.id AS membersid, "+
			"members.firstname AS firstname, "+
			"members.lastname AS lastname, "+
			"menus.id AS menusid, "+
			"menus.name AS menusname, "+
			"menus.price AS menusprice"+
	"FROM "+
	"activities "+
	" LEFT JOIN members ON activities.membersid = members.id"+
	" LEFT JOIN menus ON activities.menusid = menus.id"+
	"ORDER BY activities.id DESC LIMIT 1";
	
}
