package com.example.bookinglessons.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.bookinglessons.Model.Lesson;
import com.example.bookinglessons.R;
import org.w3c.dom.Text;

import java.util.List;

public class AdapterListHome extends ArrayAdapter<Lesson> {

    private int resourceLayout;
    private Context mContext;

    public AdapterListHome(Context context, int resource, List<Lesson> items) {
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
            TextView tt3 = (TextView) v.findViewById(R.id.day);
            TextView tt4 = (TextView) v.findViewById(R.id.slot);
            TextView tt5 = (TextView) v.findViewById(R.id.subject);
            TextView tt6 = (TextView) v.findViewById(R.id.state);
            TextView tt7 = (TextView) v.findViewById(R.id.nameTeacher);
            TextView tt8 = (TextView) v.findViewById(R.id.surnameTeacher);

            if (tt3 != null) {
                System.out.println(p.getDay());
                switch (p.getDay()) {
                    case "0":
                        tt3.setText("MON");
                        break;
                    case "1":
                        tt3.setText("TUE");
                        break;
                    case "2":
                        tt3.setText("WEN");
                        break;
                    case "3":
                        tt3.setText("THU");
                        break;
                    case "4":
                        tt3.setText("FRI");
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
                switch (p.getStatus()) {
                    case "Da frequentare":
                        tt6.setText("Booked");
                        break;
                    case "Passata":
                        tt6.setText("Frequented");
                        break;
                    case "Cancellata":
                        tt6.setText("Deleted");
                }
            }

            if(tt7 != null) {
                tt7.setText(p.getNameTeacher());
            }

            if(tt8 != null) {
                tt8.setText(p.getSurnameTeacher());
            }
        }

        return v;
    }

}