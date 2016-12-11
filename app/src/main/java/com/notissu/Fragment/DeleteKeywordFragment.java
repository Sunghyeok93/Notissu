package com.notissu.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.notissu.Adapter.DeleteKeywordAdapter;
import com.notissu.R;
import com.notissu.SyncAdapter.RssDatabase;
import com.notissu.Util.LogUtils;

import java.util.ArrayList;

/**
 * Created by dnfld on 2016-12-10.
 */

public class DeleteKeywordFragment extends Fragment {
    private static final String TAG = LogUtils.makeLogTag(DeleteKeywordFragment.class);
    View mRootView;
    DeleteKeywordAdapter deleteKeywordAdapter;
    RelativeLayout mRlList;
    ListView mLvKeyword;
    Button mBtnRemoveAll;


    public static DeleteKeywordFragment newInstance() {
        Bundle bundle = new Bundle();
        DeleteKeywordFragment fragment = new DeleteKeywordFragment();
        fragment.setArguments(bundle);

        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_delete_keyword, container, false);

        View view = inflater.inflate(R.layout.activity_main, container, false);

        initWidget();
        settingWidget();
        settingListener();

        return mRootView;

    }
    private void initWidget() {
        mLvKeyword = (ListView) mRootView.findViewById(R.id.delete_keyword_lv_keyword);
        mRlList = (RelativeLayout) mRootView.findViewById(R.id.delete_keyword_rl_list);
        mBtnRemoveAll = (Button) mRootView.findViewById(R.id.delete_keyword_btn_remove_all);
    }

    private void settingWidget() {
        RssDatabase rssDatabase = RssDatabase.getInstance();
        ArrayList<String> keywordListDB = new ArrayList<String>(rssDatabase.getKeyword());
        deleteKeywordAdapter = new DeleteKeywordAdapter(getContext(), keywordListDB);
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
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
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
