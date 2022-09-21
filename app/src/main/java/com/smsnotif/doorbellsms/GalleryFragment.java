package com.smsnotif.doorbellsms;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.doorbellsms.R;

import java.util.HashMap;

public class GalleryFragment extends Fragment {

    EditText text1, text2, text3 , text4 ;
    String dbmod, dbname,phone,message;
    Button btnsubmit;
    Boolean CheckEditorText ;
    ProgressDialog progressDialog;
    String thisisfinalResult;
    HashMap<String,String> hashMap2 = new HashMap<>();
    HttpParse httpParse2 = new HttpParse();
    String HttpURLshow = "https://rjjmsmsdoorbell.tech/main/doorbellRegistration.php";

    public GalleryFragment(){
        super(R.layout.fragment_gallery);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text1 = view.findViewById(R.id.dbmodel);
        text2 = view.findViewById(R.id.dbname);
        text3 = view.findViewById(R.id.phonenumber);
        text4 = view.findViewById(R.id.notifmessage);
        btnsubmit = view.findViewById(R.id.buttonSubmit);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbmod = text1.getText().toString();
                dbname = text2.getText().toString();
                phone = text3.getText().toString();
                message = text4.getText().toString();
                boolean check = validateinfo(dbmod, dbname,phone, message);
                if (check) {
                    // If EditText is not empty and CheckEditText = True then this block will execute.
                    DbRegisterFunction(dbmod, dbname, "+63"+phone, message);

                } else {
                    // If EditText is empty then this block will execute .
                    Toast.makeText(getContext(), "ERROR: Check information.", Toast.LENGTH_LONG).show();
                }
            }

        });
    }
    public void DbRegisterFunction(final String db_name,final String db_model,final String db_phone, final String db_message){
        class DbRegisterFunctionClass extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(getContext(),"Adding Data",null,true,true);
            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                Toast.makeText(GalleryFragment.this.getActivity(),httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(String... params) {
                hashMap2.put("dbmodel",params[0]);
                hashMap2.put("dbname",params[1]);
                hashMap2.put("phonenumber",params[2]);
                hashMap2.put("notificationmessage",params[3]);
                thisisfinalResult = httpParse2.postRequest(hashMap2, HttpURLshow);
                return thisisfinalResult;
            }
        }
        DbRegisterFunctionClass dbRegisterFunctionClass = new DbRegisterFunctionClass();
        dbRegisterFunctionClass.execute(db_name,db_model,db_phone,db_message);
    }
    private Boolean validateinfo(String dbname,String dbmodel,String number,String message){
        if(dbmodel.length()==0){
            text2.requestFocus();
            text2.setError("FIELD CANNOT BE EMPTY");
            return false;
        }
        else if(dbname.length()==0){
            text1.requestFocus();
            text1.setError("FIELD CANNOT BE EMPTY");
            return false;
        }
        else if(number.length()==0){
            text3.requestFocus();
            text3.setError("FIELD CANNOT BE EMPTY");
            return false;
        }
        else if (!number.matches("^[0-9]{10}$")){
            text3.requestFocus();
            text3.setError("Please enter correct PH format eg: 912xxxxxx length must be 10");
            return false;
        }
        else if(message.length()==0){
            text4.requestFocus();
            text4.setError("Field cannot be empty");
            return false;
        }
        else if(message.length()<10){
            text4.requestFocus();
            text4.setError("Notification message is too short");
            return false;
        }
        else{
            return true;
        }

    }
}
