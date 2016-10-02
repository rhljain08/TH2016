package com.b.trap.database;

public class NextToUnlock {
	public int[] checkNextToUnlockLevel(int id,int wins){
		int level=0;
		int winRequired = 0;
		int level_win[] = new int[2];
		switch(id){
		case 0:{
			if(wins < 5){
				level= 1;
				winRequired =5;
			}else if(wins < 10){
				level= 2;
				winRequired =10;
			}else if(wins < 15){
				level= 3;
				winRequired =15;
			}
		}
			break;
		case 1:{
			if(wins < 15){
				level= 4;
				winRequired =15;
			}
		}
			break;
		case 2:{
			if(wins < 15){
				level= 5;
				winRequired =15;
			}
		}
			break;
		case 3:{
			if(wins < 50){
				level= 6;
				winRequired =50;
			}
		}
			break;
		case 4:{
			if(wins < 50){
				level= 7;
				winRequired =50;
			}
		}
			break;
		case 5:{
			if(wins < 50){
				level= 8;
				winRequired =50;
			}
		}
			break;
		case 6:{
			if(wins < 200){
				level= 9;
				winRequired =200;
			}
		}
			break;
		case 7:{
			if(wins < 200){
				level= 10;
				winRequired =200;
			}
		}
			break;
		case 8:{
			if(wins < 200){
				level= 11;
				winRequired =200;
			}
		}
			break;
		case 9:{
			if(wins < 500){
				level= 12;
				winRequired =500;
			}
		}
			break;
		case 10:{
			if(wins < 500){
				level= 13;
				winRequired =500;
			}
		}
			break;
		case 11:{
			if(wins < 500){
				level= 14;
				winRequired =500;
			}
		}
			break;
		case 15:{
			if(wins < 5){
				level= 16;
				winRequired =5;
			}else if(wins < 10){
				level= 17;
				winRequired =10;
			}else if(wins < 15){
				level= 18;
				winRequired =15;
			}
		}
			break;
		case 16:{
			if(wins < 15){
				level= 19;
				winRequired =15;
			}
		}
			break;
		case 17:{
			if(wins < 15){
				level= 20;
				winRequired =15;
			}
		}
			break;
		case 18:{
			if(wins < 50){
				level= 21;
				winRequired =50;
			}
		}
			break;
		case 19:{
			if(wins < 50){
				level= 22;
				winRequired =50;
			}
		}
			break;
		case 20:{
			if(wins < 50){
				level= 23;
				winRequired =50;
			}
		}
			break;
		case 21:{
			if(wins < 200){
				level= 24;
				winRequired =200;
			}
		}
			break;
		case 22:{
			if(wins < 200){
				level= 25;
				winRequired =200;
			}
		}
			break;
		case 23:{
			if(wins < 200){
				level= 26;
				winRequired =200;
			}
		}
			break;
		case 24:{
			if(wins < 500){
				level= 27;
				winRequired = 500;
			}
		}
			break;
		case 25:{
			if(wins < 500){
				level= 28;
				winRequired = 500;
			}
		}
			break;
		case 26:{
			if(wins < 500){
				level= 29;
				winRequired = 500;
			}
		}
			break;
		}
		
		level_win[0] = level;
		level_win[1] = winRequired;
		
		return level_win;
	}
}