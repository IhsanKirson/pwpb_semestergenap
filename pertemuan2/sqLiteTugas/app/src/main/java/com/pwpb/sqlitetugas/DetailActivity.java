package com.pwpb.sqlitetugas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {
    TextView valNim, valName, valTgl, valGender, valAlamat;
    HashMap<String, String> passedInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setTitle("Detail Data");
        
        initView();
    }

    private void initView() {
        valNim = findViewById(R.id.valNim);
        valName = findViewById(R.id.valName);
        valTgl = findViewById(R.id.valTgl);
        valGender = findViewById(R.id.valGender);
        valAlamat = findViewById(R.id.valAlamat);

        Intent intent = getIntent();
        passedInfo = (HashMap<String, String>) intent.getSerializableExtra("passedInfo");

        valNim.setText(passedInfo.get("nim"));
        valName.setText(passedInfo.get("name"));
        valTgl.setText(passedInfo.get("tglLahir"));
        valGender.setText(passedInfo.get("gender"));
        valAlamat.setText(passedInfo.get("alamat"));
    }
}