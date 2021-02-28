package com.pwpb.sqlitetugas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnLihat, btnInput, btnInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Dashboard");

        btnLihat = (Button) findViewById(R.id.btnLihat);
        btnLihat.setOnClickListener(this);
        btnInput = (Button) findViewById(R.id.btnInput);
        btnInput.setOnClickListener(this);
        btnInfo = (Button) findViewById(R.id.btnInfo);
        btnInfo.setOnClickListener(this);
    }
//Toast.makeText(context, currentPerson.name + " 2", Toast.LENGTH_SHORT).show();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLihat:
                Intent lihat = new Intent(this, ContainerActivity.class);
                startActivity(lihat);
                break;
            case R.id.btnInput:
                Intent input = new Intent(this, FormActivity.class);
                startActivity(input);
                break;
            case R.id.btnInfo:
                String info = "Aplikasi ini dibuat oleh Muhamad Nur Ihsan Mutaqin dan Muhamad Fadil";
                Toast.makeText(this, info, Toast.LENGTH_LONG).show();
                break;
        }
    }


}