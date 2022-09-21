package com.smsnotif.doorbellsms;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.doorbellsms.R;


public class HomeFragment extends Fragment {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public HomeFragment(){super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)  {
        ImageView image1=view.findViewById(R.id.menu1);
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_navigationhome, new GalleryFragment());
                fragmentTransaction.commit();
            }
        });
        ImageView image2=view.findViewById(R.id.menu4);
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_navigationhome, new streamvid());
                fragmentTransaction.commit();
            }
        });
        ImageView image3=view.findViewById(R.id.menu3);
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_navigationhome, new showdbdetails());
                fragmentTransaction.commit();
            }
        });
        ImageView image4=view.findViewById(R.id.menu2);
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_navigationhome, new qrcodescanner());
                fragmentTransaction.commit();
            }
        });
    }
}

