package com.longtail360.autochessrpg.entity;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Player {
    @Expose
    public int crystal;
    @Expose
    public List<String> unlockCards = new ArrayList<>();
}
