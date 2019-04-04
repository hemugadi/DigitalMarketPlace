package com.onepointsolution.onemarketplace.database.dbmodel;

public class ArticleBookMarks {
    public static final String TABLE_NAME = "article_bookmarks";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PUBLISHED_AT = "publishedAt";
    public static final String COLUMN_SRC_URL = "srcUrl";
    public static final String COLUMN_IMG_URL = "urlToImage";
    public static final String COLUMN_CONTENT = "content";

    private int id;
    private String title;
    private String description;
    private String srcUrl;
    private String urlToImage;
    private String publishedAt;
    private String content;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_PUBLISHED_AT + " TEXT,"
                    + COLUMN_SRC_URL + " TEXT,"
                    + COLUMN_IMG_URL + " TEXT,"
                    + COLUMN_CONTENT + " TEXT"
                    + ")";

    public ArticleBookMarks() {
    }

    public ArticleBookMarks(int id, String title, String description, String publishedAt, String srcUrl,
                                                            String urlToImage, String content) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.publishedAt = publishedAt;
        this.srcUrl = srcUrl;
        this.urlToImage = urlToImage;
        this.content = content;
    }
}
