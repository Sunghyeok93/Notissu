package com.notissu.DeleteKeyword.View;

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

import com.notissu.DeleteKeyword.Adapter.DeleteKeywordAdapter;
import com.notissu.DeleteKeyword.Presenter.DeleteKeywordContract;
import com.notissu.DeleteKeyword.Presenter.DeleteKeywordPresenter;
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
    RelativeLayout mRlList;
    @BindView(R.id.delete_keyword_rv_keyword)
    RecyclerView mRvKeyword;
    @BindView(R.id.delete_keyword_btn_remove_all)
    Button mBtnRemoveAll;

    DeleteKeywordContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_keyword);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DeleteKeywordAdapter deleteKeywordAdapter = new DeleteKeywordAdapter(this);

        presenter = new DeleteKeywordPresenter(this,deleteKeywordAdapter,deleteKeywordAdapter);
        presenter.start();

        mRvKeyword.setLayoutManager(new LinearLayoutManager(DeleteKeywordActivity.this));
        mRvKeyword.addItemDecoration(new DividerItemDecoration(mRvKeyword.getContext(),DividerItemDecoration.VERTICAL));
        mRvKeyword.setAdapter(deleteKeywordAdapter);

        deleteKeywordAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.Adapter adapter, final int position) {
                showDeleteDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.deleteKeyword(position);
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
                presenter.deleteKeywordAll();
            }
        });

    }

    @Override
    public void showTextNoKeyword() {
        mRlList.setVisibility(View.GONE);
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

    @Override
    public void setPresenter(@NonNull DeleteKeywordContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }
}