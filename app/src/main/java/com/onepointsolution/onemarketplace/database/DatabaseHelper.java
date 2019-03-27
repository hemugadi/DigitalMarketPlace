package com.onepointsolution.onemarketplace.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.onepointsolution.onemarketplace.database.dbmodel.ArticleBookMarks;
import com.onepointsolution.onemarketplace.model.Article;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bookmarks_db";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ArticleBookMarks.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ArticleBookMarks.TABLE_NAME);
        onCreate(db);
    }

    public long insertBookmarkedArticle(Article article){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ArticleBookMarks.COLUMN_TITLE,article.getTitle());
        contentValues.put(ArticleBookMarks.COLUMN_DESCRIPTION,article.getDescription());
        contentValues.put(ArticleBookMarks.COLUMN_PUBLISHED_AT,article.getPublishedAt());
        contentValues.put(ArticleBookMarks.COLUMN_SRC_URL,article.getUrl());
        contentValues.put(ArticleBookMarks.COLUMN_IMG_URL,article.getUrlToImage());
        contentValues.put(ArticleBookMarks.COLUMN_CONTENT,article.getContent());

        long id = db.insert(ArticleBookMarks.TABLE_NAME,null,contentValues);
        return id;
    }

    public void deleteBookmarkedArticle(String publishedAt){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ArticleBookMarks.TABLE_NAME,ArticleBookMarks.COLUMN_PUBLISHED_AT+" =?",
                            new String[]{publishedAt});
        db.close();
    }

    public List<Article> getBookmarkedArticles(){
        List<Article> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM "+ArticleBookMarks.TABLE_NAME+" ORDER BY "+
                ArticleBookMarks.COLUMN_ID+" DESC";

        Cursor cursor = db.rawQuery(selectQuery,null);

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
        db.close();
        return result;
    }
}
