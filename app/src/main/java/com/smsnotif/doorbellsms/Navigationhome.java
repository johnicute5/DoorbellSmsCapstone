package com.smsnotif.doorbellsms;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.doorbellsms.R;
import com.example.doorbellsms.databinding.ActivityNavigationhomeBinding;
import com.google.android.material.navigation.NavigationView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Navigationhome extends AppCompatActivity{
    private AppBarConfiguration mAppBarConfiguration;
    String Ip = "192.168.43.245:5001";
    private ActivityNavigationhomeBinding binding;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Socket myAppSocket = null;
    ImageView img1, img2;
    public static String CMD ="0";
    public static String wifimoduleip ="";
    public static int wifimoduleport = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavigationhomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        img1 = findViewById(R.id.goback);
        img2 = findViewById(R.id.reboot);
        setSupportActionBar(binding.appBarNavigationhome.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_contact, R.id.nav_home, R.id.nav_aboutus, R.id.nav_gallery, R.id.navlogout, R.id.profile, R.id.nav_aboutus, R.id.share)
                .setOpenableLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigationhome);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        binding.appBarNavigationhome.profiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = Navigationhome.this.getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_navigationhome, new userdetails());
                fragmentTransaction.commit();
            }
        });
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = Navigationhome.this.getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_navigationhome, new HomeFragment());
                fragmentTransaction.commit();

            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Navigationhome.this,"Rebooting Doorbell",Toast.LENGTH_SHORT).show();
                getIpandPort();
                CMD = "reboot";
                Socket_AsynTask doorbell_reboot = new Socket_AsynTask();
                doorbell_reboot.execute();
            }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigationhome, menu);
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigationhome);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public class Socket_AsynTask extends AsyncTask<Void,Void,Void>
    {
        Socket socket;
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                InetAddress inetAdress= InetAddress.getByName(Navigationhome.wifimoduleip);
                socket=new java.net.Socket(inetAdress,Navigationhome.wifimoduleport);
                DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeBytes(CMD);
                dataOutputStream.close();
                socket.close();
        } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public void getIpandPort(){

        Log.d("MYTEST","IP String:"+Ip);
        String temp[]=Ip.split(":");
        wifimoduleip=temp[0];
        wifimoduleport = Integer.valueOf(temp[1]);
        Log.d("MY TEST","IP:"+wifimoduleip);
        Log.d("MY TEST","PORT:"+wifimoduleport);
    }
}