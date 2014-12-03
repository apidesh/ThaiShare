package com.example.thaishare;

import android.R.bool;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHandler {
	public static final String DATABASE_NAME = "thaisharedb";
	public static final int DATABASE_VERSION = 1;
	public static String DROP_TABLE = "DROP TABLE IF EXISTS %s";
	
	DataBaseHelper dbhelper;
	Context ctx;
	SQLiteDatabase db;
	public DataHandler(Context ctx)
	{
		this.ctx = ctx;
		dbhelper = new DataBaseHelper(ctx);
	}
	
	
	
	private static class DataBaseHelper extends SQLiteOpenHelper
	{

		public DataBaseHelper(Context ctx) {
			super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}
		
			
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			try
			{
			   String sqlTableMenus = String.format(TableMenus.Create_Table_Script);		
			   db.execSQL(sqlTableMenus);
			   
			   String sqlTableMembers = String.format(TableMembers.Create_Table_Script);		
			   db.execSQL(sqlTableMembers);
			   
			   String sqlTableActivities = String.format(TableActivities.Create_Table_Script);		
			   db.execSQL(sqlTableActivities);
			   
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

	
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			try
			{
			// TODO Auto-generated method stub
		   String sqlTableMenus = String.format(DROP_TABLE, TableMenus.Table_Name);		
		   db.execSQL(sqlTableMenus);
		   
		   String sqlTableMemberss = String.format(DROP_TABLE, TableMembers.Table_Name);		
		   db.execSQL(sqlTableMemberss);
		   
		   String sqlTableActivities = String.format(DROP_TABLE, TableActivities.Table_Name);		
		   db.execSQL(sqlTableActivities);
		   
		   onCreate(db);
		   
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	public DataHandler open()
	{
		db = dbhelper.getReadableDatabase();
		return this;
	}
	
	public void close()
	{
		dbhelper.close();
	}
	
	
	/*Menus*/
	public long insertMenusData(String name, String price)
	{		
		ContentValues content = new ContentValues();
		content.put(TableMenus.Field_Name, name);
		content.put(TableMenus.Field_Price, price);
		return db.insertOrThrow(TableMenus.Table_Name, null, content);
	}

	public Cursor returnMenusData()
	{
		return db.query(TableMenus.Table_Name, new String[]{TableMenus.Field_Id, TableMenus.Field_Name, TableMenus.Field_Price},null,null,null,null,null);
	}

	public Cursor findMenusById(int id)
	{
       return db.query(TableMenus.Table_Name, new String[]{TableMenus.Field_Id, TableMenus.Field_Name, TableMenus.Field_Price},TableMenus.Field_Id+"=?", new String[] { Integer.toString(id) } ,null,null,null);	
	}
	
	public void UpdateMenusData(int id, String name, String price){
		ContentValues newValues = new ContentValues();
		newValues.put(TableMenus.Field_Name, name);
		newValues.put(TableMenus.Field_Price, price);
		db.update(TableMenus.Table_Name, newValues, TableMenus.Field_Id+"="+id, null);
	}
	
	/*Members*/
	
	public long insertMemberssData(String firstName, String lastName, String phoneNumber)
	{		
		ContentValues content = new ContentValues();
		content.put(TableMembers.Field_FirstName, firstName);
		content.put(TableMembers.Field_LastName, lastName);
		content.put(TableMembers.Field_PhoneNumber, phoneNumber);
		return db.insertOrThrow(TableMembers.Table_Name, null, content);
	}
	
	public Cursor returnMembersData()
	{
		return db.query(TableMembers.Table_Name, new String[]{TableMembers.Field_Id, TableMembers.Field_FirstName, TableMembers.Field_LastName, TableMembers.Field_PhoneNumber},null,null,null,null,null);
	}
	
	public Cursor findMembersById(int id)
	{
       return db.query(TableMembers.Table_Name, new String[]{TableMembers.Field_Id, TableMembers.Field_FirstName, TableMembers.Field_LastName, TableMembers.Field_PhoneNumber},TableMembers.Field_Id+"=?", new String[] { Integer.toString(id) } ,null,null,null);	
	}
	
	public void UpdateMembersData(int id, String firstName, String lastName, String phoneNumber){
		ContentValues newValues = new ContentValues();
		newValues.put(TableMembers.Field_FirstName, firstName);
		newValues.put(TableMembers.Field_LastName, lastName);
		newValues.put(TableMembers.Field_PhoneNumber, phoneNumber);
		db.update(TableMembers.Table_Name, newValues, TableMembers.Field_Id+"="+id, null);
	}
	
	
	/*Activities*/
	public long insertActivitiesData(boolean isOrder, int membersId, int menusId)
	{		
		ContentValues content = new ContentValues();
		content.put(TableActivities.Field_IsOrder, isOrder);
		content.put(TableActivities.Field_Members_Id, membersId);
		content.put(TableActivities.Field_Menus_Id, menusId);
		return db.insertOrThrow(TableActivities.Table_Name, null, content);
	}
	
	public Cursor returnActivitiesData()
	{
		//return db.query(TableActivities.Table_Name, new String[]{TableActivities.Field_Id, TableActivities.Field_IsOrder, TableActivities.Field_Members_Id, TableActivities.Field_Menus_Id},null,null,null,null,null);
		
		return db.rawQuery(TableActivities.SelectAlls_Script, null);
	}
	public Cursor findActivitiesById(int id)
	{
		return db.rawQuery(TableActivities.Find_By_Id_Script, new String[] { Integer.toString(id) });
      // return db.query(TableMembers.Table_Name, new String[]{TableMembers.Field_Id, TableMembers.Field_FirstName, TableMembers.Field_LastName, TableMembers.Field_PhoneNumber},TableMembers.Field_Id+"=?", new String[] { Integer.toString(id) } ,null,null,null);	
	}
	public Cursor findActivitiesByLatest()
	{
		return db.rawQuery(TableActivities.Find_By_Latest_Script, null);
      // return db.query(TableMembers.Table_Name, new String[]{TableMembers.Field_Id, TableMembers.Field_FirstName, TableMembers.Field_LastName, TableMembers.Field_PhoneNumber},TableMembers.Field_Id+"=?", new String[] { Integer.toString(id) } ,null,null,null);	
	}
	public void UpdateActivitiesData(int id, boolean isOrder, int membersId, int menusId){
		
		ContentValues newValues = new ContentValues();
		newValues.put(TableActivities.Field_IsOrder, isOrder);
		newValues.put(TableActivities.Field_Members_Id, membersId);
		newValues.put(TableActivities.Field_Menus_Id, menusId);
		db.update(TableActivities.Table_Name, newValues, TableActivities.Field_Id+"="+id, null);
	}
}
