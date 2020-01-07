package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.utils.ImageUtils;
import com.longtail360.autochessrpg.utils.Logger;

public class ItemIcon extends FrameLayout {
    private String tag = "ItemIcon";
    public ImageView itemIcon;

    public ItemIcon(Context context, final Item item) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_icon, this);
        itemIcon = (ImageView)findViewById(R.id.itemIcon);
        Logger.log(tag, "item.itemCode:"+item.itemCode);
        itemIcon.setImageResource(ImageUtils.convertImageStringToInt(context, item.imageName));


    }



}
