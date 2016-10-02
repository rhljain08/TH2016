package com.b.trap.database;

import org.w3c.dom.Comment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_SCORE = "scoreboard";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_GAMES = "games";
	public static final String COLUMN_WON = "won";
	public static final String COLUMN_AVERAGE = "average";
	public static final String COLUMN_LOCK = "lock";

	private static final String DATABASE_NAME = "score.db";
	private static final int DATABASE_VERSION = 1;
	private SQLiteDatabase database;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_GAMES, MySQLiteHelper.COLUMN_WON };
	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_SCORE + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_GAMES
			+ " integer ," + COLUMN_WON
			+" integer," +  COLUMN_AVERAGE 
			+" float,"
			+ COLUMN_LOCK + " integer );";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
		loadInitial(database);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/*Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");*/
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);
		onCreate(db);
	}
	public void loadInitial(SQLiteDatabase database){
		ContentValues values = new ContentValues();
		for(int i =0 ;i<30;i++){
			values.put(MySQLiteHelper.COLUMN_ID, i);
			values.put(MySQLiteHelper.COLUMN_GAMES, 0);
			values.put(MySQLiteHelper.COLUMN_WON, 0);
			values.put(MySQLiteHelper.COLUMN_AVERAGE, 0);
			if(i == 0 || i == 15){
				values.put(MySQLiteHelper.COLUMN_LOCK, 1);
			}else
				values.put(MySQLiteHelper.COLUMN_LOCK, 0);
			database.insert(MySQLiteHelper.TABLE_SCORE, null,
					values);
		}
	} 
}

