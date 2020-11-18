package com.radiatelabs.noqdryrun;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {


    Intent intent = getIntent();
    String emailHomePage = intent.getStringExtra("email");
    String passwordHomePage = intent.getStringExtra("password");
    TextView Store_Name, Store_Location, Store_Address, Store_City, PincodeText, Store_State, Store_Country, Phone_No, in_store, takeaway, home_delivery, Delivery_Charge, Min_Charge, Max_Charge;
    ImageView editDetails;
    ProgressDialog progressDialog;
    private static final String url = "jdbc:mysql://ec2-13-234-120-100.ap-south-1.compute.amazonaws.com:3306/myDB";
    private static final String user = "admin";
    private static final String pass = "Radiate@123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Store_Name = (TextView) findViewById(R.id.Store_Name);
        Store_Location = (TextView) findViewById(R.id.Store_Location);
        Store_Address = (TextView) findViewById(R.id.Store_Address);
        Store_City = (TextView) findViewById(R.id.Store_City);
        PincodeText = (TextView) findViewById(R.id.Pincode);
        Store_State = (TextView) findViewById(R.id.Store_State);
        Store_Country = (TextView) findViewById(R.id.Store_Country);
        Phone_No = (TextView) findViewById(R.id.Phone_No);
        in_store = (TextView) findViewById(R.id.in_store);
        takeaway = (TextView) findViewById(R.id.takeaway);
        home_delivery = (TextView) findViewById(R.id.home_delivery);
        Delivery_Charge = (TextView) findViewById(R.id.Delivery_Charge);
        Min_Charge = (TextView) findViewById(R.id.Min_Charge);
        Max_Charge = (TextView) findViewById(R.id.Max_Charge);
        editDetails = (ImageView) findViewById(R.id.imageEditDetails);


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, pass);
            if (connection == null) {
                Toast.makeText(HomeActivity.this, "Connection went wrong", Toast.LENGTH_SHORT).show();
            } else {

                Statement stmt = connection.createStatement();
                String sql = MessageFormat.format("SELECT Store_Name, Store_Location, Store_Address, Store_City, Pincode, Store_State, Store_Country, Phone_No, in_store, takeaway, home_delivery, Delivery_Charge, Min_Charge, Max_Charge FROM New_Store_Table WHERE email = '{0}' AND password = '{1}'", emailHomePage, passwordHomePage);
                ResultSet resultSet = stmt.executeQuery(sql);

                String StoreName = resultSet.getString("Store_Name");
                Store_Name.setText(StoreName);

                String StoreLocation = resultSet.getString("Store_Location");
                Store_Location.setText(StoreLocation);

                String StoreAddress = resultSet.getString("Store_Address");
                Store_Address.setText(StoreAddress);

                String StoreCity = resultSet.getString("Store_City");
                Store_City.setText(StoreCity);

                String Pincode = resultSet.getString("Pincode");
                PincodeText.setText(Pincode);

                String StoreState = resultSet.getString("Store_State");
                Store_State.setText(StoreState);

                String StoreCountry = resultSet.getString("Store_Country");
                Store_Country.setText(StoreCountry);

                String PhoneNo = resultSet.getString("Phone_No");
                Phone_No.setText(PhoneNo);

                String instore = resultSet.getString("in_store");
                in_store.setText(instore);

                String takeAway = resultSet.getString("takeaway");
                takeaway.setText(takeAway);

                String homeD = resultSet.getString("home_delivery");
                home_delivery.setText(homeD);

                String DeliveryC = resultSet.getString("Delivery_Charge");
                Delivery_Charge.setText(DeliveryC);

                String minCharge = resultSet.getString("Min_Charge");
                Min_Charge.setText(minCharge);

                String maxCharge = resultSet.getString("Max_Charge");
                Max_Charge.setText(maxCharge);


                connection.close();
            }
            assert connection != null;
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

//    public void btnConn(View view) {
//        progressDialog = new ProgressDialog(HomeActivity.this);
//        progressDialog.show();
//        progressDialog.setContentView(R.layout.progress_dialog);
//        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//        MainActivity2.Send objSend = new MainActivity2.Send();
//        objSend.execute("");
//    }


    public void edtdtls(View view) {
        Intent intent = new Intent(getApplicationContext(), DataEditForm.class);
        intent.putExtra("email", emailHomePage);
        intent.putExtra("password", passwordHomePage);
        startActivity(intent);
    }
}