package com.smsnotif.doorbellsms;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.doorbellsms.R;

public class LogoutFragment extends Fragment {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logout,container,false);
        Button btnyes= view.findViewById(R.id.yes);
        Button btnno = view.findViewById(R.id.no);

        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_navigationhome, new HomeFragment());
                fragmentTransaction.commit();

            }
        });
        btnyes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Signed out", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);


            }
        });
        return view;
    }
}