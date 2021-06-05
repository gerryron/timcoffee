package com.example.timcoffee.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timcoffee.CartActivity;
import com.example.timcoffee.OrderActivity;
import com.example.timcoffee.R;
import com.example.timcoffee.util.SessionManager;
import com.example.timcoffee.adapter.ProductAdapter;
import com.example.timcoffee.api.APIInterface;
import com.example.timcoffee.api.ApiClient;
import com.example.timcoffee.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment {

    private SessionManager sessionManager;
    private APIInterface apiInterface;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private TextView mainGreeting, mainQoutes;
    private ImageButton mainRefresh;
    private String greetingMessage, quoteMessage, nama;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initViews(view);
        initBottomNavView();
        setGreeting();
        setRecyclerViewProduct();

        mainRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRecyclerViewProduct();
            }
        });


        return view;
    }

    private void setRecyclerViewProduct() {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Menyiapkan data ...");
        progressDialog.show();

        Call<List<Product>> listProductCall = apiInterface.getAllProductResponse();
        listProductCall.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.body() != null && response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Berhasil memuat data", Toast.LENGTH_SHORT).show();
                    recyclerView.setAdapter(new ProductAdapter(getActivity(), response.body()));
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setGreeting() {
        Calendar calendar = new GregorianCalendar();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        nama = sessionManager.getUserDetail().get(SessionManager.NAME);

        if (nama.split(" ").length > 1){
            nama = nama.split(" ")[1];;
        }

        if(timeOfDay>=0 && timeOfDay<12) {
            greetingMessage = getResources().getString(R.string.goodMorning);
        }else if(timeOfDay>=12 && timeOfDay<=18) {
            greetingMessage = getResources().getString(R.string.goodAfternoon);
        }else {
            greetingMessage = getResources().getString(R.string.goodEvening);
        }

        switch (dayOfWeek) {
            case Calendar.MONDAY :
                quoteMessage = getResources().getString(R.string.mondayQuote);
                break;
            case Calendar.TUESDAY :
                quoteMessage = getResources().getString(R.string.tuesdayQuote);
                break;
            case Calendar.WEDNESDAY :
                quoteMessage = getResources().getString(R.string.wednesdayQuote);
                break;
            case Calendar.THURSDAY :
                quoteMessage = getResources().getString(R.string.thursdayQuote);
                break;
            case Calendar.FRIDAY :
                quoteMessage = getResources().getString(R.string.fridayQuote);
                break;
            default :
                quoteMessage = getResources().getString(R.string.weekendQuote);
                break;
        }

        mainGreeting.setText(greetingMessage + ", " + nama.substring(0,1).toUpperCase() + nama.substring(1).toLowerCase());
        mainQoutes.setText(quoteMessage);
    }

    private void initBottomNavView() {
        bottomNavigationView.setSelectedItemId(R.id.btmNav_Home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btmNav_Cart:
                        Intent cartIntent = new Intent(getActivity(), CartActivity.class);
                        cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartIntent);
                        break;
                    case R.id.btmNav_Order:
                        Intent orderIntent = new Intent(getActivity(), OrderActivity.class);
                        orderIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(orderIntent);
                        break;
                }

                return false;
            }
        });
    }

    private void initViews(View view) {
        sessionManager = new SessionManager(getActivity());
        apiInterface = ApiClient.getRetrofit().create(APIInterface.class);
        bottomNavigationView = view.findViewById(R.id.bottomNavView);
        recyclerView = view.findViewById(R.id.recyclerView);
        mainGreeting = view.findViewById(R.id.tv_mainGreeting);
        mainQoutes = view.findViewById(R.id.tv_mainQuotes);
        mainRefresh = view.findViewById(R.id.ib_mainRefresh);
    }
}