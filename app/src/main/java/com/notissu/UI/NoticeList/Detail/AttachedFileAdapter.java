package com.notissu.UI.NoticeList.Detail;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.notissu.Model.AttachedFile;
import com.notissu.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttachedFileAdapter extends RecyclerView.Adapter<AttachedFileAdapter.ViewHolder>
        implements AttachedFileAdapterContract.Model, AttachedFileAdapterContract.View {

    private List<AttachedFile> mAttachedFileList = new ArrayList<>();

    private AttachedFileAdapterContract.OnRecyclerItemClickListener mOnRecyclerItemClickListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.row_attached_file, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        AttachedFile attachedFile = mAttachedFileList.get(position);
        SpannableString spannableString = new SpannableString(attachedFile.getTitle());
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        holder.attchedFile.setText(spannableString);
        holder.attchedFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecyclerItemClickListener.onItemClick(holder.itemView,AttachedFileAdapter.this,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAttachedFileList.size();
    }

    @Override
    public void setAttachedFileList(List<AttachedFile> mAttachedFileList) {
        this.mAttachedFileList = mAttachedFileList;
    }

    @Override
    public void setOnRecyclerItemClickListener(AttachedFileAdapterContract.OnRecyclerItemClickListener mOnRecyclerItemClickListener) {
        this.mOnRecyclerItemClickListener = mOnRecyclerItemClickListener;
    }

    @Override
    public int getCount() {
        return getItemCount();
    }

    @Override
    public AttachedFile getItem(int position) {
        return mAttachedFileList.get(position);
    }

    @Override
    public void refresh() {
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.attched_file_title)
        TextView attchedFile;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
