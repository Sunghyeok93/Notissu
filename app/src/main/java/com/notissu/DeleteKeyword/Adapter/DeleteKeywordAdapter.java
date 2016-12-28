package com.notissu.DeleteKeyword.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.notissu.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dnfld on 2016-12-10.
 */

public class DeleteKeywordAdapter extends RecyclerView.Adapter<DeleteKeywordAdapter.ViewHolder>
        implements DeleteKeywordAdapterContract.Model, DeleteKeywordAdapterContract.View{
    Context context;
    //ListView에 보여줄 Item
    ArrayList<String> keywordList = new ArrayList<>();

    public DeleteKeywordAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.row_delete_keyword, null);
        return new DeleteKeywordAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvKeyword.setText(getItem(position));
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
    public String getItem(int i) {
        return keywordList.get(i);
    }

    @Override
    public void remove(String string) {
        keywordList.remove(string);
    }

    @Override
    public void addItems(ArrayList<String> items) {
        keywordList = items;
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