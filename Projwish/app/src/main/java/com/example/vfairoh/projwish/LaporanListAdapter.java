package com.example.vfairoh.projwish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.vfairoh.projwish.Models.mLaporan;

import java.util.ArrayList; import java.util.List;

public class LaporanListAdapter extends ArrayAdapter<mLaporan> {
    private Context context;
    private List<mLaporan> LaporanList = new ArrayList<mLaporan>();

    public LaporanListAdapter(Context context, ArrayList<mLaporan> list) {
        super(context, 0, list);
        this.context = context;
        LaporanList = list;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.laporan_custom, parent, false);
        }

        mLaporan currentLaporan = LaporanList.get(position);
        TextView txt_type = listItem.findViewById(R.id.txt_type);
        TextView txt_detail = listItem.findViewById(R.id.txt_detailcus);
        TextView txt_jumlahUang = listItem.findViewById(R.id.txt_jumlahUang);
        TextView txt_timestamp = listItem.findViewById(R.id.txt_timestamp);
        txt_type.setText(currentLaporan.getType());
        txt_detail.setText(currentLaporan.getDetail());
        txt_jumlahUang.setText(String.valueOf(currentLaporan.getJumlahUang()));
        txt_timestamp.setText(currentLaporan.getTimestamp());
        return listItem;
    }
}
