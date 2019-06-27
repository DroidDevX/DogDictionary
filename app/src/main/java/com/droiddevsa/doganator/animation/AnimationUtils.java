package com.droiddevsa.doganator.animation;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.droiddevsa.doganator.R;

public class AnimationUtils {
    private final static String LOG_TAG= AnimationUtils.class.getSimpleName();

    public static void applyFadeInOutAnimation(final View imageView)
    {
        Context context = imageView.getContext();
        final Animation fadeInAnimation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.fade_in_animation);
        final Animation fadeOutAnimation = android.view.animation.AnimationUtils.loadAnimation(context,R.anim.fade_out_animation);
        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.clearAnimation();
                imageView.startAnimation(fadeOutAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.clearAnimation();
                imageView.startAnimation(fadeInAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(fadeInAnimation);
    }


    public static void startDownloadingAnimation(ImageView imageView){
        Log.i(LOG_TAG,"startDownloadingAnimation()");
        if(imageView!=null) {
            RotateAnimation rotateAnimation =new RotateAnimation(0,360f,
                    Animation.RELATIVE_TO_SELF,0.5f
                    ,Animation.RELATIVE_TO_SELF,0.5f);
            rotateAnimation.setInterpolator(new LinearInterpolator());
            rotateAnimation.setDuration(1500);
            rotateAnimation.setRepeatCount(Animation.INFINITE);
            imageView.startAnimation(rotateAnimation);
        }
        else
            Log.i(LOG_TAG,"Imageview null, not starting animation");

    }

    public static void stopDownloadngAnimation(ImageView imageView) {
        Log.i(LOG_TAG,"stopDownloadngAnimation()");
        if(imageView!=null)
            imageView.clearAnimation();
    }

}
