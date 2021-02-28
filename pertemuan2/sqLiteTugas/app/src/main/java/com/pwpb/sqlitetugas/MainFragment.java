package com.pwpb.sqlitetugas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainFragment extends Fragment implements
        View.OnClickListener,RecyclerviewAdapter.OnUserClickListener {
    RecyclerView recyclerView;
    Context context;
    List<Mahasiswa> listPersonInfo;
    RecyclerView.LayoutManager layoutManager;
    Button btnInput;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        recyclerView = view.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        setupRecyclerView();
        btnInput = view.findViewById(R.id.btnInput);
        btnInput.setOnClickListener(this);
    }

    private void setupRecyclerView() {
        DatabaseHelper db=new DatabaseHelper(context);
        listPersonInfo=db.selectUserData();
        RecyclerviewAdapter adapter=new
                RecyclerviewAdapter(context,listPersonInfo,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnInput) {
            Intent intent = new Intent(context, FormActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onUserClick(Mahasiswa currentPerson, String action) {
        HashMap<String, String> passedInfo = new HashMap<String, String>();
        passedInfo.put("nim", currentPerson.nim);
        passedInfo.put("name", currentPerson.name);
        passedInfo.put("tglLahir", currentPerson.tglLahir);
        passedInfo.put("gender", currentPerson.gender);
        passedInfo.put("alamat", currentPerson.alamat);

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Pilihan");

        // add a list
        String[] choices = {"Lihat Data", "Update Data", "Hapus Data"};
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0 :
                        Intent detail = new Intent(context, DetailActivity.class);
                        detail.putExtra("passedInfo", passedInfo);
                        startActivity(detail);
                        break;
                    case 1:
                        Intent update = new Intent(context, FormActivity.class);
                        update.putExtra("passedInfo", passedInfo);
                        update.putExtra("edit", true);
                        startActivity(update);
                        break;
                    case 2:
                        DatabaseHelper db=new DatabaseHelper(context);
                        db.delete(currentPerson.getNim());
                        setupRecyclerView();
                        break;
                }
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}