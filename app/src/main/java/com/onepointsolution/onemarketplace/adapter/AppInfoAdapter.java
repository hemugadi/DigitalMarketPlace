package com.onepointsolution.onemarketplace.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.onepointsolution.onemarketplace.R;
import com.onepointsolution.onemarketplace.model.AppInfo;

import java.util.ArrayList;


public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.ViewHolder> {

    ArrayList<AppInfo> mValues;
    Context mContext;

    public AppInfoAdapter(Context context, ArrayList values) {

        mValues = values;
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ImageView imageView;
        public RelativeLayout relativeLayout;
        AppInfo item;

        public ViewHolder(View v) {

            super(v);
            textView = (TextView) v.findViewById(R.id.textView);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.relativeLayout);

        }

        public void setData(AppInfo item) {
            this.item = item;

            textView.setText(item.text);
            imageView.setImageResource(item.drawable);
            relativeLayout.setBackgroundColor(mContext.getResources().getColor(item.color));

        }
    }

    @Override
    public AppInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_app_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setData(mValues.get(i));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(mContext, LaunchAppActivity.class);
                mContext.startActivity(intent);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}

