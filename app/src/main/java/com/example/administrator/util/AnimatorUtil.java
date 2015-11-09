package com.example.administrator.util;

import android.animation.AnimatorSet;
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
     *      用于动画结合
     * @param target   传入控件
     * @param propertyName   propertyName的值缩放动画(scaleX\scaleY)透明度（alpha）旋转（rotation）（rotationX）（rotationY）平移X(translationX),平移Y（translationY）更多有待发现
     * @param by   by与to要用单位，例如200F
     * @param to
     * @param delaytime
     */
     public static  void objectAnimatorTranTogether(Object target,String propertyName,Float by,Float to,long delaytime)
     {
         ObjectAnimator animator = ObjectAnimator.ofFloat(target, propertyName, by, to).setDuration(delaytime);
         animator.start();
     }

    /**
     * 异步处理的动画效果的类，用于objectAnimator分开执行的
     * @param target    传入控件
     * @param propertyName1     先执行的动画(scaleX\scaleY)透明度（alpha）旋转（rotation）（rotationX）（rotationY）平移X(translationX),平移Y（translationY）更多有待发现
     * @param propertyName2     后执行的动画(scaleX\scaleY)透明度（alpha）旋转（rotation）（rotationX）（rotationY）平移X(translationX),平移Y（translationY）更多有待发现
     * @param by1                 先执行的by与to要用单位，例如200F
     * @param to1
     * @param by2                 后执行的by与to要用单位，例如200F
     * @param to2
     * @param delaytime          执行时间
     */
    public  static  void objectAnimatorTranAfter(Object target,String propertyName1,String propertyName2,Float by1,Float to1,Float by2,Float to2,long delaytime)
    {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(target,propertyName1,by1,to1);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(target,propertyName2,by2,to2);
        AnimatorSet set = new AnimatorSet();
        set.play(animator1).after(animator2);
        set.setDuration(delaytime);
        set.start();
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
