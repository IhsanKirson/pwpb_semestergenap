package com.pwpb.sqlitetugas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

public class FormActivity extends AppCompatActivity implements View.OnClickListener {
    EditText inputNim, inputName, inputTgl, inputGender, inputAlamat;
    Button btnSubmit;
    Boolean edit;
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
        inputNim = findViewById(R.id.edtNim);
        inputName = findViewById(R.id.edtName);
        inputTgl = findViewById(R.id.edtTgl);
        inputGender = findViewById(R.id.edtGender);
        inputAlamat = findViewById(R.id.edtAlamat);
        btnSubmit = findViewById(R.id.btnSubmit);

        Intent intent = getIntent();

        edit = intent.getBooleanExtra("edit", false);
        if (edit) {
            getSupportActionBar().setTitle("Update Data");
            passedInfo = (HashMap<String, String>) intent.getSerializableExtra("passedInfo");

            inputNim.setText(passedInfo.get("nim"));
            inputNim.setEnabled(false);
            inputName.setText(passedInfo.get("name"));
            inputTgl.setText(passedInfo.get("tglLahir"));
            inputGender.setText(passedInfo.get("gender"));
            inputAlamat.setText(passedInfo.get("alamat"));
        }

        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmit) {
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            Mahasiswa currentPerson = new Mahasiswa();

            if (edit) {
                currentPerson.setNim(inputNim.getText().toString());
                currentPerson.setName(inputName.getText().toString());
                currentPerson.setTglLahir(inputTgl.getText().toString());
                currentPerson.setGender(inputGender.getText().toString());
                currentPerson.setAlamat(inputAlamat.getText().toString());
                db.update(currentPerson);

                Intent intent = new Intent(this, ContainerActivity.class);
                startActivity(intent);
            } else {
                currentPerson.setNim(inputNim.getText().toString());
                currentPerson.setName(inputName.getText().toString());
                currentPerson.setTglLahir(inputTgl.getText().toString());
                currentPerson.setGender(inputGender.getText().toString());
                currentPerson.setAlamat(inputAlamat.getText().toString());
                db.insert(currentPerson);

                inputNim.setText("");
                inputName.setText("");
                inputTgl.setText("");
                inputGender.setText("");
                inputAlamat.setText("");
                inputNim.setFocusable(true);
            }
        }
    }
}