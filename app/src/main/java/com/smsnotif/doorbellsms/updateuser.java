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

public class updateuser extends Fragment {

    String HttpURL = "https://rjjmsmsdoorbell.tech/main/updateuser.php";
    ProgressDialog progressDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    EditText userfnmae,userlname,useremail,userpass,usersname;
    Button UpdateStudent1;
    String IdHolder, fholdr,lholder,emailholder,passholder,usersholder;
    public updateuser(){super(R.layout.updateprofile);
    }
    @Override

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userpass = (EditText)view.findViewById(R.id.edittextpassword);
        usersname = (EditText)view.findViewById(R.id.edittextusername);
        useremail = (EditText)view.findViewById(R.id.edittextemail);
        userfnmae = (EditText)view.findViewById(R.id.edittextfname);
        userlname = (EditText)view.findViewById(R.id.edittextlname);
        UpdateStudent1 = (Button)view.findViewById(R.id.buttonUpdate1);
        // Receive Doorbell ID, Name , Phone Number , Class Send by previous ShowSingleRecordActivity.
        IdHolder = getActivity().getIntent().getStringExtra("user_id");
        usersholder = getActivity().getIntent().getStringExtra("username");
        passholder = getActivity().getIntent().getStringExtra("password");
        emailholder = getActivity().getIntent().getStringExtra("email");
        fholdr = getActivity().getIntent().getStringExtra("firstname");
        lholder = getActivity().getIntent().getStringExtra("lastname");
        // Setting Received Doorbell Name, Phone Number, Class into EditText.

        usersname.setText(usersholder);
        userpass.setText(passholder);
        useremail.setText(emailholder);
        userfnmae.setText(fholdr);
        userlname.setText(lholder);


        // Adding click listener to update button .
        UpdateStudent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Getting data from EditText after button click.
                GetDataFromEditText();
                // Sending Doorbell Name, Phone Number, Class to method to update on server.
                StudentRecordUpdate(IdHolder,usersholder,passholder,emailholder,fholdr,lholder);
            }
        });

    }
    // Method to get existing data from EditText.
    public void GetDataFromEditText(){
        usersholder = usersname.getText().toString();
        passholder = userpass.getText().toString();
        emailholder =useremail.getText().toString();
        fholdr = userfnmae.getText().toString();
        lholder = userlname.getText().toString();

    }

    // Method to Update Doorbell Record.
    public void StudentRecordUpdate(final String id, final String usrname,final String passwd,final String emails, final String fname, final String lname){

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

                hashMap.put("user_id",params[0]);
                hashMap.put("username",params[1]);
                hashMap.put("password",params[2]);
                hashMap.put("email",params[3]);
                hashMap.put("firstname",params[4]);
                hashMap.put("lastname",params[5]);
                finalResult = httpParse.postRequest(hashMap, HttpURL);
                return finalResult;
            }
        }

        StudentRecordUpdateClass studentRecordUpdateClass = new StudentRecordUpdateClass();
        studentRecordUpdateClass.execute(id,usrname,passwd,emails,fname,lname);
    }
}
