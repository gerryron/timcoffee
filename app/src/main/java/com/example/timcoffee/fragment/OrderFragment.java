package com.example.timcoffee.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timcoffee.R;
import com.example.timcoffee.adapter.CartAdapter;
import com.example.timcoffee.adapter.OrderAdapter;
import com.example.timcoffee.api.APIInterface;
import com.example.timcoffee.api.ApiClient;
import com.example.timcoffee.model.Order;
import com.example.timcoffee.util.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {

    private SessionManager sessionManager;
    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private APIInterface apiInterface;
    private String nomerHp, role;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        sessionManager = new SessionManager(getActivity());
        recyclerView = view.findViewById(R.id.recyclerView);
        apiInterface = ApiClient.getRetrofit().create(APIInterface.class);
        nomerHp = sessionManager.getUserDetail().get(SessionManager.NOMER_HP);
        role = sessionManager.getUserDetail().get(SessionManager.ROLE);

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Memuat Data ...");
        progressDialog.show();

        if (role.equalsIgnoreCase("user")){
            Call<List<Order>> order = apiInterface.getOrderByPhoneNumber(nomerHp);
            order.enqueue(new Callback<List<Order>>() {

                @Override
                public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Berhasil Memuat Data" , Toast.LENGTH_SHORT).show();
                        adapter = new OrderAdapter(getActivity(), response.body(),role);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Gagal Memuat Data" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Order>> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Call<List<Order>> order = apiInterface.getAllOrderQueue();
            order.enqueue(new Callback<List<Order>>() {

                @Override
                public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Berhasil Memuat Data" , Toast.LENGTH_SHORT).show();
                        adapter = new OrderAdapter(getActivity(), response.body(),role);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Gagal Memuat Data" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Order>> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


        return view;
    }
}
