package org.team10424102.whisky.models;

import java.util.Date;

public class Post {
    private String content;
    private String device;
    private Date sendTime;
    private User sender;
    private PostExtension ext;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PostExtension getExt() {
        return ext;
    }

    public void setExt(PostExtension ext) {
        this.ext = ext;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}
