package com.shorturlxx;

public class ListDetails {
    String first;
    String second;
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ListDetails(String first, String second, String date) {
        this.first = first;
        this.second = second;
        this.date = date;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }
}
