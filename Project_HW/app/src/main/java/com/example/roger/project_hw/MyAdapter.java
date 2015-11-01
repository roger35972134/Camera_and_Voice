package com.example.roger.project_hw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MyAdapter extends BaseAdapter {
    //ListView
        int[] resId = new int[]{R.drawable.basketball, R.drawable.baseball, R.drawable.football};
        CharSequence[] Balls=null;
        CharSequence[] engName=null;
        private LayoutInflater myInflater;

        public MyAdapter(Context ctxt, CharSequence[] title, CharSequence[] info){
            myInflater = LayoutInflater.from(ctxt);
            this.Balls = title;
            this.engName = info;
        }
        @Override
        public int getCount(){return Balls.length;}
        @Override
        public Object getItem(int position){return Balls[position];}
        @Override
        public long getItemId(int position){return position;}
        @Override
        public View getView(int position,View convertView,ViewGroup parent) {
            ViewTag viewTag;
            if (convertView == null) {
                convertView = myInflater.inflate(R.layout.adapter, null);

                viewTag = new ViewTag(
                        (ImageView) convertView.findViewById(R.id.img),
                        (TextView) convertView.findViewById(R.id.txtName),
                        (TextView) convertView.findViewById(R.id.txtBelow)
                );
                convertView.setTag(viewTag);
            } else {
                viewTag = (ViewTag) convertView.getTag();
            }
            viewTag.icon.setImageResource(resId[position]);
            viewTag.title.setText(Balls[position]);
            viewTag.info.setText(engName[position]);
            return convertView;
        }
        class ViewTag{
            ImageView icon;
            TextView title;
            TextView info;

            public ViewTag(ImageView icon,TextView title,TextView info)
            {
                this.icon=icon;
                this.title=title;
                this.info=info;
            }
        }
    }
