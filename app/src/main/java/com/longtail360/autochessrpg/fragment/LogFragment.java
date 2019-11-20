package com.longtail360.autochessrpg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.utils.Logger;

public class LogFragment extends BaseFragment{
    String tag = "LogFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logger.log(tag,"init-LogFragment");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.adv_log, container, false);
    }
}
