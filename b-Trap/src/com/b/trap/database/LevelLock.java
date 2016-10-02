package com.b.trap.database;

public class LevelLock {
	public int checkLockLevel(int id,int wins){
		int level=0;
		switch(id){
		case 0:{
			if(wins == 5){
				level= 1;
			}else if(wins == 10){
				level= 2;
			}else if(wins == 15){
				level= 3;
			}
		}
			break;
		case 1:{
			if(wins == 15){
				level= 4;
			}
		}
			break;
		case 2:{
			if(wins == 15){
				level= 5;
			}
		}
			break;
		case 3:{
			if(wins == 50){
				level= 6;
			}
		}
			break;
		case 4:{
			if(wins == 50){
				level= 7;
			}
		}
			break;
		case 5:{
			if(wins == 50){
				level= 8;
			}
		}
			break;
		case 6:{
			if(wins == 200){
				level= 9;
			}
		}
			break;
		case 7:{
			if(wins == 200){
				level= 10;
			}
		}
			break;
		case 8:{
			if(wins == 200){
				level= 11;
			}
		}
			break;
		case 9:{
			if(wins == 500){
				level= 12;
			}
		}
			break;
		case 10:{
			if(wins == 500){
				level= 13;
			}
		}
			break;
		case 11:{
			if(wins == 500){
				level= 14;
			}
		}
			break;
		case 15:{
			if(wins == 5){
				level= 16;
			}else if(wins == 10){
				level= 17;
			}else if(wins == 15){
				level= 18;
			}
		}
			break;
		case 16:{
			if(wins == 15){
				level= 19;
			}
		}
			break;
		case 17:{
			if(wins == 15){
				level= 20;
			}
		}
			break;
		case 18:{
			if(wins == 50){
				level= 21;
			}
		}
			break;
		case 19:{
			if(wins == 50){
				level= 22;
			}
		}
			break;
		case 20:{
			if(wins == 50){
				level= 23;
			}
		}
			break;
		case 21:{
			if(wins == 200){
				level= 24;
			}
		}
			break;
		case 22:{
			if(wins == 200){
				level= 25;
			}
		}
			break;
		case 23:{
			if(wins == 200){
				level= 26;
			}
		}
			break;
		case 24:{
			if(wins == 500){
				level= 27;
			}
		}
			break;
		case 25:{
			if(wins == 500){
				level= 28;
			}
		}
			break;
		case 26:{
			if(wins == 500){
				level= 29;
			}
		}
			break;
		}
		return level;
	}
}