package com.longtail360.autochessrpg.fragment;

import android.content.Context;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.longtail360.autochessrpg.prefab.PopupBox;

public class BaseFragment extends Fragment {
    public PopupBox popupBox;
    public void initPopupBox(ViewGroup thisLayout, Context context) {
        popupBox = new PopupBox(context, 0);
        thisLayout.addView(popupBox);
    }
}
