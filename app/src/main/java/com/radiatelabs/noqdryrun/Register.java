package com.radiatelabs.noqdryrun;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Objects;

public class Register extends AppCompatActivity {


    EditText email, password, cPassword;
    String emailQ, passQ, cPassQ;
    TextView alreadyRegistered;
//    ProgressDialog progressDialog;
    private static final String url = "jdbc:mysql://ec2-13-234-120-100.ap-south-1.compute.amazonaws.com:3306/myDB";
    private static final String user = "dev";
    private static final String pass = "Radiate@123";
    public String error;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText) findViewById(R.id.editTextEmailRegister);
        password = (EditText) findViewById(R.id.editTextPassword);
        cPassword = (EditText) findViewById(R.id.editTextConfPassword);
        register = (Button) findViewById(R.id.registerButton);
        alreadyRegistered = (TextView) findViewById(R.id.already_registered_link);
        
    }

    public void btnConn(View view) {
//        progressDialog = new ProgressDialog(Register.this);
//        progressDialog.show();
//        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Send objSend = new Send();
        objSend.execute("");
    }

    public void sendToLoginPage(View view) {
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }


    private class Send extends AsyncTask<String, String, String> {
        String msg = "";

        @Override
        protected void onPreExecute() {
            Toast.makeText(Register.this, "Please wait", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(url, user, pass);
                if (conn == null) {
                    Toast.makeText(Register.this, "Connection went wrong", Toast.LENGTH_SHORT).show();
                } else {
                    emailQ = email.getText().toString();
                    passQ = password.getText().toString();
                    cPassQ = cPassword.getText().toString();

                    if (TextUtils.isEmpty(emailQ) || TextUtils.isEmpty(passQ) || TextUtils.isEmpty(cPassQ)) {
                        if (passQ == cPassQ) {
                            String query = "INSERT INTO `New_Store_Table`(`email`, 'password') VALUES ('" + emailQ + "','" + passQ + "')";
                            Statement stmt = conn.createStatement();
                            stmt.executeUpdate(query);
                            Toast.makeText(Register.this, "Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Register.this, "Password and Confirm Password should match", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Register.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                    }
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
                error = e.getMessage().toString();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
            password.setText("");
            email.setText("");
//            progressDialog.dismiss();

        }

    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}