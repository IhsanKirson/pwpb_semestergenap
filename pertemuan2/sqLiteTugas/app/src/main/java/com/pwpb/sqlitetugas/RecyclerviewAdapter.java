package com.pwpb.sqlitetugas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.UserViewHolder> {

    Context context;
    OnUserClickListener listener;
    List<Mahasiswa> listPersonInfo;

    public RecyclerviewAdapter(Context context, List<Mahasiswa>
            listPersonInfo, OnUserClickListener listener) {
        this.context=context;
        this.listPersonInfo=listPersonInfo;
        this.listener=listener;
    }

    public interface OnUserClickListener{
        void onUserClick(Mahasiswa currentPerson, String action);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view=
                LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row_item,parent,false);
        UserViewHolder userViewHolder=new UserViewHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewAdapter.UserViewHolder holder, int position) {
        final Mahasiswa currentPerson=listPersonInfo.get(position);
        holder.ctxtName.setText(currentPerson.getName());
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        holder.ctxtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onUserClick(currentPerson,"dialog");
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPersonInfo.size();
    }
    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView ctxtName;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            ctxtName=itemView.findViewById(R.id.ctxtName);
        }
    }
}
