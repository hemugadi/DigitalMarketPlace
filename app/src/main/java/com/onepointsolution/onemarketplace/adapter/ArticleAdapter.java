package com.onepointsolution.onemarketplace.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.onepointsolution.onemarketplace.R;
import com.onepointsolution.onemarketplace.activity.news.ArticalDetailsActivity;
import com.onepointsolution.onemarketplace.database.DataBaseManager;
import com.onepointsolution.onemarketplace.database.DatabaseHelper;
import com.onepointsolution.onemarketplace.model.Article;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private List<Article> Article;
    private int rowLayout;
    private Context context;
    private DataBaseManager dataBaseManager;


    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        ImageView articleThumbnail;
        TextView articleTitle;
        TextView publishData;
        TextView articleDescription;
        ImageView bookmarkImage;


        public ArticleViewHolder(View v) {
            super(v);
            articleThumbnail = (ImageView) v.findViewById(R.id.thumbnail);
            articleTitle = (TextView) v.findViewById(R.id.title);
            publishData = (TextView) v.findViewById(R.id.publishedAt);
            articleDescription = (TextView) v.findViewById(R.id.description);
            bookmarkImage = (ImageView) v.findViewById(R.id.bookmark_image);

        }

    }

    public ArticleAdapter(List<Article> Article, int rowLayout, Context context) {
        this.Article = Article;
        this.rowLayout = rowLayout;
        this.context = context;
        dataBaseManager = new DataBaseManager(context);
    }

    @Override
    public ArticleAdapter.ArticleViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ArticleViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ArticleViewHolder holder, final int position) {
        Glide.with(context).load(Article.get(position).getUrlToImage())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.articleThumbnail);
        holder.articleTitle.setText(Article.get(position).getTitle());
        holder.publishData.setText(Article.get(position).getPublishedAt());
        holder.articleDescription.setText(Article.get(position).getDescription());
        holder.bookmarkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView image = (ImageView)v;
                dataBaseManager.open();
                if(image.getDrawable().equals(  R.drawable.ic_bookmark_white_24dp)){
                    image.setImageResource(R.drawable.ic_bookmark_black_24dp);
                    dataBaseManager.insertBookmarkedArticle(Article.get(position));
                } else {
                    image.setImageResource(R.drawable.ic_bookmark_black_24dp);
                    dataBaseManager.deleteBookmarkedArticle(Article.get(position).getPublishedAt());
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ArticalDetailsActivity.class);
                intent.putExtra(ArticalDetailsActivity.ARTICLE_DETAIL, Article.get(position));
                /*ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,
                                holder.articleThumbnail, "poster");*/
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Article.size();
    }
}