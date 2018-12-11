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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vfairoh.projwish.Models.mLaporan;
import com.example.vfairoh.projwish.Models.mWishlist;
import com.example.vfairoh.projwish.SQLiteOperations.DataHelper;

import java.util.ArrayList;

public class AllWishlist extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ListView list_data;
    DataHelper dbHelper;
    private EditText txt_detail, txt_jumlah;

    @Override
    protected void onResume() {
        super.onResume();
        populateList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_wishlist);
        populateList();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabw);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent obj = new Intent(getApplicationContext(), WishList.class);
                startActivity(obj);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void populateList(){
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
        int duit = hitung -hitung1;
        list_data = findViewById(R.id.wlView);
        dbHelper = new DataHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("select * from WishList order by idWL desc", null);

        final ArrayList<mWishlist> WishList = new ArrayList<>();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    Integer idwL = c.getInt(c.getColumnIndex("idwL"));
                    String detail = c.getString(c.getColumnIndex("detail"));
                    Integer jumlahUang = c.getInt(c.getColumnIndex("jumlahUang"));
                    WishList.add(new mWishlist(idwL, detail, jumlahUang));
                } while(c.moveToNext());
            }
        }

        WishListAdapter adapter = new WishListAdapter(this, WishList);
        list_data.setAdapter(adapter);

        list_data.setOnItemClickListener(new

                                                 AdapterView.OnItemClickListener() {
                                                     @Override
                                                     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                         final int idwL = WishList.get(position).getIdwL();
                                                         final String detailwl = WishList.get(position).getDetail();
                                                         final int jumlahwl = WishList.get(position).getJumlahUang();
                                                         final String[] option = {"Edit","Finish"};
                                                         AlertDialog.Builder adb = new AlertDialog.Builder(AllWishlist.this);
                                                         adb.setItems(option, new DialogInterface.OnClickListener() {
                                                             @Override
                                                             public void onClick(DialogInterface dialog, int which) {
                                                                 switch(which){
                                                                     case 0:
                                                                         Intent obj = new Intent(getApplicationContext(), WishlistUpdate.class);
                                                                         obj.putExtra("idwL", String.valueOf(idwL));
                                                                         startActivity(obj);
                                                                         break;
                                                                     case 1 :
                                                                         AlertDialog.Builder alertdialog = new AlertDialog.Builder(AllWishlist.this);
                                                                         alertdialog.setTitle("FINISH");
                                                                         alertdialog.setMessage("Apakah WishItem mu ini sudah tercapai?");
                                                                         alertdialog.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                                                                             @Override
                                                                             public void onClick(DialogInterface dialog, int which) {
                                                                                 SQLiteDatabase db = dbHelper.getWritableDatabase();
                                                                                 db.execSQL("insert into Transaksi (type, detail, jumlahUang) values('Pengeluaran','"+detailwl+" (Wish List tercapai)', '"+String.valueOf(jumlahwl)+"')");
                                                                                 db.execSQL("delete from WishList where idWL ="+ String.valueOf(idwL));
                                                                                 Toast.makeText(getApplicationContext(), "Berhasil menyelesaikan WishList", Toast.LENGTH_SHORT).show();
                                                                                 populateList();
                                                                             }
                                                                         });
                                                                         alertdialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                                                             @Override
                                                                             public void onClick(DialogInterface dialog, int which) {
                                                                                         SQLiteDatabase db = dbHelper.getWritableDatabase();
                                                                                         db.execSQL("delete from WishList where idWL ="+ String.valueOf(idwL));
                                                                                         Toast.makeText(getApplicationContext(), "Berhasil mengahapus wishlist", Toast.LENGTH_SHORT).show();
                                                                                         populateList();
                                                                             }
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
        TextView txt_uangB = (TextView) navigationView.getHeaderView(0).findViewById(R.id.uangA);
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
        getMenuInflater().inflate(R.menu.all_wishlist, menu);
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
