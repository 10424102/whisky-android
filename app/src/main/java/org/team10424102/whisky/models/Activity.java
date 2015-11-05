package org.team10424102.whisky.models;

import java.util.Date;
import java.util.List;

/**
 * Created by yy on 10/30/15.
 */
public class Activity {
    private String title;
    private Date startTime;
    private Date endTime;
    private Date registrationDeadline;
    private String content;
    private List<LazyImage> photos;
    private String games;
    private String type;
    private LazyImage cover;
    private UserGroup group;
    private String location;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(Date registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<LazyImage> getPhotos() {
        return photos;
    }

    public void setPhotos(List<LazyImage> photos) {
        this.photos = photos;
    }

    public String getGames() {
        return games;
    }

    public void setGames(String games) {
        this.games = games;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LazyImage getCover() {
        return cover;
    }

    public void setCover(LazyImage cover) {
        this.cover = cover;
    }

    public UserGroup getGroup() {
        return group;
    }

    public void setGroup(UserGroup group) {
        this.group = group;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public enum Type {
        SCHOOL, FRIENDS, FOLLOWINGS, RECOMMENDATIONS
    }
}
