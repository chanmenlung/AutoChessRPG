package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.longtail360.autochessrpg.R;

import java.util.List;

/**
 * Created by chanmenlung on 24/10/2018.
 */

public class DropDown extends FrameLayout {
    public Spinner spinner;
    private TextView ddPreLabel;
    private TextView ddPostLabel;
    public DropDown(Context context, List<String> items, int initPos) {
        this(context, items, initPos, null, null);
    }

    public DropDown(Context context, List<String> items, int initPos, String preLabel, String posLabel) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.drop_down, this);
        spinner = (Spinner)findViewById(R.id.spinner);
        ddPreLabel = (TextView)findViewById(R.id.ddPreLabel);
        ddPostLabel = (TextView)findViewById(R.id.ddPostLabel);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(initPos);
        setPrePostLabel(preLabel, posLabel);
    }

//    public int getSelectedPosition() {
//        return spinner.getSelectedItemPosition();
//    }
//    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener){
//        spinner.setOnItemSelectedListener(listener);
//    }

    public static DropDown create(Context context, ViewGroup parent, List<String> items, int initPos){
        DropDown dropDown = new DropDown(context,items,initPos);
        parent.addView(dropDown);
        return dropDown;
    }
    public static void update(Context context, DropDown dd, List<String> items, int initPos){
        dd.spinner.setAdapter(null);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dd.spinner.setAdapter(dataAdapter);
        dd.spinner.setSelection(initPos);
    }

    public void setPrePostLabel(String preLabel, String posLabel ){
        ddPreLabel.setVisibility(VISIBLE);
        ddPostLabel.setVisibility(VISIBLE);
        if(preLabel != null){
            ddPreLabel.setText(preLabel);
        }else {
            ddPreLabel.setVisibility(GONE);
        }
        if(posLabel != null){
            ddPostLabel.setText(posLabel);
        }else {
            ddPostLabel.setVisibility(GONE);
        }
    }
}
