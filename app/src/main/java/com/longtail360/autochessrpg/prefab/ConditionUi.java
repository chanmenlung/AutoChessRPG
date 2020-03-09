package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.tactic.Tactics;
import com.longtail360.autochessrpg.entity.tactic.condition.BaseCondition;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanmenlung on 24/10/2018.
 */

public class ConditionUi extends FrameLayout {
    private String tag = "ConditionUi";
    public LinearLayout condParas;
    private List<BaseCondition> conditions;
    public BaseCondition selectedCondition;
    private View operatorLayout;
    private View deleteButton;
    private ViewGroup parentView; //for delete view
    private View thisView;
    private CheckBox negateCondition;
    private Tactics tactics;
    private DropDown dd1;
    private DropDown dd2;
    private DropDown dd3;
    private RadioButton andRadio;
    private RadioButton orRadio;
    private TacticsEdit strategyEdit;
    private ConditionUi conditionUi; //this

    public ConditionUi(Context context, ViewGroup parentView, TacticsEdit strategyEdit, Tactics tactics, BaseCondition initCondition) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.condition, this);
        condParas = (LinearLayout)findViewById(R.id.condParas);
        operatorLayout = findViewById(R.id.operatorLayout);
        deleteButton = findViewById(R.id.deleteButton);
        negateCondition = (CheckBox)findViewById(R.id.negateCondition);
        andRadio = (RadioButton)findViewById(R.id.andRadio);
        orRadio = (RadioButton)findViewById(R.id.orRadio);
        selectedCondition = initCondition;
        this.parentView =parentView;
        this.thisView = this;
        this.strategyEdit = strategyEdit;
        this.conditionUi = this;
        this.tactics = tactics;
        conditions = BaseCondition.listAll (context);
        negateCondition.setChecked(selectedCondition.negation);
        orRadio.setChecked(selectedCondition.operatorType == 1 ? false : true);
        andRadio.setChecked(selectedCondition.operatorType == 1 ? true : false);
        renderDd1();
        renderDd2();
        renderDd3();
        setListener();
    }

    private void setListener() {
        andRadio.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedCondition.operatorType = 1;
                andRadio.setChecked(true);
                orRadio.setChecked(false);
            }
        });
        orRadio.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedCondition.operatorType = 2;
                andRadio.setChecked(false);
                orRadio.setChecked(true);
            }
        });
        deleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tactics.conditions.remove(selectedCondition);
                parentView.removeView(thisView);
                strategyEdit.callBackFromDeleteCond(conditionUi);
            }
        });

        negateCondition.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCondition.negation = negateCondition.isChecked();
            }
        });
    }
    public void showOperator(boolean show) {
        if(show) {
            operatorLayout.setVisibility(VISIBLE);
        }else {
            operatorLayout.setVisibility(GONE);
        }
    }

    public void showDelete(boolean show) {
        if(show){
            deleteButton.setVisibility(VISIBLE);
        }else {
            deleteButton.setVisibility(INVISIBLE);
        }
    }


    private void renderDd1() {
        List<String> opts = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < conditions.size(); i++) {
            Logger.log(tag, conditions.get(i).key+"");
            Logger.log(tag, selectedCondition.key+"");
            if (conditions.get(i).key.equals (selectedCondition.key)) {
                index = i;
            }
            opts.add(conditions.get(i).desc);
        }
        if(dd1 == null) {
            dd1 = DropDown.create(getContext(), condParas,opts, 0);
        }else {
            DropDown.update(getContext(),dd1, opts, index);
        }
        dd1.spinner.setSelected(false);
        dd1.spinner.setSelection(index,false);
        dd1.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCondition = conditions.get(i);
                Logger.log(tag, "dd1-selected changed");
                renderDd2 ();
                renderDd3 ();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void renderDd2() {
        if(dd2 != null) {
            dd2.setVisibility(GONE);
        }
        List<String> opts = new ArrayList<>();
        int index = 0;
        if (selectedCondition.optionItems2 != null) {
            for (int i = 0; i < selectedCondition.optionItems2.size(); i++) {
                if (selectedCondition.optionItems2.get(i).value.equals(selectedCondition.selectOption2.value)) {
                    index = i;
                }
                opts.add(selectedCondition.optionItems2.get(i).label);
            }
        }

        if (opts.size() > 0) {
            Logger.log(tag,"dd2 - index:"+index);
            if(dd2 == null) {
                dd2 = DropDown.create(getContext(), condParas,opts, 0);
            }else {
                DropDown.update(getContext(),dd2, opts, index);
            }
            dd2.spinner.setSelection(index);
            dd2.setVisibility(VISIBLE);
            dd2.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                    if (selectedCondition.intParas != null && selectedCondition.intParas.size() > 0) {
//                        selectedCondition.selectIntPara = selectedCondition.intParas.get(i).value;
//                    }
//                    if (selectedCondition.stringParas != null && selectedCondition.stringParas.size() > 0) {
//                        selectedCondition.selectStringPara = selectedCondition.stringParas.get(i).valueStr;
//                    }
                    selectedCondition.selectOption2 = selectedCondition.optionItems2.get(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

    }

    private void renderDd3() {
        if(dd3 != null) {
            dd3.setVisibility(GONE);
        }
        List<String> opts = new ArrayList<>();
        int index = 0;
        if (selectedCondition.selectOption2.optionItems != null) {
            for (int i = 0; i < selectedCondition.selectOption2.optionItems .size(); i++) {
                if (selectedCondition.selectOption2.optionItems.get(i).value.equals(selectedCondition.selectOption2_1.value)) {
                    index = i;
                }
                opts.add(selectedCondition.selectOption2.optionItems.get(i).label);
            }
            if (opts.size() > 0) {
                if(dd3 == null) {
                    dd3 = DropDown.create(getContext(), condParas,opts, 0);
                }else {
                    DropDown.update(getContext(),dd3, opts, index);
                }
                dd3.setVisibility(VISIBLE);

                dd3.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedCondition.selectOption2_1 = selectedCondition.selectOption2.optionItems.get(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

        }

        if(opts.size() <=0){
            if (selectedCondition.optionItems3 != null) {
                for (int i = 0; i < selectedCondition.optionItems3.size(); i++) {
                    if (selectedCondition.optionItems3.get(i).value.equals(selectedCondition.selectOption3.value)) {
                        index = i;
                    }
                    opts.add(selectedCondition.optionItems3.get(i).label);
                }

                if (opts.size() > 0) {
                    Logger.log(tag,"dd3-opts-size:"+opts.size());
                    if(dd3 == null) {
                        dd3 = DropDown.create(getContext(), condParas,opts, 0);
                    }else {
                        DropDown.update(getContext(),dd3, opts, index);
                    }
                    dd3.setVisibility(VISIBLE);

                    dd3.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedCondition.selectOption3 = selectedCondition.optionItems3.get(i);
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