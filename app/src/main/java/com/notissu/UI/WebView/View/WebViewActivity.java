package com.notissu.UI.WebView.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.notissu.R;
import com.notissu.UI.WebView.Presenter.WebViewContract;
import com.notissu.UI.WebView.Presenter.WebViewPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity implements WebViewContract.View{
    @BindView(R.id.activity_web_view_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.web_view_wv)
    WebView mWebView;

    private WebViewContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPresenter = new WebViewPresenter(getIntent().getExtras(), this);
        mPresenter.start();

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void setPresenter(WebViewContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showWebView(String link) {
        mWebView.loadUrl(link);
        mWebView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
