package com.zhang.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 德医互联 on 2017/8/22.
 */

public abstract class MyFragment extends Fragment {

    protected View mRoot;
    protected Unbinder mRootUnbinder;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //初始化参数
        initArgs(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot == null) {
            int layId = getContentLayoutId();
            View root = inflater.inflate(layId, container, false);
            initWidget(root);
            mRoot = root;
        } else {
            //把当前Root从父中移除
            if (mRoot.getParent() != null) {
                ((ViewGroup) mRoot.getParent()).removeView(mRoot);
            }
        }
        return mRoot;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    protected abstract int getContentLayoutId();

    protected void initWidget(View view) {
        mRootUnbinder=ButterKnife.bind(this, view);
    }

    protected void initData() {

    }

    protected void initArgs(Bundle bundle) {
    }

    /**
     * 返回按键触发调用
     *
     * @return True 我已经处理了逻辑  Activity不用自己finish
     * False 我没有处理逻辑  Activity自己走自己的逻辑
     */
    public boolean onBackPressed() {

        return false;
    }
}
