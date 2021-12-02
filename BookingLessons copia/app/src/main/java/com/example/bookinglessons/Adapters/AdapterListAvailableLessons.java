package com.example.bookinglessons.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.bookinglessons.Model.Lesson;
import com.example.bookinglessons.R;

import java.util.List;

public class AdapterListAvailableLessons extends ArrayAdapter<Lesson> {

    private int resourceLayout;
    private Context mContext;

    public AdapterListAvailableLessons(Context context, int resource, List<Lesson> items) {
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

        Lesson p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.slot);
            TextView tt2 = (TextView) v.findViewById(R.id.nameTeacherAvailable);

            TextView tt3 = (TextView) v.findViewById(R.id.surnameTeacherAvailable);

            if (tt1 != null) {
                tt1.setText(p.getSlot());
            }

            if (tt2 != null) {
                tt2.setText(p.getNameTeacher());
            }

            if(tt3 != null) {
                tt3.setText(p.getSurnameTeacher());
            }
        }

        return v;
    }

}
