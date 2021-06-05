package com.example.timcoffee.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timcoffee.R;
import com.example.timcoffee.model.OrderDetailsItem;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    public interface DeleteItem {
        void onDeleteResult(OrderDetailsItem items);
    }

    public interface TotalPrice {
        void getTotalPrice(BigDecimal price);
    }

    private DeleteItem deleteItem;
    private TotalPrice totalPrice;

    private ArrayList<OrderDetailsItem> items = new ArrayList<>();
    private Context context;
    private Fragment fragment;

    public CartAdapter(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_cart_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        BigDecimal price = items.get(position).getPrice();

        holder.tvName.setText(items.get(position).getProductName());
        holder.tvPrice.setText("Rp." + price.toString());
        holder.tvMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    deleteItem = (DeleteItem) fragment;
                    deleteItem.onDeleteResult(items.get(position));
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<OrderDetailsItem> items) {
        this.items = items;
        calculateTotalPrice();
        notifyDataSetChanged();
    }

    private void calculateTotalPrice() {
        BigDecimal price = new BigDecimal(0);
        for(OrderDetailsItem item : items) {
            price = price.add(item.getPrice());
        }

        try {
            totalPrice = (TotalPrice) fragment;
            totalPrice.getTotalPrice(price);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvMin, tvPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvMin = itemView.findViewById(R.id.tv_min);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }
}
