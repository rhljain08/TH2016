package com.b.trap;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EndScreenActivity extends Activity {

	private Button restart, home;
	// private TextView appName;
	private TextView highScore;
	private ImageView winningShot;
	private ImageView movesTensPlace;
	private ImageView movesOnesPlace;
	private TextView winLose /* , appname, moves_count */;
	private LinearLayout /* text_score , */ social_connect;
	private RelativeLayout screenLayout;
	private Bitmap bitmap, finalBitmap;
	private int moves;
	private boolean win;
	private LinearLayout scoreLayout;
	private Vibrator vibe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.menu_items);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.screenshot_anim);

		restart = (Button) findViewById(R.id.restart_button);
		home = (Button) findViewById(R.id.rate_button);
		winningShot = (ImageView) findViewById(R.id.screenshot);
		// appName = (TextView) findViewById(R.id.app_name);
		highScore = (TextView) findViewById(R.id.highest_score);
		movesTensPlace = (ImageView) findViewById(R.id.moves_tens);
		movesOnesPlace = (ImageView) findViewById(R.id.moves_ones);
		winLose = (TextView) findViewById(R.id.win_loose);
		// appname = (TextView) findViewById(R.id.text_appname);
		// moves_count = (TextView) findViewById(R.id.text_count);
		screenLayout = (RelativeLayout) findViewById(R.id.screen_layout);
		// text_score = (LinearLayout)findViewById(R.id.text_score);
		social_connect = (LinearLayout) findViewById(R.id.social_connect);
		scoreLayout = (LinearLayout) findViewById(R.id.score_layout);
		Typeface typeFace = Typeface.createFromAsset(getAssets(), "Nanami HM Solid ExtraLight.otf");
		// appName.setTypeface(typeFace);
		highScore.setTypeface(typeFace);
		winLose.setTypeface(typeFace);
		// appname.setTypeface(typeFace);
		// appname.setTextSize(40);

		Intent intent = getIntent();
		moves = intent.getIntExtra("MOVES", 0);
		// moves_count.setText(moves+"");
		SharedPreferences sharedPref = getSharedPreferences(MainActivity.PREFS_NAME, 0);
		int level = sharedPref.getInt("level", 0);
		// int beeCount = sharedPref.getInt("beeCount", 0);
		// int valueLevel =beeCount*15 +level*1;
		win = intent.getBooleanExtra("WIN", false);
		if (win) {
			SharedPreferences.Editor edit = getSharedPreferences(MainActivity.PREFS_NAME, 0).edit();
			// text_score.setVisibility(View.VISIBLE);
			winLose.setText("congratulations");
			if (sharedPref.getInt("scoreArray" + level, 0) == 0) {
				edit.putInt("Count", 3);
				edit.putInt("scoreArray" + level, moves);
				edit.commit();
			}
			if (moves < sharedPref.getInt("scoreArray" + level, 0)) {
				edit.putInt("Count", 3);
				edit.putInt("scoreArray" + level, moves);
				edit.commit();
			}

		} else {
			winLose.setText("damn! bee got away");
			scoreLayout.setVisibility(View.GONE);
		}
		highScore.setText("best moves : " + sharedPref.getInt("scoreArray" + level, 0));

		bitmap = HexagonView.winningShot;
		winningShot.setVisibility(View.VISIBLE);

		winningShot.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));

		screenLayout.startAnimation(animation);
		// screenLayout.setRotation(-5.0f);
		// appname.setRotation(-5.0f);
		// text_score.setRotation(-5.0f);
		screenLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vibe.vibrate(50);
				social_connect.setVisibility(View.GONE);
				shareScreenShot();
				social_connect.setVisibility(View.VISIBLE);
			}
		});
		// }

		restart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vibe.vibrate(50);

				Intent intent = new Intent(getApplicationContext(), HexagonView.class);
				startActivity(intent);
				finish();
				// HexagonView.mContext.finish();

			}
		});

		home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vibe.vibrate(50);
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				// startActivity(intent);
				finish();
				startActivity(intent);

			}
		});

		final int countResImages[] = { R.drawable.num_00, R.drawable.num_01, R.drawable.num_02, R.drawable.num_03,
				R.drawable.num_04, R.drawable.num_05, R.drawable.num_06, R.drawable.num_07, R.drawable.num_08,
				R.drawable.num_09, };
		movesOnesPlace.setBackgroundResource(countResImages[moves % 10]);
		if (moves / 10 > 0) {
			movesTensPlace.setVisibility(View.VISIBLE);
			movesTensPlace.setBackgroundResource(countResImages[moves / 10]);
		}

	}

	private void shareScreenShot() {

		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + "/b-trap/shared_screens/");
		myDir.mkdirs();
		Random generator = new Random();
		int n = 10000;
		n = generator.nextInt(n);
		String fname = "Image-" + n + ".jpg";
		File file = new File(myDir, fname);
		if (file.exists())
			file.delete();
		try {
			FileOutputStream out = new FileOutputStream(file);
			finalBitmap = screenShot(screenLayout);
			finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		Intent sharingIntent = new Intent();
		sharingIntent.setAction(Intent.ACTION_SEND);
		// sharingIntent.setType("text/*");
		sharingIntent.setType("image/jpg");
		// Log.d("RAJAT","Image Path : "+myDir+"/"+fname);
		String appGooglePlayLink = getString(R.string.app_google_play_link);
		sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(myDir + "/" + fname));
		if (win) {
			sharingIntent.putExtra(Intent.EXTRA_TEXT, "Hey! I just trapped the bee in " + moves
					+ " moves. Get this awesome game B-TRAP on Google Play at " + appGooglePlayLink);
		} else {
			sharingIntent.putExtra(Intent.EXTRA_TEXT,
					"damn! bee got away. Hey! try this awesome game B-TRAP on Google Play at " + appGooglePlayLink);
		}
		startActivity(Intent.createChooser(sharingIntent, "Share via"));
	}

	public Bitmap screenShot(View view) {
		Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		view.draw(canvas);
		float scale = 0.25f;
		bitmap = Bitmap.createScaledBitmap(bitmap, (int) (scale * bitmap.getWidth()),
				(int) (scale * bitmap.getHeight()), false);
		return bitmap;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(intent);
		finish();
	}

}