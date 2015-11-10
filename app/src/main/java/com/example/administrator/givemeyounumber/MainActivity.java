package com.example.administrator.givemeyounumber;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.util.AnimatorUtil;

public class MainActivity extends BaseActivity {
    private ImageView iv_background,iv_people,iv_dialog;
    private TextView tv_context1,tv_context2,tv_context3,tv_context4,tv_context5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiView();
    }

    private void intiView() {
        iv_background = (ImageView)findViewById(R.id.iv_background);
        iv_people = (ImageView)findViewById(R.id.iv_people);
        iv_dialog = (ImageView)findViewById(R.id.iv_dialog);
        tv_context1 =(TextView)findViewById(R.id.tv_context1);
        tv_context2 =(TextView)findViewById(R.id.tv_context2);
        tv_context3 =(TextView)findViewById(R.id.tv_context3);
        tv_context4 =(TextView)findViewById(R.id.tv_context4);
        tv_context5 =(TextView)findViewById(R.id.tv_context5);

        iv_dialog_effect();
    }

    private void iv_dialog_effect() {



    }
}
