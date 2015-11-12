package com.example.administrator.givemeyounumber;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends BaseActivity {
    private ImageView iv_background,iv_people,iv_dialog;
    private TextView tv_context;
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
        tv_context =(TextView)findViewById(R.id.tv_context);

        iv_dialog_effect();
    }

    private void iv_dialog_effect() {
        

    }
}
