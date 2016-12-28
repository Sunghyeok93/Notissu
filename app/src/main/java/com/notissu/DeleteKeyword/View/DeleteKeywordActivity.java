package com.notissu.DeleteKeyword.View;

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
import com.notissu.DeleteKeyword.Presenter.DeleteKeywordPresenterImpl;
import com.notissu.R;
import com.notissu.Util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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


    DeleteKeywordAdapter deleteKeywordAdapter;

    DeleteKeywordContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_keyword);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        deleteKeywordAdapter = new DeleteKeywordAdapter(this);

        presenter = new DeleteKeywordPresenterImpl();
        presenter.attachView(this);
        presenter.setAdapterModel(deleteKeywordAdapter);
        presenter.setAdapterView(deleteKeywordAdapter);

        mRvKeyword.setLayoutManager(new LinearLayoutManager(DeleteKeywordActivity.this));
        mRvKeyword.addItemDecoration(new DividerItemDecoration(mRvKeyword.getContext(),DividerItemDecoration.VERTICAL));
        mRvKeyword.setAdapter(deleteKeywordAdapter);

        presenter.loadKeyword();
    }

    @OnClick(R.id.delete_keyword_toolbar)
    void onClickHome() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    /*//리스트 중 하나 삭제
    @OnItemClick(R.id.delete_keyword_rv_keyword)
    void onItemClickLvKeyword(int i) {
        final String title = deleteKeywordAdapter.getItem(i);
        showDeleteDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteKeyword(title);
            }
        });
    }

    //전체삭제 버튼
    @OnClick(R.id.delete_keyword_btn_remove_all)
    void onClickRemoveAll() {
        showDeleteDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteKeywordAll();
            }
        });
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

    private void deleteKeywordAll() {
        int keywordSize = deleteKeywordAdapter.getCount();
        //i를 증가시키면 index오류가 난다. 맨 첫번째것을 여러번 지우는것으로 했다.
        for (int i = 0; i < keywordSize; i++) {
            String title = deleteKeywordAdapter.getItem(0);
            deleteKeyword(title);
        }
    }

    private void deleteKeyword(String title){
        //키워드를 지울 때 무엇을 해야할까?
        //1. DB에서 지워져야한다.
        mKeywordProvider.deleteKeyword(title);
        //2. Menu에서 지워져야한다.
        Menu menu = NavigationMenu.getInstance().getKeywordMenu();
        int menuSize = menu.size();
        // 내가 지우고자 하는 키워드의 이름으로 아이템을 찾고 아이디를 받아옴
        for(int i=0;i< menuSize;i++) {
            String menuTitle = menu.getItem(i).getTitle().toString();
            if (title.equals(menuTitle) == true) {
                //지워버릴 Item의 아이디 얻어옴
                int itemId = menu.getItem(i).getItemId();
                menu.removeItem(itemId);
                break;
            }
        }
        //3. 화면을 갱신해야한다.
        deleteKeywordAdapter.remove(title);
        deleteKeywordAdapter.notifyDataSetChanged();
        if (deleteKeywordAdapter.getCount() == 0) {
            mRlList.setVisibility(View.GONE);
        }
    }*/

    @Override
    public void setVisibilityGone() {
        mRvKeyword.setVisibility(View.GONE);
    }
}