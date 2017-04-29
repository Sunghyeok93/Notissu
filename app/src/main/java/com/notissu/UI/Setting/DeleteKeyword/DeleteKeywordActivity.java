package com.notissu.UI.Setting.DeleteKeyword;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.notissu.R;
import com.notissu.Util.LogUtils;
import com.notissu.View.Interface.OnRecyclerItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.google.common.base.Preconditions.checkNotNull;

public class DeleteKeywordActivity extends AppCompatActivity implements DeleteKeywordContract.View {
    private static final String TAG = LogUtils.makeLogTag(DeleteKeywordActivity.class);

    @BindView(R.id.delete_keyword_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.delete_keyword_rl_list)
    RelativeLayout mKeywordContainer;
    @BindView(R.id.delete_keyword_rv_keyword)
    RecyclerView mList;
    @BindView(R.id.delete_keyword_btn_remove_all)
    Button mBtnRemoveAll;

    private DeleteKeywordContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_keyword);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DeleteKeywordAdapter deleteKeywordAdapter = new DeleteKeywordAdapter();

        mPresenter = new DeleteKeywordPresenter(this);
        mPresenter.setAdapter(deleteKeywordAdapter);
        mPresenter.fetchKeyword();

        deleteKeywordAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View itemView, RecyclerView.Adapter adapter, final int position) {
                showDeleteDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteKeyword(position);
                    }
                });
            }
        });

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //전체삭제 버튼
    @OnClick(R.id.delete_keyword_btn_remove_all)
    void onClickRemoveAll() {
        showDeleteDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.deleteKeywordAll();
            }
        });

    }

    @Override
    public void showTextNoKeyword() {
        mKeywordContainer.setVisibility(View.GONE);
    }

    @Override
    public void setAdapter(DeleteKeywordAdapter deleteKeywordAdapter) {
        mList.setLayoutManager(new LinearLayoutManager(DeleteKeywordActivity.this));
        mList.addItemDecoration(new DividerItemDecoration(mList.getContext(), DividerItemDecoration.VERTICAL));
        mList.setAdapter(deleteKeywordAdapter);
    }

    private void showDeleteDialog(DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setPositiveButton("삭제", onClickListener);
        alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();     //닫기S
            }
        });
        alert.setMessage("키워드를 삭제 하시겠습니까?");
        alert.show();
    }
}