package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.billingclient.api.SkuDetails;
import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.passiveskill.BasePassiveSkill;
import com.longtail360.autochessrpg.utils.Logger;

public class PassiveSkillDescLayout extends FrameLayout {
    private String tag = "PassiveSkillDescLayout";
    private View raceClassValue;
    private ImageView iconImage;
    private View descLayout1;
    private View descLayout2;
    private View descLayout3;
    private TextView descNumber1;
    private TextView descContent1;
    private TextView descNumber2;
    private TextView descContent2;
    private TextView descNumber3;
    private TextView descContent3;
    private View centerConfirmButton;
    public PassiveSkillDescLayout(Context context, BasePassiveSkill basePassiveSkill) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.passive_skill_desc_layout, this);
        raceClassValue = findViewById(R.id.raceClassValue);
        centerConfirmButton = findViewById(R.id.centerConfirmButton);
        iconImage = findViewById(R.id.iconImage);
        descLayout1 = findViewById(R.id.descLayout1);
        descLayout2 = findViewById(R.id.descLayout2);
        descLayout3 = findViewById(R.id.descLayout3);

        descNumber1 = findViewById(R.id.descNumber1);
        descContent1 = findViewById(R.id.descContent1);
        descNumber2 = findViewById(R.id.descNumber2);
        descContent2 = findViewById(R.id.descContent2);
        descNumber3 = findViewById(R.id.descNumber3);
        descContent3 = findViewById(R.id.descContent3);
        descLayout1.setVisibility(INVISIBLE);
        descLayout2.setVisibility(INVISIBLE);
        descLayout3.setVisibility(INVISIBLE);
        centerConfirmButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                PassiveSkillDescLayout.this.setVisibility(GONE);
            }
        });
    }

    public void reload(BasePassiveSkill basePassiveSkill){
        Logger.log(tag, "reload PassiveSkillDescLayout");
        descNumber1.setTextColor(Color.WHITE);
        descContent1.setTextColor(Color.WHITE);
        descNumber2.setTextColor(Color.WHITE);
        descContent2.setTextColor(Color.WHITE);
        descNumber3.setTextColor(Color.WHITE);
        descContent3.setTextColor(Color.WHITE);
        if(basePassiveSkill != null) {
            if(basePassiveSkill.raceClass.equals(Card.CLAZZ_MAGE)){
                iconImage.setImageResource(R.drawable.class_mage);
            }else if(basePassiveSkill.raceClass.equals(Card.CLAZZ_WARRIOR)){
                iconImage.setImageResource(R.drawable.class_warrior);
            }else if(basePassiveSkill.raceClass.equals(Card.CLAZZ_PRIEST)){
                iconImage.setImageResource(R.drawable.class_priest);
            }else if(basePassiveSkill.raceClass.equals(Card.CLAZZ_HUNTER)){
                iconImage.setImageResource(R.drawable.class_hunter);
            }else if(basePassiveSkill.raceClass.equals(Card.CLAZZ_KNIGHT)){
                iconImage.setImageResource(R.drawable.class_knight);
            }else if(basePassiveSkill.raceClass.equals(Card.CLAZZ_SHAMAN)){
                iconImage.setImageResource(R.drawable.class_shaman);
            }else if(basePassiveSkill.raceClass.equals(Card.CLAZZ_ROGUE)){
                iconImage.setImageResource(R.drawable.class_rogue);
            }else if(basePassiveSkill.raceClass.equals(Card.CLAZZ_WARLOCK)){
                iconImage.setImageResource(R.drawable.class_warlock);
            }else if(basePassiveSkill.raceClass.equals(Card.RACE_HUMAN)){
                iconImage.setImageResource(R.drawable.race_human);
            }else if(basePassiveSkill.raceClass.equals(Card.RACE_ELF)){
                iconImage.setImageResource(R.drawable.race_elf);
            }else if(basePassiveSkill.raceClass.equals(Card.RACE_SPIRIT)){
                iconImage.setImageResource(R.drawable.race_spirit);
            }else if(basePassiveSkill.raceClass.equals(Card.RACE_UNDEAD)){
                iconImage.setImageResource(R.drawable.race_undead);
            }else if(basePassiveSkill.raceClass.equals(Card.RACE_ORCS)){
                iconImage.setImageResource(R.drawable.race_orc);
            }else if(basePassiveSkill.raceClass.equals(Card.RACE_DEMON)){
                iconImage.setImageResource(R.drawable.race_demon);
            }else if(basePassiveSkill.raceClass.equals(Card.RACE_DIVINITY)){
                iconImage.setImageResource(R.drawable.race_divinity);
            }else if(basePassiveSkill.raceClass.equals(Card.RACE_MARINE)){
                iconImage.setImageResource(R.drawable.race_marine);
            }else if(basePassiveSkill.raceClass.equals(Card.RACE_DWARF)){
                iconImage.setImageResource(R.drawable.race_dwarf);
            }else if(basePassiveSkill.raceClass.equals(Card.RACE_GOO)){
                iconImage.setImageResource(R.drawable.race_goo);
            }

            if(basePassiveSkill.desc1 != null){
                descLayout1.setVisibility(VISIBLE);
                descNumber1.setText(basePassiveSkill.number1+"");
                descContent1.setText(basePassiveSkill.desc1);
                if(basePassiveSkill.isActive1){
                    descNumber1.setTextColor(Color.GREEN);
                    descContent1.setTextColor(Color.GREEN);
                }
            }
            if(basePassiveSkill.desc2 != null && basePassiveSkill.number2 > 0){
                descLayout2.setVisibility(VISIBLE);
                descNumber2.setText(basePassiveSkill.number2+"");
                descContent2.setText(basePassiveSkill.desc2);
                if(basePassiveSkill.isActive2){
                    descNumber2.setTextColor(Color.GREEN);
                    descContent2.setTextColor(Color.GREEN);
                }
            }
            if(basePassiveSkill.desc3 != null && basePassiveSkill.number3 > 0) {
                descLayout3.setVisibility(VISIBLE);
                descNumber3.setText(basePassiveSkill.number3+"");
                descContent3.setText(basePassiveSkill.desc3);
                if(basePassiveSkill.isActive3){
                    descNumber3.setTextColor(Color.GREEN);
                    descContent3.setTextColor(Color.GREEN);
                }
            }
        }
    }


}
