package com.example.timcoffee.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.timcoffee.model.OrderDetailsItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {

    private final static String CART_ITEMS_KEY = "all_items";
    private final static String DB_NAME = "fake_database";
    private static Gson gson = new Gson();
    private static Type OrderDetailsItemListType = new TypeToken<ArrayList<OrderDetailsItem>>(){}.getType();

    public static void addItemToCart(Context context, OrderDetailsItem item) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
        ArrayList<OrderDetailsItem> cartItems = gson.fromJson(sharedPreferences.getString(CART_ITEMS_KEY, null), OrderDetailsItemListType);
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        cartItems.add(item);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(CART_ITEMS_KEY);
        editor.putString(CART_ITEMS_KEY, gson.toJson(cartItems));
        editor.commit();
    }

    public static ArrayList<OrderDetailsItem> getCartItems(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
        ArrayList<OrderDetailsItem> cartItems = gson.fromJson(sharedPreferences.getString(CART_ITEMS_KEY, null), OrderDetailsItemListType);
        return cartItems;
    }

    public static void deteleItemFromCart (Context context, OrderDetailsItem item) {
        ArrayList<OrderDetailsItem> cartItems = getCartItems(context);
        if(cartItems != null) {
            ArrayList<OrderDetailsItem> newItems = new ArrayList<>();
            for(OrderDetailsItem i : cartItems) {
                if(i.getId() != item.getId()) {
                    newItems.add(i);
                }
            }
            SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(CART_ITEMS_KEY);
            editor.putString(CART_ITEMS_KEY, gson.toJson(newItems));
            editor.commit();
        }
    }

    public static void destroyCart (Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(CART_ITEMS_KEY);
        editor.commit();
    }

}
