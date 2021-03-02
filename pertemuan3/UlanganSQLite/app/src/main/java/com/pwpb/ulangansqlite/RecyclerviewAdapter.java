package com.pwpb.ulangansqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.UserViewHolder> {

    Context context;
    OnUserClickListener listener;
    List<NoteModel> ListNotes;

    public RecyclerviewAdapter(Context context, List<NoteModel>
            ListNotes, OnUserClickListener listener) {
        this.context=context;
        this.ListNotes=ListNotes;
        this.listener=listener;
    }

    public interface OnUserClickListener{
        void onUserClick(NoteModel currentData, String action);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.note_row_item,parent,false);
        UserViewHolder userViewHolder=new UserViewHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, final int position) {
        final NoteModel currentData=ListNotes.get(position);
        holder.txtTgl.setText(currentData.getCreated_at());
        holder.txtTitle.setText(currentData.getTitle());
        holder.txtDesc.setText(currentData.getDesc());
        holder.dataContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onUserClick(currentData,"dialog");
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListNotes.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView txtTgl, txtTitle, txtDesc;
        CardView dataContainer;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            dataContainer = itemView.findViewById(R.id.dataContainer);
            txtTgl = itemView.findViewById(R.id.txtTgl);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDesc = itemView.findViewById(R.id.txtDesc);
        }
    }

}
