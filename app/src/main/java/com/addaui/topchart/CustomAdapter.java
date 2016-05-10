package com.addaui.topchart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.MalformedInputException;
import java.util.List;

/**
 * Created by ADDAUI on 5/9/2016.
 */
public class CustomAdapter extends ArrayAdapter<Entry> {


    public CustomAdapter(Context context, int resource) {
        super(context, resource);
    }

    public CustomAdapter(Context context, int resource, List<Entry> entries) {
        super(context, resource, entries);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item, null);
        }

        Entry entry = getItem(position);

        if(v != null){
            TextView name = (TextView) v.findViewById(R.id.name);
            TextView artist = (TextView) v.findViewById(R.id.artist);
            TextView releaseDate = (TextView) v.findViewById(R.id.release_date);
            TextView category = (TextView) v.findViewById(R.id.category);

            name.setText((entry.getName() != null)? entry.getName():"N/A");
            artist.setText((entry.getArtist() != null)?entry.getArtist():"N/A");
            releaseDate.setText((entry.getReleaseDate() != null)? entry.getReleaseDate():"N/A");
            category.setText((entry.getCategory() != null)? entry.getCategory():"N/A");
            DownloadImage downloadImage = new DownloadImage((ImageView) v.findViewById(R.id.img),String.valueOf(position));
            downloadImage.execute(entry.getImgUrl());
        }
        return v;
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap>{
        ImageView bmImage;
        String position;

        public DownloadImage(ImageView bmImage,String position) {
            this.bmImage = bmImage;
            this.position = position;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            String url = params[0];
            Bitmap bitmap = null;
            try{
                Log.d("Dev_ADDAUI.Images", "Downloading Image " + position);
                InputStream is = new  java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(is);
            }catch(Exception e){
                Log.e("Dev_ADDAUI", "ERROR : " + e.getMessage());
                return BitmapFactory.decodeResource(getContext().getResources(),R.drawable.unknown_artist);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Log.d("Dev_ADDAUI.Images", "Setting Image " + position);
            bmImage.setImageBitmap(bitmap);

        }
    }

}
