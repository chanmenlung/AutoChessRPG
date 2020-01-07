package com.longtail360.autochessrpg.entity.log;

import java.util.List;

/**
 * Created by chanmenlung on 3/2/2019.
 */

public class BattleRootLog {
    public long id;
    public int numOfTurns;
    public int success;
    public String monsterImage; //use this name to get image
    public String monsterName;
    public List<BattleItemLog> battleItemLogList;
    public ProcessLog processLog;
}
