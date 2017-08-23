package com.zhang.itakler_new;

import android.widget.TextView;

import com.zhang.common.app.MyActivity;

import butterknife.BindView;

public class MainMyActivity extends MyActivity {

    @BindView(R.id.txt_test)
    TextView mTestTv;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mTestTv.setText("Test");
    }
}
