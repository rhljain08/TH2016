package com.b.trap;

import java.math.BigDecimal;
import java.util.List;

import com.b.trap.database.TableColumns;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class FirstHalfAdapter extends BaseAdapter {
	List<TableColumns> myList1 = null;
	List<TableColumns> myList2 = null;
	public FirstHalfAdapter(List<TableColumns> list1 , List<TableColumns> list2) {
		// TODO Auto-generated constructor stub
		myList1 = list1;
		myList2 = list2;
	}
	
	private class ViewHolder 
    {
		TextView level ,oneBeePlayed ,oneBeeWon ,oneBeeAverage;
	   ImageView oneBeePlayedImage,  oneBeeWonImage, oneBeeAverageImage;
	   TextView twoBeePlayed ,twoBeeWon ,twoBeeAverage;
		ImageView twoBeePlayedImage,  twoBeeWonImage, twoBeeAverageImage;
	    
    };
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return myList1.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (view == null) {
			view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_column1, null);
        }
		
		ViewHolder holder =new ViewHolder();
		
		holder.level = (TextView) view.findViewById(R.id.level);
		holder.oneBeePlayed = (TextView) view.findViewById(R.id.one_bee_played);
		holder.oneBeeWon = (TextView) view.findViewById(R.id.one_bee_won);
		holder.oneBeeAverage = (TextView) view.findViewById(R.id.one_bee_average);
	    holder.oneBeePlayedImage =(ImageView) view.findViewById(R.id.one_bee_game_image);
	    holder.oneBeeWonImage =(ImageView) view.findViewById(R.id.one_bee_won_image);
	    holder.oneBeeAverageImage = (ImageView) view.findViewById(R.id.one_bee_average_image);
	    int levelCount = myList1.get(position).getId() +1;
	      int oneBeePlayedCount = myList1.get(position).getGames();
	      int oneBeeWonCount = myList1.get(position).getWon();
	      float oneBeeAverageCount = myList1.get(position).getAverage();
	      int lock = myList1.get(position).getLock();
	      BigDecimal bd = new BigDecimal(Float.toString(oneBeeAverageCount));
	        bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
	        holder.twoBeePlayed = (TextView) view.findViewById(R.id.two_bee_played);
			holder.twoBeeWon = (TextView) view.findViewById(R.id.two_bee_won);
			holder.twoBeeAverage = (TextView) view.findViewById(R.id.two_bee_average);
			
			holder.twoBeePlayedImage =(ImageView) view.findViewById(R.id.two_bee_game_image);
			holder.twoBeeWonImage =(ImageView) view.findViewById(R.id.two_bee_won_image);
			holder.twoBeeAverageImage = (ImageView) view.findViewById(R.id.two_bee_average_image);
			// Extract properties from cursor
			int twoBeePlayedCount = myList2.get(position).getGames();
			int twoBeeWonCount = myList2.get(position).getWon();
			float twoBeeAverageCount = myList2.get(position).getAverage();
			int lock2 = myList2.get(position).getLock();
			BigDecimal bd2 = new BigDecimal(Float.toString(twoBeeAverageCount));
			bd2 = bd2.setScale(1, BigDecimal.ROUND_HALF_UP);
	      // Populate fields with extracted properties
	      //level.setId(levelCount);
	        holder.level.setText(String.valueOf(levelCount));
	      if(lock == 0){
	    	  holder.oneBeePlayedImage.setVisibility(View.VISIBLE);
	    	  holder.oneBeeWonImage.setVisibility(View.VISIBLE);
	    	  holder.oneBeeAverageImage.setVisibility(View.VISIBLE);
	    	  holder.oneBeePlayed.setVisibility(View.GONE);
	    	  holder.oneBeeWon.setVisibility(View.GONE);
	    	  holder.oneBeeAverage.setVisibility(View.GONE);
	    	  /*holder.oneBeePlayed.setCompoundDrawablesWithIntrinsicBounds(
	                    R.drawable.level_lock, 0, 0, 0);
	    	  holder.oneBeeWon.setCompoundDrawablesWithIntrinsicBounds(
	                    R.drawable.level_lock, 0, 0, 0);
	    	  holder.oneBeeAverage.setCompoundDrawablesWithIntrinsicBounds(
	                    R.drawable.level_lock, 0, 0, 0);*/
	    	  /*holder.oneBeeAverage.setBackgroundResource(R.drawable.level_lock);
	    	  holder.oneBeeWon.setBackgroundResource(R.drawable.level_lock);
	    	  holder.oneBeePlayed.setBackgroundResource(R.drawable.level_lock);*/
	    	  /*holder.oneBeePlayed.setText("Lock");
	    	  holder.oneBeeWon.setText("Lock");
	    	  holder.oneBeeAverage.setText("Lock");*/
	      }else{
	    	 /* holder.oneBeeAverage.setBackgroundResource(R.color.wallet_bright_foreground_disabled_holo_light);
	    	  holder.oneBeeWon.setBackgroundResource(R.color.wallet_bright_foreground_disabled_holo_light);
	    	  holder.oneBeePlayed.setBackgroundResource(R.color.wallet_bright_foreground_disabled_holo_light);*/
	    	  holder.oneBeePlayed.setVisibility(View.VISIBLE);
	    	  holder.oneBeeWon.setVisibility(View.VISIBLE);
	    	  holder.oneBeeAverage.setVisibility(View.VISIBLE);
	    	  holder.oneBeePlayedImage.setVisibility(View.GONE);
	    	  holder.oneBeeWonImage.setVisibility(View.GONE);
	    	  holder.oneBeeAverageImage.setVisibility(View.GONE);
	    	  holder.oneBeePlayed.setText(String.valueOf(oneBeePlayedCount));
	    	  holder.oneBeeWon.setText(String.valueOf(oneBeeWonCount));
	    	  holder.oneBeeAverage.setText(String.valueOf(bd.floatValue()));
	    	  
	      }
			// Populate fields with extracted properties

			if(lock2 == 0){
				holder.twoBeePlayedImage.setVisibility(View.VISIBLE);
				holder.twoBeeWonImage.setVisibility(View.VISIBLE);
				holder.twoBeeAverageImage.setVisibility(View.VISIBLE);
				holder.twoBeePlayed.setVisibility(View.GONE);
				holder.twoBeeWon.setVisibility(View.GONE);
				holder.twoBeeAverage.setVisibility(View.GONE);
			}else{
				holder.twoBeePlayed.setVisibility(View.VISIBLE);
				holder.twoBeeWon.setVisibility(View.VISIBLE);
				holder.twoBeeAverage.setVisibility(View.VISIBLE);
				
				holder.twoBeePlayedImage.setVisibility(View.GONE);
				holder.twoBeeWonImage.setVisibility(View.GONE);
				holder.twoBeeAverageImage.setVisibility(View.GONE);
				holder.twoBeePlayed.setText(String.valueOf(twoBeePlayedCount));
				holder.twoBeeWon.setText(String.valueOf(twoBeeWonCount));
				holder.twoBeeAverage.setText(String.valueOf(bd2.floatValue()));
			}
	      view.setTag(holder);
		return view;
	}


	/*	public FirstHalfAdapter(Context context, Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		TextView level = (TextView) view.findViewById(R.id.level);
	    TextView oneBeePlayed = (TextView) view.findViewById(R.id.one_bee_played);
	    TextView oneBeeWon = (TextView) view.findViewById(R.id.one_bee_won);
	    TextView oneBeeAverage = (TextView) view.findViewById(R.id.one_bee_average);
	    TextView twoBeePlayed = (TextView) view.findViewById(R.id.two_bee_played);
	    TextView twoBeeWon = (TextView) view.findViewById(R.id.two_bee_won);
	    TextView twoBeeAverage = (TextView) view.findViewById(R.id.two_bee_average);




	      // Extract properties from cursor
	      int levelCount = cursor.getInt(cursor.getColumnIndexOrThrow("_id")) + 1 ;
	      int oneBeePlayedCount = cursor.getInt(cursor.getColumnIndexOrThrow("games")) ;
	      int oneBeeWonCount = cursor.getInt(cursor.getColumnIndexOrThrow("won")) ;
	      float oneBeeAverageCount = cursor.getFloat(cursor.getColumnIndexOrThrow("average")) ;	
	      int lock = cursor.getInt(cursor.getColumnIndexOrThrow("lock"));
	      BigDecimal bd = new BigDecimal(Float.toString(oneBeeAverageCount));
	        bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
	      int TwoBeePlayedCount = cursor.getInt(cursor.getColumnIndexOrThrow("_id")) ;
	      int TwoBeeWonCount = cursor.getInt(cursor.getColumnIndexOrThrow("_id")) ;
	      int TwoBeeAverageCount = cursor.getInt(cursor.getColumnIndexOrThrow("_id")) ;

	      // Populate fields with extracted properties
	      //level.setId(levelCount);
	      level.setText(String.valueOf(levelCount));
	      if(lock == 0){
	    	  oneBeePlayed.setBackgroundResource(R.drawable.level_lock);
	    	  oneBeeWon.setBackgroundResource(R.drawable.level_lock);
	    	  oneBeeAverage.setBackgroundResource(R.drawable.level_lock);
	    	  oneBeePlayed.setCompoundDrawablesWithIntrinsicBounds(
	                    R.drawable.level_lock, 0, 0, 0);
	    	  oneBeeWon.setCompoundDrawablesWithIntrinsicBounds(
	                    R.drawable.level_lock, 0, 0, 0);
	    	  oneBeeAverage.setCompoundDrawablesWithIntrinsicBounds(
	                    R.drawable.level_lock, 0, 0, 0);
	      }else{
	    	  oneBeePlayed.setText(String.valueOf(oneBeePlayedCount));
		      oneBeeWon.setText(String.valueOf(oneBeeWonCount));
		      oneBeeAverage.setText(String.valueOf(bd.floatValue()));
	      }
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		return LayoutInflater.from(context).inflate(R.layout.row_column1, parent, false);
	}*/

}
