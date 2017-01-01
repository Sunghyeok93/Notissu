package com.notissu.NoticeList.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.notissu.Database.StarredProvider;
import com.notissu.Database.StarredProviderImp;
import com.notissu.Model.RssItem;
import com.notissu.R;
import com.notissu.Util.LogUtils;
import com.notissu.View.Interface.OnRecyclerItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by forhack on 2016-10-18.
 */

public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.ViewHolder> implements
        NoticeListAdapterContract.Model, NoticeListAdapterContract.View{
    private static final String TAG = LogUtils.makeLogTag(NoticeListAdapter.class);
    Context context;
    //ListView에 보여줄 Item
    ArrayList<RssItem> noticeList = new ArrayList<>();
    //즐겨찾기에 추가된 List
    ArrayList<RssItem> starredList = new ArrayList<>();
    //노드들 CheckBox 체크되어있는지 저장하기.
    boolean[] isChecked;

    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    @Override
    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    public NoticeListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.row_notice_list, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new NoticeListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String title = getItem(position).getTitle();

        holder.tvSubject.setText(title);
        if (getItem(position).getIsRead() == RssItem.READ) {
            holder.tvSubject.setTextColor(Color.parseColor("#aaaaaa"));
            holder.tvSubject.setTypeface(Typeface.DEFAULT);
        } else if (getItem(position).getIsRead() == RssItem.NOT_READ) {
            holder.tvSubject.setTextColor(Color.parseColor("#000000"));
            holder.tvSubject.setTypeface(Typeface.DEFAULT_BOLD);
        }
        holder.tvTime.setText(getItem(position).getPublishDateShort());
        holder.cbStar.setChecked(isChecked[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerItemClickListener.onItemClick(holder.itemView, NoticeListAdapter.this, position);
            }
        });

        holder.llWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StarredProvider starredProvider = new StarredProviderImp();
                CheckBox cb = (CheckBox)v.findViewById(R.id.notice_cb_star);

                //클릭되고 난 다음이라 isChecked는 체크되는 순간이다.
                if (cb.isChecked()) {
                    cb.setChecked(false);
                    starredProvider.deleteStarred(title);
                    isChecked[position] = false;
                } else {
                    cb.setChecked(true);
                    starredProvider.addStarred(title);
                    isChecked[position] = true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return getCount();
    }

    @Override
    public void setLists(@NonNull ArrayList<RssItem> noticeList, @NonNull ArrayList<RssItem> starredList) {
        this.noticeList = noticeList;
        this.starredList = starredList;
        isChecked = new boolean[this.noticeList.size()];

        //보여줄 노드 List 중 즐겨찾기에 등록된 Row를 찾아서 isChecked를 Check하기
        //즐겨찾기한 리스트가 없을 수 있으니 성능을 위해 for문 바깥으로 넣음.
        for (RssItem starredItem : starredList) {
            String starredTitle = starredItem.getTitle();
            for (int i = 0; i < noticeList.size(); i++) {
                String noticeTitle = noticeList.get(i).getTitle();
                if (noticeTitle.equals(starredTitle)) { //같다면!
                    isChecked[i] = true;
                    break;
                }
            }
        }
    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public RssItem getItem(int i) {
        return noticeList.get(i);
    }

    @Override
    public void remove(RssItem item) {
        noticeList.remove(item);
    }


    @Override
    public void addItems(ArrayList<RssItem> items) {
        noticeList = items;
    }

    @Override
    public void removeAll() {
        noticeList.clear();
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

    class ViewHolder extends RecyclerView.ViewHolder{
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
