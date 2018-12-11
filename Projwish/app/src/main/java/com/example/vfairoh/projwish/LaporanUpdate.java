package com.example.vfairoh.projwish;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.vfairoh.projwish.SQLiteOperations.DataHelper;

public class LaporanUpdate extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    //    ini codingan buat Add Trans
    protected Cursor cursor;
    private  Spinner update_type;
    private EditText update_detail, update_jumlah, update_id;
    private Button btn_update;
    DataHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_update);
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
        Intent obj = getIntent();
        String idTrans = obj.getStringExtra("idTrans");
        update_id = findViewById(R.id.update_id);
        update_type = findViewById(R.id.update_type);
        update_detail = findViewById(R.id.update_detail);
        update_jumlah = findViewById(R.id.update_jumlah);
        btn_update = findViewById(R.id.btn_update);
        update_id.setEnabled(false);
        dbHelper = new DataHelper(this);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from Transaksi where idTrans = " + idTrans;
        Cursor c = db.rawQuery(sql, null);
        if (c != null) {
            if (c.moveToFirst()) {
                int id = c.getInt(c.getColumnIndex("idTrans"));
                String detail = c.getString(c.getColumnIndex("detail"));
                int jumlahUang = c.getInt(c.getColumnIndex("jumlahUang"));
                update_id.setText(String.valueOf(id));
                update_detail.setText(detail);
                update_jumlah.setText(String.valueOf(jumlahUang));
            }
        }

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = update_id.getText().toString();
                String type = update_type.getSelectedItem().toString();
                String detail = update_detail.getText().toString();
                String jumlah = update_jumlah.getText().toString();
                db.execSQL("update Transaksi set detail = '"+ detail +"', type = '"+ type +"', jumlahUang = "+ jumlah +" where idTrans = "+ id);
                Toast.makeText(getApplicationContext(), "Berhasil mengubah data", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
//    end codingan add Trans


        Spinner spinner = findViewById(R.id.update_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView txt_uangB = (TextView) navigationView.getHeaderView(0).findViewById(R.id.uangC);
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
        getMenuInflater().inflate(R.menu.transaksi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
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
            Intent obj = new Intent(getApplicationContext(), WishList.class);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
