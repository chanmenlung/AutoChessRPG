package com.longtail360.autochessrpg.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.dao.AdventureDAO;
import com.longtail360.autochessrpg.dao.CardDAO;
import com.longtail360.autochessrpg.dao.DungeonDAO;
import com.longtail360.autochessrpg.dao.GameDBHelper;
import com.longtail360.autochessrpg.dao.ItemDAO;
import com.longtail360.autochessrpg.dao.ItemGotDAO;
import com.longtail360.autochessrpg.dao.MonsterDAO;
import com.longtail360.autochessrpg.dao.log.BattleItemLogDAO;
import com.longtail360.autochessrpg.dao.log.BattleRootLogDAO;
import com.longtail360.autochessrpg.dao.log.ProcessLogDAO;
import com.longtail360.autochessrpg.dao.log.RootLogDAO;
import com.longtail360.autochessrpg.entity.Adventure;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.Dungeon;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.MyItem;
import com.longtail360.autochessrpg.entity.passiveskill.BasePassiveSkill;
import com.longtail360.autochessrpg.entity.skill.BaseSkill;
import com.longtail360.autochessrpg.prefab.DropDown;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MonsterValueActivity  extends BaseActivity{
    private String tag = "MonsterValueActivity";
    public static String MEET_MONSTER = "MEET_MONSTER";
    public static String GOOD_EVENT = "GOOD_EVENT";
    public static String BAD_EVENT = "BAD_EVENT";
    public static String HP = "HP";
    public static String ATTACK = "ATTACK";
    public static String DEFENSE = "DEFENSE";
    public static String SKILL = "SKILL";

    private EditText meetMonster;
    private EditText goodEvent;
    private EditText badEvent;
    private EditText hp;
    private EditText attack;
    private EditText defense;
    private ViewGroup skillValues;
    private View saveBt;
    private View resetDBBt;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monster_value);

        meetMonster = findViewById(R.id.meetMonster);
        goodEvent = findViewById(R.id.goodEvent);
        badEvent = findViewById(R.id.badEvent);
        hp = findViewById(R.id.hp);
        attack = findViewById(R.id.attack);
        defense = findViewById(R.id.defense);
        saveBt = findViewById(R.id.saveBt);
        resetDBBt = findViewById(R.id.resetDBBt);
        skillValues = findViewById(R.id.skillValues);
        prefs = PreferenceManager.getDefaultSharedPreferences(MonsterValueActivity.this);
        editor = prefs.edit();
        meetMonster.setText(prefs.getInt(MonsterValueActivity.MEET_MONSTER,0)+"");
        goodEvent.setText(prefs.getInt(MonsterValueActivity.GOOD_EVENT,0)+"");
        badEvent.setText(prefs.getInt(MonsterValueActivity.BAD_EVENT,0)+"");
        hp.setText(prefs.getInt(MonsterValueActivity.HP,0)+"");
        attack.setText(prefs.getInt(MonsterValueActivity.ATTACK,0)+"");
        defense.setText(prefs.getInt(MonsterValueActivity.DEFENSE,0)+"");
        final List<String> skills = listSkillNames();
        DropDown dd1 = DropDown.create(this, skillValues,skills,0);
        dd1.spinner.setSelected(false);
//        dd1.spinner.setSelection(index,false);
        dd1.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Logger.log(tag, skills.get(i));
                editor.putString(SKILL, skills.get(i));
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        saveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt(MEET_MONSTER, Integer.parseInt(meetMonster.getText().toString()));
                editor.putInt(GOOD_EVENT, Integer.parseInt(goodEvent.getText().toString()));
                editor.putInt(BAD_EVENT, Integer.parseInt(badEvent.getText().toString()));
                editor.putInt(HP, Integer.parseInt(hp.getText().toString()));
                editor.putInt(ATTACK, Integer.parseInt(attack.getText().toString()));
                editor.putInt(DEFENSE, Integer.parseInt(defense.getText().toString()));
                editor.commit();
                Logger.toast("Saved", MonsterValueActivity.this);
            }
        });

        resetDBBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameDBHelper.getDatabase(MonsterValueActivity.this).execSQL("delete from " + AdventureDAO.TABLE_NAME);
                GameDBHelper.getDatabase(MonsterValueActivity.this).execSQL("delete from " + CardDAO.TABLE_NAME);
                GameDBHelper.getDatabase(MonsterValueActivity.this).execSQL("delete from " + DungeonDAO.TABLE_NAME);
                GameDBHelper.getDatabase(MonsterValueActivity.this).execSQL("delete from " + ItemDAO.TABLE_NAME);
                GameDBHelper.getDatabase(MonsterValueActivity.this).execSQL("delete from " + ItemGotDAO.TABLE_NAME);
                GameDBHelper.getDatabase(MonsterValueActivity.this).execSQL("delete from " + MonsterDAO.TABLE_NAME);

                GameDBHelper.getDatabase(MonsterValueActivity.this).execSQL("delete from " + BattleRootLogDAO.TABLE_NAME);
                GameDBHelper.getDatabase(MonsterValueActivity.this).execSQL("delete from " + BattleItemLogDAO.TABLE_NAME);
                GameDBHelper.getDatabase(MonsterValueActivity.this).execSQL("delete from " + ProcessLogDAO.TABLE_NAME);
                GameDBHelper.getDatabase(MonsterValueActivity.this).execSQL("delete from " + RootLogDAO.TABLE_NAME);
