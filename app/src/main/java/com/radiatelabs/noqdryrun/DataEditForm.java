package com.radiatelabs.noqdryrun;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;

public class DataEditForm extends AppCompatActivity {

    Intent intent = getIntent();
    String emailEditPage = intent.getStringExtra("email");
    String passwordEditPage = intent.getStringExtra("password");

    private static final String url = "jdbc:mysql://ec2-13-234-120-100.ap-south-1.compute.amazonaws.com:3306/myDB";
    private static final String user = "admin";
    private static final String pass = "Radiate@123";

    ImageView backArrow;
    Button save;
    EditText Store_Name, Store_Location, Store_Address, Store_City, PincodeText, Store_State, Store_Country, Phone_No, in_store, takeaway, home_delivery, Delivery_Charge, Min_Charge, Max_Charge;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_edit_form);

        backArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                // Code here executes on main thread after user presses button
            }
        });

        Store_Name = (EditText) findViewById(R.id.Store_Name);
        Store_Location = (EditText) findViewById(R.id.Store_Location);
        Store_Address = (EditText) findViewById(R.id.Store_Address);
        Store_City = (EditText) findViewById(R.id.Store_City);
        PincodeText = (EditText) findViewById(R.id.Pincode);
        Store_State = (EditText) findViewById(R.id.Store_State);
        Store_Country = (EditText) findViewById(R.id.Store_Country);
        Phone_No = (EditText) findViewById(R.id.Phone_No);
        in_store = (EditText) findViewById(R.id.in_store);
        takeaway = (EditText) findViewById(R.id.takeaway);
        home_delivery = (EditText) findViewById(R.id.home_delivery);
        Delivery_Charge = (EditText) findViewById(R.id.Delivery_Charge);
        Min_Charge = (EditText) findViewById(R.id.Min_Charge);
        Max_Charge = (EditText) findViewById(R.id.Max_Charge);
        save = (Button) findViewById(R.id.editDetailsSaveBtn);


        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, pass);
            if (connection == null) {
                Toast.makeText(DataEditForm.this, "Connection went wrong", Toast.LENGTH_SHORT).show();
            } else {
                Statement stmt = connection.createStatement();
                String sql = MessageFormat.format("SELECT Store_Name, Store_Location, Store_Address, Store_City, Pincode, Store_State, Store_Country, Phone_No, in_store, takeaway, home_delivery, Delivery_Charge, Min_Charge, Max_Charge FROM New_Store_Table WHERE email = '{0}' AND password = '{1}'", emailEditPage, passwordEditPage);
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


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void update(View view) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, pass);
            if (connection == null) {
                Toast.makeText(DataEditForm.this, "Connection went wrong", Toast.LENGTH_SHORT).show();
            } else {
                String storeName = Store_Name.getText().toString();
                String storeLocation = Store_Location.getText().toString();
                String storeAddress = Store_Address.getText().toString();
                String storeCity = Store_City.getText().toString();
                String storePincode = PincodeText.getText().toString();
                String storeState = Store_State.getText().toString();
                String storeCountry = Store_Country.getText().toString();
                String storePhoneNo = Phone_No.getText().toString();
                String storeInStore = in_store.getText().toString();
                String storeTakeaway = takeaway.getText().toString();
                String storeHomeD = home_delivery.getText().toString();
                String storeDeliveryC = Delivery_Charge.getText().toString();
                String storeMinC = Min_Charge.getText().toString();
                String storeMaxC = Max_Charge.getText().toString();
                String updateQuery = MessageFormat.format("UPDATE `New_Store_Table` SET `Store_Name`='{0}',`Store_Location`='{1}',`Store_Address`='{2}',`Store_City`='{3}',`Pincode`='{4}',`Store_State`='{5}',`Store_Country`='{6}',`Phone_No`='{7}',`in_store`='{8}',`takeaway`='{9}',`home_delivery`='{10}',`Delivery_Charge`='{11}',`Min_Charge`='{12}',`Max_Charge`='{13}' WHERE Email = '{14}' AND Password = '{15}'", storeName, storeLocation, storeAddress, storeCity, storePincode, storeState, storeCountry, storePhoneNo, storeInStore, storeTakeaway, storeHomeD, storeDeliveryC, storeMinC, storeMaxC, emailEditPage, passwordEditPage);


                Statement stmt = connection.createStatement();
                stmt.executeUpdate(updateQuery);
                Toast.makeText(this, "Details Updated", Toast.LENGTH_SHORT).show();
            }
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}