package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.tactic.action.BaseAction;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanmenlung on 31/10/2018.
 */

public class ActionUi extends FrameLayout {
    private static String tag = "ActionUi";
    public DropDown dd1;
    public DropDown dd2;
    public DropDown dd3;
    public DropDown dd4;
    private List<BaseAction> actions = new ArrayList<>();
    public BaseAction selectedAction;
    private LinearLayout actionParas;
    public ActionUi(Context context, BaseAction selectedAction) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.action, this);
        actionParas = (LinearLayout)findViewById(R.id.actionParas);

        Logger.log(tag,"selectedAction.key:"+selectedAction.key);
        this.selectedAction = selectedAction;
        actions = BaseAction.listAll(getContext());
//        renderDd1();
//        renderDd2();
//        renderDd3();
    }

    public void reload(BaseAction selectedAction) {

        this.selectedAction = selectedAction;
        actions = BaseAction.listAll(getContext());
        renderDd1();
        renderDd2();
        renderDd3();
    }
    private void renderDd1() {
        actions = BaseAction.listAll (getContext());
        List<String> opts = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < actions.size(); i++) {
            if (actions.get(i).key.equals (selectedAction.key)) {
                index = i;
            }
            opts.add(actions.get(i).desc);
        }
        if(dd1 == null){
            Logger.log(tag,"opts.size:"+opts.size());
            dd1 = DropDown.create(getContext(), actionParas, opts, index);
        }else {
            DropDown.update(getContext(),dd1, opts, index);
        }
        Logger.log(tag,"index:"+index);

        dd1.spinner.setSelected(false);
        dd1.spinner.setSelection(index,false);
//        dd1.spinner.setSelection(4);
        dd1.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedAction = actions.get(i);
                Logger.log(tag,"dd1-selected changed");
                renderDd2 ();
                renderDd3 ();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void renderDd2() {
        if (dd2 != null) {
            dd2.setVisibility(GONE);
        }
        List<String> opts = new ArrayList<>();
        int index = 0;
        if(selectedAction.optionItems2 != null){
            for (int i = 0; i < selectedAction.optionItems2.size(); i++) {
                if (selectedAction.optionItems2.get(i).value.equals (selectedAction.selectOption2.value)) {
                    index = i;
                }
                opts.add(selectedAction.optionItems2.get(i).label);
            }
            if(dd2 == null){
                dd2 = DropDown.create(getContext(), actionParas, opts, index);
            }
            dd2.spinner.setSelection(index);

        }

        if (opts.size() > 0) {
            Logger.log(tag,"dd2 - index:"+index);
            if(dd2 == null) {
                dd2 = DropDown.create(getContext(), actionParas,opts, 0);
            }else {
                DropDown.update(getContext(),dd2, opts, index);
            }
            dd2.setVisibility(VISIBLE);
            dd2.spinner.setSelected(false);
            dd2.spinner.setSelection(index,false);
            dd2.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedAction.selectOption2 = selectedAction.optionItems2.get(i);
                    if(selectedAction.selectOption2.optionItems != null && selectedAction.selectOption2.optionItems.size()>0){
                        selectedAction.selectOption2_1 = selectedAction.selectOption2.optionItems.get(0);
                    }
                    renderDd3();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    private void renderDd3() {
        if (dd3 != null) {
            dd3.setVisibility(GONE);
        }
        List<String> opts = new ArrayList<>();
        int index = 0;
        if(selectedAction.selectOption2 != null) {
            if (selectedAction.selectOption2.optionItems != null) {
                for (int i = 0; i < selectedAction.selectOption2.optionItems.size(); i++) {
                    if (selectedAction.selectOption2.optionItems.get(i).value.equals(selectedAction.selectOption2_1.value)) {
                        index = i;
                    }
                    opts.add(selectedAction.selectOption2.optionItems.get(i).label);
                }
                if (opts.size() > 0) {
                    Logger.log(tag,"action dd3 part 1");
                    if (dd3 == null) {
                        dd3 = DropDown.create(getContext(), actionParas, opts, 0);
                    } else {
                        DropDown.update(getContext(), dd3, opts, index);
                    }
                    dd3.setVisibility(VISIBLE);
                    dd3.setPrePostLabel(selectedAction.selectOption2.optionItemTitle,"");
                    dd3.spinner.setSelection(index);
                    dd3.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedAction.selectOption2_1 = selectedAction.selectOption2.optionItems.get(i);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }else {
                Logger.log(tag,"selectedAction.selectOption2.optionItems is null");
            }
//=========================================
            if(selectedAction.optionItems3 != null) {
                if (opts.size() <= 0) {
                    for (int i = 0; i < selectedAction.optionItems3.size(); i++) {
                        if (selectedAction.optionItems3.get(i).value.equals(selectedAction.selectOption3.value)) {
                            index = i;
                        }
                        opts.add(selectedAction.optionItems3.get(i).label);
                    }
                }
                if (opts.size() > 0) {
                    Logger.log(tag,"dd3-opts-size:" + opts.size());
                    if (dd3 == null) {
                        dd3 = DropDown.create(getContext(), actionParas, opts, 0);
                    } else {
                        DropDown.update(getContext(), dd3, opts, index);
                    }
                    dd3.setVisibility(VISIBLE);
                    dd3.spinner.setSelection(index);
                    dd3.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedAction.selectOption3 = selectedAction.optionItems3.get(i);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }
        }
    }

}
