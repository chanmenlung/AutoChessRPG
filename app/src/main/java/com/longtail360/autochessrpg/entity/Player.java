package com.longtail360.autochessrpg.entity;

import com.google.gson.annotations.Expose;
import com.longtail360.autochessrpg.entity.tactic.Tactics;

import java.util.ArrayList;
import java.util.List;

public class Player {
    @Expose
    public int crystal;
    @Expose
    public List<String> unlockCards = new ArrayList<>();
    @Expose
    public List<Tactics> tacticsList = new ArrayList<> ();
}
