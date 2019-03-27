package com.onepointsolution.onemarketplace.activity.news;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onepointsolution.onemarketplace.R;
import com.onepointsolution.onemarketplace.model.Article;

public class ArticalDetailsActivity extends AppCompatActivity {
    public static final String ARTICLE_DETAIL = "movie_detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artical_details);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final Article article = getIntent().getParcelableExtra(ARTICLE_DETAIL);
        final String imageUrl =  article.getUrlToImage();
        Glide.with(this).load(imageUrl).into( (ImageView) findViewById(R.id.thumbnail));

        // set title for the appbar
        /*CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.main_collapsing);
        collapsingToolbarLayout.setTitle(detail.Title);*/

        ((TextView) findViewById(R.id.title)).setText(article.getTitle());
        ((TextView) findViewById(R.id.publishedAt)).setText(article.getPublishedAt());
        ((TextView) findViewById(R.id.description)).setText(article.getContent());
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
