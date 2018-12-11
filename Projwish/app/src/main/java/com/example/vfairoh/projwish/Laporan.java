package com.example.vfairoh.projwish;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vfairoh.projwish.Models.mLaporan;
import com.example.vfairoh.projwish.SQLiteOperations.DataHelper;

import java.util.ArrayList;

public class Laporan extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ListView list_data;
    DataHelper dbHelper;
    mLaporan mlaporan;
    private Button total;
    private TextView totalMsk, totalLuar, totalUang;
    final ArrayList<mLaporan> laporanList = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
        populateList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);
        populateList();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent obj = new Intent(getApplicationContext(), Transaksi.class);
                startActivity(obj);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }
//============================
    private void populateList()
    {
        //UANG MASUK
        totalMsk = findViewById(R.id.total_msk);
        dbHelper = new DataHelper(this);
        SQLiteDatabase db3 = dbHelper.getWritableDatabase();
        String count = "select sum(jumlahUang)from Transaksi where type = 'Pemasukan'";
        Cursor c3 = db3.rawQuery(count, null);
        c3.moveToFirst();
        int hitung = c3.getInt(0);
        totalMsk.setText(String.valueOf(hitung));
        //UANG KELUAR
        totalLuar = findViewById(R.id.total_luar);
        dbHelper = new DataHelper(this);
        SQLiteDatabase db1 = dbHelper.getWritableDatabase();
        String count1 = "select sum(jumlahUang)from Transaksi where type = 'Pengeluaran'";
        Cursor c1 = db1.rawQuery(count1, null);
        c1.moveToFirst();
        int hitung1 = c1.getInt(0);
        totalLuar.setText(String.valueOf(hitung1));
        //UANG SAAT INI
        totalUang = findViewById(R.id.total_uang);
        int duit = hitung -hitung1;
        totalUang.setText(String.valueOf(duit));
        //DUITNAV

        //========================
        list_data = findViewById(R.id.lvView);
        dbHelper = new DataHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("select * from Transaksi order by idTrans desc", null);

        final ArrayList<mLaporan> LaporanList = new ArrayList<>();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    Integer idTrans = c.getInt(c.getColumnIndex("idTrans"));
                    String timestamp = c.getString(c.getColumnIndex("timestamp"));
                    String type = c.getString(c.getColumnIndex("type"));
                    String detail = c.getString(c.getColumnIndex("detail"));
                    Integer jumlahUang = c.getInt(c.getColumnIndex("jumlahUang"));
                    LaporanList.add(new mLaporan(idTrans, timestamp, type, detail, jumlahUang));
                } while(c.moveToNext());
            }
        }

        LaporanListAdapter adapter = new LaporanListAdapter(this, LaporanList);
        list_data.setAdapter(adapter);
        list_data.setOnItemClickListener(new
                                                 AdapterView.OnItemClickListener() {
                                                     @Override
                                                     public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                                                         final int idTrans = LaporanList.get(position).getIdTrans();
                                                         final String[] option = {"edit","delete"};

                                                         AlertDialog.Builder adb = new AlertDialog.Builder(Laporan.this);
                                                         adb.setItems(option, new DialogInterface.OnClickListener() {
                                                             @Override
                                                             public void onClick(DialogInterface dialog, int which) {
                                                                 switch(which){
                                                                     case 0:
                                                                         Intent obj = new Intent(getApplicationContext(), LaporanUpdate.class);
                                                                         obj.putExtra("idTrans", String.valueOf(idTrans));
                                                                         startActivity(obj);
                                                                         break;
                                                                     case 1 :
                                                                         AlertDialog.Builder alertdialog = new AlertDialog.Builder(Laporan.this);
                                                                         alertdialog.setTitle("DELETE");
                                                                         alertdialog.setMessage("Anda yakin akan menghapus transaksi?");
                                                                         alertdialog.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                                                                             @Override
                                                                             public void onClick(DialogInterface dialog, int which) {
                                                                                 SQLiteDatabase db = dbHelper.getWritableDatabase();
                                                                                 db.execSQL("delete from Transaksi where idTrans ="+ String.valueOf(idTrans));
                                                                                 Toast.makeText(getApplicationContext(), "Berhasil mengahpus transaksi", Toast.LENGTH_SHORT).show();
                                                                                 populateList();
                                                                             }
                                                                         });
                                                                         alertdialog.setNegativeButton("tidak", new DialogInterface.OnClickListener() {
                                                                             @Override
                                                                             public void onClick(DialogInterface dialog, int which) {
                                                                                 dialog.dismiss(); }
                                                                         });
                                                                         alertdialog.show();
                                                                 }
                                                             }
                                                         });
                                                         adb.show();
                                                     }
                                                 });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView txt_uangB = (TextView) navigationView.getHeaderView(0).findViewById(R.id.uangB);
        txt_uangB.setText(String.valueOf(duit));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.laporan, menu);
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
            // Handle the camera action
        } else if (id == R.id.nav_wishlist) {
            Intent obj = new Intent(getApplicationContext(), AllWishlist.class);
            startActivity(obj);
        }
        else if (id == R.id.nav_rekap) {
            Intent obj = new Intent(getApplicationContext(), Rekap.class);
            startActivity(obj);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
