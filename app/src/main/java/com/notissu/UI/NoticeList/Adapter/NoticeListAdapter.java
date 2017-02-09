package com.notissu.UI.NoticeList.Adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.notissu.Model.Notice;
import com.notissu.Model.RssItem;
import com.notissu.R;
import com.notissu.Util.LogUtils;
import com.notissu.View.Interface.OnRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by forhack on 2016-10-18.
 */

public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.ViewHolder> implements
        NoticeListAdapterContract.Model, NoticeListAdapterContract.View {
    private static final String TAG = LogUtils.makeLogTag(NoticeListAdapter.class);

    private List<Notice> mNoticeList = new ArrayList<>();

    private OnRecyclerItemClickListener mOnRecyclerItemClickListener;
    private NoticeListAdapterContract.OnStarredClickListner mOnStarredClickListener;

    @Override
    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.row_notice_list, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new NoticeListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Notice notice = getItem(position);
        holder.tvSubject.setText(notice.getTitle());
        holder.tvTime.setText(notice.getDate());
        holder.cbStar.setChecked(notice.isStarred());

        if (notice.isRead()) {
            holder.tvSubject.setTextColor(Color.parseColor("#aaaaaa"));
            holder.tvSubject.setTypeface(Typeface.DEFAULT);
        } else {
            holder.tvSubject.setTextColor(Color.parseColor("#000000"));
            holder.tvSubject.setTypeface(Typeface.DEFAULT_BOLD);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecyclerItemClickListener.onItemClick(holder.itemView, NoticeListAdapter.this, position);
            }
        });

        holder.llWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnStarredClickListener.onClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getCount();
    }

    @Override
    public int getCount() {
        return mNoticeList.size();
    }

    @Override
    public Notice getItem(int i) {
        return mNoticeList.get(i);
    }

    @Override
    public void setLists(List<Notice> noticeList) {
        mNoticeList = noticeList;
    }

    @Override
    public void refresh() {
        notifyDataSetChanged();
    }

    @Override
    public void setItemRead(View itemView) {
        TextView tvSubject = (TextView) itemView.findViewById(R.id.notice_tv_subject);
        tvSubject.setTextColor(Color.parseColor("#aaaaaa"));
        tvSubject.setTypeface(Typeface.DEFAULT);
    }

    @Override
    public void setOnStarredClickListener(NoticeListAdapterContract.OnStarredClickListner onStarredClickListener) {
        this.mOnStarredClickListener = onStarredClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.notice_tv_subject)
        TextView tvSubject;
        @BindView(R.id.notice_tv_time)
        TextView tvTime;
        @BindView(R.id.notice_cb_star)
        CheckBox cbStar;
        @BindView(R.id.notice_ll_cb_wrapper)
        LinearLayout llWrapper;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
