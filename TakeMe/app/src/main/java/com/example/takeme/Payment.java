package com.example.takeme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class Payment extends AppCompatActivity implements View.OnClickListener {
    TextView txvDetails, txvTotal;
    EditText txtPName,txtKm, txtDate;
    Button btnAdd, btnPay;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference root = db.getReference().child("Payments");

    Calendar calendar=Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        txtPName = findViewById(R.id.txtPName);
        txvDetails = findViewById(R.id.txvDetails);
        txvTotal = findViewById(R.id.txvTotal);
        txtKm = findViewById(R.id.txtKm);
        txtDate = findViewById(R.id.txtDate);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnPay = findViewById(R.id.btnPay);
        btnPay.setOnClickListener(this);

        //getting service provider details
        String intentResult = getIntent().getStringExtra("details");
        txvDetails.setText(intentResult);

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                GetSelectedDate();
            }
        };

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Payment.this,listener,calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void GetSelectedDate()
    {
        String date = "dd/MM/yyyy";
        SimpleDateFormat format=new SimpleDateFormat(date, Locale.UK);
        txtDate.setText(format.format(calendar.getTime()));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                addPay();
                break;

            case R.id.btnPay:
                Pay();
                break;
          }
    }


    private void addPay(){
        String name = txtPName.getText().toString().trim();
        String Km = txtKm.getText().toString().trim();
        String date = txtDate.getText().toString().trim();

        if (name.isEmpty()) {
            txtPName.setError("Name is required!");
            txtPName.requestFocus();
            return;
        }

        if (Km.isEmpty()) {
            txtKm.setError("Km is required!");
            txtKm.requestFocus();
            return;
        }

        if (date.isEmpty()) {
            txtDate.setError("Date is required!");
            txtDate.requestFocus();
            return;
        }

        double km = 0;
        km = Integer.valueOf(txtKm.getText().toString());

        if (km <= 5)
        {
            km = 17.50;
        }
        else if (km > 5 && km <= 15){
            km = 22.75;
        }
        else if (km > 15 && km <= 25){
            km = 28.50;
        }
        else if (km > 25 && km <= 35){
            km = 34.15;
        }
        else if (km > 35 && km <= 45){
            km = 38.25;
        }
        else if (km > 45 && km <= 55){
            km = 43.75;
        }
        else if (km > 55 && km <= 65){
            km = 52.50;
        }
        else if (km > 65 && km <= 75){
            km = 65.00;
        }
        else if (km > 75 && km <= 85){
            km = 70.30;
        }
        else if (km > 85 && km <= 95){
            km = 92.25;
        }
        else{
            km = 115.00;
        }

        txvTotal.setText(String.valueOf(km));
        Toast.makeText(getApplicationContext(),"Total Added",Toast.LENGTH_LONG).show();
    }

    private void Pay() {
        String Total = txvTotal.getText().toString().trim();

        if (Total.isEmpty()){
            txvTotal.setError("!");
            return;
        } else {

            //Moving to next activity "Receipt"
            Intent intent = new Intent(Payment.this, Receipt.class);
            intent.putExtra("date", String.valueOf(txtDate.getText().toString()));
            intent.putExtra("details", String.valueOf(txvDetails.getText().toString()));
            intent.putExtra("total", String.valueOf(txvTotal.getText().toString()));
            startActivity(intent);

            //Saving data to firebase
            //Get all the values
            String name = txtPName.getText().toString();
            String details = txvDetails.getText().toString();
            String km = txtKm.getText().toString();
            String date = txtDate.getText().toString();
            String total = txvTotal.getText().toString();

            HashMap<String, String> paymentMap = new HashMap<>();

            paymentMap.put("name", name);
            paymentMap.put("details", details);
            paymentMap.put("km", km);
            paymentMap.put("date", date);
            paymentMap.put("total", total);

            root.push().setValue(paymentMap);

        }

    }

}