package com.medicalcenter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<DataClass> dataList;

    public MyAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.dateCard.setText(dataList.get(position).getDataTimedate());
        holder.epfCard.setText(dataList.get(position).getDataEpf());
        holder.nameCard.setText(dataList.get(position).getDataName());
        holder.deptCard.setText(dataList.get(position).getDataDepartment());

        holder.Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Date", dataList.get(holder.getAdapterPosition()).getDataTimedate());
                intent.putExtra("Time", dataList.get(holder.getAdapterPosition()).getDataTime());
                intent.putExtra("Epf", dataList.get(holder.getAdapterPosition()).getDataEpf());
                intent.putExtra("Name", dataList.get(holder.getAdapterPosition()).getDataName());
                intent.putExtra("Department", dataList.get(holder.getAdapterPosition()).getDataDepartment());
                intent.putExtra("Diagnose", dataList.get(holder.getAdapterPosition()).getDataDiagnose());
                intent.putExtra("Medicine", dataList.get(holder.getAdapterPosition()).getDataMedicine());
                intent.putExtra("Note", dataList.get(holder.getAdapterPosition()).getDataNote());
                intent.putExtra("Reported", dataList.get(holder.getAdapterPosition()).getDataReported());

                intent.putExtra("Key",  dataList.get(holder.getAdapterPosition()).getKey());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchDataList(ArrayList<DataClass> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

    TextView dateCard, epfCard, nameCard, deptCard;
    CardView Card;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        dateCard = itemView.findViewById(R.id.dateCard);
        epfCard = itemView.findViewById(R.id.epfCard);
        nameCard = itemView.findViewById(R.id.nameCard);
        deptCard = itemView.findViewById(R.id.deptCard);
        Card = itemView.findViewById(R.id.Card);
    }
}
