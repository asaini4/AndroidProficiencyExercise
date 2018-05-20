package com.wipro.androidproficiencyexercise.pojo;

import java.util.List;

public class WSResponse {

    private String title;

    private List<Row> rows = null;

    public WSResponse() {
    }

    /**
     * @param title
     * @param rows
     */
    public WSResponse(String title, List<Row> rows) {
        super();
        this.title = title;
        this.rows = rows;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

}