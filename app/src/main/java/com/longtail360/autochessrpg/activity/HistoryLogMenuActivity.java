package com.longtail360.autochessrpg.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Dungeon;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.log.RootLog;
import com.longtail360.autochessrpg.fragment.AdvFragment;
import com.longtail360.autochessrpg.prefab.HistoryLogDescItem;

import java.util.List;

public class HistoryLogMenuActivity extends BaseActivity {

    private AdvFragment advFragment;
    private ViewGroup historyLogLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_log_menu);
        historyLogLayout = findViewById(R.id.historyLogLayout);
        historyLogLayout.removeAllViews();
        List<RootLog> rootLogList = GameContext.gameContext.rootLogDAO.listAll();
        if(advFragment == null){
            advFragment = new AdvFragment();
            advFragment.callback = null;
        }
        if(rootLogList.size() > 0){
            for(final RootLog rootLog : rootLogList){
                if(rootLog.advStatus != 3){
                    Dungeon dungeon = GameContext.gameContext.dungeonDAO.getByIndex(rootLog.dungeonId);
                    HistoryLogDescItem historyLogDescItem = new HistoryLogDescItem(this,getString(R.string.ui_history_logBtText).replace("{stage}", dungeon.index+"").replace("{stageName}", dungeon.name));
                    historyLogDescItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            advFragment.selectRootLogId = rootLog.id;
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragmentContainer, advFragment);
                            transaction.addToBackStack(null);

                            transaction.commit();
                        }
                    });
                    historyLogLayout.addView(historyLogDescItem);
                }
            }
        }

    }
}
