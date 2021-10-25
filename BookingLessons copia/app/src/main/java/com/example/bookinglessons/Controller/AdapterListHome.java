package com.example.bookinglessons.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.bookinglessons.Data.BookedLesson;
import com.example.bookinglessons.R;

import java.util.List;

public class AdapterListHome extends ArrayAdapter<BookedLesson> {

    private int resourceLayout;
    private Context mContext;

    public AdapterListHome(Context context, int resource, List<BookedLesson> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        BookedLesson p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.user);
            TextView tt2 = (TextView) v.findViewById(R.id.teacher);
            TextView tt3 = (TextView) v.findViewById(R.id.day);

            if (tt1 != null) {
                tt1.setText(p.getIdUser());
            }

            if (tt2 != null) {
                tt2.setText(p.getIdTeacher());
            }

            if (tt3 != null) {
                tt3.setText(p.getDay());
            }
        }

        return v;
    }

}