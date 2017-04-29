package com.notissu.UI.NoticeList;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.notissu.Model.Notice;
import com.notissu.UI.NoticeList.Search.SearchActivity;
import com.notissu.R;
import com.notissu.UI.NoticeList.Detail.NoticeDetailActivity;
import com.notissu.Util.LogUtils;
import com.notissu.View.Interface.EndlessRecyclerViewScrollListener;
import com.notissu.View.Interface.OnRecyclerItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeListFragment extends Fragment implements NoticeListContract.View {
    private static final String TAG = LogUtils.makeLogTag(NoticeListFragment.class);

    @BindView(R.id.notice_list)
    RecyclerView mNoticeList;
    @BindView(R.id.notice_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private SearchView mSearchView;

    //Progress dialog
    private ProgressDialog mProgressDialog;

    private NoticeListContract.Presenter mPresenter;

    public static Fragment newInstance(int flag, String title, int category) {
        Bundle bundle = new Bundle();
        bundle.putInt(NoticeListContract.KEY_FLAG, flag);
        bundle.putString(NoticeListContract.KEY_TITLE, title);
        bundle.putInt(NoticeListContract.KEY_CATEGORY, category);
        Fragment fragment = new NoticeListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static Fragment newInstance(int flag, String title) {
        Bundle bundle = new Bundle();
        bundle.putInt(NoticeListContract.KEY_FLAG, flag);
        bundle.putString(NoticeListContract.KEY_TITLE, title);
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
        mProgressDialog.setMessage("잠시만 기다려 주세요...");

        noticeListAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View itemView, RecyclerView.Adapter adapter, int position) {
                mPresenter.onItemClick(itemView, position);
            }
        });

        noticeListAdapter.setOnStarredClickListener(new NoticeListAdapterContract.OnStarredClickListner() {
            @Override
            public void onClick(View view, Notice notice) {
                mPresenter.onStarredClick(view, notice);
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.fetchNotice(1);
            }
        });

        mPresenter.fetchNotice(1);
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
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void showSearch(String query) {
        Intent intent = new Intent(getContext(), SearchActivity.class);
        intent.putExtra(NoticeListContract.KEY_SEARCH_QUERY, query);
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
                showSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void showNotice(int noticeId) {
        Intent intent = new Intent(getContext(), NoticeDetailActivity.class);
        intent.putExtra(NoticeListContract.KEY_NOTICE_ID, noticeId);
        startActivity(intent);
    }

    @Override
    public void setAdapter(NoticeListAdapter noticeListAdapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mNoticeList.setLayoutManager(linearLayoutManager);
        mNoticeList.addItemDecoration(new DividerItemDecoration(mNoticeList.getContext(), DividerItemDecoration.VERTICAL));
        mNoticeList.setAdapter(noticeListAdapter);
        mNoticeList.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPresenter.fetchNotice(page + 1);
                Log.d(TAG, "page = " + page + ", totalItemsCount = " + totalItemsCount);
            }
        });
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
}
