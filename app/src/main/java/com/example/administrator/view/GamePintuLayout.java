package com.example.administrator.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.givemeyounumber.R;
import com.example.administrator.util.ImagePiece;
import com.example.administrator.util.ImageSplitterUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import java.util.logging.LogRecord;

/**
 * 自定义容器
 * Created by Administrator on 2016/2/10.
 */
public class GamePintuLayout extends RelativeLayout implements View.OnClickListener {

    private int mColumn = 3;

    /**
     * 容器的内边距
     */
    private int mPadding;

    /**
     * 每张小图之间的距离(横，纵) dp
     */
    private int mMagin = 3;

    private ImageView[] mGamePintuItems;

    /**
     * item的宽度跟高度
     */
    private int mItemWidth;

    /**
     * 游戏的图片
     */
    private Bitmap mBitmap;

    /**
     * 切图之后的保存
     */
    private List<ImagePiece> mItemBitmaps;

    /**
     * 限定它只有一次操作
     */
    private boolean once;

    /**
     * 游戏面板宽度
     */
    private int mWidth;
    public boolean isGameSuccess;
    private boolean isGameOver;



    /**
     * 给mainActivity设置一些接口使用
     */
    public interface GamePintuListener
    {
        void nextLevel(int nextLevel);

        void timechanged(int currentTime);

        void gameover();
    }

    //当前的关卡数
    private int level = 1;
    //主界面传进来的状态
    private  static  final  int TIME_CHANGED = 1;
    private  static  final int NEXT_LEVEL = 2;



    //进行接口的初始化关联这个界面的作用
    public  GamePintuListener mListener;

    /**
     * 设置接口回调
     * @param mListener
     */
    public void setOnGamePintuListener(GamePintuListener mListener)
    {
        this.mListener = mListener;
    }




    private Handler mHandler = new Handler() {

        public void  handleMessage(android.os.Message msg)
        {
             switch (msg.what)
             {
                 case TIME_CHANGED:
                     if (isGameSuccess||isGameOver||isPause)
                         return;

                     if (mListener!=null)
                     {
                         mListener.timechanged(mTime);
                         if (mTime ==0)
                         {
                             isGameOver =true;
                             mListener.gameover();
                             return;
                         }
                     }
                     mTime--;

                     mHandler.sendEmptyMessageDelayed(TIME_CHANGED,1000);

                     break;
                 case NEXT_LEVEL:
                     level = level =1;
                     if (mListener!=null)
                     {
                         mListener.nextLevel(level);
                     }
                     else {
                         nextLevel();
                     }
                     break;
             }
        };

    };

    //给用户提供设置时间的接口
    private  boolean isTimeEnabled = false;
    private int mTime;

    /**
     * 设置是否开启时间
     * @param isTimeEnabled
     */
    public void setTimeEnabled(boolean isTimeEnabled) {
        this.isTimeEnabled = isTimeEnabled;
    }


    public GamePintuLayout(Context context) {
        this(context, null);
    }

