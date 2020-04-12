package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.utils.ImageUtils;
import com.longtail360.autochessrpg.utils.Logger;

import java.io.File;

/**
 * Created by chanmenlung on 9/2/2019.
 */

public class ItemLogDesc extends LinearLayout {
    private String tag = "ItemLogDesc";
    public ImageView itemIcon;
    public TextView title;
    public TextView desc;

    public ItemLogDesc(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_log_desc, this);
        itemIcon = (ImageView)findViewById(R.id.itemIcon);
        title = (TextView)findViewById(R.id.title);
        desc = (TextView)findViewById(R.id.desc);
    }
    public ItemLogDesc(Context context, final String titleStr, String contentStr, int rid) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_log_desc, this);
        itemIcon = (ImageView)findViewById(R.id.itemIcon);
        title = (TextView)findViewById(R.id.title);
        desc = (TextView)findViewById(R.id.desc);
        refresh(titleStr,contentStr,rid);
    }

    public void refresh(String titleStr, String contentStr, int rid){
        if(rid !=0){
            itemIcon.setImageResource(rid);
        }
        if(title != null){
            Logger.log(tag, "set itemlogdesc-title:"+titleStr);
            title.setText(titleStr);
        }
        if(contentStr != null){
            Logger.log(tag, "set itemlogdesc-contentStr:"+contentStr);
            desc.setText(contentStr);
        }
    }

    public void refresh(Context context, String titleStr, String contentStr, String defaultHead, String headBase64){
        if(headBase64 != null) {
            itemIcon.setImageBitmap(ImageUtils.convertBase64ToImage(headBase64));
        }else {
                itemIcon.setImageResource(ImageUtils.convertImageStringToInt(context, defaultHead));
        }
        if(title != null){
            title.setText(titleStr);
        }
        if(contentStr != null){
            desc.setText(contentStr);
        }
    }
}
