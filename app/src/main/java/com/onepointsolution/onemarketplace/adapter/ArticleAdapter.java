package com.onepointsolution.onemarketplace.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.onepointsolution.onemarketplace.R;
import com.onepointsolution.onemarketplace.model.Article;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private List<Article> Article;
    private int rowLayout;
    private Context context;


    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        ImageView articleThumbnail;
        TextView articleTitle;
        TextView publishData;
        TextView articleDescription;


        public ArticleViewHolder(View v) {
            super(v);
            articleThumbnail = (ImageView) v.findViewById(R.id.thumbnail);
            articleTitle = (TextView) v.findViewById(R.id.title);
            publishData = (TextView) v.findViewById(R.id.publishedAt);
            articleDescription = (TextView) v.findViewById(R.id.description);
        }
    }

    public ArticleAdapter(List<Article> Article, int rowLayout, Context context) {
        this.Article = Article;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ArticleAdapter.ArticleViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ArticleViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ArticleViewHolder holder, final int position) {
        Glide.with(context).load(Article.get(position).getUrlToImage())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.articleThumbnail);
        holder.articleTitle.setText(Article.get(position).getTitle());
        holder.publishData.setText(Article.get(position).getPublishedAt());
        holder.articleDescription.setText(Article.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return Article.size();
    }
}