package com.example.administrator.util;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.widget.Button;

/**
 * 属性动画的工具类
 * Created by Administrator on 2015/10/10.
 */
public class AnimatorUtil {
    /**
     *      * 对象属性objectAnimator的动画效果类，属于异步处理，可以同时几个组合调用实现多种效果
     * @param target   传入控件
     * @param propertyName   propertyName的值缩放动画(scaleX\scaleY)透明度（alpha）旋转（rotation）（rotationX）（rotationY）平移X(translationX),平移Y（translationY）更多有待发现
     * @param by   by与to要用单位，例如200F
     * @param to
     * @param delaytime
     */
     public static  void objectAnimatorTran(Object target,String propertyName,Float by,Float to,long delaytime)
     {
         ObjectAnimator animator = ObjectAnimator.ofFloat(target, propertyName, by, to).setDuration(delaytime);
         animator.start();
     }

    /**
     * 动画（暂时作用不大，是objectAnimator的子类，控制之后可以生成更加特殊的效果）
     * @param button
     */

    public static void valueAnimatorButtomTimeSet(final Button button)
    {
        ValueAnimator animator = ValueAnimator.ofInt(0,5);
        animator.setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer)animation.getAnimatedValue();
                button.setText(""+value);
            }
        });
        animator.start();

    }
}
