package com.example.timcoffee.fragment;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timcoffee.CartActivity;
import com.example.timcoffee.MainActivity;
import com.example.timcoffee.OrderSuccessActivity;
import com.example.timcoffee.R;
import com.example.timcoffee.adapter.CartAdapter;
import com.example.timcoffee.api.APIInterface;
import com.example.timcoffee.api.ApiClient;
import com.example.timcoffee.model.Order;
import com.example.timcoffee.model.OrderDetailsItem;
import com.example.timcoffee.util.SessionManager;
import com.example.timcoffee.util.Utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment implements CartAdapter.DeleteItem, CartAdapter.TotalPrice {

    private SessionManager sessionManager;
    private APIInterface apiInterface;
    private CartAdapter adapter;

    private LinearLayout llCartMenu, llBookingMenu, llCartTotalMenu;
    private TextView tvNoCart, tvBookingTime, tvTotalPrice;
    private RecyclerView recyclerView;
    private CheckBox cbBookingCheck;
    private EditText etNote;
    private Button btnPay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        initViews(view);

        sessionManager = new SessionManager(getActivity());
        apiInterface = ApiClient.getRetrofit().create(APIInterface.class);

        adapter = new CartAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        showCart();
        showBookingOrder();

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("Send Order ...")
                        .setMessage("Are you sure you want to order this items ?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Order orderRequest = createOrderRequest();
                                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                                progressDialog.setMessage("Mengirim pesanan ...");
                                progressDialog.show();

                                Call<Order> postOrderCall = apiInterface.postOrder(orderRequest);
                                postOrderCall.enqueue(new Callback<Order>() {
                                    @Override
                                    public void onResponse(Call<Order> call, Response<Order> response) {
                                        if (response.body() != null && response.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Utils.destroyCart(getActivity());
                                            showCart();
                                            Intent intent = new Intent(getActivity(), OrderSuccessActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), "Gagal Mengirim Order" , Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Order> call, Throwable t) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                builder.create().show();
            }
        });

        return view;
    }

    private Order createOrderRequest() {
        Order order = new Order();
        order.setNama(sessionManager.getUserDetail().get(SessionManager.NAME));
        order.setNomerHp(sessionManager.getUserDetail().get(SessionManager.NOMER_HP));
        if(etNote != null){
            order.setNote(etNote.getText().toString());
        }

        if(cbBookingCheck.isChecked()){
            Calendar curDate = new GregorianCalendar();
            Calendar orderTime = new GregorianCalendar();
            SimpleDateFormat tvFormat = new SimpleDateFormat("hh:mm aa");
            SimpleDateFormat requestFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            try {
                orderTime.setTime(tvFormat.parse(tvBookingTime.getText().toString()));
                curDate.set(Calendar.HOUR_OF_DAY, orderTime.get(Calendar.HOUR_OF_DAY));
                curDate.set(Calendar.MINUTE, orderTime.get(Calendar.MINUTE));
                order.setOrderDate(requestFormat.format(curDate.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        order.setOrderDetails(Utils.getCartItems(getActivity()));
        return order;
    }

    private void showBookingOrder() {
        cbBookingCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox)v).isChecked();
                if (checked) {
                    llBookingMenu.setVisibility(View.VISIBLE);
                    tvBookingTime.setOnClickListener(new View.OnClickListener() {
                        int _hourOfDay= 0;
                        int _minute = 0;
                        @Override
                        public void onClick(View v) {
                            TimePickerDialog timePickerDialog = new TimePickerDialog(
                                    getActivity(),
                                    new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                            _hourOfDay = hourOfDay;
                                            _minute = minute;
                                            Calendar calendar = new GregorianCalendar();
                                            calendar.set(0,0,0,hourOfDay, minute);
                                            tvBookingTime.setText(DateFormat.format("hh:mm aa", calendar));
                                        }
                                    }, 12, 0, false
                            );
                            timePickerDialog.updateTime(_hourOfDay, _minute);
                            timePickerDialog.show();
                        }
                    });
                } else {
                    llBookingMenu.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initViews(View view) {
        llCartMenu = view.findViewById(R.id.ll_cart);
        tvNoCart = view.findViewById(R.id.tv_noCart);
        recyclerView = view.findViewById(R.id.recyclerView);
        cbBookingCheck = view.findViewById(R.id.cb_bookingOrder);
        llBookingMenu = view.findViewById(R.id.ll_bookingOrder);
        tvBookingTime = view.findViewById(R.id.tv_selectOrderTime);
        etNote = view.findViewById(R.id.et_cartNote);
        llCartTotalMenu = view.findViewById(R.id.ll_cartTotal);
        tvTotalPrice = view.findViewById(R.id.tv_totalPrice);
        btnPay = view.findViewById(R.id.bt_cartPay);
    }

    @Override
    public void onDeleteResult(OrderDetailsItem items) {
        Utils.deteleItemFromCart(getActivity(), items);
        showCart();
    }

    private void showCart() {
        ArrayList<OrderDetailsItem> cartItems = Utils.getCartItems(getActivity());
        if(cartItems != null) {
            if(cartItems.size()>0) {
                tvNoCart.setVisibility(View.GONE);
                llCartMenu.setVisibility(View.VISIBLE);
                adapter.setItems(cartItems);
            } else {
                tvNoCart.setVisibility(View.VISIBLE);
                llCartMenu.setVisibility(View.GONE);
            }
        } else {
            tvNoCart.setVisibility(View.VISIBLE);
            llCartMenu.setVisibility(View.GONE);
        }
    }

    @Override
    public void getTotalPrice(BigDecimal price) {
        tvTotalPrice.setText("Rp." + price.toString());
    }
}
