package com.example.trafalgar.myplaylist;

/**
 * Created by trafalgar on 13/03/18.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trafalgar.myplaylist.Videos;
import com.squareup.picasso.Picasso;
import com.example.trafalgar.myplaylist.R;

import java.util.ArrayList;


public class Adapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Videos> videosList;

    public Adapter(Activity activity, ArrayList<Videos> newsList) {
        this.activity = activity;
        this.videosList = newsList;
    }

    @Override
    public int getCount() {
        return videosList.size();
    }

    @Override
    public Object getItem(int position) {
        return videosList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (convertView == null){
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.videos_item, null);
        }

        Videos video = videosList.get(position);

        TextView title = v.findViewById(R.id.textViewTitle);
        title.setText(video.getTitulo());

        TextView author = v.findViewById(R.id.textViewUser);
        author.setText(video.getUsuario());

        TextView views = v.findViewById(R.id.textViewViews);
        views.setText(video.getVisitas());

        ImageView videosPic = v.findViewById(R.id.imageVideoPic);

        Picasso.with(v.getContext()).load(video.getImage()).into(videosPic);

        return v;
    }
}
