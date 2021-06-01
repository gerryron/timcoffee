package com.example.timcoffee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.timcoffee.MainActivity;
import com.example.timcoffee.R;
import com.example.timcoffee.model.MenuModel;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private Context context;
    private List<MenuModel> listMenu = new ArrayList<>();

    public MenuAdapter(Context context, List<MenuModel> listMenu) {
        this.context = context;
        this.listMenu = listMenu;
    }

    //menyambungkan layout item
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.card_menu, parent,false);
        return new MyViewHolder(itemView);
    }

    //set item
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvNamaMenu.setText(listMenu.get(position).getNamaMenu());
        holder.tvHarga.setText(listMenu.get(position).getHargaMenu().toString());
        holder.ivGambar.setImageDrawable(listMenu.get(position).getGambarMenu());
//        Glide.with(context).load(listMenu.get(position).getGambarMenu()).into(holder.ivGambar);
    }

    //menhitung jumlah data
    @Override
    public int getItemCount() {
        return listMenu.size();
    }

    //mengenalkan komponen ke item
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGambar;
        TextView tvNamaMenu;
        TextView tvHarga;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivGambar = itemView.findViewById(R.id.iv_menu);
            tvNamaMenu = itemView.findViewById(R.id.tv_menu);
            tvHarga = itemView.findViewById(R.id.tv_price);
        }
    }
}
