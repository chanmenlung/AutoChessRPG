package com.longtail360.autochessrpg.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by chanmenlung on 14/10/2018.
 */

public class AnimationUtil {
    public static Animation animFadeIn;
    public static Animation animFadeOut;

    public static void doFadeIn(Context context, final View view) {
        if(animFadeIn == null){
            init(context);
        }
        animFadeIn.setAnimationListener(new Animation.AnimationListener(){
                                            @Override
                                            public void onAnimationStart(Animation arg0) {
                                            }
                                            @Override
                                            public void onAnimationRepeat(Animation arg0) {
                                            }
                                            @Override
                                            public void onAnimationEnd(Animation arg0) {
                                                view.setVisibility(View.VISIBLE);
                                            }
                                        }
        );
        view.startAnimation(animFadeIn);
    }

    public static void doFadeOut(Context context,final View view) {
        if(animFadeOut == null){
            init(context);
        }
        animFadeOut.setAnimationListener(new Animation.AnimationListener(){
                                             @Override
                                             public void onAnimationStart(Animation arg0) {
                                             }
                                             @Override
                                             public void onAnimationRepeat(Animation arg0) {
                                             }
                                             @Override
                                             public void onAnimationEnd(Animation arg0) {
                                                 view.setVisibility(View.INVISIBLE);
                                             }
                                         }
        );
        view.startAnimation(animFadeOut);
    }

    private static void init(Context context){
        if(AnimationUtil.animFadeIn == null)
            AnimationUtil.animFadeIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        if(AnimationUtil.animFadeOut == null)
            AnimationUtil.animFadeOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
    }
}
