package com.notissu.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.notissu.Adapter.DeleteKeywordAdapter;
import com.notissu.Fragment.SettingFragment;
import com.notissu.R;
import com.notissu.SyncAdapter.RssDatabase;
import com.notissu.Util.LogUtils;

import java.util.ArrayList;

public class DeleteKeywordActivity extends AppCompatActivity {
    private static final String TAG = LogUtils.makeLogTag(DeleteKeywordActivity.class);
    DeleteKeywordAdapter deleteKeywordAdapter;
    RelativeLayout mRlList;
    ListView mLvKeyword;
    Button mBtnRemoveAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_keyword);

        initWidget();
        settingWidget();
        settingListener();
    }

    private void initWidget() {
        mLvKeyword = (ListView) findViewById(R.id.delete_keyword_lv_keyword);
        mRlList = (RelativeLayout) findViewById(R.id.delete_keyword_rl_list);
        mBtnRemoveAll = (Button) findViewById(R.id.delete_keyword_btn_remove_all);
    }

    private void settingWidget() {
        Intent dataIntent = getIntent();
        String title = dataIntent.getStringExtra(SettingFragment.KEY_DELETE_KEYWORD_TITLE);
        setTitle(title);

        RssDatabase rssDatabase = RssDatabase.getInstance();
        ArrayList<String> keywordListDB = new ArrayList<String>(rssDatabase.getKeyword());
        deleteKeywordAdapter = new DeleteKeywordAdapter(this, keywordListDB);
        mLvKeyword.setAdapter(deleteKeywordAdapter);

        if (deleteKeywordAdapter.getCount() == 0) {
            mRlList.setVisibility(View.GONE);
        }
    }
    private void settingListener() {
        //리스트 중 하나 삭제
        mLvKeyword.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String title = deleteKeywordAdapter.getItem(i);
                showDeleteDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteKeyword(title);
                    }
                });
            }
        });

        //전체삭제 버튼
        mBtnRemoveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteKeywordAll();
                    }
                });
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
        RssDatabase rssDatabase = RssDatabase.getInstance();
        //키워드를 지울 때 무엇을 해야할까?
        //1. DB에서 지워져야한다.
        rssDatabase.deleteKeyword(title);
        //2. Menu에서 지워져야한다.


        //3. 화면을 갱신해야한다.
        deleteKeywordAdapter.remove(title);
        deleteKeywordAdapter.notifyDataSetChanged();
        if (deleteKeywordAdapter.getCount() == 0) {
            mRlList.setVisibility(View.GONE);
        }

        /*Menu menu = navigationView.getMenu().getItem(2).getSubMenu();
        int itemId = 0;
        // 내가 지우고자 하는 키워드의 이름으로 아이템을 찾고 아이디를 받아옴
        Log.e(TAG,"itemName 받아옴 : " +title);
        for(int i=0;i< menu.size();i++) {
            if ( title.equals((String) menu.getItem(i).getTitle()) == true)
            {
                itemId = menu.getItem(i).getItemId();
            }
        }*/
    }

}
