package com.example.vfairoh.projwish.Models;

import java.util.Date;

public class mLaporan {
    private String detail, type, timestamp;
    private int idTrans, jumlahUang;

    public mLaporan(){}

    public mLaporan(int idTrans, String timestamp, String type, String detail, int jumlahUang) {
        this.idTrans = idTrans;
        this.timestamp = timestamp;
        this.type = type;
        this.detail = detail;
        this.jumlahUang = jumlahUang;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIdTrans() {
        return idTrans;
    }

    public void setIdTrans(int idTrans) {
        this.idTrans = idTrans;
    }

    public int getJumlahUang() {
        return jumlahUang;
    }

    public void setJumlahUang(int jumlahUang) {
        this.jumlahUang = jumlahUang;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
