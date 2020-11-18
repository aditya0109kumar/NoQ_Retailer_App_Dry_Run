package com.radiatelabs.noqdryrun;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;

class login extends AppCompatActivity {

    private static final String url = "jdbc:mysql://ec2-13-234-120-100.ap-south-1.compute.amazonaws.com:3306/myDB";
    private static final String user = "admin";
    private static final String pass = "Radiate@123";
    CheckBox rememberMe;
    EditText emailLogin, passwordLogin;
    Button loginButton;
    ProgressDialog progressDialog;
    String loginEmail, loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailLogin = (EditText) findViewById(R.id.loginEditTextEmail);
        passwordLogin = (EditText) findViewById(R.id.loginEditTextPassword);
        loginButton = (Button) findViewById(R.id.loginButton);


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, pass);
            if (conn == null) {
                Toast.makeText(login.this, "Connection went wrong", Toast.LENGTH_SHORT).show();
            } else {
                if (TextUtils.isEmpty(loginEmail) || TextUtils.isEmpty(loginPassword)) {
                    loginEmail = emailLogin.getText().toString();
                    loginPassword = passwordLogin.getText().toString();
                    // String query = "INSERT INTO `customers`(`name`, `phone`, `city`, `email`, 'password') VALUES ('"+nameQ+"','"+phoneQ+"','"+cityQ+"','"+emailQ+"','"+passQ+"')";
                    Statement stmt = conn.createStatement();
                    String query1 = MessageFormat.format("SELECT Store_ID, Store_Name, Store_City, Phone_No FROM New_Store_Table WHERE Email = '{0}' AND Password = '{1}'", loginEmail, loginPassword);
                    // stmt.executeUpdate(query);
                    ResultSet resultSet = stmt.executeQuery(query1);
                    if(resultSet!=null) {
                        while (resultSet.next()) {
                            int id = resultSet.getInt("id");
                            String customerName = resultSet.getString("Store_Name");
                            String customerCity = resultSet.getString("Store_City");
                            String customerPhone = resultSet.getString("Phone_No");
                        }
                        Toast.makeText(login.this, "Successful", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("email", loginEmail);
                        intent.putExtra("password", loginPassword);
                        startActivity(intent);
                    } else{
                        Toast.makeText(this, "Please check your credentials and your internet connection", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(login.this, "Fill all the details", Toast.LENGTH_SHORT).show();
                }
            }
            assert conn != null;
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Please check your credentials and your internet connection", Toast.LENGTH_SHORT).show();
        }
    }
}