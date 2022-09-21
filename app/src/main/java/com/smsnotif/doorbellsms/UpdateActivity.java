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

public class UpdateActivity extends Fragment {

    String HttpURL = "https://rjjmsmsdoorbell.tech/main/Updatedoorbell.php";
    ProgressDialog progressDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    EditText dbmodel,dbname,dbPhoneNumber,db_message;
    Button UpdateStudent;
    String IdHolder, model_holder,dbNameHolder, dbPhoneHolder, message_holder;
    public UpdateActivity(){super(R.layout.fragment_updaterecords);
    }
    @Override

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbmodel = (EditText)view.findViewById(R.id.editdbmodel);
        dbname = (EditText)view.findViewById(R.id.editdbname);
        dbPhoneNumber = (EditText)view.findViewById(R.id.editphone);
        db_message = (EditText)view.findViewById(R.id.edit_textmessage);
        UpdateStudent = (Button)view.findViewById(R.id.UpdateButton);
        // Receive Doorbell ID, Name , Phone Number , Class Send by previous ShowSingleRecordActivity.
        IdHolder = getActivity().getIntent().getStringExtra("id");
        model_holder = getActivity().getIntent().getStringExtra("dbmodel");
        dbNameHolder = getActivity().getIntent().getStringExtra("dbname");
        dbPhoneHolder = getActivity().getIntent().getStringExtra("phonenumber");
        message_holder = getActivity().getIntent().getStringExtra("notificationmessage");
        // Setting Received Doorbell Name, Phone Number, Class into EditText.
        dbmodel.setText(model_holder);
        dbname.setText(dbNameHolder);
        dbPhoneNumber.setText(dbPhoneHolder);
        db_message.setText(message_holder);


        // Adding click listener to update button .
        UpdateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Getting data from EditText after button click.
                GetDataFromEditText();

                // Sending Doorbell Name, Phone Number, Class to method to update on server.
                StudentRecordUpdate(IdHolder, model_holder, dbNameHolder, dbPhoneHolder, message_holder);
            }
        });

    }
    // Method to get existing data from EditText.
    public void GetDataFromEditText(){
        model_holder = dbmodel.getText().toString();
        dbNameHolder = dbname.getText().toString();
        dbPhoneHolder = dbPhoneNumber.getText().toString();
        message_holder = db_message.getText().toString();

    }

    // Method to Update Doorbell Record.
    public void StudentRecordUpdate(final String id, final String dbmodel, final String dbname, final String dbphone, final String db_message){

        class StudentRecordUpdateClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(getActivity(),"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                Toast.makeText(getActivity(),httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("id",params[0]);
                hashMap.put("dbmodel",params[1]);
                hashMap.put("dbname",params[2]);
                hashMap.put("phonenumber",params[3]);
                hashMap.put("notificationmessage",params[4]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        StudentRecordUpdateClass studentRecordUpdateClass = new StudentRecordUpdateClass();
        studentRecordUpdateClass.execute(id,dbmodel,dbname,dbphone,db_message);
    }
}
