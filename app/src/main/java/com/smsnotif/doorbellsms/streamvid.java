package com.smsnotif.doorbellsms;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.doorbellsms.R;

public class streamvid extends Fragment {

    WebView wv;
//    String url = "https://rjjmsmsdoorbell.tech/pi_video_doorbell/templates/index.html";
//    String url ="http://proxy66.rt3.io:31001/?fbclid=IwAR36arJF7NW8RjWdwsNG9UAy50LWuDfHl3grUoR-81PhsaQSUYSi_G14CMU";
    String url ="http://192.168.43.245:5000";
    public streamvid(){super(R.layout.livestream);
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wv = (WebView)view.findViewById(R.id.webview);
        wv.setWebViewClient(new WebViewClient());
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl(url);

    }
}
