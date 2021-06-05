package com.example.timcoffee.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.timcoffee.R;
import com.example.timcoffee.model.OrderDetailsItem;
import com.example.timcoffee.model.Product;
import com.example.timcoffee.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private Context context;
    private List<Product> listProduct = new ArrayList<>();
    private static final String IMAGE_URL_BASE = "https://i.ibb.co";

    public ProductAdapter(Context context, List<Product> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
    }

    //menyambungkan layout item
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_product, parent,false);
        return new MyViewHolder(itemView);
    }

    //set item
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvNamaMenu.setText(listProduct.get(position).getName());
        holder.tvHarga.setText(listProduct.get(position).getPrice().toString());
        Glide.with(context).load(IMAGE_URL_BASE + listProduct.get(position).getImage()).into(holder.ivGambar);
        holder.btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.addItemToCart(context, new OrderDetailsItem(listProduct.get(position)));
                Toast.makeText(context, "Berhasil menambahkan " + listProduct.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //menhitung jumlah data
    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    //mengenalkan komponen ke item
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGambar;
        TextView tvNamaMenu;
        TextView tvHarga;
        Button btnAddtoCart;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivGambar = itemView.findViewById(R.id.iv_menu);
            tvNamaMenu = itemView.findViewById(R.id.tv_menu);
            tvHarga = itemView.findViewById(R.id.tv_price);
            btnAddtoCart = itemView.findViewById(R.id.bt_addToCart);
        }
    }
}
