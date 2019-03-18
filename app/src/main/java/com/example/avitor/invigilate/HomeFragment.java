package com.example.avitor.invigilate;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private View view;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }


    //Starting web view when the fragment is about to appear
    @Override
    public void onStart() {
        super.onStart();
        view = getView();
        assert view != null;
        WebView webView = view.findViewById(R.id.homePageWeb);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.google.com/");


    }
}
