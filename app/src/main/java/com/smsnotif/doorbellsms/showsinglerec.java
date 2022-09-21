package com.smsnotif.doorbellsms;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.doorbellsms.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class showsinglerec extends Fragment {
    String ParseResult ;
    HttpParse httpParse = new HttpParse();
    ProgressDialog pDialog;
    // Http Url For Filter Doorbell Data from Id Sent from previous activity.
    String HttpURL = "https://rjjmsmsdoorbell.tech/main/filterdoorbelldata.php";
    // Http URL for delete Already Open Doorbell Record.
    String HttpUrlDeleteRecord = "https://rjjmsmsdoorbell.tech/main/deletedoorbell.php";
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HashMap<String,String> ResultHash = new HashMap<>();
    String FinalJSonObject ;
    TextView NAME,PHONE_NUMBER,MODEL, MESSAGE;
    String NameHolder, NumberHolder, MessageHolder, ModelHolder;
    Button UpdateButton, DeleteButton;
    ProgressDialog progressDialog2;
    String TempItem;
    public showsinglerec(){super(R.layout.fragment_manage);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MODEL = (TextView)view.findViewById(R.id.edittextdbmodel);
        NAME = (TextView)view.findViewById(R.id.textdbname);
        PHONE_NUMBER = (TextView)view.findViewById(R.id.textphonenum);
        MESSAGE = (TextView)view.findViewById(R.id.textnotifmessage);
        UpdateButton = (Button)view.findViewById(R.id.buttonUpdate);
        DeleteButton = (Button)view.findViewById(R.id.buttonDelete);
        //Receiving the ListView Clicked item value send by previous activity.
        TempItem =getActivity().getIntent().getStringExtra("ListViewValue");
        //Calling method to filter Doorbell Record and open selected record.
        HttpWebCall(TempItem);

        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_navigationhome,new UpdateActivity());
                // Sending Doorbell Id, Name, Number and Class to next UpdateActivity.
                getActivity().getIntent().putExtra("id", TempItem);
                getActivity().getIntent().putExtra("dbmodel", ModelHolder);
                getActivity().getIntent().putExtra("dbname", NameHolder);
                getActivity().getIntent().putExtra("phonenumber", NumberHolder);
                getActivity().getIntent().putExtra("notificationmessage", MessageHolder);
                fragmentTransaction.commit();
                // Finishing current activity after opening next activity.


            }
        });

        // Add Click listener on Delete button.
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling Doorbell delete method to delete current record using Doorbell ID.
                StudentDelete(TempItem);

            }
        });

    }
    // Method to Delete Doorbell Record
    public void StudentDelete(final String StudentID) {

        class StudentDeleteClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog2 = ProgressDialog.show(getActivity(), "Loading Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog2.dismiss();
                Toast.makeText(getActivity(), httpResponseMsg.toString(), Toast.LENGTH_LONG).show();


            }@Override
            protected String doInBackground(String... params) {

                // Sending STUDENT id.
                hashMap.put("id", params[0]);

                finalResult = httpParse.postRequest(hashMap, HttpUrlDeleteRecord);

                return finalResult;
            }
        }

        StudentDeleteClass studentDeleteClass = new StudentDeleteClass();

        studentDeleteClass.execute(StudentID);
    }


    //Method to show current record Current Selected Record
    public void HttpWebCall(final String PreviousListViewClickedItem){

        class HttpWebCallFunction extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = ProgressDialog.show(getActivity(),"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                pDialog.dismiss();

                //Storing Complete JSon Object into String Variable.
                FinalJSonObject = httpResponseMsg ;

                //Parsing the Stored JSOn String to GetHttpResponse Method.
                new GetHttpResponse(getActivity()).execute();

            }
            @Override
            protected String doInBackground(String... params) {

                ResultHash.put("id",params[0]);

                ParseResult = httpParse.postRequest(ResultHash, HttpURL);

                return ParseResult;
            }
        }

        HttpWebCallFunction httpWebCallFunction = new HttpWebCallFunction();

        httpWebCallFunction.execute(PreviousListViewClickedItem);
    }


    // Parsing Complete JSON Object.
    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        public Context context;

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
            try
            {
                if(FinalJSonObject != null)
                {
                    JSONArray jsonArray = null;

                    try {
                        jsonArray = new JSONArray(FinalJSonObject);

                        JSONObject jsonObject;

                        for(int i=0; i<jsonArray.length(); i++)
                        {
                            jsonObject = jsonArray.getJSONObject(i);

                            // Storing Doorbell Name, Phone Number, Class into Variables.
                            ModelHolder = jsonObject.getString("dbmodel").toString();
                            NameHolder = jsonObject.getString("dbname").toString();
                            NumberHolder = jsonObject.getString("phonenumber").toString();
                            MessageHolder = jsonObject.getString("notificationmessage").toString();

                        }
                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
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
            // Setting Doorbell Name, Phone Number, Class into TextView after done all process .
            MODEL.setText(ModelHolder);
            NAME.setText(NameHolder);
            PHONE_NUMBER.setText(NumberHolder);
            MESSAGE.setText(MessageHolder);

        }
    }
}
