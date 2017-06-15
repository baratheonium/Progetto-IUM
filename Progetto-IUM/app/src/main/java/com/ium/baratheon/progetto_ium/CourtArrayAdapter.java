package com.ium.baratheon.progetto_ium;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by utente on 11/02/2017.
 */

public class CourtArrayAdapter<Court> extends ArrayAdapter {
    private List<Court> items;
    private User user;

    public CourtArrayAdapter(Context context, int resource, int textViewResourceId, User user, List<Court> objects){
        super(context, resource, textViewResourceId, objects);
        this.items = objects;
        this.user = user;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.listview, null);
        }

        final Court item = items.get(position);

        if (item != null) {
            final ImageView image = (ImageView) v.findViewById(R.id.icon);
            TextView courtName = (TextView) v.findViewById(R.id.row);

            if (courtName != null) {
                courtName.setText(item.toString());
            }

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(true){
                        System.out.print(item);
                    }
                    /*
                    if(user.getFavorites().get(position)) {
                        image.setImageResource(R.drawable.star_off);
                        user.getFavorites().set(position, false);
                    }*/
                    else{
                        image.setImageResource(R.drawable.star_on);
                        /***
                        user.getFavorites().set(position, true);
                         ***/
                    }
                }
            });

        }
        return v;
    }
}
