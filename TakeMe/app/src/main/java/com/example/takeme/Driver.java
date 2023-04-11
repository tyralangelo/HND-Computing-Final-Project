package com.example.takeme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Driver extends AppCompatActivity implements View.OnClickListener {

    EditText txtDName, txtDAddress, txtDEmail, txtDLicence, txtDPhone,txtDVehicle,txtDRNumber,txtDPlate,txtDRoute;
    Button btnConfirm;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference root = db.getReference().child("Drivers");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        txtDName = findViewById(R.id.txtDName);
        txtDAddress = findViewById(R.id.txtDAddress);
        txtDEmail = findViewById(R.id.txtDEmail);
        txtDLicence = findViewById(R.id.txtDLicence);
        txtDPhone = findViewById(R.id.txtDPhone);
        txtDVehicle = findViewById(R.id.txtDVehicle);
        txtDRNumber = findViewById(R.id.txtDRNumber);
        txtDPlate = findViewById(R.id.txtDPlate);
        txtDRoute = findViewById(R.id.txtDRoute);

        btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConfirm:
                Confirm();
                break;

        }
    }

    private void Confirm() {
        String name = txtDName.getText().toString().trim();
        String address = txtDAddress.getText().toString().trim();
        String email = txtDEmail.getText().toString().trim();
        String licence = txtDLicence.getText().toString().trim();
        String phone = txtDPhone.getText().toString().trim();
        String vehicle = txtDVehicle.getText().toString().trim();
        String register = txtDRNumber.getText().toString().trim();
        String plate = txtDPlate.getText().toString().trim();
        String route = txtDRoute.getText().toString().trim();

        if (name.isEmpty()){
            txtDName.setError("Driver name is required!");
            txtDName.requestFocus();
            return;
        }

        if (address.isEmpty()){
            txtDAddress.setError("Address is required!");
            txtDAddress.requestFocus();
            return;
        }

        if (email.isEmpty()){
            txtDEmail.setError("Email is required!");
            txtDEmail.requestFocus();
            return;
        }
        if (licence.isEmpty()){
            txtDLicence.setError("Driver licence is required!");
            txtDLicence.requestFocus();
            return;
        }

        if (phone.isEmpty()){
            txtDPhone.setError("Phone number is required!");
            txtDPhone.requestFocus();
            return;
        }

        if (vehicle.isEmpty()){
            txtDVehicle.setError("Vehicle model is required!");
            txtDVehicle.requestFocus();
            return;
        }
        if (register.isEmpty()){
            txtDRNumber.setError("Vehicle register number is required!");
            txtDRNumber.requestFocus();
            return;
        }

        if (plate.isEmpty()){
            txtDPlate.setError("Plate number is required!");
            txtDPlate.requestFocus();
            return;
        }
        if (route.isEmpty()){
            txtDRoute.setError("Route is required!");
            txtDRoute.requestFocus();
            return;

        }else {
            HashMap<String, String> driverMap = new HashMap<>();

            driverMap.put("name", name);
            driverMap.put("address", address);
            driverMap.put("email", email);
            driverMap.put("licence", licence);
            driverMap.put("phone", phone);
            driverMap.put("vehicle", vehicle);
            driverMap.put("register no", register);
            driverMap.put("plate", plate);
            driverMap.put("route", route);

            root.push().setValue(driverMap);
            Toast.makeText(getApplicationContext(),"QR code will be provided to you within 2 days! ",Toast.LENGTH_LONG).show();
        }

    }
}