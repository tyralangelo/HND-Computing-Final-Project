package com.example.takeme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<Model> mList;
    Context context;

    public MyAdapter(Context context, ArrayList<Model> mList) {
        this.mList = mList;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item , parent , false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model model =mList.get(position);
        holder.name.setText(model.getName());
        holder.details.setText(model.getDetails());
        holder.km.setText(model.getKm());
        holder.date.setText(model.getDate());
        holder.total.setText(model.getTotal());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, details, km, date , total;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txvVName);
            details = itemView.findViewById(R.id.txvVDetails);
            km = itemView.findViewById(R.id.txvVKm);
            date = itemView.findViewById(R.id.txvVDate);
            total = itemView.findViewById(R.id.txvVTotal);
        }
    }
}
