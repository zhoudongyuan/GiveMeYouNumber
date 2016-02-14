package com.example.administrator.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.givemeyounumber.R;
import com.example.administrator.util.ImagePiece;
import com.example.administrator.util.ImageSplitterUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 自定义容器
 * Created by Administrator on 2016/2/10.
 */
class GamePintuLayout extends RelativeLayout implements View.OnClickListener {

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
            once = true;

        }
        //重置界面的宽高
        setMeasuredDimension(mWidth, mWidth);
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
     * 交换我们的item
     */
    private void exchangView() {
        mFirst.setColorFilter(null);
        String fitstTag = (String) mFirst.getTag();
        String secondTag = (String) mSecond.getTag();

        String[] firstParams = fitstTag.split("_");
        String[] secondPatams = secondTag.split("_");

        //通过mItemBitmaps的值找到储存的bitmap数据，然后两个之间进行交换
        Bitmap firstBitmap = mItemBitmaps.get(Integer.parseInt(firstParams[0])).getBitmap();


        Bitmap secondBitmap = mItemBitmaps.get(Integer.parseInt(secondPatams[0])).getBitmap();
        mFirst.setImageBitmap(secondBitmap);
        mSecond.setImageBitmap(firstBitmap);

        //交换完成之后还要交换两个Tag
        mFirst.setTag(secondTag);
        mSecond.setTag(fitstTag);

        mFirst = mSecond = null;
    }
}
