package com.notissu.Fragment;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.notissu.Adapter.KeywordAdapter;
import com.notissu.Dialog.DelKeywordDialog;
import com.notissu.R;
import com.notissu.SyncAdapter.RssDatabase;

import java.util.ArrayList;

/**
 * Created by dnfld on 2016-12-10.
 */

public class DelKeywordFragment extends Fragment {
    private static final String KEY_KEYWORD_ROWS = "KEY_KEYWORD_ROWS";
    NavigationView navigationView;
    KeywordAdapter keywordAdapter;
    ListView keywordList;
    TextView delAllkeyword = null;
    View rootView;
    RssDatabase rssDatabase = RssDatabase.getInstance();



    public static Fragment newInstance(ArrayList<String> keywordRows) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(KEY_KEYWORD_ROWS,keywordRows);

        Fragment fragment = new DelKeywordFragment();
        fragment.setArguments(bundle);

        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_setting_keyword, container, false);

        View view = inflater.inflate(R.layout.activity_main, container, false);

        navigationView =(NavigationView) view.findViewById(R.id.nav_view);

        initWidget();
        settingWidget();


        return rootView;

    }
    private void initWidget() {
        keywordList = (ListView) rootView.findViewById(R.id.notice_list);

    }

    private void settingWidget() {

        Bundle bundle = getArguments();
        RssDatabase rssDatabase = RssDatabase.getInstance();
        ArrayList<String> keywordList = new ArrayList<String>(rssDatabase.getKeyword());
        keywordAdapter = new KeywordAdapter(getContext(), keywordList);
        this.keywordList.setAdapter(keywordAdapter);
        if(keywordList.size()!=0)
        {
            delAllkeyword = (TextView) rootView.findViewById(R.id.del_all_keyword);
            delAllkeyword.setText("모든 키워드 삭제");
            settingListener();
        }
        else
        {
            delAllkeyword = (TextView) rootView.findViewById(R.id.del_all_keyword);
            delAllkeyword.setText("등록된 키워드가 없습니다.");
        }
    }
    private void settingListener() {

        keywordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentManager manager = getFragmentManager();
                final String item = keywordAdapter.getItem(i);
                DelKeywordDialog mydialog = DelKeywordDialog.newInstance();
                mydialog.setOnDelKeywordListner(new DelKeywordDialog.OnAddKeywordListner() {
                    @Override
                    public void onDel(Bundle bundle) {
                       int name = bundle.getInt(DelKeywordDialog.DEL_KEY_KEYWORD);
                        if (name==1) {
                            delKeyword(item, 0);
                        }
                    }
                });
                mydialog.show(manager, "");
            }
        });
        delAllkeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();

                DelKeywordDialog mydialog = DelKeywordDialog.newInstance();
                mydialog.setOnDelKeywordListner(new DelKeywordDialog.OnAddKeywordListner() {
                    @Override
                    public void onDel(Bundle bundle) {
                        int name = bundle.getInt(DelKeywordDialog.DEL_KEY_KEYWORD);
                        if (name == 1) {
                            delKeyword(null, 1);
                        }
                    }
                });
                mydialog.show(manager, "");
            }

        });
    }

    public void delKeyword(String itemName, int DEL_ALL_KEWORD){

        Menu menu = navigationView.getMenu().getItem(2).getSubMenu();
        int itemId = 0;
        // 내가 지우고자 하는 키워드의 이름으로 아이템을 찾고 아이디를 받아옴
        Toast.makeText(getContext(), "itemName : " +itemName , Toast.LENGTH_LONG).show();
        Log.e("superdroid","itemName 받아옴 : " +itemName);
        for(int i=0;i< menu.size();i++) {
            if ( itemName.equals((String) menu.getItem(i).getTitle()) == true)
            {
                itemId = menu.getItem(i).getItemId();
            }
        }
        if(itemName != null) {
            rssDatabase.deleteKeyword(itemName);
            menu.removeItem(itemId);
        }
        // 키워드 전체삭제 코드
        if(DEL_ALL_KEWORD == 1) {
            for(int i=0;i<menu.size();i++) {
               rssDatabase.deleteKeyword((String)menu.getItem(i).getTitle());
            }
            menu.removeGroup(R.id.group_keyword);
        }
        // fragment 재실행함
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();

    }
}
