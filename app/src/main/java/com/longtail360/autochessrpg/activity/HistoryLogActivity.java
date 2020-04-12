package com.longtail360.autochessrpg.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Dungeon;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.log.RootLog;

import java.util.List;

public class HistoryLogActivity extends BaseActivity {
    View log1Bt;
    View log2Bt;
    View log3Bt;
    View log4Bt;
    View log5Bt;
    TextView log1BtText;
    TextView log2BtText;
    TextView log3BtText;
    TextView log4BtText;
    TextView log5BtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_log);
        log1Bt = findViewById(R.id.log1Bt);
        log2Bt = findViewById(R.id.log2Bt);
        log3Bt = findViewById(R.id.log3Bt);
        log4Bt = findViewById(R.id.log4Bt);
        log5Bt = findViewById(R.id.log5Bt);
        log1BtText = findViewById(R.id.log1BtText);
        log2BtText = findViewById(R.id.log2BtText);
        log3BtText = findViewById(R.id.log3BtText);
        log4BtText = findViewById(R.id.log4BtText);
        log5BtText = findViewById(R.id.log5BtText);

        log1Bt.setVisibility(View.INVISIBLE);
        log2Bt.setVisibility(View.INVISIBLE);
        log3Bt.setVisibility(View.INVISIBLE);
        log4Bt.setVisibility(View.INVISIBLE);
        log5Bt.setVisibility(View.INVISIBLE);

        List<RootLog> rootLogList = GameContext.gameContext.rootLogDAO.listAll();
        if(rootLogList.size() > 0){
            RootLog rootLog = rootLogList.get(0);
            Dungeon dungeon = GameContext.gameContext.dungeonDAO.getByIndex(rootLog.dungeonId);
            log1Bt.setVisibility(View.VISIBLE);
            log1BtText.setText(getString(R.string.ui_history_logBtText).replace("{stage}", dungeon.index+"").replace("{stageName}", dungeon.name));
            log1Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        if(rootLogList.size() > 1){
            RootLog rootLog = rootLogList.get(1);
            Dungeon dungeon = GameContext.gameContext.dungeonDAO.getByIndex(rootLog.dungeonId);
            log2Bt.setVisibility(View.VISIBLE);
            log2BtText.setText(getString(R.string.ui_history_logBtText).replace("{stage}", dungeon.index+"").replace("{stageName}", dungeon.name));
            log2Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        if(rootLogList.size() > 2){
            RootLog rootLog = rootLogList.get(2);
            Dungeon dungeon = GameContext.gameContext.dungeonDAO.getByIndex(rootLog.dungeonId);
            log3Bt.setVisibility(View.VISIBLE);
            log3BtText.setText(getString(R.string.ui_history_logBtText).replace("{stage}", dungeon.index+"").replace("{stageName}", dungeon.name));
            log3Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        if(rootLogList.size() > 3){
            RootLog rootLog = rootLogList.get(3);
            Dungeon dungeon = GameContext.gameContext.dungeonDAO.getByIndex(rootLog.dungeonId);
            log4Bt.setVisibility(View.VISIBLE);
            log4BtText.setText(getString(R.string.ui_history_logBtText).replace("{stage}", dungeon.index+"").replace("{stageName}", dungeon.name));
            log4Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        if(rootLogList.size() > 4){
            RootLog rootLog = rootLogList.get(4);
            Dungeon dungeon = GameContext.gameContext.dungeonDAO.getByIndex(rootLog.dungeonId);
            log5Bt.setVisibility(View.VISIBLE);
            log5BtText.setText(getString(R.string.ui_history_logBtText).replace("{stage}", dungeon.index+"").replace("{stageName}", dungeon.name));
            log5Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
