package com.example.takeme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Receipt extends AppCompatActivity {
    TextView txvRDate,txvRDetails, txvRTotal ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        txvRDate =findViewById(R.id.txvRDate);
        txvRDetails = findViewById(R.id.txvRDetails);
        txvRTotal = findViewById(R.id.txvRTotal);

        String date = getIntent().getStringExtra("date");
        String details = getIntent().getStringExtra("details");
        String total = getIntent().getStringExtra("total");
        txvRDate.setText(date);
        txvRDetails.setText(details);
        txvRTotal.setText(total);
    }
}