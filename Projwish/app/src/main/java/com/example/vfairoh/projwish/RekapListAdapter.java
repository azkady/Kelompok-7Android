package com.example.vfairoh.projwish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.vfairoh.projwish.Models.mRekap;

import java.util.ArrayList; import java.util.List;

public class RekapListAdapter extends ArrayAdapter<mRekap> {
    private Context context;
    private List<mRekap> RekapList = new ArrayList<mRekap>();

    public RekapListAdapter(Context context, ArrayList<mRekap> list) {
        super(context, 0, list);
        this.context = context;
        RekapList = list;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.rekap_custom, parent, false);
        }

        mRekap currentRekap = RekapList.get(position);
        TextView txt_tanggal = listItem.findViewById(R.id.tglrekap);
        TextView txt_totMsk = listItem.findViewById(R.id.rekap_totmsk);
        TextView txt_totLuar = listItem.findViewById(R.id.rekap_totluar);
        TextView txt_sisa = listItem.findViewById(R.id.rekap_sisa);
        txt_tanggal.setText(currentRekap.getTimestamp());
        txt_totMsk.setText(String.valueOf(currentRekap.getTotalMasuk()));
        txt_totLuar.setText(String.valueOf(currentRekap.getTotalLuar()));
        txt_sisa.setText(String.valueOf(currentRekap.getUangSisa()));
        return listItem;
    }
}
