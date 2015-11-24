package com.example.administrator.givemeyounumber;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.util.AnimatorUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private ImageView iv_background,iv_people,iv_dialog;
    private TextView tv_context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiOntouth();
        intiView();
    }

    private void intiOntouth() {
        iv_background.setOnClickListener(this);

    }

    private void intiView() {
        iv_background = (ImageView)findViewById(R.id.iv_background);
        iv_people = (ImageView)findViewById(R.id.iv_people);
        iv_dialog = (ImageView)findViewById(R.id.iv_dialog);
        tv_context =(TextView)findViewById(R.id.tv_context);

        iv_dialog_effect();
    }

    private void iv_dialog_effect() {
        tv_context.setText("你好，我是一名腼腆的IT工程师");
        AnimatorUtil.objectAnimatorTranTogether(tv_context,"alpha",0f,1f,4000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_background:

                break;
        }
    }
}
