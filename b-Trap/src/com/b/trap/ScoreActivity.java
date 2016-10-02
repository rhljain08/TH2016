package com.b.trap;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.b.trap.database.CommentsDataSource;
import com.b.trap.database.TableColumns;

public class ScoreActivity extends Activity {
	private CommentsDataSource datasource;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.score_board);
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		datasource = new CommentsDataSource(this);
		datasource.open();
		datasource.fetchAllValue();
		// Find ListView to populate
		List<TableColumns> data1 = new ArrayList<TableColumns>();
		final ListView lvItems1 = (ListView) findViewById(R.id.listviewScore1);
		List<TableColumns> data2 = new ArrayList<TableColumns>();
		//final ListView lvItems2 = (ListView) findViewById(R.id.listviewScore2);
		// Setup cursor adapter using cursor from last step
		//FirstHalfAdapter firstHalfAdapter = new FirstHalfAdapter(this, datasource.fetchFirstHalf());
		data1 = datasource.fetchFirstHalf();
		data2 = datasource.fetchSecondtHalf();
		FirstHalfAdapter firstAdapter = new FirstHalfAdapter(data1,data2);
		lvItems1.setAdapter(firstAdapter);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		datasource.close();
		datasource = null;
	}

}
