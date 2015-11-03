package com.example.roger.project_hw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MyAdapter extends BaseAdapter {
    //ListView

        //int[] resId = new int[]{R.drawable.basketball, R.drawable.baseball, R.drawable.football};
        ArrayList<String> filename=null;
        ArrayList<String> Fileid=null;
        private LayoutInflater myInflater;
        public MyAdapter(Context ctxt,ArrayList<String> title,ArrayList<String> files){
            myInflater = LayoutInflater.from(ctxt);
            this.filename = title;
            this.Fileid=files;
        }
        @Override
        public int getCount(){return filename.size();}
        @Override
        public Object getItem(int position){return filename;}
        @Override
        public long getItemId(int position){return position;}
        @Override
        public View getView(int position,View convertView,ViewGroup parent) {
            ViewTag viewTag;
            if (convertView == null) {
                convertView = myInflater.inflate(R.layout.adapter, null);

                viewTag = new ViewTag(
                        (ImageView) convertView.findViewById(R.id.img),
                        (TextView) convertView.findViewById(R.id.txtName)
                );
                convertView.setTag(viewTag);
            } else {
                viewTag = (ViewTag) convertView.getTag();
            }
            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(Fileid.get(position), option);

            //The new size to decode to
            final int NEW_SIZE=100;
            //Now we have image width and height. We should find the correct scale value. (power of 2)

            int width=option.outWidth;
            int height=option.outHeight;
            int scale=1;
            while(true){
                if(width/2<NEW_SIZE || height/2<NEW_SIZE)
                    break;
                width/=2;
                height/=2;
                scale++;

            }

            //Decode again with inSampleSize

            option = new BitmapFactory.Options();

            option.inSampleSize=scale;

            Bitmap bmp=BitmapFactory.decodeFile(Fileid.get(position), option);
            viewTag.icon.setImageBitmap(bmp);
            viewTag.title.setText(filename.get(position));
            return convertView;
        }

    class ViewTag{
            ImageView icon;
            TextView title;

            public ViewTag(ImageView icon,TextView title)
            {
                this.icon=icon;
                this.title=title;
            }
        }
    }
