package com.pwpb.ulangansqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class FormActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtTitle, txtDesc;
    EditText edtTitle, edtDesc;
    Button btnSubmit;
    Boolean editStatus;
    HashMap<String, String> passedInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Input Data");

        initView();
    }

    private void initView() {
        txtTitle = findViewById(R.id.ctxtTitle);
        txtDesc = findViewById(R.id.ctxtDesc);
        edtTitle = findViewById(R.id.edtTitle);
        edtDesc = findViewById(R.id.edtDesc);
        btnSubmit = findViewById(R.id.btnSubmit);

        Intent intent = getIntent();
        editStatus = intent.getBooleanExtra("edit", false);
        if (editStatus) {
            getSupportActionBar().setTitle("Update Data");
            passedInfo = (HashMap<String, String>) intent.getSerializableExtra("passedInfo");

            edtTitle.setText(passedInfo.get("title"));
            edtDesc.setText(passedInfo.get("desc"));
            btnSubmit.setText("Update");
        }

        edtTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    txtTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    txtTitle.setTextColor(Color.parseColor("#808080"));
                }
            }
        });

        edtDesc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    txtDesc.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    txtDesc.setTextColor(Color.parseColor("#808080"));
                }
            }
        });

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmit) {
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            NoteModel currentData = new NoteModel();
            String currentDate = new SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(new Date());
            String currentDateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(new Date());

            if (editStatus) {
                currentData.setId(passedInfo.get("id"));
                currentData.setTitle(edtTitle.getText().toString());
                currentData.setDesc(edtDesc.getText().toString());
                currentData.setCreated_at(currentDateTime);
                db.update(currentData);
            } else {
                String id = String.valueOf(Math.round(Math.random()*1000));

                currentData.setId(currentDate + "_" + id);
                currentData.setTitle(edtTitle.getText().toString());
                currentData.setDesc(edtDesc.getText().toString());
                currentData.setCreated_at(currentDateTime);
                db.insert(currentData);
            }

            edtTitle.setText("");
            edtDesc.setText("");
            edtTitle.setFocusable(true);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}