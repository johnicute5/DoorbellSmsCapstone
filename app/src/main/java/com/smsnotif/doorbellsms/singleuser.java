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

public class singleuser extends Fragment {
    String ParseResult ;
    HttpParse httpParse = new HttpParse();
    ProgressDialog pDialog;
    // Http Url For Filter Doorbell Data from Id Sent from previous activity.
    String HttpURL = "https://rjjmsmsdoorbell.tech/main/userprofile.php";
    // Http URL for delete Already Open Doorbell Record.
    String HttpUrlDeleteRecord = "https://rjjmsmsdoorbell.tech/main/deletesingle.php";
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HashMap<String,String> ResultHash = new HashMap<>();
    String FinalJSonObject ;
    TextView FNAME,LNAME,USERNAME,PASSWORD, EMAIL;
    String FHOLDER,LHOLDER,EMAILHOLDER,PASSWORDHOLDER,USERHOLDER;
    Button UpdateButtons, DeleteButtons;
    ProgressDialog progressDialog2;
    String TempItem,tempassword;
    public singleuser(){super(R.layout.userprofile);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FNAME= (TextView)view.findViewById(R.id.textfname1);
        LNAME= (TextView)view.findViewById(R.id.textlname1);
        EMAIL= (TextView)view.findViewById(R.id.textemail1);
        USERNAME= (TextView)view.findViewById(R.id.textusername1);
        PASSWORD= (TextView)view.findViewById(R.id.textpassword1);
        UpdateButtons = (Button)view.findViewById(R.id.buttonUpdate1);
        DeleteButtons = (Button)view.findViewById(R.id.buttonDelete2);
        //Receiving the ListView Clicked item value send by previous activity.
        TempItem =getActivity().getIntent().getStringExtra(Login.Username);
        tempassword =getActivity().getIntent().getStringExtra(Login.Password);
        //Calling method to filter Doorbell Record and open selected record.
        HttpWebCall(TempItem,tempassword);

        UpdateButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_navigationhome,new updatesingleuser());
                // Sending Doorbell Id, Name, Number and Class to next UpdateActivity.
                getActivity().getIntent().putExtra("username", TempItem);
                getActivity().getIntent().putExtra("username", USERHOLDER);
                getActivity().getIntent().putExtra("password", PASSWORDHOLDER);
                getActivity().getIntent().putExtra("email", EMAILHOLDER);
                getActivity().getIntent().putExtra("firstname", FHOLDER);
                getActivity().getIntent().putExtra("lastname",LHOLDER);
                fragmentTransaction.commit();
                // Finishing current activity after opening next activity.


            }
        });

        // Add Click listener on Delete button.
        DeleteButtons.setOnClickListener(new View.OnClickListener() {
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
                hashMap.put("username", params[0]);

                finalResult = httpParse.postRequest(hashMap, HttpUrlDeleteRecord);

                return finalResult;
            }
        }

        StudentDeleteClass studentDeleteClass = new StudentDeleteClass();

        studentDeleteClass.execute(StudentID);
    }


    //Method to show current record Current Selected Record
    public void HttpWebCall(final String PreviousListViewClickedItem,final String getpass){

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

                ResultHash.put("username",params[0]);
                ResultHash.put("password",params[1]);

                ParseResult = httpParse.postRequest(ResultHash, HttpURL);

                return ParseResult;
            }
        }

        HttpWebCallFunction httpWebCallFunction = new HttpWebCallFunction();

        httpWebCallFunction.execute(PreviousListViewClickedItem, getpass);
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
                    JSONArray jsonArray;

                    try {
                        jsonArray = new JSONArray(FinalJSonObject);

                        JSONObject jsonObject;

                        for(int i=0; i<jsonArray.length(); i++)
                        {
                            jsonObject = jsonArray.getJSONObject(i);

                            // Storing Doorbell Name, Phone Number, Class into Variables.
                            USERHOLDER = jsonObject.getString("username").toString();
                            PASSWORDHOLDER = jsonObject.getString("password").toString();
                            EMAILHOLDER= jsonObject.getString("email").toString();
                            FHOLDER = jsonObject.getString("firstname").toString();
                            LHOLDER = jsonObject.getString("lastname").toString();

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

            USERNAME.setText(USERHOLDER);
            PASSWORD.setText(PASSWORDHOLDER);
            EMAIL.setText(EMAILHOLDER);
            FNAME.setText(FHOLDER);
            LNAME.setText(LHOLDER);


        }
    }
}
