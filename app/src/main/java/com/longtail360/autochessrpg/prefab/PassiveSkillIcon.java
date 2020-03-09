package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.passiveskill.BasePassiveSkill;
import com.longtail360.autochessrpg.utils.Logger;

public class PassiveSkillIcon extends FrameLayout {
    private String tag = "PassiveSkillIcon";
    private ImageView icon;
    private TextView number;
    public PassiveSkillIcon(Context context, BasePassiveSkill basePassiveSkil) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.passive_skill_icon, this);
        icon = findViewById(R.id.icon);
        number = findViewById(R.id.number);
        if(basePassiveSkil != null){
            if(basePassiveSkil.raceClass.equals(Card.CLAZZ_MAGE)){
                icon.setImageResource(R.drawable.class_mage);
            }else if(basePassiveSkil.raceClass.equals(Card.CLAZZ_WARRIOR)){
                icon.setImageResource(R.drawable.class_warrior);
            }else if(basePassiveSkil.raceClass.equals(Card.CLAZZ_PRIEST)){
                icon.setImageResource(R.drawable.class_priest);
            }else if(basePassiveSkil.raceClass.equals(Card.CLAZZ_HUNTER)){
                icon.setImageResource(R.drawable.class_hunter);
            }else if(basePassiveSkil.raceClass.equals(Card.CLAZZ_KNIGHT)){
                icon.setImageResource(R.drawable.class_knight);
            }else if(basePassiveSkil.raceClass.equals(Card.CLAZZ_SHAMAN)){
                icon.setImageResource(R.drawable.class_shaman);
            }else if(basePassiveSkil.raceClass.equals(Card.CLAZZ_ROGUE)){
                icon.setImageResource(R.drawable.class_rogue);
            }else if(basePassiveSkil.raceClass.equals(Card.CLAZZ_WARLOCK)){
                icon.setImageResource(R.drawable.class_warlock);
            }else if(basePassiveSkil.raceClass.equals(Card.RACE_HUMAN)){
                icon.setImageResource(R.drawable.race_human);
            }else if(basePassiveSkil.raceClass.equals(Card.RACE_ELF)){
                icon.setImageResource(R.drawable.race_elf);
            }else if(basePassiveSkil.raceClass.equals(Card.RACE_SPIRIT)){
                icon.setImageResource(R.drawable.race_spirit);
            }else if(basePassiveSkil.raceClass.equals(Card.RACE_UNDEAD)){
                icon.setImageResource(R.drawable.race_undead);
            }else if(basePassiveSkil.raceClass.equals(Card.RACE_ORCS)){
                icon.setImageResource(R.drawable.race_orc);
            }else if(basePassiveSkil.raceClass.equals(Card.RACE_DEMON)){
                icon.setImageResource(R.drawable.race_demon);
            }else if(basePassiveSkil.raceClass.equals(Card.RACE_DIVINITY)){
                icon.setImageResource(R.drawable.race_divinity);
            }else if(basePassiveSkil.raceClass.equals(Card.RACE_MARINE)){
                icon.setImageResource(R.drawable.race_marine);
            }else if(basePassiveSkil.raceClass.equals(Card.RACE_DWARF)){
                icon.setImageResource(R.drawable.race_dwarf);
            }else if(basePassiveSkil.raceClass.equals(Card.RACE_GOO)){
                icon.setImageResource(R.drawable.race_goo);
            }
            Logger.log(tag, basePassiveSkil.code+basePassiveSkil.number2);
            Logger.log(tag, ""+basePassiveSkil.isActive3);
            if(basePassiveSkil.activeNumber > 0){
                number.setText(basePassiveSkil.activeNumber+"/"+basePassiveSkil.number1);
            }
            if(basePassiveSkil.isActive1){
                if(basePassiveSkil.number2 > 0) {
                    number.setText(basePassiveSkil.activeNumber+"/"+basePassiveSkil.number2);
                }else {
                    number.setText(basePassiveSkil.activeNumber+"/"+basePassiveSkil.number1);
                }
            }
            if(basePassiveSkil.isActive2){
                if(basePassiveSkil.number3 > 0) {
                    number.setText(basePassiveSkil.activeNumber+"/"+basePassiveSkil.number3);
                }else {
                    number.setText(basePassiveSkil.activeNumber+"/"+basePassiveSkil.number2);
                }
            }
            if(basePassiveSkil.isActive3){
                number.setText(basePassiveSkil.activeNumber+"/"+basePassiveSkil.number3);
            }
            Logger.log(tag, "number:"+number.getText().toString());
        }
    }
}
