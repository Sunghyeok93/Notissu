package com.notissu.UI.NoticeDetail.Adapter;

import android.support.v7.widget.RecyclerView;

import com.notissu.Model.AttachedFile;

import java.util.List;

public interface AttachedFileAdapterContract {
    interface View {
        void refresh();
    }

    interface Model {
        void setAttachedFileList(List<AttachedFile> mAttachedFileList);

        void setOnRecyclerItemClickListener(AttachedFileAdapterContract.OnRecyclerItemClickListener mOnRecyclerItemClickListener);

        int getCount();

        AttachedFile getItem(int position);
    }

    interface OnRecyclerItemClickListener {
        void onItemClick(android.view.View itemView, RecyclerView.Adapter adapter, int position);
    }
}
