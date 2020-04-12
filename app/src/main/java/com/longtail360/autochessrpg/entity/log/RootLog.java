package com.longtail360.autochessrpg.entity.log;

import java.util.List;

/**
 * Created by chanmenlung on 20/1/2019.
 */

public class RootLog {
    public static int ICON1_TYPE_NOT_CARD = 1;
    public static int ICON1_TYPE_CARD = 2;
    public static int ADV_STATUS_SUCCESS = 1;
    public static int ADV_STATUS_FAIL = 2;
    public static int ADV_STATUS_PROGRESSING = 3;
    public long id;
    public long dungeonId; //dungeon index
    public int advStatus;  //1:success, 2:fail, 3:progressing
    public int isComingBack; //0:progressing, 1:comingBack
    public int numOfDead;
    public long startingTime;
    public int isHistoryLog; //0=false, 1=true, if is not history log, get price update buying card when this log is finish
    public int progress;
    public int currentFloor; //start from 0
    public int currentArea; //start from 0
    public int currentBlock; //start from 0
    public int startingCoin; //for calculate interest
    public RootLog() {
    }

    public RootLog(int id, long dungeonId, int advStatus, int numOfDead, long startingTime) {
        this.id = id;
        this.dungeonId = dungeonId;
        this.advStatus = advStatus;
        this.numOfDead = numOfDead;
        this.startingTime = startingTime;
    }
}