    public GamePintuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GamePintuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inti();
    }

    private void inti() {

        mMagin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3,
                getResources().getDisplayMetrics());

        mPadding = min(getPaddingLeft(), getPaddingRight(), getPaddingBottom(), getPaddingTop());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //取宽和高中的最小值
        mWidth = Math.min(getMeasuredHeight(), getMeasuredWidth());
        if (!once) {
            //进行切图跟排序
            initBitmap();
            //设置ImageView（Item）的宽高等属性
            initTItem();
            //判断是否开启时间
            checkTimeEnable();

            once = true;

        }
        //重置界面的宽高
        setMeasuredDimension(mWidth, mWidth);
    }

    /**
     * 判断是否要开启时间
     */
    private void checkTimeEnable() {
        //如果输入是需要开启执行操作
        if (isTimeEnabled)
        {
            //根据当前登记设置事件
            countTimeBaseLevel();
            //通知界面时间改变了
            mHandler.sendEmptyMessage(TIME_CHANGED);
        }
    }

    private void countTimeBaseLevel() {
        mTime = (int)Math.pow(2,level)*60;
    }

    /**
     * 进行切图跟排序
     */
    private void initBitmap() {
        if (mBitmap == null) {
            mBitmap = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.qiaoqiao);

        }
        mItemBitmaps = ImageSplitterUtil.splitImage(mBitmap, mColumn);

        //使用sort完成我们的乱序
        Collections.sort(mItemBitmaps, new Comparator<ImagePiece>() {
            @Override
            public int compare(ImagePiece lhs, ImagePiece rhs) {
                return Math.random() > 0.5 ? 1 : -1;
            }
        });
    }

    /**
     * 设置ImageView（Item）的宽高等属性
     */

    private void initTItem() {

        mItemWidth = (mWidth - mPadding * 2 - mMagin * (mColumn - 1)) / mColumn;

        mGamePintuItems = new ImageView[mColumn * mColumn];

        //生成我们的Item，设置Rule
        for (int i = 0; i < mGamePintuItems.length; i++) {
            ImageView item = new ImageView(getContext());
            item.setOnClickListener(this);

            item.setImageBitmap(mItemBitmaps.get(i).getBitmap());
            mGamePintuItems[i] = item;
            item.setId(i + 1);
            //在Item的tag中存储了index
            item.setTag(i + "_" + mItemBitmaps.get(i).getIndex());

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    mItemWidth, mItemWidth);

            //设置Item间横向间隙，通过rightMargin
            //不是最后一列
            if ((i + 1) % mColumn != 0) {
                lp.rightMargin = mMagin;

            }
            //不是第一列
            if (i % mColumn != 0) {
                lp.addRule(RelativeLayout.RIGHT_OF, mGamePintuItems[i - 1].getId());

            }
            //如果不是第一行,设置topMargin和rule
            if ((i + 1) > mColumn) {
                lp.topMargin = mMagin;
                lp.addRule(RelativeLayout.BELOW, mGamePintuItems[i - mColumn].getId());
            }
            addView(item, lp);
        }

    }

    private boolean isPause;

    /**
     * 暂停状态
     */
    public void pause()
    {
        isPause =true;
        mHandler.removeMessages(TIME_CHANGED);
    }

    /**
     * 重新开始
     */
    public void reStart()
    {
        if (isPause) {
            isPause = false;
            mHandler.sendEmptyMessage(TIME_CHANGED);
        }
    }


    /**
     * 重新开始这一关卡
     */
    public  void restart()
    {
        isGameOver =false;
        mColumn --;
        nextLevel();
    }


    /**
     * 进入下一关的操作
     */
    public void nextLevel()
    {
        this.removeAllViews();
        mAnimLayout = null;
        level++;
        mColumn ++;
        isGameSuccess = false;
        checkTimeEnable();
        initBitmap();
        initTItem();
    }


    /**
     * 获取多个参数的最小值
     *
     * @param params int...指无论传入多少个值都可以处理
     * @return
     */
    private int min(int... params) {
        int min = params[0];
        for (int param : params) {
            if (param < min)
                min = param;
        }
        return min;
    }

    private ImageView mFirst;
    private ImageView mSecond;

    @Override
    public void onClick(View v) {
        //表示如果当前正在进行动画的话就执行返回return
        if (isAniming)
            return;
        //两次点击同一个item，取消所有操作
        if (mFirst == v) {
            mFirst.setColorFilter(null);
            mFirst = null;
            return;
        }
        if (mFirst == null) {
            //点击之后设置界面的透明度
            mFirst = (ImageView) v;
            mFirst.setColorFilter(Color.parseColor("#55FF0000"));
        } else {
            mSecond = (ImageView) v;
            exchangView();
        }
    }

    /**
     * 动画层
     */
    private RelativeLayout mAnimLayout;
    private boolean isAniming;

    /**
     * 交换我们的item
     */
    private void exchangView() {
        mFirst.setColorFilter(null);
        //构造我们的动画层
        setUpAnimLayout();

        ImageView first = new ImageView(getContext());
        //通过mItemBitmaps的值找到储存的bitmap数据，然后两个之间进行交换
        final Bitmap firstBitmap = mItemBitmaps.get(getImageIdByTag((String) mFirst.getTag())).getBitmap();
        first.setImageBitmap(firstBitmap);
        LayoutParams lp = new LayoutParams(mItemWidth, mItemWidth);
        lp.leftMargin = mFirst.getLeft() - mPadding;
        lp.topMargin = mFirst.getTop() - mPadding;
        first.setLayoutParams(lp);
        mAnimLayout.addView(first);


        ImageView second = new ImageView(getContext());
        final Bitmap secondBitmap = mItemBitmaps.get(getImageIdByTag((String) mSecond.getTag())).getBitmap();
        second.setImageBitmap(secondBitmap);
        LayoutParams lp2 = new LayoutParams(mItemWidth, mItemWidth);
        lp2.leftMargin = mSecond.getLeft() - mPadding;
        lp2.topMargin = mSecond.getTop() - mPadding;
        second.setLayoutParams(lp2);
        mAnimLayout.addView(second);


        //设置动画
        TranslateAnimation anim = new TranslateAnimation(0,
                mSecond.getLeft() - mFirst.getLeft(), 0, mSecond.getTop() - mFirst.getTop());

        anim.setDuration(300);
        anim.setFillAfter(true);
        first.startAnimation(anim);

        TranslateAnimation animSecond = new TranslateAnimation(0,
                -mSecond.getLeft() + mFirst.getLeft(), 0, -mSecond.getTop() + mFirst.getTop());

        animSecond.setDuration(300);
        animSecond.setFillAfter(true);
        second.startAnimation(animSecond);

        //监听动画
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //先把之前的图片进行隐藏
                mFirst.setVisibility(View.INVISIBLE);
                mSecond.setVisibility(View.INVISIBLE);
                isAniming = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                String fitstTag = (String) mFirst.getTag();
                String secondTag = (String) mSecond.getTag();

                mFirst.setImageBitmap(secondBitmap);
                mSecond.setImageBitmap(firstBitmap);

                //交换完成之后还要交换两个Tag
                mFirst.setTag(secondTag);
                mSecond.setTag(fitstTag);

                mFirst.setVisibility(View.VISIBLE);
                mSecond.setVisibility(View.VISIBLE);

                mFirst = mSecond = null;
                //移除动画层
                mAnimLayout.removeAllViews();

                //判断用户游戏是否成功
                checkSuccess();
                isAniming = false;

            }

            /**
             * 每一次动画结束都判断一次是否游戏结束
             */
            private void checkSuccess() {

                boolean isSuccess = true;
                for (int i =0;i<mGamePintuItems.length;i++)
                {
                    ImageView imageView = mGamePintuItems[i];

                    if (getImageIndex((String)imageView.getTag())!=i)
                    {
                        isSuccess = false;
                    }

                }
                if (isSuccess)
                {
                    isGameSuccess =true;
                    mHandler.removeMessages(TIME_CHANGED);
                    Log.e("TAG", "Success");
                    Toast.makeText(getContext(),"success!",Toast.LENGTH_LONG).show();
                    mHandler.sendEmptyMessage(NEXT_LEVEL);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



    }
//以下为两个简便方法

    /**
     * 根据tag获取Id
     *
     * @param tag
     * @return
     */
    public int getImageIdByTag(String tag) {
        String[] split = tag.split("_");
        return Integer.parseInt(split[0]);
    }

    public int getImageIndex(String tag) {
        String[] split = tag.split("_");
        return Integer.parseInt(split[1]);
    }


    /**
     * 构造我们的动画层
     */
    private void setUpAnimLayout() {
        if (mAnimLayout == null) {
            mAnimLayout = new RelativeLayout(getContext());
            addView(mAnimLayout);

        }
    }
}
