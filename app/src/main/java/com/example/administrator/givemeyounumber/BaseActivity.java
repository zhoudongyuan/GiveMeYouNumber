package com.example.administrator.givemeyounumber;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * 基类的BaseActiviy
 * Created by Administrator on 2015/11/6.
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cancleTile();
    }

    private void cancleTile() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}
