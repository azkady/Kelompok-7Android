package com.example.vfairoh.projwish.Models;

import java.util.Date;

public class mRekap {
    private String timestamp;
    private int idRekap, totalMasuk, totalLuar, uangSisa;

    public mRekap(){}

    public mRekap(int idRekap, String timestamp, int totalMasuk, int totalLuar, int uangSisa) {
        this.idRekap = idRekap;
        this.timestamp = timestamp;
        this.totalMasuk = totalMasuk;
        this.totalLuar = totalLuar;
        this.uangSisa = uangSisa;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getIdRekap() {
        return idRekap;
    }

    public void setIdRekap(int idRekap) {
        this.idRekap = idRekap;
    }

    public int getTotalMasuk() {
        return totalMasuk;
    }

    public void setTotalMasuk(int totalMasuk) {
        this.totalMasuk = totalMasuk;
    }

    public int getTotalLuar() {
        return totalLuar;
    }

    public void setTotalLuar(int totalLuar) {
        this.totalLuar = totalLuar;
    }

    public int getUangSisa() {
        return uangSisa;
    }

    public void setUangSisa(int uangSisa) {
        this.uangSisa = uangSisa;
    }
}