//                GameDBHelper.getDatabase(MonsterValueActivity.this).execSQL("delete from " + TeamStatusDAO.TABLE_NAME);

                Card.init(MonsterValueActivity.this);
                BaseSkill.init(MonsterValueActivity.this);
                Dungeon.init(MonsterValueActivity.this);
                Item.init(MonsterValueActivity.this);
                Monster.init(MonsterValueActivity.this);

                GameContext.gameContext.passiveSkillList = BasePassiveSkill.listAll(MonsterValueActivity.this);
                Dungeon firestDungeon =  GameContext.gameContext.dungeonDAO.getAll().get(0);
                List<Adventure> advs =  GameContext.gameContext.advDAO.getAll();
                if(advs == null || advs.size() == 0) {
                    GameContext.gameContext.adventure = new Adventure(100, firestDungeon.index, 0, 1,0,100);
                    GameContext.gameContext.advDAO.insert(GameContext.gameContext.adventure);
                    insertCardForBuyingOnNewAccount(GameContext.gameContext.adventure.id);
                    insertItemGotOnNewAccount(GameContext.gameContext.adventure.id);
                }else {
                    GameContext.gameContext.adventure = advs.get(0);

                    GameContext.gameContext.getPlayer(MonsterValueActivity.this).concreteConds(MonsterValueActivity.this);
                    GameContext.gameContext.getPlayer(MonsterValueActivity.this).concreteAction(MonsterValueActivity.this);
                }

                Logger.toast("Saved", MonsterValueActivity.this);
            }
        });
    }

    private List<String> listSkillNames() {
        List<String> result = new ArrayList<>();
        result.add("HealSingle");
        result.add("HealAll");
        result.add("HitSingleHurt");
        result.add("FireAllHurt");
        result.add("PotionAllHurt");
        result.add("FireSingleHurt");
        result.add("PotionAllBigHurt");
        result.add("SummonMonsterHeal");
        result.add("FireAllBigHurt");
        result.add("HitAllAndDodgeAttackTeam");
        result.add("HitAllBigHurt");
        result.add("HealAllBig");
        result.add("Connect2Monster");
        result.add("HitSingleBigHurt");
        result.add("SummonMonsterLarge");
        result.add("HitAllHurt");
        result.add("TauntAndHeal");
        result.add("TauntAndDefenseUp");
        result.add("ConnectMyselfAndMonster");
        result.add("ValueUpAllAttack");
        result.add("PotionSingleBigHurt");
        result.add("PotionSingleHurt");
        result.add("FireSingleBigHurt");
        result.add("IceSingleBigHurt");
        result.add("ConnectAllMonster");
        result.add("ValueDownOneDefense");
        result.add("ValueUpOneDefense");
        result.add("HitDoubleTime");
        result.add("ValueUpAttackDeadNumber");
        result.add("ValueUpDefenseDeadNumber");
        result.add("ValueUpAttackLowHp");
        result.add("ValueUpAttackTurnNumber");
        result.add("ValueUpDefenseTurnNumber");
        result.add("ValueUpOneAttack");
        result.add("ValueDownOneAttack");
        result.add("SummonMonsterMiddle");
        result.add("ValueUpAllDefense");
        result.add("ValueDownAllAttack");
        result.add("HitIgnoreDefenseSingle");
        result.add("ValueDownAllDefense");
        result.add("SummonMonsterSmall");
        result.add("IceAllBigHurt");
        result.add("IceAllHurt");
        result.add("RandomAttackOfMonster");
        result.add("IceSingleHurt");
        result.add("ElectricSingleHurt");
        result.add("ConnectMyselfAndAllMonster");
        result.add("ZeroHurtOnMyself");
        result.add("ReflectAttackOnMyself");
        result.add("TauntAndDodgeAttackUp");
        result.add("HitIgnoreDefenseSingleBig");
        result.add("ValueUpDefenseLowHp");
        result.add("HitPercentage");
        result.add("HitSingleAndDodgeAttack");
        result.add("ReflectAttackOnTeam");
        return result;
    }

    private void insertItemGotOnNewAccount(long advId) {
        MyItem item;
        item = new MyItem();
        item.onBody = 1;
        item.item = GameContext.gameContext.itemDAO.getByItemCode(Item.ITEM_HP_UP);
        item.itemId = item.item.id;
        item.adventureId = advId;
        GameContext.gameContext.itemGotDAO.insert(item);

        item = new MyItem();
        item.onBody = 1;
        item.item = GameContext.gameContext.itemDAO.getByItemCode(Item.ITEM_CD_DOWN);
        item.itemId = item.item.id;
        item.adventureId = advId;
        GameContext.gameContext.itemGotDAO.insert(item);

        item = new MyItem();
        item.onBody = 1;
        item.item = GameContext.gameContext.itemDAO.getByItemCode(Item.ITEM_ATTACK_UP);
        item.itemId = item.item.id;
        item.adventureId = advId;
        GameContext.gameContext.itemGotDAO.insert(item);

        item = new MyItem();
        item.onBody = 1;
        item.item = GameContext.gameContext.itemDAO.getByItemCode(Item.ITEM_ELECTRICITY);
        item.itemId = item.item.id;
        item.adventureId = advId;
        GameContext.gameContext.itemGotDAO.insert(item);

        item = new MyItem();
        item.onBody = 1;
        item.item = GameContext.gameContext.itemDAO.getByItemCode(Item.ITEM_FIRE);
        item.itemId = item.item.id;
        item.adventureId = advId;
        GameContext.gameContext.itemGotDAO.insert(item);

        item = new MyItem();
        item.onBody = 1;
        item.item = GameContext.gameContext.itemDAO.getByItemCode(Item.ITEM_ICE);
        item.itemId = item.item.id;
        item.adventureId = advId;
        GameContext.gameContext.itemGotDAO.insert(item);

        item = new MyItem();
        item.onBody = 1;
        item.item = GameContext.gameContext.itemDAO.getByItemCode(Item.ITEM_PERFUME);
        item.itemId = item.item.id;
        item.adventureId = advId;
        GameContext.gameContext.itemGotDAO.insert(item);

        item = new MyItem();
        item.onBody = 1;
        item.item = GameContext.gameContext.itemDAO.getByItemCode(Item.ITEM_POTION);
        item.itemId = item.item.id;
        item.adventureId = advId;
        GameContext.gameContext.itemGotDAO.insert(item);

        item = new MyItem();
        item.onBody = 1;
        item.item = GameContext.gameContext.itemDAO.getByItemCode(Item.ITEM_SUMMON_MONSTER_SMALL);
        item.itemId = item.item.id;
        item.adventureId = advId;
        GameContext.gameContext.itemGotDAO.insert(item);

        item = new MyItem();
        item.onBody = 1;
        item.item = GameContext.gameContext.itemDAO.getByItemCode(Item.ITEM_SUMMON_MONSTER_MIDDLE);
        item.itemId = item.item.id;
        item.adventureId = advId;
        GameContext.gameContext.itemGotDAO.insert(item);


        item = new MyItem();
        item.onBody = 1;
        item.item = GameContext.gameContext.itemDAO.getByItemCode(Item.ITEM_SUMMON_MONSTER_LARGE);
        item.itemId = item.item.id;
        item.adventureId = advId;
        GameContext.gameContext.itemGotDAO.insert(item);

        item = new MyItem();
        item.onBody = 1;
        item.item = GameContext.gameContext.itemDAO.getByItemCode(Item.ITEM_SUMMON_MONSTER_HEAL);
        item.itemId = item.item.id;
        item.adventureId = advId;
        GameContext.gameContext.itemGotDAO.insert(item);
    }

    private void insertCardForBuyingOnNewAccount(long advId) {
        List<Card> normalCards = GameContext.gameContext.cardDAO.getAllByRare(1);
        List<Integer> numbers = new ArrayList<>();
        for(int i=0;i<6;i++){
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        MyCard cfb;
        cfb = new MyCard();
        cfb.adventureId = advId;
        cfb.cardId = normalCards.get(numbers.get(0)).id;
        cfb.type = MyCard.TYPE_FOR_BUY;
        GameContext.gameContext.myCardDAO.insert(cfb);

        cfb = new MyCard();
        cfb.adventureId = advId;
        cfb.cardId = normalCards.get(numbers.get(1)).id;
        cfb.type = MyCard.TYPE_FOR_BUY;
        GameContext.gameContext.myCardDAO.insert(cfb);

        cfb = new MyCard();
        cfb.adventureId = advId;
        cfb.cardId = normalCards.get(numbers.get(2)).id;
        cfb.type = MyCard.TYPE_FOR_BUY;
        GameContext.gameContext.myCardDAO.insert(cfb);

        cfb = new MyCard();
        cfb.adventureId = advId;
        cfb.cardId = normalCards.get(numbers.get(3)).id;
        cfb.type = MyCard.TYPE_FOR_BUY;
        GameContext.gameContext.myCardDAO.insert(cfb);

        cfb = new MyCard();
        cfb.adventureId = advId;
        cfb.cardId = normalCards.get(numbers.get(4)).id;
        cfb.type = MyCard.TYPE_FOR_BUY;
        GameContext.gameContext.myCardDAO.insert(cfb);

        cfb = new MyCard();
        cfb.adventureId = advId;
        cfb.cardId = normalCards.get(numbers.get(5)).id;
        cfb.type = MyCard.TYPE_FOR_BUY;
        GameContext.gameContext.myCardDAO.insert(cfb);
    }
}
