package com.wipro.androidproficiencyexercise.pojo;

public class Row {

    private String title;
    private String description;
    private Object imageHref;

    /**
     * No args constructor for use in serialization
     */
    public Row() {
    }

    /**
     * @param title
     * @param description
     * @param imageHref
     */
    public Row(String title, String description, Object imageHref) {
        super();
        this.title = title;
        this.description = description;
        this.imageHref = imageHref;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getImageHref() {
        return imageHref;
    }

    public void setImageHref(Object imageHref) {
        this.imageHref = imageHref;
    }

}