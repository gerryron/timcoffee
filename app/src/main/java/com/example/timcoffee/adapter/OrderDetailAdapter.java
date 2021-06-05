package com.example.timcoffee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timcoffee.R;
import com.example.timcoffee.model.OrderDetailsItem;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.MyViewHolder> {

    private Context context;
    private List<OrderDetailsItem> orderDetailsItems = new ArrayList<>();

    public OrderDetailAdapter(Context context, List<OrderDetailsItem> list) {
        this.context = context;
        this.orderDetailsItems = list;
    }

    @NonNull
    @Override
    public OrderDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_order_detail_items, parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.MyViewHolder holder, int position) {
        holder.tvNama.setText(orderDetailsItems.get(position).getProductName());
        holder.tvPrice.setText(orderDetailsItems.get(position).getPrice().toString());
    }

    @Override
    public int getItemCount() {
        return orderDetailsItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }
}
