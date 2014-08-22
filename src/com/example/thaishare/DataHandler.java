package com.example.thaishare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHandler {
	public static final String NAME = "name";
	public static final String PRICE = "price";
	public static final String TABLE_NAME = "menu";
	public static final String DATABASE_NAME = "thaisharedb";
	public static final int DATABASE_VERSION = 1;
	public static final String CREATE_TABLE = "create table menu (name text not null, price text not null)";
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
			   String sql = String.format(CREATE_TABLE);		
			   db.execSQL(sql);
			}catch(SQLException e)
			{
				e.printStackTrace();
			}
		}

	
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
		   String sql = String.format(DROP_TABLE, TABLE_NAME);		
		   db.execSQL(sql);
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
	
	public long insertData(String name, String price)
	{		
		ContentValues content = new ContentValues();
		content.put(NAME, name);
		content.put(PRICE, price);
		return db.insertOrThrow(TABLE_NAME, null, content);
	}
	
	
	public Cursor returnData()
	{
		return db.query(TABLE_NAME, new String[]{NAME, PRICE},null,null,null,null,null);
		
	}
	
	
}
