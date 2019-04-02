package com.onepointsolution.onemarketplace.model;

public class AppInfo {
    private String tag;
    private String text;
    private int drawable;
    private int color;

    public AppInfo(String tag, String text, int drawable, int color )
    {
        this.tag=tag;
        this.text=text;
        this.drawable=drawable;
        this.color=color;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
