package com.smsnotif.doorbellsms;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.doorbellsms.R;

public class sound extends Fragment {
    public sound(){
        super(R.layout.sound);
    }
    private Button play;
    private Spinner dropdown;
    private MediaPlayer sound1,sound2,sound3,sound4;
    private MediaPlayer[] holder = new MediaPlayer[]{sound1, sound2, sound3, sound4};

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sound1 = MediaPlayer.create(getActivity(),R.raw.homestandard);
        sound2 = MediaPlayer.create(getActivity(),R.raw.elevator);
        sound3 = MediaPlayer.create(getActivity(),R.raw.christmas);
        sound4 = MediaPlayer.create(getActivity(),R.raw.quickquick);
        play = (Button) view.findViewById(R.id.playsound);

        dropdown =(Spinner) view.findViewById(R.id.dropd);
        String[] soundlist = getResources().getStringArray(R.array.sound_list);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item ,soundlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
