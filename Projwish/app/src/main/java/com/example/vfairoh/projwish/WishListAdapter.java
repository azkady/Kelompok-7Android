package com.example.vfairoh.projwish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.vfairoh.projwish.Models.mLaporan;
import com.example.vfairoh.projwish.Models.mWishlist;

import java.util.ArrayList; import java.util.List;

public class WishListAdapter extends ArrayAdapter<mWishlist> {
    private Context context;
    private List<mWishlist> WishList = new ArrayList<mWishlist>();

    public WishListAdapter(Context context, ArrayList<mWishlist> list) {
        super(context, 0, list);
        this.context = context;
        WishList = list;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.wishlist_custom, parent, false);
        }

        mWishlist currentLaporan = WishList.get(position);
        TextView txt_detail = listItem.findViewById(R.id.txt_wdetail);
        TextView txt_harga = listItem.findViewById(R.id.txt_harga);
        txt_detail.setText(currentLaporan.getDetail());
        txt_harga.setText(String.valueOf(currentLaporan.getJumlahUang()));
        return listItem;
    }
}
