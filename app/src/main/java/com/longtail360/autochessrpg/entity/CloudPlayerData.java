package com.longtail360.autochessrpg.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CloudPlayerData implements Serializable {
    public int crystal;
    public int isOldPlayer;
    public String tacticsJson;
    public List<CustomCard> customCards = new ArrayList<>();

    public CloudPlayerData(Player player) {
        this.crystal = player.crystal;
        this.isOldPlayer = player.isOldPlayer;
        this.tacticsJson = player.tacticsJson;
        this.customCards = player.customCards;
    }
}
