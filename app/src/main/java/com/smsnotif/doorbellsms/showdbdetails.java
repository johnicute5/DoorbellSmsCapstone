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

public class showdbdetails extends Fragment {

    ListView doorbelllistview;
    ProgressBar progressBar;
    String HttpUrl = "https://rjjmsmsdoorbell.tech/main/showdoorbell.php";
    List<String> dbid = new ArrayList<>();
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public showdbdetails(){
        super(R.layout.fragment_showrecords);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        doorbelllistview = (ListView)view.findViewById(R.id.listview);

        progressBar = (ProgressBar)view.findViewById(R.id.progressbar);

        new GetHttpResponse(getActivity()).execute();

        //Adding ListView Item click Listener.
        doorbelllistview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO Auto-generated method stub

                 fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_navigationhome,new showsinglerec());
                getActivity().getIntent().putExtra("ListViewValue", dbid.get(position).toString());
                fragmentTransaction.commit();
                // Sending ListView clicked value using intent.
                //Finishing current activity after open next activity.


            }
        });
    }

    // JSON parse class started from here.
    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        public Context context;

        String JSonResult;

        List<doorbell> studentList;

        public GetHttpResponse(Context context)
        {
            this.context = context;
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
                    JSonResult = httpServicesClass.getResponse();

                    if(JSonResult != null)
                    {
                        JSONArray js = null;

                        try {
                            js = new JSONArray(JSonResult);

                            JSONObject jsonObject;

                            doorbell dbDoorbell;

                            studentList = new ArrayList<doorbell>();

                            for(int i=0; i<js.length(); i++)
                            {
                                dbDoorbell = new doorbell();

                                jsonObject = js.getJSONObject(i);

                                // Adding Student Id TO IdList Array.
                                dbid.add(jsonObject.getString("id").toString());
                                //Adding Student Name.
                                dbDoorbell.doorbellName = jsonObject.getString("dbname").toString();
                                studentList.add(dbDoorbell);

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
                    Toast.makeText(context, httpServicesClass.getErrorMessage(), Toast.LENGTH_SHORT).show();
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
            progressBar.setVisibility(View.GONE);

            doorbelllistview.setVisibility(View.VISIBLE);

            if (studentList!= null) {
                Listadapter adapter = new Listadapter(studentList, context);

                doorbelllistview.setAdapter(adapter);
            }
        }
    }
}