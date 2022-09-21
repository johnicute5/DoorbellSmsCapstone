package com.smsnotif.doorbellsms;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doorbellsms.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    EditText username, password;
    Button btnlogin, btnregister;
    String users, pass;
    String finalResult ;
    String HttpURL = "https://rjjmsmsdoorbell.tech/main/UserLogin.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;

    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    public static final String Username = "";
    public static final String Password = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username11);
        password = findViewById(R.id.password11);
        btnlogin = findViewById(R.id.btnsignin1);
        btnregister = findViewById(R.id.btnsignup1);
        users = username.getText().toString();
        pass = password.getText().toString();



        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                users = username.getText().toString();
                pass = password.getText().toString();
                // Checking whether EditText is Empty or Not
                CheckEditTextIsEmptyOrNot();
                if (CheckEditText) {
                    ParseUser.logInInBackground(users, pass, new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (e==null){
                                UserLoginFunction(users, pass);
                                showAlert("Login Successful","Your Email is Verified",false);

                            }
                            else {
                                // If EditText is empty then this block will execute .
                                showAlert("Sorry Your email not verified",e.getMessage(),true);

                            }

                        }
                    });
                    // If EditText is not empty and CheckEditText = True then this block will execute
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please fill all fields.", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void CheckEditTextIsEmptyOrNot(){

        users = username.getText().toString();
        pass = password.getText().toString();
        if(TextUtils.isEmpty(users) || TextUtils.isEmpty(pass))
        {
            CheckEditText = false;
        }
        else {

            CheckEditText = true ;
        }
    }
    public void UserLoginFunction(final String user_name, final String user_password){
        class UserLoginClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Login.this, "Loading Data", null, true, true);
            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                if(httpResponseMsg.equalsIgnoreCase("Data Matched")){

                }
                else{

                    Toast.makeText(Login.this,httpResponseMsg,Toast.LENGTH_LONG).show();
                }

            }
            @Override
            protected String doInBackground(String... params) {
                hashMap.put("username",params[0]);
                hashMap.put("password",params[1]);
                finalResult = httpParse.postRequest(hashMap, HttpURL);
                return finalResult;
            }
        }
        UserLoginClass userLoginClass = new UserLoginClass();
        userLoginClass.execute(user_name,user_password);
    }
    private void showAlert(String title, String Message,Boolean eRror){
        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(Message)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (!eRror){
                            Intent intent = new Intent(Login.this,Navigationhome.class);
                            startActivity(intent);
                        }
                    }
                }).show();
    }

}