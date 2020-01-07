package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.log.ProcessLog;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.Date;

/**
 * Created by chanmenlung on 6/10/2018.
 */

public class ProcessLogItem extends LinearLayout {
    private String tag = "ProcessLogItem";
    public static int RED = 1;
    public static int GREEN = 2;
    public static int BLUE = 3;
    public static int YELLOW = 4;
    private ViewGroup processItemLayout;
    private TextView content;
    private TextView title;
    private ImageView icon1;
    private ImageView icon2;
    private ImageView featherView;
//    private TextView time;
    private View line;
    public ProcessLogItem(Context context, ProcessLog pLog, int color) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.process_log_desc_item, this);
        processItemLayout = (ViewGroup)findViewById(R.id.processItemLayout);
        line = findViewById(R.id.line);
        title = ((TextView)findViewById(R.id.processItemTitle));
        content = ((TextView)findViewById(R.id.processItemContent));
//        time = findViewById(R.id.time);
        title.setText(pLog.title);
        icon1 = (ImageView)findViewById(R.id.icon1);
        Logger.log(tag, pLog.title);
        Logger.log(tag, pLog.icon1);
        int resourceId = getResources().getIdentifier(pLog.icon1, "drawable",
                context.getPackageName());
        icon1.setImageResource(resourceId);

        if(pLog.icon2 != null){
            icon2 = (ImageView)findViewById(R.id.icon2);
            int resourceId2 = getResources().getIdentifier(pLog.icon2, "drawable",
                    context.getPackageName());
            icon2.setImageResource(resourceId2);
            icon2.setVisibility(VISIBLE);
        }
        if(pLog.content != null){
            line.setVisibility(VISIBLE);
            content = ((TextView)findViewById(R.id.processItemContent));
            content.setText(pLog.content);
            content.setVisibility(VISIBLE);
        }

        if(color == RED){ //red
            title.setTextColor(Color.parseColor("#FFD81B60"));
            content.setTextColor(Color.parseColor("#FFD81B60"));
        }else if(color == GREEN){ //green
            title.setTextColor(Color.parseColor("#FF008577"));
            content.setTextColor(Color.parseColor("#FFD81B60"));
        }else if(color == BLUE){ //blue
            title.setTextColor(Color.parseColor("#FF0027f7"));
            content.setTextColor(Color.parseColor("#FFD81B60"));
        }
        String timeStr = DateFormat.format("HH.mm", new Date(pLog.logTime)).toString();
//        time.setText(timeStr);
    }
}
