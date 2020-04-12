package com.longtail360.autochessrpg.entity.tactic;

import com.google.gson.annotations.Expose;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.skill.BaseSkill;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanmenlung on 21/10/2018.
 */

public class OptionItem {
    @Expose
    public String value;
    @Expose
    public String label;

    public String optionItemTitle;
    public List<OptionItem> optionItems;

    public OptionItem(String value, String label){
        this.value = value;
        this.label = label;
    }

    public int convertValueToInt(){
        return Integer.parseInt(value);
    }

    public static List<OptionItem> listNumber(int min, int max, int factor, String preLabel, String postLabel) {
        List<OptionItem> result = new ArrayList<>();
        OptionItem item;
        for (int i = min; i <= max; i=i+factor) {
            String istr = preLabel+i+postLabel;
            item = new OptionItem (i+"",istr);
            result.add (item);
        }
        return result;
    }

//    public static List<OptionItem> convertCardListToOptionList(List<Card> list){
//        List<OptionItem> result = new ArrayList<>();
//        for(Card obj : list){
//            OptionItem item = new OptionItem (obj.uuid,obj.name);
//            result.add (item);
//        }
//        return result;
//    }

//    public static List<OptionItem> convertConditionListToOptionList(List<BaseCondition> list){
//        List<OptionItem> result = new ArrayList<>();
//        for(BaseCondition obj : list){
//            OptionItem item = new OptionItem (obj.key,obj.desc);
//            result.add (item);
//        }
//        return result;
//    }
//
//    public static List<OptionItem> convertTargetListToOptionList(List<BaseTarget> creatures){
//        List<OptionItem> result = new ArrayList<>();
//        for(BaseTarget obj : creatures){
//            OptionItem item = new OptionItem (obj.key,obj.desc);
//            item.optionItems = obj.optionItems;
//            result.add (item);
//        }
//        return result;
//    }

//    public static List<OptionItem> convertActionListToOptionList(List<BaseAction> creatures){
//        List<OptionItem> result = new ArrayList<>();
//        for(BaseAction obj : creatures){
//            OptionItem item = new OptionItem (obj.key,obj.desc);
//            result.add (item);
//        }
//        return result;
//    }

    public static List<OptionItem> convertItemListToOptionList(List<Item> items){
        List<OptionItem> result = new ArrayList<>();
        for(Item obj : items){
            OptionItem item = new OptionItem (obj.itemCode,obj.itemCode);
//            item.optionItemTitle = obj.optionTitle();
//            item.optionItems = obj.listOptions();
            result.add (item);
        }
        return result;
    }

//    public static List<OptionItem> convertSkillListToOptionList(List<BaseSkill> skills){
//        List<OptionItem> result = new ArrayList<>();
//        for(BaseSkill obj : skills){
//            OptionItem item = new OptionItem (obj.code,obj.getDesc());
//            result.add (item);
//        }
//        return result;
//    }

}

