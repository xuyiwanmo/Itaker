package com.zhang.common.widget.recycler;

/**
 * Created by 德医互联 on 2017/8/22.
 */

public interface AdapterCallback<Data> {
    void update(Data data,RecyclerAdapter.ViewHolder<Data> holder);
}
