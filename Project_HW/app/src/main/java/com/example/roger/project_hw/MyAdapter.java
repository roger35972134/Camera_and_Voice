package com.example.roger.project_hw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MyAdapter extends BaseAdapter {
    //ListView
        int[] resId = new int[]{R.drawable.basketball, R.drawable.baseball, R.drawable.football};
        ArrayList<String> filename=null;
        private LayoutInflater myInflater;

        public MyAdapter(Context ctxt,ArrayList<String> title){
            myInflater = LayoutInflater.from(ctxt);
            this.filename = title;
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
            viewTag.icon.setImageResource(resId[position%3]);
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
