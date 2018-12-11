package com.example.vfairoh.projwish;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vfairoh.projwish.Models.mRekap;
import com.example.vfairoh.projwish.SQLiteOperations.DataHelper;

import java.sql.Time;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Rekap extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView tanggal, msk1, luar1, sisa1;
    private ListView list_data;
    private String time;
    DataHelper dbHelper;

    @Override
    protected void onResume() {
        super.onResume();
        populateList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekap);
        //UANG MASUK
        dbHelper = new DataHelper(this);
        SQLiteDatabase db11 = dbHelper.getWritableDatabase();
        String count11 = "select sum(jumlahUang)from Transaksi where type = 'Pemasukan'";
        Cursor c11 = db11.rawQuery(count11, null);
        c11.moveToFirst();
        int hitung11 = c11.getInt(0);
        //UANG KELUAR
        dbHelper = new DataHelper(this);
        SQLiteDatabase db112 = dbHelper.getWritableDatabase();
        String count112 = "select sum(jumlahUang)from Transaksi where type = 'Pengeluaran'";
        Cursor c112 = db112.rawQuery(count112, null);
        c112.moveToFirst();
        int hitung112 = c112.getInt(0);
        //UANG SAAT INI
        int duit112 = hitung11 - hitung112;


        tanggal = findViewById(R.id.tgl2);
        String tgl = getCurrentDate();
        String bulan = getCurrentDateAll();
        populateList();

        if (tgl.equals("1")) {

//UANG MASUK
            dbHelper = new DataHelper(this);
            SQLiteDatabase db3 = dbHelper.getWritableDatabase();
            String count = "select sum(jumlahUang)from Transaksi where type = 'Pemasukan'";
            Cursor c3 = db3.rawQuery(count, null);
            c3.moveToFirst();
            int hitung = c3.getInt(0);
            //UANG KELUAR
            dbHelper = new DataHelper(this);
            SQLiteDatabase db1 = dbHelper.getWritableDatabase();
            String count1 = "select sum(jumlahUang)from Transaksi where type = 'Pengeluaran'";
            Cursor c1 = db1.rawQuery(count1, null);
            c1.moveToFirst();
            int hitung1 = c1.getInt(0);
            //UANG SAAT INI
             int duit = hitung - hitung1;

//=======================
            dbHelper = new DataHelper(this);
            SQLiteDatabase db7 = dbHelper.getWritableDatabase();
            Cursor c = db7.rawQuery("select * from Rekap where timestamp = '"+bulan+"'", null);
            if(!c.moveToFirst()) {
                dbHelper = new DataHelper(this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String totMsk = String.valueOf(hitung);
                String totLuar = String.valueOf(hitung1);
                String sisaUang = String.valueOf(duit);
                String timestamp = String.valueOf(bulan);
                db.execSQL("insert into Rekap (timestamp, totalMasuk, totalLuar, uangSisa) values('" + timestamp + "','" + totMsk + "','" + totLuar + "', '" + sisaUang + "')");
                db.execSQL("delete from Transaksi");
                String sisaku = "select uangSisa from Rekap where idRekap = (select max(idRekap)from Rekap)";
                Cursor c10 = db3.rawQuery(sisaku, null);
                c10.moveToFirst();
                int sisaku2 = c10.getInt(0);
                db.execSQL("insert into Transaksi (type, detail, jumlahUang) values('Pemasukan','Sisa uang bulan kemarin', '"+String.valueOf(sisaku2)+"')");
                Toast.makeText(getApplicationContext(), "Rekap data keuangan Berhasil", Toast.LENGTH_SHORT).show();
                populateList();
            }
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutr);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        TextView txt_uangG = (TextView) navigationView.getHeaderView(0).findViewById(R.id.uangG);
        txt_uangG.setText(String.valueOf(duit112));
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void populateList(){
        list_data = findViewById(R.id.rekapView1);
        dbHelper = new DataHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("select * from Rekap order by idRekap desc", null);
        final ArrayList<mRekap> RekapList = new ArrayList<>();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    Integer idRekap = c.getInt(c.getColumnIndex("idRekap"));
                    String timestamp = c.getString(c.getColumnIndex("timestamp"));
                    Integer totalMasuk = c.getInt(c.getColumnIndex("totalMasuk"));
                    Integer totalLuar = c.getInt(c.getColumnIndex("totalLuar"));
                    Integer uangSisa = c.getInt(c.getColumnIndex("uangSisa"));
                    RekapList.add(new mRekap(idRekap, timestamp, totalMasuk, totalLuar, uangSisa));
                } while(c.moveToNext());
            }
        }

        RekapListAdapter adapter = new RekapListAdapter(this, RekapList);
        list_data.setAdapter(adapter);
        list_data.setOnItemClickListener(new
             AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final int idRekap = RekapList.get(position).getIdRekap();
                final double totMasuk = RekapList.get(position).getTotalMasuk();
                final double totLuar = RekapList.get(position).getTotalLuar();
                final double result = (totLuar/totMasuk)*100;
                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setMaximumFractionDigits(1);
                    final String iniResult = nf.format(result);
                    if(result > 60){
                         AlertDialog.Builder adb = new AlertDialog.Builder(Rekap.this);
                         adb.setTitle("KESIMPULAN");
                         adb.setMessage("Kamu cukup boros, coba lebih berhemat bulan ini ya! :) Pengeluaranmu bulan sebelumnya " +iniResult+ "% dari pemasukanmu loh.");
                         adb.setPositiveButton("Delete Rekap", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 SQLiteDatabase db = dbHelper.getWritableDatabase();
                                 db.execSQL("delete from Rekap where idRekap ="+ String.valueOf(idRekap));
                                 Toast.makeText(getApplicationContext(), "Berhasil mengahapus Rekap", Toast.LENGTH_SHORT).show();
                                 populateList();
                                 }});
                         adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 dialog.dismiss();
                                 }});
                         adb.show();
                         }else if (result <= 60){
                             AlertDialog.Builder adb = new AlertDialog.Builder(Rekap.this);
                             adb.setTitle("KESIMPULAN");
                             adb.setMessage("Kamu sudah berhemat! Tabung terus sisa uangmu ya! Biar bulan depan bisa menyelesaikan wish listmu yang belum tercapai :) Pengeluaranmu bulan sebelumnya " +iniResult+ "% dari pemasukanmu.");
                             adb.setPositiveButton("Delete Rekap", new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                     SQLiteDatabase db = dbHelper.getWritableDatabase();
                                     db.execSQL("delete from Rekap where idRekap ="+ String.valueOf(idRekap));
                                     Toast.makeText(getApplicationContext(), "Berhasil mengahapus Rekap", Toast.LENGTH_SHORT).show();
                                   populateList();
                                 }});
                                 adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which) {
                                         dialog.dismiss();
                                     }});
                                 adb.show();
                                 }
                }
                });}

    public String getCurrentDate(){
        final Calendar c = Calendar.getInstance();
        int day;
        day = c.get(Calendar.DATE);
        return ""+day+"";
    }
    public String getCurrentDateAll(){
        final Calendar c = Calendar.getInstance();
        int day, month, year;
        day = c.get(Calendar.DATE);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        return ""+year+"-"+(month+1)+"-"+(day<10?("0"+day):(day))+"";
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutr);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rekap, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_laporan) {
            Intent obj = new Intent(getApplicationContext(), Laporan.class);
            startActivity(obj);
        } else if (id == R.id.nav_wishlist) {
            Intent obj = new Intent(getApplicationContext(), AllWishlist.class);
            startActivity(obj);
        }
        else if (id == R.id.nav_rekap) {
            Intent obj = new Intent(getApplicationContext(), Rekap.class);
            startActivity(obj);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutr);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
