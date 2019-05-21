package com.onepointsolution.onemarketplace.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.onepointsolution.onemarketplace.database.dbmodel.ArticleBookMarks;
import com.onepointsolution.onemarketplace.model.Article;

import java.util.ArrayList;
import java.util.List;

public class DataBaseManager {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private DatabaseHelper databaseHelper;

    public DataBaseManager(Context context) {
        this.context = context;
    }

    public DataBaseManager open(){
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        sqLiteDatabase.close();
    }

    public long insertBookmarkedArticle(Article article){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ArticleBookMarks.COLUMN_TITLE,article.getTitle());
        contentValues.put(ArticleBookMarks.COLUMN_DESCRIPTION,article.getDescription());
        contentValues.put(ArticleBookMarks.COLUMN_PUBLISHED_AT,article.getPublishedAt());
        contentValues.put(ArticleBookMarks.COLUMN_SRC_URL,article.getUrl());
        contentValues.put(ArticleBookMarks.COLUMN_IMG_URL,article.getUrlToImage());
        contentValues.put(ArticleBookMarks.COLUMN_CONTENT,article.getContent());

        long id = sqLiteDatabase.insert(ArticleBookMarks.TABLE_NAME,null,contentValues);
        return id;
    }

    public void deleteBookmarkedArticle(String publishedAt){
        sqLiteDatabase.delete(ArticleBookMarks.TABLE_NAME,ArticleBookMarks.COLUMN_PUBLISHED_AT+" =?",
                new String[]{publishedAt});
        sqLiteDatabase.close();
    }

    public List<Article> getBookmarkedArticles(){
        List<Article> result = new ArrayList<>();

        String selectQuery = "SELECT * FROM "+ArticleBookMarks.TABLE_NAME+" ORDER BY "+
                ArticleBookMarks.COLUMN_ID+" DESC";

        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);

        if (cursor != null && cursor.moveToFirst()){
            do {
                Article article = new Article();
                article.setTitle(cursor.getString(cursor.getColumnIndex(ArticleBookMarks.COLUMN_TITLE)));
                article.setDescription(cursor.getString(cursor.getColumnIndex(ArticleBookMarks.COLUMN_DESCRIPTION)));
                article.setPublishedAt(cursor.getString(cursor.getColumnIndex(ArticleBookMarks.COLUMN_PUBLISHED_AT)));
                article.setUrl(cursor.getString(cursor.getColumnIndex(ArticleBookMarks.COLUMN_SRC_URL)));
                article.setUrlToImage(cursor.getString(cursor.getColumnIndex(ArticleBookMarks.COLUMN_IMG_URL)));
                article.setContent(cursor.getString(cursor.getColumnIndex(ArticleBookMarks.COLUMN_CONTENT)));

                result.add(article);

            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return result;
    }
}
