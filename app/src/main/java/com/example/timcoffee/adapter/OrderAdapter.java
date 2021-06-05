package com.example.timcoffee.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timcoffee.R;
import com.example.timcoffee.api.APIInterface;
import com.example.timcoffee.api.ApiClient;
import com.example.timcoffee.model.Order;
import com.example.timcoffee.model.OrderUpdateStatusRequest;
import com.example.timcoffee.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private APIInterface apiInterface;
    private Context context;
    private List<Order> listOrder = new ArrayList<>();
    private String userRole;

    public OrderAdapter (Context context, List<Order> listOrder, String userRole) {
        this.context = context;
        this.listOrder = listOrder;
        this.userRole = userRole;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_order_item, parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvNamaPelanggan.setText(listOrder.get(position).getNama());
        if(userRole.equalsIgnoreCase("User")){
            String statusMessage = String.valueOf(listOrder.get(position).getStatus());
            switch (statusMessage) {
                case "0" :
                    statusMessage = "Order";
                    break;
                case "1" :
                    statusMessage = "On Process";
                    break;
                case "2" :
                    statusMessage = "Sucess";
                    break;
                case "3" :
                    statusMessage = "Cancel";
                    break;
            }
            holder.tvOrderStatus.setText(statusMessage);
            holder.rlMenuUser.setVisibility(View.VISIBLE);
            holder.rlMenuAdmin.setVisibility(View.GONE);
        } else {
            apiInterface = ApiClient.getRetrofit().create(APIInterface.class);
            holder.tvOrderCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("Tunggu Sebentar ...");
                    progressDialog.show();

                    Call<Order> updateStatusOrderCall = apiInterface.updateOrder( listOrder.get(position).getId(),new OrderUpdateStatusRequest("3"));
                    updateStatusOrderCall.enqueue(new Callback<Order>() {
                        @Override
                        public void onResponse(Call<Order> call, Response<Order> response) {
                            if (response.body() != null && response.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Sukses update status", Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Gagal Update Status " + userRole, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Order> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            holder.tvOrderProcess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("Tunggu Sebentar ...");
                    progressDialog.show();
                    Call<Order> updateStatusOrderCall = apiInterface.updateOrder( listOrder.get(position).getId(),new OrderUpdateStatusRequest("1"));
                    updateStatusOrderCall.enqueue(new Callback<Order>() {
                        @Override
                        public void onResponse(Call<Order> call, Response<Order> response) {
                            if (response.body() != null && response.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Sukses update status", Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Gagal Update Status", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Order> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            holder.tvOrderSuccess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("Tunggu Sebentar ...");
                    progressDialog.show();

                    Call<Order> updateStatusOrderCall = apiInterface.updateOrder( listOrder.get(position).getId(),new OrderUpdateStatusRequest("2"));
                    updateStatusOrderCall.enqueue(new Callback<Order>() {
                        @Override
                        public void onResponse(Call<Order> call, Response<Order> response) {
                            if (response.body() != null && response.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Sukses update status", Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Gagal Update Status", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Order> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            holder.rlMenuUser.setVisibility(View.GONE);
            holder.rlMenuAdmin.setVisibility(View.VISIBLE);
        }
        holder.recyclerView.setAdapter(new OrderDetailAdapter(context, listOrder.get(position).getOrderDetails()));
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public int getItemCount() {
        return listOrder.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaPelanggan, tvOrderCancel, tvOrderProcess, tvOrderSuccess, tvOrderStatus;
        RelativeLayout rlMenuAdmin, rlMenuUser;
        RecyclerView recyclerView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaPelanggan = itemView.findViewById(R.id.tv_name);
            tvOrderCancel = itemView.findViewById(R.id.tv_orderCancel);
            tvOrderProcess = itemView.findViewById(R.id.tv_orderProcess);
            tvOrderSuccess = itemView.findViewById(R.id.tv_orderSuccess);
            tvOrderStatus = itemView.findViewById(R.id.tv_status);
            rlMenuAdmin = itemView.findViewById(R.id.rl_orderMenuAdmin);
            rlMenuUser = itemView.findViewById(R.id.rl_orderMenuUser);
            recyclerView = itemView.findViewById(R.id.recyclerView);
        }
    }
}
