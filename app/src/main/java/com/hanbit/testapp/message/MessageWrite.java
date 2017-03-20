package com.hanbit.testapp.message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by hb2004 on 2017-03-17.
 */

public class MessageWrite extends AppCompatActivity {
String temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       /* setContentView(R.layout.activity_test);*/
        final Context context=MessageWrite.this;
        LinearLayout frame=new LinearLayout(context);
        LinearLayout.LayoutParams mm=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams mw=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        frame.setLayoutParams(mm);
      /*  TextView tv=new TextView(context);
        tv.setGravity(1);
        ViewGroup.MarginLayoutParams top100=new ViewGroup.MarginLayoutParams(mw);
        top100.setMargins(0,200,0,0);
        tv.setLayoutParams(new LinearLayout.LayoutParams(top100));
        tv.setGravity(1);
        tv.setTextSize(30);*/
        Intent intent=this.getIntent();
        String idAndPhone=intent.getExtras().getString("idAndPhone").toString();
        Log.d("넘어온id,이름,전화번호:",idAndPhone);
        WebView wv=new WebView(context);
        wv.setLayoutParams(mm);
        WebSettings setting=wv.getSettings();
        setting.setUseWideViewPort(true);
        setting.setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient());

        wv.addJavascriptInterface(new JavascriptInterface(){
            @android.webkit.JavascriptInterface
            public void showToast(String message){
                Toast.makeText(context,temp, Toast.LENGTH_SHORT).show();
            }

            @Override @android.webkit.JavascriptInterface
            public void sendMessage(String message) {
                temp=message;
            }

        },"Hybrid");
        wv.loadUrl("file:///android_asset/www/html/messageWrite.html");
        frame.addView(wv);
        setContentView(frame);


    }
    //---------------------------------------------------프록시 패턴
    public interface JavascriptInterface{   // Javascriptsms json을 던짐
        public void showToast(String message);
        public void sendMessage(String message);
    }
}
