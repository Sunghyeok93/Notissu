package com.notissu.UI.WithUs;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.notissu.R;
import com.notissu.Util.Utils;

import java.util.ArrayList;

/**
 * Created by forhack on 2016-12-11.
 */

public class WithUsAdapter extends ArrayAdapter<We> {

    Context context;
    ViewHolder viewHolder;
    //ListView에 보여줄 Item
    ArrayList<We> weList = new ArrayList<>();

    public WithUsAdapter(Context context, ArrayList<We> weList) {
        super(context, android.R.layout.simple_expandable_list_item_2);
        this.context = context;
        this.weList = weList;

    }

    @Override
    public int getCount() {
        return weList.size();
    }

    @Override
    public We getItem(int i) {
        return weList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = View.inflate(context, R.layout.row_with_us, null);
            viewHolder = new ViewHolder();
            viewHolder.ivPicture = (ImageView) view.findViewById(R.id.with_us_iv_picture);
            viewHolder.tvName = (TextView) view.findViewById(R.id.with_us_tv_name);
            viewHolder.tvPosition = (TextView) view.findViewById(R.id.with_us_tv_position);
            viewHolder.tvEmail = (TextView) view.findViewById(R.id.with_us_tv_email);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        We we = getItem(i);
        Utils.showImage(context,we.getImgName(),viewHolder.ivPicture);
        viewHolder.tvName.setText(we.getName());
        viewHolder.tvPosition.setText(we.getPosition());
        viewHolder.tvEmail.setText(we.getEmail());

        return view;
    }

    static class ViewHolder {
        ImageView ivPicture;
        TextView tvName;
        TextView tvPosition;
        TextView tvEmail;
    }

}
