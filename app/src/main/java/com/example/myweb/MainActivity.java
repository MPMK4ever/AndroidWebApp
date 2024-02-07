package com.example.myweb;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

        ImageView webBack,webForward,webRefresh, webStop,webHome,webHistory;
        EditText urlInput;
        ImageView clearUrl;
        WebView myWebView;
        ProgressBar progressBar;
        Stack<String> history;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webBack = findViewById(R.id.web_back);
        webForward =findViewById(R.id.web_forward);
        webRefresh = findViewById(R.id.web_refresh);
        webStop = findViewById(R.id.web_stop);
        webHome = findViewById(R.id.web_home);
        webHistory = findViewById(R.id.web_history);

        urlInput = findViewById(R.id.url_input);
        clearUrl = findViewById(R.id.clear_icon);

        myWebView = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.progress_bar);
        history = new Stack<>();




        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setLoadWithOverviewMode(true);

        myWebView.setWebViewClient(new MyWebViewClient());
        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override


            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }
        });


    loadMyUrl("google.com");

    urlInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int i, KeyEvent event) {

            if( i == EditorInfo.IME_ACTION_GO || i == EditorInfo.IME_ACTION_DONE){
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(urlInput.getWindowToken(),0);
                loadMyUrl(urlInput.getText().toString());
                return true;
            }
            return false;
        }
    });

    clearUrl.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            urlInput.setText("");
        }
    });

    webBack.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(myWebView.canGoBack()){
                myWebView.goBack();
            }
        }
    });

    webForward.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(myWebView.canGoForward()){
                myWebView.goForward();
            }
        }
    });

    webRefresh.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            myWebView.reload();
        }
    });

    webStop.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) { myWebView.stopLoading();
        }
    });

    webHome.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v)  {
            myWebView.loadUrl("http://google.com");


        }
    });

    webHistory.setOnClickListener((new View.OnClickListener() {
        @Override
        public void onClick(View v){

        }
    }
    ));


    }


    void loadMyUrl(String url){
        boolean matchUrl = Patterns.WEB_URL.matcher(url).matches();
        if (matchUrl){
            myWebView.loadUrl(url);
        }else{
            myWebView.loadUrl("google.com/search?q="+url);
        }
    }

    class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            urlInput.setText(myWebView.getUrl());
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.INVISIBLE);
        }


    }



    @Override
    protected void onStart() {
        Log.d("ONSTART","Starting MainActivity");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d("ONSTOP","Stopping MainActivity");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("ONDESTROY","Destroying MainActivity");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.d("ONPAUSE","Pausing MainActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d("ONRESUME","Resuming MainActivity");
        super.onResume();
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the URL of the current page
        String currentUrl = myWebView.getUrl();
        if (currentUrl != null) {
            outState.putString("currentUrl", currentUrl);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Restore the URL of the current page
        String savedUrl = savedInstanceState.getString("currentUrl");
        if (savedUrl != null) {
            loadMyUrl(savedUrl);
        }
    }


}


