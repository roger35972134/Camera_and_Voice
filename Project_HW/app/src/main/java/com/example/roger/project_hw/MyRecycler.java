package com.example.roger.project_hw;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyRecycler extends RecyclerView.Adapter<MyRecycler.ViewHolder>{

    Activity activity;
    private OnItemClickLitener mOnItemClickLitener;
    private LayoutInflater mInflater;
    private List<String> mFilepath;
    private List<String> mFilename;
    public MyRecycler(Context context, List<String> filepath,List<String> filename,Activity activity)
    {
        mInflater = LayoutInflater.from(context);
        mFilepath = filepath;
        mFilename=filename;
        this.activity=activity;
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mImg = (ImageView) view.findViewById(R.id.id_index_gallery_item_image);
        viewHolder.mTxt = (TextView) view.findViewById(R.id.id_index_gallery_item_text);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Glide.with(activity).load(mFilepath.get(position)).into(holder.mImg);
        holder.mTxt.setText(mFilename.get(position));

        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mOnItemClickLitener.onItemClick(holder.itemView, position);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return mFilename.size();
    }

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView mImg;
        TextView mTxt;
        public ViewHolder(View arg0)
        {
            super(arg0);
        }
    }
}
