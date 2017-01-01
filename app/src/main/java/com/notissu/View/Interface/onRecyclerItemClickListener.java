package com.notissu.View.Interface;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by forhack on 2016-12-28.
 */

public interface OnRecyclerItemClickListener {
    void onItemClick(View itemView, RecyclerView.Adapter adapter, int position);
}
