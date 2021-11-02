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
            TextView tt4 = (TextView) v.findViewById(R.id.slot);
            TextView tt5 = (TextView) v.findViewById(R.id.subject);
            TextView tt6 = (TextView) v.findViewById(R.id.state);

            if (tt1 != null) {
                tt1.setText(p.getIdUser());
            }

            if (tt2 != null) {
                tt2.setText(p.getIdTeacher());
            }

            if (tt3 != null) {
                System.out.println(p.getDay());
                switch (p.getDay()) {
                    case "0":
                        tt3.setText("LUN");
                        break;
                    case "1":
                        tt3.setText("MAR");
                        break;
                    case "2":
                        tt3.setText("MER");
                        break;
                    case "3":
                        tt3.setText("GIO");
                        break;
                    case "4":
                        tt3.setText("VEN");
                        break;
                    default:
                        System.out.println("No valid value for day");
                }

            }

            if (tt4 != null) {
                tt4.setText(p.getSlot());
            }

            if (tt5 != null) {
                tt5.setText(p.getSubject());
            }

            if( tt6 != null) {
                tt6.setText(p.getStatus());
            }
        }

        return v;
    }

}