package com.notissu.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.notissu.R;

import java.util.ArrayList;

/**
 * Created by dnfld on 2016-12-10.
 */

public class KeywordAdapter extends ArrayAdapter<String> {
    Context context;
    ViewHolder viewHolder;
    //ListView에 보여줄 Item
    ArrayList<String> keywordList = new ArrayList<>();
    //즐겨찾기에 추가된 List
    //ArrayList<String> keywordList = new ArrayList<>();
    //노드들 CheckBox 체크되어있는지 저장하기.

    public KeywordAdapter(Context context,ArrayList<String> keywordList) {
        super(context, android.R.layout.simple_expandable_list_item_2);
        this.context = context;
        this.keywordList = keywordList;

        //보여줄 노드 List 중 즐겨찾기에 등록된 Row를 찾아서 isChecked를 Check하기
        //즐겨찾기한 리스트가 없을 수 있으니 성능을 위해 for문 바깥으로 넣음.

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
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = View.inflate(context, R.layout.fragment_keyword_list, null);
            viewHolder = new KeywordAdapter.ViewHolder();
            viewHolder.tvKeyword = (TextView) view.findViewById(R.id.fragment_keyword);

            view.setTag(viewHolder);
        }else{
            viewHolder = (KeywordAdapter.ViewHolder) view.getTag();
        }

        final int index = i;
        final String title = getItem(index);

        viewHolder.tvKeyword.setText(title);

        return view;
    }

    static class ViewHolder{
            TextView tvKeyword;
        }
    }