package com.b.trap.database;

public class LockStatus {

	public static int checkLockStatus(int id){
		int level = 0;
		switch (id) {
		case 1:
			level = 0;
			
			break;
		case 2:
			level = 0;

			break;
		case 3:
			level = 0;

			break;
		case 4:
			level = 1;

			break;
		case 5:
			level = 2;

			break;
		case 6:
			level = 3;

			break;
		case 7:
			level = 4;

			break;

		case 8:
			level = 5;

			break;
		case 9:
			level = 6;

			break;
		case 10:
			level = 7;

			break;
		case 11:
			level = 8;

			break;
		case 12:
			level = 9;

			break;
		case 13:
			level = 10;

			break;
		case 14:
			level = 11;

			break;

		case 16:
			level = 15;

			break;
		case 17:
			level = 15;

			break;
		case 18:
			level = 15;

			break;
		case 19:
			level = 16;

			break;
		case 20:
			level = 17;

			break;
		case 21:
			level = 18;

			break;
		case 22:
			level = 19;

			break;
		case 23:
			level = 20;

			break;
		case 24:
			level = 21;

			break;
		case 25:
			level = 22;

			break;
		case 26:
			level = 23;

			break;
		case 27:
			level = 24;

			break;
		case 28:
			level = 25;

			break;
		case 29:
			level = 26;

			break;

		}

		return level;
	}
}
