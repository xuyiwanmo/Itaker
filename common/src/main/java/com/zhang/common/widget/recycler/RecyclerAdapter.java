package com.zhang.common.widget.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhang.common.R;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 德医互联 on 2017/8/22.
 */

public abstract class RecyclerAdapter<Data> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>>
        implements View.OnClickListener, View.OnLongClickListener, AdapterCallback<Data> {
    private List<Data> mDataList;
    private AdapterListener mListener;

    public RecyclerAdapter(AdapterListener listener) {
        mListener = listener;
    }
    public RecyclerAdapter(List<Data> dataList, AdapterListener listener) {
        this.mDataList=dataList;
        mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    protected abstract int getItemViewType(int position, Data data);

    /**
     * @param parent   RecycleView
     * @param viewType 界面的类型  约定为xml布局的id
     * @return
     */
    @Override
    public ViewHolder<Data> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(viewType, parent, false);
        ViewHolder<Data> holder = onCreateViewHolder(root, viewType);
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);
        //设置view的Tag为holder  进行双向绑定
        root.setTag(R.id.tay_recycler_holer, holder);
        holder.mUnbinder = ButterKnife.bind(holder, root);
        holder.mCallback = this;
        return holder;
    }

    public abstract ViewHolder<Data> onCreateViewHolder(View root, int viewType);


    @Override
    public void onBindViewHolder(ViewHolder<Data> holder, int position) {
        Data data = mDataList.get(position);
        //触发Holer绑定方法
        holder.bind(data);
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 插入一条数据
     */
    public void add(Data data) {
        mDataList.add(data);
        notifyItemInserted(mDataList.size() - 1);
    }

    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 替换为新的集合
     *
     * @param dataList
     */
    public void replace(Collection<Data> dataList) {
        mDataList.clear();
        if (dataList == null || dataList.size() == 0) {
            return;
        }
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag(R.id.tay_recycler_holer);
        if (mListener != null) {
            int pos = viewHolder.getAdapterPosition();
            //回调方法
            mListener.onItemClick(viewHolder, mDataList.get(pos));
        }
    }

    @Override
    public boolean onLongClick(View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag(R.id.tay_recycler_holer);
        if (mListener != null) {
            int pos = viewHolder.getAdapterPosition();
            //回调方法
            mListener.onItemLongClick(viewHolder, mDataList.get(pos));
            return true;
        }
        return false;
    }

    public void setListener(AdapterListener<Data> adapterListener) {
        this.mListener = adapterListener;
    }

    public interface AdapterListener<Data> {
        //当点击的时候触发
        void onItemClick(RecyclerAdapter.ViewHolder holder, Data data);


        void onItemLongClick(RecyclerAdapter.ViewHolder holder, Data data);
    }

    /**
     * 插入一堆数据,并通知集合更新
     *
     * @param dataList
     */
    public void add(Data... dataList) {
        if (dataList != null && dataList.length > 0) {
            int startPos = mDataList.size();
            Collections.addAll(mDataList, dataList);
            notifyItemRangeInserted(startPos, dataList.length);
        }
    }

    /**
     * 自定义viewHolder
     *
     * @param <Data>
     */
    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder {
        private Unbinder mUnbinder;
        private Data mData;
        private AdapterCallback<Data> mCallback;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        //当前的包中调用
        void bind(Data data) {
            this.mData = data;
            onBind(data);
        }

        //界面绑定
        protected abstract void onBind(Data data);

        public void updateData(Data data) {
            if (mCallback != null) {
                mCallback.update(data, this);
            }
        }
    }
}
