package com.b.trap;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import com.b.trap.database.CommentsDataSource;
import com.b.trap.database.NextToUnlock;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HexagonView extends Activity {

	private static final String TAG_FIRST_BEE_THREAD = "FIRST_BEE_THREAD";
	private int beeOneLeftMargin, bottomMargin, beeTwoLeftMargin;
	private int height, width;
	private RelativeLayout ll;
	private static int beeOneNextRow, beeOneNextColumn, beeTwoNextRow, beeTwoNextColumn;
	private static int beeOneCurrRow, beeOneCurrColumn, beeTwoCurrRow, beeTwoCurrColumn;
	// private float toXDeltaFirstBee, toYDeltaFirstBee;
	private float[] toXDelta = new float[7];
	private float[] toYDelta = new float[7];
	// private float toXDeltaSecondBee, toYDeltaSecondBee;
	private int directionFirstBee;
	private int directionSecondBee;
	private float xDiff, yDiff;
	public static int beeOneCurrPosition;
	public static int beeTwoCurrPosition;
	private int beeOneNextPosition, beeTwoNextPosition;
	// private int beeOneNextToNextPosition, beeTwoNextToNextPosition;
	ImageButton firstBee;
	ImageButton secondBee;
	int initialBlocks[];
	private ImageButton myButton;
	private boolean gameOver = false;
	private boolean won;
	public static Activity mContext;
	private int moves = 0;
	private ImageView movesTensPlace;
	private ImageView movesOnesPlace;
	private ImageView refresh;
	private View contentView;
	// private TextView appName;
	public static Bitmap winningShot;
	private RelativeLayout superLayout;
	public static int level = -1;
	private int[] hexagonLevel = new int[15];
	private int[] hexagonFrontLevel = new int[15];
	private int[] beeLevel = new int[15];
	private int[] fly_anim_drawables = new int[15];
	private int[] hex_bg_change_anim = new int[15];
	private int[] rowLevel = new int[15];
	private int[] columnLevel = new int[15];
	private int[] initialBlockCount = new int[15];
	private int[] countResImages = new int[10];
	private int randomCount = 0;
	private boolean loop;
	public static int beeCount;
	private RelativeLayout.LayoutParams lp1;
	private RelativeLayout.LayoutParams lp2;
	private RelativeLayout.LayoutParams lp3;
	private RelativeLayout.LayoutParams lp4;
	private CommentsDataSource datasource;
	private LayoutInflater toastInflater;
	private static View toastLayout;
	private static View nextToUnlockToast;
	private static TextView toastText;
	private static TextView nextToUnlockToastText;
	private static ImageView toastBee, toastLevel;
	private static ImageView nextToUnlockToastBee, nextToUnlockToastLevel;
	private static Context toastConetxt;
	private static int[] levelDialogImages = new int[15];
	private Vibrator vibe;
	private NextToUnlock nxtUnlock;
	private AnimatorSet animatorSetSecondBee;
	private AnimatorSet animatorSetFirstBee;
	// private FirstBeeAnimationThread mFirstBeeAnimationThread;
	private Handler mSecondBeeAnimationHandler;
	private Handler mFirstBeeAnimationHandler;
	private Handler firstBeeHandler;
	private final static int MSG_START_ANNIMATION = 1;
	private int counterFirstBee = 0;
	private int counterSecondBee = 0;
	private Queue<Integer> firstBeeQueue;
	private Queue<Integer> secondBeeQueue;
	private AnimationDrawable beeFlyingAnimationFirstBee;
	private AnimationDrawable beeFlyingAnimationSecondBee;
	private int measuredWidth = 0;
	private int measuredHeight = 0;
	private MediaPlayer onClickSound;
	private MediaPlayer beeFlySound;
	private SharedPreferences sharedPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		toastConetxt = getApplicationContext();
		ll = (RelativeLayout) findViewById(R.id.linear);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		superLayout = (RelativeLayout) findViewById(R.id.superlayout);
		refresh = (ImageView) findViewById(R.id.refresh);
		datasource = new CommentsDataSource(this);
		datasource.open();
		toastInflater = getLayoutInflater();
		toastLayout = toastInflater.inflate(R.layout.unlock_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
		toastText = (TextView) toastLayout.findViewById(R.id.custom_toast_message);
		toastBee = (ImageView) toastLayout.findViewById(R.id.bee);
		toastLevel = (ImageView) toastLayout.findViewById(R.id.level);
		nextToUnlockToast = toastInflater.inflate(R.layout.next_unlock_toast,
				(ViewGroup) findViewById(R.id.custom_toast_layout));
		nextToUnlockToastText = (TextView) nextToUnlockToast.findViewById(R.id.custom_toast_message);
		nextToUnlockToastBee = (ImageView) nextToUnlockToast.findViewById(R.id.bee);
		nextToUnlockToastLevel = (ImageView) nextToUnlockToast.findViewById(R.id.level);

		mSecondBeeAnimationHandler = new SecondBeeAnimationHandler();
		mFirstBeeAnimationHandler = new FirstBeeAnimationHandler();

		refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vibe.vibrate(50);
				// Intent intent = new
				// Intent(getApplicationContext(),HexagonView.class);
				// startActivity(intent);

				startActivity(getIntent());
				finish();
			}
		});

		WindowManager w = getWindowManager();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			Point size = new Point();
			w.getDefaultDisplay().getSize(size);
			measuredWidth = size.x;
			measuredHeight = size.y;
		} else {
			Display d = w.getDefaultDisplay();
			measuredWidth = d.getWidth();
			measuredHeight = d.getHeight();
		}

		firstBeeQueue = new LinkedList<Integer>();
		secondBeeQueue = new LinkedList<Integer>();

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
		contentView = findViewById(android.R.id.content);
		hexagonLevel[0] = hexagonLevel[3] = hexagonLevel[6] = hexagonLevel[9] = hexagonLevel[12] = R.drawable.hexagon_easy;
		hexagonLevel[1] = hexagonLevel[4] = hexagonLevel[7] = hexagonLevel[10] = hexagonLevel[13] = R.drawable.hexagon_medium;
		hexagonLevel[2] = hexagonLevel[5] = hexagonLevel[8] = hexagonLevel[11] = hexagonLevel[14] = R.drawable.hexagon_difficult;

		hexagonFrontLevel[0] = hexagonFrontLevel[3] = hexagonFrontLevel[6] = hexagonFrontLevel[9] = hexagonFrontLevel[12] = R.drawable.hexagonfront_easy;
		hexagonFrontLevel[1] = hexagonFrontLevel[4] = hexagonFrontLevel[7] = hexagonFrontLevel[10] = hexagonFrontLevel[13] = R.drawable.hexagonfront_medium;
		hexagonFrontLevel[2] = hexagonFrontLevel[5] = hexagonFrontLevel[8] = hexagonFrontLevel[11] = hexagonFrontLevel[14] = R.drawable.hexagonfront_difficult;

		beeLevel[0] = beeLevel[3] = beeLevel[6] = beeLevel[9] = beeLevel[12] = R.drawable.bee_easy;
		beeLevel[1] = beeLevel[4] = beeLevel[7] = beeLevel[10] = beeLevel[13] = R.drawable.bee_medium;
		beeLevel[2] = beeLevel[5] = beeLevel[8] = beeLevel[11] = beeLevel[14] = R.drawable.bee_difficult;

		fly_anim_drawables[0] = fly_anim_drawables[3] = fly_anim_drawables[6] = fly_anim_drawables[9] = fly_anim_drawables[12] = R.drawable.easy_fly_anim_drawables;
		fly_anim_drawables[1] = fly_anim_drawables[4] = fly_anim_drawables[7] = fly_anim_drawables[10] = fly_anim_drawables[13] = R.drawable.med_fly_anim_drawables;
		fly_anim_drawables[2] = fly_anim_drawables[5] = fly_anim_drawables[8] = fly_anim_drawables[11] = fly_anim_drawables[14] = R.drawable.diff_fly_anim_drawables;

		hex_bg_change_anim[0] = hex_bg_change_anim[3] = hex_bg_change_anim[6] = hex_bg_change_anim[9] = hex_bg_change_anim[12] = R.drawable.easy_hex_bg_anim;
		hex_bg_change_anim[1] = hex_bg_change_anim[4] = hex_bg_change_anim[7] = hex_bg_change_anim[10] = hex_bg_change_anim[13] = R.drawable.med_hex_bg_anim;
		hex_bg_change_anim[2] = hex_bg_change_anim[5] = hex_bg_change_anim[8] = hex_bg_change_anim[11] = hex_bg_change_anim[14] = R.drawable.diff_hex_bg_anim;

		countResImages[0] = R.drawable.num_00;
		countResImages[1] = R.drawable.num_01;
		countResImages[2] = R.drawable.num_02;
		countResImages[3] = R.drawable.num_03;
		countResImages[4] = R.drawable.num_04;
		countResImages[5] = R.drawable.num_05;
		countResImages[6] = R.drawable.num_06;
		countResImages[7] = R.drawable.num_07;
		countResImages[8] = R.drawable.num_08;
		countResImages[9] = R.drawable.num_09;

		rowLevel[0] = rowLevel[3] = rowLevel[6] = rowLevel[9] = rowLevel[12] = 11;
		rowLevel[1] = rowLevel[4] = rowLevel[7] = rowLevel[10] = rowLevel[13] = 9;
		rowLevel[2] = rowLevel[5] = rowLevel[8] = rowLevel[11] = rowLevel[14] = 7;
		// rowLevel[3] = 17;

		columnLevel[0] = columnLevel[3] = columnLevel[6] = columnLevel[9] = columnLevel[12] = 15;
		columnLevel[1] = columnLevel[4] = columnLevel[7] = columnLevel[10] = columnLevel[13] = 12;
		columnLevel[2] = columnLevel[5] = columnLevel[8] = columnLevel[11] = columnLevel[14] = 10;
		// columnLevel[3] = 24;

		sharedPref = getSharedPreferences(MainActivity.PREFS_NAME, 0);
		level = sharedPref.getInt("level", 0) % 15;
		beeCount = sharedPref.getInt("beeCount", 0);
		initialBlockCount[0] = (rowLevel[0] * columnLevel[0]) / 4;
		initialBlockCount[1] = (rowLevel[1] * columnLevel[1]) / 4;
		initialBlockCount[2] = (rowLevel[2] * columnLevel[2]) / 4;

		initialBlockCount[3] = (rowLevel[0] * columnLevel[0]) / 5;
		initialBlockCount[4] = (rowLevel[1] * columnLevel[1]) / 5;
		initialBlockCount[5] = (rowLevel[2] * columnLevel[2]) / 5;

		initialBlockCount[6] = (rowLevel[0] * columnLevel[0]) / 6;
		initialBlockCount[7] = (rowLevel[1] * columnLevel[1]) / 6;
		initialBlockCount[8] = (rowLevel[2] * columnLevel[2]) / 6;

		initialBlockCount[9] = (rowLevel[0] * columnLevel[0]) / 7;
		initialBlockCount[10] = (rowLevel[1] * columnLevel[1]) / 7;
		initialBlockCount[11] = (rowLevel[2] * columnLevel[2]) / 7;

		initialBlockCount[12] = (rowLevel[0] * columnLevel[0]) / 8;
		initialBlockCount[13] = (rowLevel[1] * columnLevel[1]) / 8;
		initialBlockCount[14] = (rowLevel[2] * columnLevel[2]) / 8;

		onClickSound = MediaPlayer.create(this, R.raw.click);
		beeFlySound = MediaPlayer.create(this, R.raw.beetrapbuzz);

		mContext = this;

		BitmapDrawable bd = (BitmapDrawable) this.getResources().getDrawable(hexagonLevel[level]);
		height = bd.getBitmap().getHeight();
		width = bd.getBitmap().getWidth();
		xDiff = (float) (width / 2.0);
		yDiff = (float) (3 * (height / 4.0));

		toXDelta[1] = -xDiff;
		toXDelta[2] = xDiff;
		toXDelta[3] = -width;
		toXDelta[4] = width;
		toXDelta[5] = -xDiff;
		toXDelta[6] = xDiff;

		toYDelta[1] = -yDiff;
		toYDelta[2] = -yDiff;
		toYDelta[3] = 0;
		toYDelta[4] = 0;
		toYDelta[5] = yDiff;
		toYDelta[6] = yDiff;

		firstBee = new ImageButton(this);

		if (beeCount == 1) {
			secondBee = new ImageButton(this);
		}

		movesTensPlace = (ImageView) findViewById(R.id.moves_tens);
		movesOnesPlace = (ImageView) findViewById(R.id.moves_ones);

		for (int i = 0; i < rowLevel[level]; i++) {
			for (int j = 0; j < columnLevel[level]; j++) {
				myButton = new ImageButton(this);
				myButton.setBackgroundResource(hex_bg_change_anim[level]);
				final TransitionDrawable transition = (TransitionDrawable) myButton.getBackground();

				lp1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

				if (i % 2 == 0)
					beeOneLeftMargin = width * j + width;
				else
					beeOneLeftMargin = width * j + 3 * width / 2;

				bottomMargin = 3 * height * i / 4 + 3 * height / 2;
				lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				lp1.setMargins(beeOneLeftMargin, 0, 0, bottomMargin);

				ll.addView(myButton, lp1);
				myButton.setId(i * columnLevel[level] + j + 1);
				final int id_ = myButton.getId();

				myButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(final View v) {
						if (id_ != beeOneNextPosition && id_ != beeTwoNextPosition) {
							/*
							 * //vibe.vibrate(50); final Handler handler = new
							 * Handler(); handler.postDelayed(new Runnable() {
							 * 
							 * @Override public void run() { if(gameOver ==
							 * false){
							 * v.setBackgroundResource(hexagonFrontLevel[level])
							 * ; } else{ moves = 0; } } }, 100);
							 */

							if (gameOver == true) {
								moves = 0;
							}

							if (Mazetopbottom.updateMatrix(id_)) {
								if (gameOver == false) {
									// vibe.vibrate(50);
									// SharedPreferences sharedPref =
									// getSharedPreferences(MainActivity.PREFS_NAME,
									// 0);
									if (sharedPref.getBoolean("mute", false) == false)
										onClickSound.start();
									// v.setBackgroundResource(hexagonFrontLevel[level]);
									transition.startTransition(500);
									moves++;
									movesOnesPlace.setBackgroundResource(countResImages[moves % 10]);
									if (moves / 10 > 0) {
										movesTensPlace.setVisibility(View.VISIBLE);
										movesTensPlace.setBackgroundResource(countResImages[moves / 10]);
									}
								}
								setMousePosition();
							}

						}

					}
				});
			}
		}

		firstBee.setBackgroundResource(beeLevel[level]);
		lp2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		if (beeCount == 1) {
			secondBee.setBackgroundResource(beeLevel[level]);
			lp3 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		}

		if ((rowLevel[level] / 2) % 2 == 0) {
			beeOneLeftMargin = width * (columnLevel[level] / 2) + width;
		}

		else {
			beeOneLeftMargin = width * (columnLevel[level] / 2) + 3 * width / 2;
		}
		beeOneLeftMargin = beeOneLeftMargin - width;
		beeTwoLeftMargin = beeOneLeftMargin + 2 * width;

		bottomMargin = (rowLevel[level] / 2) * height * 3 / 4 + 3 * height / 2;

		lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		lp2.setMargins(beeOneLeftMargin, 0, 0, bottomMargin);
		beeOneCurrPosition = columnLevel[level] * (rowLevel[level] / 2) + columnLevel[level] / 2 + 1 - 1;

		if (beeCount == 1) {
			lp3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			lp3.setMargins(beeTwoLeftMargin, 0, 0, bottomMargin);
			beeTwoCurrPosition = beeOneCurrPosition + 2;
		}

		do {
			initialBlocks = selectSomeRandomBlocks();
			Mazetopbottom.updateInitialMatrix(initialBlocks);
			loop = checkForLoop();
		} while (loop);

		if (!loop) {
			setRandomBlocks(initialBlocks);
		}
		ll.addView(firstBee, lp2);
		if (beeCount == 1) {
			ll.addView(secondBee, lp3);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (datasource == null) {
			datasource = new CommentsDataSource(this);
			datasource.open();
		}

		// overridePendingTransition(R.anim.fadein,R.anim.fadeout);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	private int[] selectSomeRandomBlocks() {
		Random r = new Random();
		randomCount = initialBlockCount[level];
		int randomBlocks[] = new int[randomCount];
		for (int i = 0; i < randomCount; i++) {
			randomBlocks[i] = r.nextInt((rowLevel[level] * columnLevel[level]) - 1) + 1;
			while (randomBlocks[i] == beeOneCurrPosition || randomBlocks[i] == beeTwoCurrPosition) {
				randomBlocks[i] = r.nextInt((rowLevel[level] * columnLevel[level]) - 1) + 1;
			}
		}

		return randomBlocks;
	}

	private void setRandomBlocks(int[] initialBlocks) {
		for (int i = 0; i < randomCount; i++) {
			beeOneNextRow = initialBlocks[i] / columnLevel[level];
			beeOneNextColumn = initialBlocks[i] % columnLevel[level] - 1;
			if (beeOneNextColumn == -1) {
				beeOneNextColumn = columnLevel[level] - 1;
				beeOneNextRow = beeOneNextRow - 1;
			}
			ImageButton myButton = new ImageButton(this);
			myButton.setBackgroundResource(hexagonFrontLevel[level]);
			lp4 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			if (beeOneNextRow % 2 == 0)
				beeOneLeftMargin = width * beeOneNextColumn + width;
			else
				beeOneLeftMargin = width * beeOneNextColumn + 3 * width / 2;

			bottomMargin = 3 * height * beeOneNextRow / 4 + 3 * height / 2;

			lp4.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

			lp4.setMargins(beeOneLeftMargin, 0, 0, bottomMargin);
			ll.addView(myButton, lp4);
		}
	}

	private boolean checkForLoop() {
		if (DijkstraAlgorithmSet.nearestExit == -1 || DijkstraAlgorithmSet_1.nearestExit == -1) {
			return true;
		} else
			return false;
	}

	private void setMousePosition() {
		beeOneNextPosition = DijkstraAlgorithmSet.getNextStep();
		beeTwoNextPosition = DijkstraAlgorithmSet_1.getNextStep();

		if (beeOneNextPosition != -1 && beeOneNextPosition == beeTwoNextPosition && beeCount == 1) {
			///////////// to Prevent the 2 bees overlap////////////////
			Mazetopbottom.startAlgorithm(beeOneNextPosition);
			beeTwoNextPosition = DijkstraAlgorithmSet_1.getNextStep();
		}

		if (DijkstraAlgorithmSet.nearestExit == -1 || (DijkstraAlgorithmSet_1.nearestExit == -1 && beeCount == 1)) {
			///////////////////// GAME WON ////////////////////
			if (gameOver == false) {
				won = true;
				datasource.updateWins(beeCount * 15 + level, moves);
				nextToUnlockToastShow();

				final Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					public void run() {
						gameOver = true;
						winningShot = screenShot(ll);
						Intent intent = new Intent(getApplicationContext(), EndScreenActivity.class);
						intent.putExtra("WIN", true);
						intent.putExtra("MOVES", moves);
						// startActivity(intent);
						finish();
						startActivity(intent);
					}
				}, 500);
			}
		} else if (beeOneNextPosition == -1 || (beeTwoNextPosition == -1 && beeCount == 1)) {
			/*
			 * //////////////////// GAME LOST ////////////////////// if(gameOver
			 * == false){ final Handler handler = new Handler();
			 * handler.postDelayed(new Runnable() { public void run() {
			 * datasource.updateLoss(beeCount*15 + level); winningShot =
			 * screenShot(ll); Intent intent = new
			 * Intent(getApplicationContext(),EndScreenActivity.class);
			 * intent.putExtra("WIN", false); intent.putExtra("MOVES", moves);
			 * startActivity(intent); gameOver = true; won = false; finish(); }
			 * 
			 * }, 200); }
			 */

		} else {
			beeOneCurrRow = beeOneCurrPosition / columnLevel[level];
			beeOneCurrColumn = beeOneCurrPosition % columnLevel[level] - 1;
			if (beeOneCurrColumn == -1) {
				beeOneCurrColumn = columnLevel[level] - 1;
				beeOneCurrRow = beeOneCurrRow - 1;
			}

			//////// Next Row and column///////////

			beeOneNextRow = beeOneNextPosition / columnLevel[level];
			beeOneNextColumn = beeOneNextPosition % columnLevel[level] - 1;
			if (beeOneNextColumn == -1) {
				beeOneNextColumn = columnLevel[level] - 1;
				beeOneNextRow = beeOneNextRow - 1;
			}

			directionFirstBee = calculateDirection(beeOneCurrRow, beeOneNextRow, beeOneCurrColumn, beeOneNextColumn);
			firstBeeQueue.add(directionFirstBee);

			// animatorSetFirstBee.start();
			mFirstBeeAnimationHandler.sendEmptyMessage(MSG_START_ANNIMATION);
			beeOneCurrPosition = beeOneNextPosition;

			if (beeCount == 1) {
				beeTwoCurrRow = beeTwoCurrPosition / columnLevel[level];
				beeTwoCurrColumn = beeTwoCurrPosition % columnLevel[level] - 1;
				if (beeTwoCurrColumn == -1) {
					beeTwoCurrColumn = columnLevel[level] - 1;
					beeTwoCurrRow = beeTwoCurrRow - 1;
				}

				beeTwoNextRow = beeTwoNextPosition / columnLevel[level];
				beeTwoNextColumn = beeTwoNextPosition % columnLevel[level] - 1;
				if (beeTwoNextColumn == -1) {
					beeTwoNextColumn = columnLevel[level] - 1;
					beeTwoNextRow = beeTwoNextRow - 1;
				}

				directionSecondBee = calculateDirection(beeTwoCurrRow, beeTwoNextRow, beeTwoCurrColumn,
						beeTwoNextColumn);
				secondBeeQueue.add(directionSecondBee);

				mSecondBeeAnimationHandler.sendEmptyMessage(MSG_START_ANNIMATION);
				// animatorSetSecondBee.start();
				beeTwoCurrPosition = beeTwoNextPosition;
			}
		}
	}

	private void animationParamsFirstBee(int direction) {

		animatorSetFirstBee = new AnimatorSet();
		ObjectAnimator translateXFirstBee = ObjectAnimator.ofFloat(firstBee, "x", firstBee.getX(),
				firstBee.getX() + toXDelta[direction]);
		ObjectAnimator translateYFirstBee = ObjectAnimator.ofFloat(firstBee, "y", firstBee.getY(),
				firstBee.getY() + toYDelta[direction]);
		animatorSetFirstBee.play(translateXFirstBee).with(translateYFirstBee);
		animatorSetFirstBee.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub

				/*
				 * counterFirstBee--; if(counterFirstBee > 0){
				 * animationParamsFirstBee(); animatorSetFirstBee.start(); }
				 */
				Log.d("Queue", "checking firstBeeQueue " + firstBeeQueue.isEmpty());

				counterFirstBee--;
				if (!firstBeeQueue.isEmpty()) {
					Log.d("Queue", "firstBeeQueue is not empty : " + firstBeeQueue.peek());
					animationParamsFirstBee((int) firstBeeQueue.remove());
					animatorSetFirstBee.start();
				} else {
					Log.d("Queue", "checking firstBeeQueue else part ");

					if (beeOneNextRow == 0 || beeOneNextRow == rowLevel[level] - 1 || beeOneNextColumn == 0
							|| beeOneNextColumn == columnLevel[level] - 1) {
						Log.d("RAJAT", "Last row");
						//////////////////// GAME LOST //////////////////////
						if (gameOver == false) {
							/*
							 * final Handler handler = new Handler();
							 * handler.postDelayed(new Runnable() { public void
							 * run() { datasource.updateLoss(beeCount*15 +
							 * level); winningShot = screenShot(ll); Intent
							 * intent = new
							 * Intent(getApplicationContext(),EndScreenActivity.
							 * class); intent.putExtra("WIN", false);
							 * intent.putExtra("MOVES", moves);
							 * startActivity(intent); gameOver = true; won =
							 * false; finish(); } }, 200);
							 */

							firstBee.setBackgroundResource(fly_anim_drawables[level]);
							beeFlyingAnimationFirstBee = (AnimationDrawable) firstBee.getBackground();
							beeFlyingAnimationFirstBee.start();
							if (sharedPref.getBoolean("mute", false) == false)
								beeFlySound.start();

							ArcTranslateAnimation anim = null;
							if (beeOneNextRow == 0) {
								if (beeOneNextColumn < 3 * columnLevel[level] / 4)
									anim = new ArcTranslateAnimation(0, measuredWidth, 0, -measuredHeight / 2);
								else
									anim = new ArcTranslateAnimation(0, -measuredWidth, 0, -measuredHeight / 2);
							} else if (beeOneNextColumn == 0) {
								if (beeOneNextRow < rowLevel[level] / 2)
									anim = new ArcTranslateAnimation(0, measuredWidth, 0, -measuredHeight / 4);
								else
									anim = new ArcTranslateAnimation(0, measuredWidth, 0, measuredHeight / 4);
							} else if (beeOneNextRow == rowLevel[level] - 1) {
								if (beeOneNextColumn < 3 * columnLevel[level] / 4)
									anim = new ArcTranslateAnimation(0, measuredWidth, 0, measuredHeight / 2);
								else
									anim = new ArcTranslateAnimation(0, -measuredWidth, 0, measuredHeight / 2);
							} else if (beeOneNextColumn == columnLevel[level] - 1) {
								if (beeOneNextRow < rowLevel[level] / 2)
									anim = new ArcTranslateAnimation(0, -measuredWidth, 0, -measuredHeight / 4);
								else
									anim = new ArcTranslateAnimation(0, -measuredWidth, 0, measuredHeight / 4);
							}
							anim.setDuration(1500);
							anim.setFillEnabled(true);
							anim.setFillAfter(true);
							anim.setAnimationListener(new AnimationListener() {

								@Override
								public void onAnimationStart(Animation animation) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAnimationRepeat(Animation animation) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAnimationEnd(Animation animation) {
									// TODO Auto-generated method stub
									datasource.updateLoss(beeCount * 15 + level);
									winningShot = screenShot(ll);
									Intent intent = new Intent(getApplicationContext(), EndScreenActivity.class);
									intent.putExtra("WIN", false);
									intent.putExtra("MOVES", moves);
									// startActivity(intent);
									gameOver = true;
									won = false;
									finish();
									startActivity(intent);
								}
							});

							firstBee.startAnimation(anim);

						}
					}
				}
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
			}
		});
	}

	private void animationParamsSecondBee(int direction) {

		animatorSetSecondBee = new AnimatorSet();
		ObjectAnimator translateXSecondBee = ObjectAnimator.ofFloat(secondBee, "x", secondBee.getX(),
				secondBee.getX() + toXDelta[direction]);
		ObjectAnimator translateYSecondBee = ObjectAnimator.ofFloat(secondBee, "y", secondBee.getY(),
				secondBee.getY() + toYDelta[direction]);
		animatorSetSecondBee.play(translateXSecondBee).with(translateYSecondBee);
		animatorSetSecondBee.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationEnd(Animator animation) {

				/*
				 * counterSecondBee--; if(counterSecondBee > 0){
				 * animationParamsSecondBee(); animatorSetSecondBee.start(); }
				 */
				counterSecondBee--;
				if (!secondBeeQueue.isEmpty()) {
					animationParamsSecondBee((int) secondBeeQueue.remove());
					animatorSetSecondBee.start();
				} else {

					if (beeTwoNextRow == 0 || beeTwoNextRow == rowLevel[level] - 1 || beeTwoNextColumn == 0
							|| beeTwoNextColumn == columnLevel[level] - 1) {
						Log.d("RAJAT", "Last row");
						//////////////////// GAME LOST //////////////////////
						if (gameOver == false) {
							/*
							 * final Handler handler = new Handler();
							 * handler.postDelayed(new Runnable() { public void
							 * run() { datasource.updateLoss(beeCount*15 +
							 * level); winningShot = screenShot(ll); Intent
							 * intent = new
							 * Intent(getApplicationContext(),EndScreenActivity.
							 * class); intent.putExtra("WIN", false);
							 * intent.putExtra("MOVES", moves);
							 * startActivity(intent); gameOver = true; won =
							 * false; finish(); } }, 200);
							 */

							secondBee.setBackgroundResource(fly_anim_drawables[level]);
							beeFlyingAnimationSecondBee = (AnimationDrawable) secondBee.getBackground();
							beeFlyingAnimationSecondBee.start();
							if (sharedPref.getBoolean("mute", false) == false)
								beeFlySound.start();

							ArcTranslateAnimation anim = null;
							if (beeTwoNextRow == 0) {
								if (beeTwoNextColumn < 3 * columnLevel[level] / 4)
									anim = new ArcTranslateAnimation(0, measuredWidth, 0, -measuredHeight / 2);
								else
									anim = new ArcTranslateAnimation(0, -measuredWidth, 0, -measuredHeight / 2);
							} else if (beeTwoNextColumn == 0) {
								if (beeTwoNextRow < rowLevel[level] / 2)
									anim = new ArcTranslateAnimation(0, measuredWidth, 0, -measuredHeight / 4);
								else
									anim = new ArcTranslateAnimation(0, measuredWidth, 0, measuredHeight / 4);
							} else if (beeTwoNextRow == rowLevel[level] - 1) {
								if (beeTwoNextColumn < 3 * columnLevel[level] / 4)
									anim = new ArcTranslateAnimation(0, measuredWidth, 0, measuredHeight / 2);
								else
									anim = new ArcTranslateAnimation(0, -measuredWidth, 0, measuredHeight / 2);
							} else if (beeTwoNextColumn == columnLevel[level] - 1) {
								if (beeTwoNextRow < rowLevel[level] / 2)
									anim = new ArcTranslateAnimation(0, -measuredWidth, 0, -measuredHeight / 4);
								else
									anim = new ArcTranslateAnimation(0, -measuredWidth, 0, measuredHeight / 4);
							}
							anim.setDuration(1500);
							anim.setFillEnabled(true);
							anim.setFillAfter(true);
							anim.setAnimationListener(new AnimationListener() {

								@Override
								public void onAnimationStart(Animation animation) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAnimationRepeat(Animation animation) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAnimationEnd(Animation animation) {
									// TODO Auto-generated method stub
									datasource.updateLoss(beeCount * 15 + level);
									winningShot = screenShot(ll);
									Intent intent = new Intent(getApplicationContext(), EndScreenActivity.class);
									intent.putExtra("WIN", false);
									intent.putExtra("MOVES", moves);
									// startActivity(intent);
									gameOver = true;
									won = false;
									finish();
									startActivity(intent);
								}
							});

							secondBee.startAnimation(anim);

						}
					}
				}
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
			}
		});
	}

	private int calculateDirection(int currRow, int nextRow, int currCol, int nextCol) {

		int direction = 0;
		if (currRow % 2 == 1) {
			if ((nextRow - currRow) == 1 && (nextCol - currCol == 0)) {
				direction = 1;
			} else if ((nextRow - currRow) == 1 && (nextCol - currCol == 1)) {
				direction = 2;
			} else if ((nextRow - currRow) == 0 && (nextCol - currCol == -1)) {
				direction = 3;
			} else if ((nextRow - currRow) == 0 && (nextCol - currCol == 1)) {
				direction = 4;
			} else if ((nextRow - currRow) == -1 && (nextCol - currCol == 0)) {
				direction = 5;
			} else if ((nextRow - currRow) == -1 && (nextCol - currCol == 1)) {
				direction = 6;
			}
		} else {
			if ((nextRow - currRow) == 1 && (nextCol - currCol == -1)) {
				direction = 1;
			} else if ((nextRow - currRow) == 1 && (nextCol - currCol == 0)) {
				direction = 2;
			} else if ((nextRow - currRow) == 0 && (nextCol - currCol == -1)) {
				direction = 3;
			} else if ((nextRow - currRow) == 0 && (nextCol - currCol == 1)) {
				direction = 4;
			} else if ((nextRow - currRow) == -1 && (nextCol - currCol == -1)) {
				direction = 5;
			} else if ((nextRow - currRow) == -1 && (nextCol - currCol == 0)) {
				direction = 6;
			}
		}
		return direction;
	}

	public Bitmap screenShot(View view) {
		Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		view.draw(canvas);
		float top = (float) (view.getHeight() - 3 * height / 2 - height * ((3 * (rowLevel[level]) + 1) / 4.0));
		if (top < 0) {
			top = 0;
		}
		bitmap = Bitmap.createBitmap(bitmap, width, (int) top, width * columnLevel[level] + width / 2,
				view.getHeight() - 3 * height / 2 - (int) top);
		float scale = 0.50f;
		bitmap = Bitmap.createScaledBitmap(bitmap, (int) (scale * bitmap.getWidth()),
				(int) (scale * bitmap.getHeight()), false);
		return bitmap;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		// startActivity(intent);
		finish();
		startActivity(intent);
	}

	public static void callToast(int id) {
		// Getting the View object as defined in the customtoast.xml file
		toastText.setText("Level Unlocked");
		if (id >= 15)
			toastBee.setBackgroundResource(R.drawable.two_bee);
		else
			toastBee.setBackgroundResource(R.drawable.bee_easy);

		toastLevel.setBackgroundResource(levelDialogImages[id % 15]);
		// Creating the Toast object
		Toast toast = new Toast(toastConetxt);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setView(toastLayout);// setting the view of custom toast layout
		toast.show();
	}

	private void nextToUnlockToastShow() {
		nxtUnlock = new NextToUnlock();
		int wins_till_now = datasource.fetchWinValue(beeCount * 15 + level);
		int[] next_to_unlock_results = nxtUnlock.checkNextToUnlockLevel(beeCount * 15 + level, wins_till_now);

		if (next_to_unlock_results[1] != 0) {
			if (beeCount == 1)
				nextToUnlockToastBee.setBackgroundResource(R.drawable.two_bee);
			else
				nextToUnlockToastBee.setBackgroundResource(R.drawable.bee_easy);

			nextToUnlockToastLevel.setBackgroundResource(levelDialogImages[next_to_unlock_results[0] % 15]);

			nextToUnlockToastText
					.setText("Trap the bee " + (next_to_unlock_results[1] - wins_till_now) + "\n more times to unlock");

			Toast toast = new Toast(toastConetxt);
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			// toast.setDuration(2500);
			toast.setView(nextToUnlockToast);// setting the view of custom toast
												// layout
			toast.show();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		datasource.close();
		datasource = null;
	}

	private class FirstBeeAnimationHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_START_ANNIMATION:
				if (counterFirstBee == 0 && !firstBeeQueue.isEmpty()) {
					counterFirstBee++;
					animationParamsFirstBee((int) firstBeeQueue.remove());
					animatorSetFirstBee.start();
				} else
					counterFirstBee++;

			}
			super.handleMessage(msg);
		}
	}

	private class SecondBeeAnimationHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_START_ANNIMATION:
				if (counterSecondBee == 0 && !secondBeeQueue.isEmpty()) {
					counterSecondBee++;
					animationParamsSecondBee((int) secondBeeQueue.remove());
					animatorSetSecondBee.start();
				} else
					counterSecondBee++;
			}
			super.handleMessage(msg);
		}
	}

}
