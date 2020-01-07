package com.longtail360.autochessrpg.entity.log;

/**
 * Created by chanmenlung on 3/2/2019.
 */

public class TeamStatus {
    public long id;
    public String cardIds;
    public String levels;
    public String hps;

    private String[] cardIdsArray;
    private String[] levelsArray;
    private String[] hpsArray;
    public String[] getCardIdArray() {
        if(cardIdsArray == null){
            cardIdsArray = cardIds.split(",");
        }
        return cardIdsArray;
    }
    public String[] getLevelArray() {
        if(levelsArray == null){
            levelsArray = levels.split(",");
        }
        return levelsArray;
    }

    public String[] getHpArray() {
        if(hpsArray == null){
            hpsArray = hps.split(",");
        }
        return hpsArray;
    }
}
