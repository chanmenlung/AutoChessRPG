package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.utils.ImageUtils;
import com.longtail360.autochessrpg.utils.Logger;

public class MonsterDescItem extends LinearLayout {
    private String tag = "MonsterDescItem";
    private TextView codeValue;
    private TextView nameValue;
    private TextView hpValue;
    private TextView attackValue;
    private TextView defenseValue;
    private ImageView monsterIcon;
    public MonsterDescItem(Context context, Monster monster) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.monster_desc_item, this);
        codeValue = findViewById(R.id.codeValue);
        nameValue = findViewById(R.id.nameValue);
        hpValue = findViewById(R.id.hpValue);
        attackValue = findViewById(R.id.attackValue);
        defenseValue = findViewById(R.id.defenseValue);
        monsterIcon = findViewById(R.id.monsterIcon);
        codeValue.setText(monster.code);
        nameValue.setText(monster.name);
        hpValue.setText(monster.getHp()+"");
        attackValue.setText(monster.attack+"");
        defenseValue.setText(monster.defense+"");
        Logger.log(tag, monster.code);
        monsterIcon.setImageResource(ImageUtils.convertImageStringToInt(context, monster.code));
    }
}
