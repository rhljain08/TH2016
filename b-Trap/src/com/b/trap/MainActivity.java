package com.b.trap;

import com.b.trap.database.CommentsDataSource;
import com.b.trap.database.LockStatus;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button start;
	private Button rate;
	private Button difficulty;
	private Button help;
	private ImageView mute, scoreBoard;
	public static final String PREFS_NAME = "MyPrefsFile";
	public static int level = -1;
	public static int beeCount;
	public static int valueLevel;
	private int[] levelResImages = new int[30];
	private int[] levelDialogImages = new int[15];
	private int[] lockDialogImages = new int[15];
	private LinearLayout.LayoutParams lp1, lp2;
	private LinearLayout oneBeeLayout, twoBeeLayout;
	private CommentsDataSource datasource;
	private int gamesToUnlock[] = { 0, 5, 10, 15, 15, 15, 50, 50, 50, 200, 200, 200, 500, 500, 500, 0, 5, 10, 15, 15,
			15, 50, 50, 50, 200, 200, 200, 500, 500, 500 };

	private Vibrator vibe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mainscreen);

		// Intent helpActivityIntent = new
		// Intent(getApplicationContext(),HelpActivity.class);
		// startActivity(helpActivityIntent);

		vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Typeface typeFace = Typeface.createFromAsset(getAssets(), "Nanami HM Solid ExtraLight.otf");
		start = (Button) findViewById(R.id.start_button);
		rate = (Button) findViewById(R.id.rate_button);
		difficulty = (Button) findViewById(R.id.level_button);
		help = (Button) findViewById(R.id.help_button);
		datasource = new CommentsDataSource(this);
		datasource.open();
		// gamesToUnlock[] =
		// {0,5,10,15,15,15,50,50,50,200,200,200,500,500,500,0,5,10,15,15,15,50,50,50,200,200,200,500,500,500};

		levelResImages[0] = R.drawable.one_one;
		levelResImages[1] = R.drawable.one_two;
		levelResImages[2] = R.drawable.one_three;
		levelResImages[3] = R.drawable.one_four;
		levelResImages[4] = R.drawable.one_five;
		levelResImages[5] = R.drawable.one_six;

		levelResImages[6] = R.drawable.one_seven;
		levelResImages[7] = R.drawable.one_eight;
		levelResImages[8] = R.drawable.one_nine;
		levelResImages[9] = R.drawable.one_ten;
		levelResImages[10] = R.drawable.one_eleven;
		levelResImages[11] = R.drawable.one_twelve;

		levelResImages[12] = R.drawable.one_thirteen;
		levelResImages[13] = R.drawable.one_forteen;
		levelResImages[14] = R.drawable.one_fifteen;
		levelResImages[15] = R.drawable.two_one;
		levelResImages[16] = R.drawable.two_two;
		levelResImages[17] = R.drawable.two_three;

		levelResImages[18] = R.drawable.two_four;
		levelResImages[19] = R.drawable.two_five;
		levelResImages[20] = R.drawable.two_six;
		levelResImages[21] = R.drawable.two_seven;
		levelResImages[22] = R.drawable.two_eight;
		levelResImages[23] = R.drawable.two_nine;

		levelResImages[24] = R.drawable.two_ten;
		levelResImages[25] = R.drawable.two_eleven;
		levelResImages[26] = R.drawable.two_twelve;
		levelResImages[27] = R.drawable.two_thirteen;
		levelResImages[28] = R.drawable.two_forteen;
		levelResImages[29] = R.drawable.two_fifteen;

		levelDialogImages[0] = R.xml.level_one_selector;
		levelDialogImages[1] = R.xml.level_two_selector;
		levelDialogImages[2] = R.xml.level_three_selector;
		levelDialogImages[3] = R.xml.level_four_selector;
		levelDialogImages[4] = R.xml.level_five_selector;
		levelDialogImages[5] = R.xml.level_six_selector;
		levelDialogImages[6] = R.xml.level_seven_selector;
		levelDialogImages[7] = R.xml.level_eight_selector;
		levelDialogImages[8] = R.xml.level_nine_selector;
		levelDialogImages[9] = R.xml.level_ten_selector;
		levelDialogImages[10] = R.xml.level_eleven_selector;
		levelDialogImages[11] = R.xml.level_twelve_selector;
		levelDialogImages[12] = R.xml.level_thirteen_selector;
		levelDialogImages[13] = R.xml.level_forteen_selector;
		levelDialogImages[14] = R.xml.level_fifteen_selector;

		lockDialogImages[0] = R.xml.lock_one_selector;
		lockDialogImages[1] = R.xml.lock_two_selector;
		lockDialogImages[2] = R.xml.lock_three_selector;
		lockDialogImages[3] = R.xml.lock_four_selector;
		lockDialogImages[4] = R.xml.lock_five_selector;
		lockDialogImages[5] = R.xml.lock_six_selector;
		lockDialogImages[6] = R.xml.lock_seven_selector;
		lockDialogImages[7] = R.xml.lock_eight_selector;
		lockDialogImages[8] = R.xml.lock_nine_selector;
		lockDialogImages[9] = R.xml.lock_ten_selector;
		lockDialogImages[10] = R.xml.lock_eleven_selector;
		lockDialogImages[11] = R.xml.lock_twelve_selector;
		lockDialogImages[12] = R.xml.lock_thirteen_selector;
		lockDialogImages[13] = R.xml.lock_forteen_selector;
		lockDialogImages[14] = R.xml.lock_fifteen_selector;

		SharedPreferences sharedPref = getSharedPreferences(MainActivity.PREFS_NAME, 0);
		level = sharedPref.getInt("level", 0);
		/*
		 * beeCount = sharedPref.getInt("beeCount", 0); valueLevel =beeCount*15
		 * +level*1;
		 */
		difficulty.setBackgroundResource(levelResImages[level]);
		start.setTypeface(typeFace);
		rate.setTypeface(typeFace);
		difficulty.setTypeface(typeFace);
		help.setTypeface(typeFace);
		start.setOnClickListener(new OnClickListener() {

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
		help.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vibe.vibrate(50);
				openHelpDialog();
			}

		});
		difficulty.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vibe.vibrate(50);
				openLevelDialog();
			}
		});

		rate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vibe.vibrate(50);
				String url = "https://play.google.com/store/apps/details?id=com.b.trap";
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		});

		sharedPref = getSharedPreferences(PREFS_NAME, 0);
		mute = (ImageView) findViewById(R.id.mute);
		if (sharedPref.getBoolean("mute", false) == false) {
			Drawable tempImage = getResources().getDrawable(R.drawable.mute_off);
			mute.setImageDrawable(tempImage);
		} else {
			Drawable tempImage = getResources().getDrawable(R.drawable.mute_on);
			mute.setImageDrawable(tempImage);
		}
		scoreBoard = (ImageView) findViewById(R.id.scoreBoard);
		scoreBoard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vibe.vibrate(50);
				Intent intent = new Intent(getApplicationContext(), ScoreActivity.class);
				startActivity(intent);
			}
		});
		mute.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences sharedPref = getSharedPreferences(MainActivity.PREFS_NAME, 0);
				if (sharedPref.getBoolean("mute", false) == false) {
					Drawable tempImage = getResources().getDrawable(R.drawable.mute_on);
					mute.setImageDrawable(tempImage);
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.clear();
					editor.putBoolean("mute", true);
					editor.apply();
				} else {
					Drawable tempImage = getResources().getDrawable(R.drawable.mute_off);
					mute.setImageDrawable(tempImage);
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.clear();
					editor.putBoolean("mute", false);
					editor.apply();
				}
			}
		});

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (datasource == null) {
			datasource = new CommentsDataSource(this);
			datasource.open();
		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		datasource.close();
		datasource = null;
	}

	private void openHelpDialog() {
		// TODO Auto-generated method stub
		Dialog dialog = new Dialog(this, R.style.Theme_Transparent);
		dialog.setContentView(R.layout.help_screen);
		dialog.setTitle("How to Play?");
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
		Window window = dialog.getWindow();
		window.setGravity(Gravity.BOTTOM);
	}

	/*
	 * public void onBackPressed() { // TODO Auto-generated method stub
	 * super.onBackPressed(); system.exit(0); }
	 */

	private void openLevelDialog() {
		// TODO Auto-generated method stub
		final Dialog dialog = new Dialog(this, R.style.Theme_Transparent);
		dialog.setContentView(R.layout.level_screen);
		dialog.setTitle("Levels");
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
		Window window = dialog.getWindow();
		window.setGravity(Gravity.BOTTOM);
		oneBeeLayout = (LinearLayout) dialog.findViewById(R.id.one_bee_layout);
		lp1 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		for (int i = 0; i < 15; i++) {
			ImageView levelImage = new ImageView(this);
			lp1.setMargins(i * 5, 0, 0, 0);

			if (datasource.fetchLockValue(i) == 0) {
				levelImage.setBackgroundResource(lockDialogImages[i]);
			} else if (datasource.fetchLockValue(i) == 1) {
				levelImage.setBackgroundResource(levelDialogImages[i]);
			}

			oneBeeLayout.addView(levelImage, lp1);
			levelImage.setId(i);
			final int id_ = levelImage.getId();
			levelImage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					// Log.d("Clicks","ID : "+id_);
					vibe.vibrate(50);
					if (datasource.fetchLockValue(id_) == 0) {
						// Creating the LayoutInflater instance
						LayoutInflater li = getLayoutInflater();
						// Getting the View object as defined in the
						// customtoast.xml file
						View layout = li.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
						TextView text = (TextView) layout.findViewById(R.id.custom_toast_message);
						TextView unlockText = (TextView) layout.findViewById(R.id.unlock_text);
						int level_to_play = LockStatus.checkLockStatus(id_);
						int wins = datasource.fetchWinValue(level_to_play);
						if (datasource.fetchLockValue(level_to_play) == 0) {
							unlockText.setText("Unlock level " + (level_to_play + 1) + " first");
						} else {
							unlockText.setText("Trap the bee " + (gamesToUnlock[id_] - wins) + " times at Level "
									+ (level_to_play + 1));
						}
						ImageView toastBee = (ImageView) layout.findViewById(R.id.bee);
						toastBee.setBackgroundResource(R.drawable.bee_easy);
						ImageView toastLevel = (ImageView) layout.findViewById(R.id.level);
						toastLevel.setBackgroundResource(levelDialogImages[id_]);
						text.setText("Level Locked");

						// Creating the Toast object
						Toast toast = new Toast(getApplicationContext());
						toast.setDuration(Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
						toast.setView(layout);// setting the view of custom
												// toast layout
						toast.show();
					} else {
						SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME, 0);
						SharedPreferences.Editor editor = sharedPref.edit();
						editor.clear();
						editor.putInt("level", id_);
						editor.putInt("beeCount", 0);
						editor.apply();
						difficulty.setBackgroundResource(levelResImages[id_]);
					}
					dialog.dismiss();
				}
			});
		}
		twoBeeLayout = (LinearLayout) dialog.findViewById(R.id.two_bee_layout);
		lp2 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		for (int i = 0; i < 15; i++) {
			ImageView levelImage = new ImageView(this);
			lp2.setMargins(i * 5, 0, 0, 0);

			if (datasource.fetchLockValue(i + 15) == 0) {
				levelImage.setBackgroundResource(lockDialogImages[i]);
			} else if (datasource.fetchLockValue(i + 15) == 1) {
				levelImage.setBackgroundResource(levelDialogImages[i]);
			}

			twoBeeLayout.addView(levelImage, lp2);
			levelImage.setId(i + 15);
			final int _id = levelImage.getId();
			levelImage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// Log.d("Clicks","ID : "+_id);
					vibe.vibrate(50);
					if (datasource.fetchLockValue(_id) == 0) {
						// Creating the LayoutInflater instance
						LayoutInflater li = getLayoutInflater();
						// Getting the View object as defined in the
						// customtoast.xml file
						View layout = li.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
						TextView text = (TextView) layout.findViewById(R.id.custom_toast_message);
						TextView unlockText = (TextView) layout.findViewById(R.id.unlock_text);
						int level_to_play = LockStatus.checkLockStatus(_id);
						int wins = datasource.fetchWinValue(level_to_play);
						if (datasource.fetchLockValue(level_to_play) == 0) {
							unlockText.setText("Unlock level " + (level_to_play % 15 + 1) + " first");
						} else {
							unlockText.setText("Trap the bees " + (gamesToUnlock[_id] - wins) + " times at Level "
									+ (level_to_play % 15 + 1));
						}
						ImageView toastBee = (ImageView) layout.findViewById(R.id.bee);
						toastBee.setBackgroundResource(R.drawable.two_bee);
						ImageView toastLevel = (ImageView) layout.findViewById(R.id.level);
						toastLevel.setBackgroundResource(levelDialogImages[_id % 15]);
						text.setText("Level Locked");

						// Creating the Toast object
						Toast toast = new Toast(getApplicationContext());
						toast.setDuration(Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
						toast.setView(layout);// setting the view of custom
												// toast layout
						toast.show();
					} else {
						SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME, 0);
						SharedPreferences.Editor editor = sharedPref.edit();
						editor.clear();
						editor.putInt("level", _id);
						editor.putInt("beeCount", 1);
						editor.apply();
						difficulty.setBackgroundResource(levelResImages[_id]);
					}
					dialog.dismiss();
				}
			});
		}

	}
}