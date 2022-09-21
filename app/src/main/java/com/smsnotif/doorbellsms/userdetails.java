package com.smsnotif.doorbellsms;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.doorbellsms.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class userdetails extends Fragment {

    private ProgressBar progressBar3;
    private String HttpUrl = "https://rjjmsmsdoorbell.tech/main/showprofile.php";
    private List<String> useid = new ArrayList<>();
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    ListView userlistview;

    public userdetails(){
        super(R.layout.fragment_showuser);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userlistview = (ListView) view.findViewById(R.id.listviewuser);

        progressBar3 = (ProgressBar)view.findViewById(R.id.progressbaruser);

        new GetHttpResponse2(getActivity()).execute();

        //Adding ListView Item click Listener.
        userlistview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO Auto-generated method stub

                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_navigationhome,new userprofile());
                getActivity().getIntent().putExtra("ListViewValue", useid.get(position).toString());
                fragmentTransaction.commit();
                // Sending ListView clicked value using intent.
                //Finishing current activity after open next activity.


            }
        });
    }

    // JSON parse class started from here.
    private class GetHttpResponse2 extends AsyncTask<Void, Void, Void>
    {
        public Context context2;

        String JSonResult2;

        List<Username> userList2;

        public GetHttpResponse2(Context context2)
        {
            this.context2 = context2;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            // Passing HTTP URL to HttpServicesClass Class.
            HttpServicesClass httpServicesClass = new HttpServicesClass(HttpUrl);
            try
            {
                httpServicesClass.ExecutePostRequest();

                if(httpServicesClass.getResponseCode() == 200)
                {
                    JSonResult2 = httpServicesClass.getResponse();

                    if(JSonResult2 != null)
                    {
                        JSONArray js2 = null;

                        try {
                            js2 = new JSONArray(JSonResult2);

                            JSONObject jsonObject1;

                            Username usernamalist;

                            userList2 = new ArrayList<Username>();

                            for(int i=0; i<js2.length(); i++)
                            {
                                usernamalist = new Username();

                                jsonObject1 = js2.getJSONObject(i);

                                // Adding Student Id TO IdList Array.
                                useid.add(jsonObject1.getString("user_id").toString());
                                //Adding Student Name.
                                usernamalist.Users_name = jsonObject1.getString("username").toString();
                                userList2.add(usernamalist);

                            }
                        }
                        catch (JSONException e) {
                            // TODO Auto-generated  catch block
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    Toast.makeText(context2, httpServicesClass.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)

        {
            progressBar3.setVisibility(View.GONE);

            userlistview.setVisibility(View.VISIBLE);

            if (userList2!= null) {
                Listadapteruser adapter = new Listadapteruser(userList2, context2);

                userlistview.setAdapter(adapter);
            }
        }
    }
}