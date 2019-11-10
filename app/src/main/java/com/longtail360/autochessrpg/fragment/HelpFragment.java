package com.longtail360.autochessrpg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.utils.Logger;

public class HelpFragment extends Fragment {
    String tag = "HelpFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logger.log(tag,"init-HelpFragment");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.help, container, false);
    }
}
