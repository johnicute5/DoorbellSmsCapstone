package com.smsnotif.doorbellsms;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doorbellsms.R;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import java.util.HashMap;



public class MainActivity extends AppCompatActivity {

    Button signup, signin;
    EditText f_name, l_name, username, email, password;
    String fname, lname, user, pass, emailadd;
    String HttpURL = "https://rjjmsmsdoorbell.tech/main/UserRegistration.php";
    String finalResult;

    ProgressDialog progressDialog;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        f_name = findViewById(R.id.dbfname);
        l_name = findViewById(R.id.dblname);
        username = findViewById(R.id.dbuser);
        password = findViewById(R.id.dbpassword1);
        email = findViewById(R.id.phonenumber);
        signup = findViewById(R.id.btnsignup);
        signin = findViewById(R.id.btnsignin);


        signup.setOnClickListener(view -> {

                fname = f_name.getText().toString();
                lname = l_name.getText().toString();
                user = username.getText().toString();
                pass = password.getText().toString();
                emailadd = email.getText().toString();
                // Checking whether EditText is Empty or Not
                Boolean CheckEditText = uservalidateinfo(fname, lname, user, pass, emailadd);
                if (CheckEditText) {
                    // If EditText is not empty and CheckEditText = True then this block will execute.
                    UserRegisterFunction(fname, lname, user, pass, emailadd);
                    signUp(user,pass,emailadd);

                } else {
                    // If EditText is empty then this block will execute .
                    Toast.makeText(MainActivity.this, "Please double check info.", Toast.LENGTH_SHORT).show();
                }
            }
        );
        signin.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);

        });
    }

    private void signUp(String username, String password, String email) {
        progressDialog.show();
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.signUpInBackground(e -> {
            progressDialog.dismiss();
            if (e == null) {
                ParseUser.logOut();
                showAlert("Account Created Successfully!", "Please verify your email before Login",false);

            } else {
                ParseUser.logOut();
                showAlert("Error Account Creation failed", "Account could not be created" + " :" + e.getMessage(),true);
            }
        });
    }



    private Boolean uservalidateinfo(String fname,String lastname,String user_name,String pass_word,String emails) {
        if (fname.length() == 0) {
            f_name.requestFocus();
            f_name.setError("FIELD CANNOT BE EMPTY");
            return false;
        }
        else if (fname.matches("[0-9]+")) {
            f_name.requestFocus();
            f_name.setError("PLEASE ENTER ALPHABET CHARACTER");
            return false;
        }
        else if (lastname.matches("[0-9]+")) {
            l_name.requestFocus();
            l_name.setError("PLEASE ENTER ALPHABET CHARACTER");
            return false;
        }else if (lastname.length() == 0) {
            l_name.requestFocus();
            l_name.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (user_name.length() == 0) {
            username.requestFocus();
            username.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (user_name.length() < 5) {
            username.requestFocus();
            username.setError("Username must atleast 5 character");
            return false;
        } else if (pass_word.length() == 0) {
            password.requestFocus();
            password.setError("Field cannot be empty");
            return false;
        } else if (pass_word.length() < 5) {
            password.requestFocus();
            password.setError("password must atleast 5 character");
            return false;
        } else if (emails.length() ==0) {
            email.requestFocus();
            email.setError("Field Cannot be empty");
        return false;
        }
        else if (!emails.contains("@")){
            email.requestFocus();
            email.setError("please enter valid email address");
            return false;
        }
        else {
            return true;
        }


    }
    public void UserRegisterFunction(final String first_name, final String last_name, final String user_name, final String user_password, final String user_email) {
        class UserRegisterFunctionClass extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(MainActivity.this, "Loading Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("firstname", params[0]);
                hashMap.put("lastname", params[1]);
                hashMap.put("username", params[2]);
                hashMap.put("password", params[3]);
                hashMap.put("email", params[4]);
                finalResult = httpParse.postRequest(hashMap, HttpURL);
                return finalResult;
            }
        }
        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();
        userRegisterFunctionClass.execute(first_name, last_name, user_name, user_password, user_email);
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
                            Intent intent = new Intent(MainActivity.this,Login.class);
                            startActivity(intent);
                        }
                    }
                }).show();
    }

}
