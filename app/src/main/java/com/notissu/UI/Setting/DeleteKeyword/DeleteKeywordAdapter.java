package com.notissu.UI.Setting.DeleteKeyword;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.notissu.Model.Keyword;
import com.notissu.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.notissu.View.Interface.OnRecyclerItemClickListener;

/**
 * Created by dnfld on 2016-12-10.
 */

public class DeleteKeywordAdapter extends RecyclerView.Adapter<DeleteKeywordAdapter.ViewHolder>
        implements DeleteKeywordAdapterContract.Model, DeleteKeywordAdapterContract.View{
    //ListView에 보여줄 Item
    List<Keyword> keywordList = new ArrayList<>();

    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.row_delete_keyword, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new DeleteKeywordAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvKeyword.setText(getItem(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecyclerItemClickListener != null) {
                    onRecyclerItemClickListener.onItemClick(holder.itemView, DeleteKeywordAdapter.this, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return getCount();
    }

    @Override
    public void refresh() {
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return keywordList.size();
    }

    @Override
    public Keyword getItem(int i) {
        return keywordList.get(i);
    }

    @Override
    public void remove(String string) {
        keywordList.remove(string);
    }

    @Override
    public void setData(List<Keyword> data) {
        keywordList = data;
    }

    @Override
    public void removeAll() {
        keywordList.clear();
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.delete_keyword_tv_title)
        TextView tvKeyword;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}