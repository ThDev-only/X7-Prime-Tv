package com.ultra.vision.tv.object;

public class TvCategory {
    private String categoryId;
    private String categoryName;
    private int parentId;

    public TvCategory(String categoryId, String categoryName, int parentId) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.parentId = parentId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getParentId() {
        return parentId;
    }
}