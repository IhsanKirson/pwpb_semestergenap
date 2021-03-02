package com.pwpb.ulangansqlite;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class MainFragment extends Fragment implements
        View.OnClickListener,RecyclerviewAdapter.OnUserClickListener {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Context context;
    List<NoteModel> listNotes;
    CardView btnInput;
    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        listNotes=db.selectUserData();
        RecyclerviewAdapter adapter=new
                RecyclerviewAdapter(context,listNotes,this);
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
    public void onUserClick(NoteModel currentData, String action) {
        HashMap<String, String> passedInfo = new HashMap<String, String>();
        passedInfo.put("id", currentData.id);
        passedInfo.put("title", currentData.title);
        passedInfo.put("desc", currentData.desc);
        passedInfo.put("created_at", currentData.created_at);

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Option");

        // add a list
        String[] choices = {"Edit Data", "Delete Data"};
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0 :
                        Intent edit = new Intent(context, FormActivity.class);
                        edit.putExtra("passedInfo", passedInfo);
                        edit.putExtra("edit", true);
                        startActivity(edit);
                        break;
                    case 1:
                        DatabaseHelper db=new DatabaseHelper(context);
                        db.delete(currentData.getId());
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