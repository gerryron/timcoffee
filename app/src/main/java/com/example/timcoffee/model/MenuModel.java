package com.example.timcoffee.model;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.math.BigDecimal;

public class MenuModel {

    private Drawable gambarMenu;
    private String namaMenu;
    private BigDecimal hargaMenu;

    public Drawable getGambarMenu() {
        return gambarMenu;
    }

    public void setGambarMenu(Drawable gambarMenu) {
        this.gambarMenu = gambarMenu;
    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public void setNamaMenu(String namaMenu) {
        this.namaMenu = namaMenu;
    }

    public BigDecimal getHargaMenu() {
        return hargaMenu;
    }

    public void setHargaMenu(BigDecimal hargaMenu) {
        this.hargaMenu = hargaMenu;
    }
}
