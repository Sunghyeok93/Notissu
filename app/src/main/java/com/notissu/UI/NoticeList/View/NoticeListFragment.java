package com.notissu.UI.NoticeList.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.notissu.UI.NoticeList.Adapter.NoticeListAdapterContract;
import com.notissu.UI.Search.View.SearchActivity;
import com.notissu.UI.NoticeList.Adapter.NoticeListAdapter;
import com.notissu.UI.NoticeList.Presenter.NoticeListContract;
import com.notissu.UI.NoticeList.Presenter.NoticeListPresenter;
import com.notissu.R;
import com.notissu.UI.NoticeDetail.View.NoticeDetailActivity;
import com.notissu.Util.LogUtils;
import com.notissu.View.Interface.OnRecyclerItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeListFragment extends Fragment implements NoticeListContract.View {
    private static final String TAG = LogUtils.makeLogTag(NoticeListFragment.class);

    public static final String KEY_SEARCH_QUERY = "KEY_SEARCH_QUERY";

    public static final String KEY_TITLE = "KEY_TITLE";
    public static final String KEY_CATEGORY = "KEY_CATEGORY";
    public static final String KEY_FLAG = "KEY_FLAG";

    public static final String KEY_RSS_TITLE = "KEY_RSS_TITLE";
    public static final String KEY_NOTICE_ID = "KEY_NOTICE_ID";

    @BindView(R.id.notice_list)
    RecyclerView mNoticeList;
    @BindView(R.id.notice_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    SearchView mSearchView;

    //Progress dialog
    ProgressDialog mProgressDialog;

    NoticeListContract.Presenter mPresenter;

    public static Fragment newInstance(int flag, String title, int category) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, title);
        bundle.putInt(KEY_CATEGORY, category);
        bundle.putInt(KEY_FLAG, flag);
        Fragment fragment = new NoticeListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static Fragment newInstance(int flag, String title) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, title);
        bundle.putInt(KEY_FLAG, flag);
        Fragment fragment = new NoticeListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_notice_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mPresenter = new NoticeListPresenter(getArguments(), this);
        mPresenter.start();

        NoticeListAdapter noticeListAdapter = new NoticeListAdapter();
        mPresenter.setAdapter(noticeListAdapter);

        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("학교 서버가 너무 느려요...");

        noticeListAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View itemView, RecyclerView.Adapter adapter, int position) {
                mPresenter.onItemClick(itemView, position);
            }
        });

        noticeListAdapter.setOnStarredClickListener(new NoticeListAdapterContract.OnStarredClickListner() {
            @Override
            public void onClick(View view, int position) {
                mPresenter.onStarredClick(view, position);
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.fetchNoticeList();
            }
        });

        mPresenter.fetchNoticeList();
    }

    @Override
    public void showTitle(String title) {
        getActivity().setTitle(title);
    }

    @Override
    public void showProgress() {
        mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showSearch(String query) {
        Intent intent = new Intent(getContext(), SearchActivity.class);
        intent.putExtra(KEY_SEARCH_QUERY, query);
        getActivity().startActivity(intent);
        mSearchView.clearFocus();
    }

    @Override
    public void showOptionMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        mSearchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.addSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void hideRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showNotice(int noticeId) {
        Intent intent = new Intent(getContext(), NoticeDetailActivity.class);
        intent.putExtra(KEY_NOTICE_ID, noticeId);
        startActivity(intent);
    }

    @Override
    public void setAdapter(NoticeListAdapter noticeListAdapter) {
        mNoticeList.setLayoutManager(new LinearLayoutManager(getContext()));
        mNoticeList.addItemDecoration(new DividerItemDecoration(mNoticeList.getContext(),DividerItemDecoration.VERTICAL));
        mNoticeList.setAdapter(noticeListAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mPresenter.loadOptionMenu(menu, inflater);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_read_all) {
            mPresenter.readAllItem();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(@NonNull NoticeListContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
