
package com.b.trap.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.b.trap.HexagonView;

public class CommentsDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private LevelLock lLock;

	public CommentsDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	public void updateWins(int id,int moves){
		int wins = 0;
		int games = 0;
		float average = 0 ;
		int lockLevel = 0;
		String selectQuery = "SELECT * FROM "+ MySQLiteHelper.TABLE_SCORE+" WHERE "+ MySQLiteHelper.COLUMN_ID+"=?";
		Cursor c = database.rawQuery(selectQuery, new String[] { String.valueOf(id) });
		if (c.moveToFirst()) {
			games = c.getInt(c.getColumnIndex(MySQLiteHelper.COLUMN_GAMES));
			wins = c.getInt(c.getColumnIndex(MySQLiteHelper.COLUMN_WON));
			average = c.getFloat(c.getColumnIndex(MySQLiteHelper.COLUMN_AVERAGE));
		}
		average = (average*(wins)+ moves)/(wins + 1);
		//Log.d("RAJAT","average win : "+average);
		if(c != null)
			c.close();
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_GAMES, games+1);
		values.put(MySQLiteHelper.COLUMN_WON, wins+1);
		values.put(MySQLiteHelper.COLUMN_AVERAGE, average);
		// updating row
		database.update(MySQLiteHelper.TABLE_SCORE, values, MySQLiteHelper.COLUMN_ID + " = ?",
				new String[] { String.valueOf(id) });
		lLock =new LevelLock();
		lockLevel = lLock.checkLockLevel(id,wins+1);
		if(lockLevel>0)
			updateLock(lockLevel);
	}
	public void updateLossAverage(int id,int moves){
		int wins = 0;
		int games = 0;
		float average = 0.0f ;
		String selectQuery = "SELECT * FROM "+ MySQLiteHelper.TABLE_SCORE+" WHERE "+ MySQLiteHelper.COLUMN_ID+"=?";
		Cursor c = database.rawQuery(selectQuery, new String[] { String.valueOf(id) });
		if (c.moveToFirst()) {
			games = c.getInt(c.getColumnIndex(MySQLiteHelper.COLUMN_GAMES));
			wins = c.getInt(c.getColumnIndex(MySQLiteHelper.COLUMN_WON));
			average = c.getFloat(c.getColumnIndex(MySQLiteHelper.COLUMN_AVERAGE));
		}
		average = (average*(wins)+ moves)/(wins + 1);
		Log.d("RAJAT","average loss : "+average);
		if(c != null)
			c.close();
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_WON, games+1);
		values.put(MySQLiteHelper.COLUMN_AVERAGE, average);
		// updating row
		database.update(MySQLiteHelper.TABLE_SCORE, values, MySQLiteHelper.COLUMN_ID + " = ?",
				new String[] { String.valueOf(id) });
	}
	public void updateLoss(int id){
		int games = 0;
		String selectQuery = "SELECT * FROM "+ MySQLiteHelper.TABLE_SCORE+" WHERE "+ MySQLiteHelper.COLUMN_ID+"=?";
		Cursor c = database.rawQuery(selectQuery, new String[] { String.valueOf(id) });
		if (c.moveToFirst()) {
			games = c.getInt(c.getColumnIndex(MySQLiteHelper.COLUMN_GAMES));
		}
		if(c != null)
			c.close();
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_GAMES, games+1);
		// updating row
		database.update(MySQLiteHelper.TABLE_SCORE, values, MySQLiteHelper.COLUMN_ID + " = ?",
				new String[] { String.valueOf(id) });
	}
	
	public int[] fetchValue(int id){
		int values[] = new int [3];
		String selectQuery = "SELECT * FROM "+ MySQLiteHelper.TABLE_SCORE+" WHERE "+ MySQLiteHelper.COLUMN_ID+"=?";
		Cursor c = database.rawQuery(selectQuery, new String[] { String.valueOf(id) });
		if (c.moveToFirst()) {
			values[0] = c.getInt(c.getColumnIndex(MySQLiteHelper.COLUMN_GAMES));
			values[1] = c.getInt(c.getColumnIndex(MySQLiteHelper.COLUMN_WON));
			values[2] = c.getInt(c.getColumnIndex(MySQLiteHelper.COLUMN_AVERAGE));
		}
		if(c != null)
			c.close();
		return values;
	}
	public int[][] fetchAllValue(){
		int values[][] = new int [30][3];
		String selectQuery = "SELECT * FROM "+ MySQLiteHelper.TABLE_SCORE;
		Cursor c = database.rawQuery(selectQuery, null);
		int id= 0;
		if (c .moveToFirst()){
			while(!c.isAfterLast()){
				id =c.getInt(c.getColumnIndex(MySQLiteHelper.COLUMN_ID));
				values[id][0] = c.getInt(c.getColumnIndex(MySQLiteHelper.COLUMN_GAMES));
				values[id][1] = c.getInt(c.getColumnIndex(MySQLiteHelper.COLUMN_WON));
				values[id][2] = c.getInt(c.getColumnIndex(MySQLiteHelper.COLUMN_AVERAGE));
				c.moveToNext();
			}
		}
		if(c != null)
			c.close();
		return values;
	}
	public void updateLock(int id){
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_LOCK, 1);
		// updating row
		database.update(MySQLiteHelper.TABLE_SCORE, values, MySQLiteHelper.COLUMN_ID + " = ?",
				new String[] { String.valueOf(id) });
		HexagonView.callToast(id);
	}
	public int fetchLockValue(int id){
		int value = 0;
		String selectQuery = "SELECT * FROM "+ MySQLiteHelper.TABLE_SCORE+" WHERE "+ MySQLiteHelper.COLUMN_ID+"=?";
		Cursor c = database.rawQuery(selectQuery, new String[] { String.valueOf(id) });
		if(c != null && !c.isClosed()){
			if (c.moveToFirst()) {
				value = c.getInt(c.getColumnIndex(MySQLiteHelper.COLUMN_LOCK));
			}
	        c.close();
	    }
		return value;
	}
	
	public int fetchWinValue(int id){
		int value = 0;
		
		String selectQuery = "SELECT * FROM "+ MySQLiteHelper.TABLE_SCORE+" WHERE "+ MySQLiteHelper.COLUMN_ID+"=?";
		Cursor c = database.rawQuery(selectQuery, new String[] { String.valueOf(id) });
		if (c.moveToFirst()) {
			value = c.getInt(c.getColumnIndex(MySQLiteHelper.COLUMN_WON));
		}
		if(c != null)
			c.close();
		return value;
	}
	
	public List<TableColumns> fetchFirstHalf(){
		String selectQuery = "SELECT * FROM "+ MySQLiteHelper.TABLE_SCORE+ " LIMIT 15" ;
		Cursor c = database.rawQuery(selectQuery, null);
		List<TableColumns> columns = new ArrayList<TableColumns>();
		TableColumns column;
		if(c.moveToFirst()){
			while(!c.isAfterLast()){
				column =new TableColumns();
				column.setId(c.getInt(c.getColumnIndex(MySQLiteHelper.COLUMN_ID)));
				column.setGames(c.getInt(c.getColumnIndex(MySQLiteHelper.COLUMN_GAMES)));
				column.setWon(c.getInt(c.getColumnIndex(MySQLiteHelper.COLUMN_WON)));
				column.setAverage(c.getFloat(c.getColumnIndex(MySQLiteHelper.COLUMN_AVERAGE)));
				column.setLock(c.getInt(c.getColumnIndex(MySQLiteHelper.COLUMN_LOCK)));
				columns.add(column);
				c.moveToNext();
			}
		}
		if(c != null)
			c.close();
		return columns;
	}
	public List<TableColumns> fetchSecondtHalf(){
		String selectQuery = "SELECT * FROM "+ MySQLiteHelper.TABLE_SCORE+ " WHERE "+MySQLiteHelper.COLUMN_ID+" >= 15" ;
		Cursor c = database.rawQuery(selectQuery, null);
		List<TableColumns> columns = new ArrayList<TableColumns>();
		TableColumns column;
		if(c.moveToFirst()){
			while(!c.isAfterLast()){
				column =new TableColumns();
				column.setId(c.getInt(c.getColumnIndex(MySQLiteHelper.COLUMN_ID)));
				column.setGames(c.getInt(c.getColumnIndex(MySQLiteHelper.COLUMN_GAMES)));
				column.setWon(c.getInt(c.getColumnIndex(MySQLiteHelper.COLUMN_WON)));
				column.setAverage(c.getFloat(c.getColumnIndex(MySQLiteHelper.COLUMN_AVERAGE)));
				column.setLock(c.getInt(c.getColumnIndex(MySQLiteHelper.COLUMN_LOCK)));
				columns.add(column);
				c.moveToNext();
			}
		}
		if(c != null)
			c.close();
		return columns;
	}
}
