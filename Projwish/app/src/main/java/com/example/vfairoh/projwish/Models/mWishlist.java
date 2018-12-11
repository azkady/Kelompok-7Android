package com.example.vfairoh.projwish.Models;

public class mWishlist {
    private String detail;
    private int jumlahUang, idwL;

    public mWishlist(){}

    public mWishlist(int idwL, String detail, int jumlahUang) {
        this.detail = detail;
        this.jumlahUang = jumlahUang;
        this.idwL = idwL;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getJumlahUang() {
        return jumlahUang;
    }

    public void setJumlahUang(int jumlahUang) {
        this.jumlahUang = jumlahUang;
    }

    public int getIdwL() {
        return idwL;
    }

    public void setIdwL(int idwL) {
        this.idwL = idwL;
    }
}
