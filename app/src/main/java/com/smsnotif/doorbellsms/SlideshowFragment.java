package com.smsnotif.doorbellsms;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.doorbellsms.R;

import java.util.HashMap;

public class SlideshowFragment extends Fragment {
    FragmentManager fragmentManager1;
    FragmentTransaction fragmentTransaction2;
    ProgressDialog pDialog;
    String HttpURL = "https://rjjmsmsdoorbell.tech/main/showprofile.php";
    String TempUSER, TempPassword;
    String userid;
    String FinalJSonObject;
    HashMap<String,String> hashMap = new HashMap<>();
    HashMap<String,String> ResultHash = new HashMap<>();
    String ParseResult ;
    HttpParse httpParse = new HttpParse();

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);
        ImageView image1 = view.findViewById(R.id.devimg1);
        ImageView image2 = view.findViewById(R.id.devimg2);
        ImageView image3 = view.findViewById(R.id.devimg3);
        ImageView image4 = view.findViewById(R.id.devimg4);



        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager1 = getActivity().getSupportFragmentManager();
                fragmentTransaction2 = fragmentManager1.beginTransaction();
                fragmentTransaction2.replace(R.id.nav_host_fragment_content_navigationhome, new reneelyn());
                fragmentTransaction2.commit();
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager1 = getActivity().getSupportFragmentManager();
                fragmentTransaction2 = fragmentManager1.beginTransaction();
                fragmentTransaction2.replace(R.id.nav_host_fragment_content_navigationhome, new jandrei());
                fragmentTransaction2.commit();
            }

        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager1 = getActivity().getSupportFragmentManager();
                fragmentTransaction2 = fragmentManager1.beginTransaction();
                fragmentTransaction2.replace(R.id.nav_host_fragment_content_navigationhome, new nico());
                fragmentTransaction2.commit();
            }
        });
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager1 = getActivity().getSupportFragmentManager();
                fragmentTransaction2 = fragmentManager1.beginTransaction();
                fragmentTransaction2.replace(R.id.nav_host_fragment_content_navigationhome, new sion());
                fragmentTransaction2.commit();
            }

        });
        return view;
    }

}



