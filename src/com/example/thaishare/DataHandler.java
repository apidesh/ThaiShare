package com.example.thaishare;

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
			   
			}catch(SQLException e)
			{
				e.printStackTrace();
			}
		}

	
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
		   String sqlTableMenus = String.format(DROP_TABLE, TableMenus.Table_Name);		
		   db.execSQL(sqlTableMenus);
		   
		   String sqlTableMemberss = String.format(DROP_TABLE, TableMembers.Table_Name);		
		   db.execSQL(sqlTableMemberss);
		   
		   onCreate(db);
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
	
	
	
}
