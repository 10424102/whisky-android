package org.team10424102.whisky.models;

import java.util.Date;

public class Message {
    private int logo;
    private String title;
    private String content;
    private Date time;

    public Message(int logo, String title, String content, Date time) {
        this.logo = logo;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
