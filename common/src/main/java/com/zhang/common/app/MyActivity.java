package com.zhang.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by 德医互联 on 2017/8/22.
 */

public abstract class MyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindows();
        //在界面未初始化之前调用的
        if (initArgs(getIntent().getExtras())) {
            int layId=getContentLayoutId();
            setContentView(layId);
            initWidget();
            initData();
        } else {
            finish();
        }
    }

    protected void initWindows() {

    }

    /**
     * 一个Activity跳转到另一个Activity
     *
     * @param bundle
     * @return
     */
    protected boolean initArgs(Bundle bundle) {
        return true;
    }

    protected abstract int getContentLayoutId();

    protected void initWidget() {
        ButterKnife.bind(this);
    }

    protected void initData() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        //当点击界面导航返回时,Finish当前界面
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        //得到当前Activiity下的所有Fragment
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                //如果是我们自己的Fragment  fragment自己处理
                if (fragment instanceof MyFragment) {
                    if (((MyFragment) fragment).onBackPressed()) {
                        return;
                    }
                }
            }
        }
        super.onBackPressed();
        finish();
    }
}
