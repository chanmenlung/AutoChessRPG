package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.billingclient.api.SkuDetails;
import com.longtail360.autochessrpg.R;

public class BuyCrystalDescItem extends LinearLayout {
    private ImageView crystalIcon;
    private TextView content;
    private TextView crystalNum;
    private TextView price;
    private View buyBt;
    private int crystal;
    public BuyCrystalDescItem(Context context, final SkuDetails skuDetails, final BuyCrystalDescItem.CallBack callBack, final int crystal, final String coin) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.buy_crystal_desc_item, this);
        crystalIcon = findViewById(R.id.crystalIcon);
        content = findViewById(R.id.content);
        price = findViewById(R.id.price);
        crystalNum = findViewById(R.id.crystalNum);
        crystalNum.setText(crystal+"");
        price.setText(coin+"");
        this.crystal = crystal;
        buyBt = findViewById(R.id.buyBt);
        buyBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.doActionAfterClickBt(BuyCrystalDescItem.this, skuDetails, crystal);
            }
        });
    }

    public interface CallBack {
        public void doActionAfterClickBt(BuyCrystalDescItem focusItem, SkuDetails skuDetails, int crystal);
    }

}
