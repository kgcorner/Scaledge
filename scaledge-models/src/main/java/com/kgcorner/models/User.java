package com.kgcorner.models;

import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Document
public class User extends UserPreview {

    private String aboutDetails;
    private List<UserPreview> followers;
    private List<UserPreview> followings;
    private Map<String, String> settings;
    private List<String> topics;
    private List<ImagePreview> images;
    private Date joinedOn;
    private Date lastSeen;
    private String refreshToken;

    public String getAboutDetails() {
        return aboutDetails;
    }

    public void setAboutDetails(String aboutDetails) {
        this.aboutDetails = aboutDetails;
    }

    public Map<String, String> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, String> settings) {
        this.settings = settings;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public Date getJoinedOn() {
        return joinedOn;
    }

    public void setJoinedOn(Date joinedOn) {
        this.joinedOn = joinedOn;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public List<UserPreview> getFollowers() {
        return followers;
    }

    public void setFollowers(List<UserPreview> followers) {
        this.followers = followers;
    }

    public List<UserPreview> getFollowings() {
        return followings;
    }

    public void setFollowings(List<UserPreview> followings) {
        this.followings = followings;
    }

    public List<ImagePreview> getImages() {
        return images;
    }

    public void setImages(List<ImagePreview> images) {
        this.images = images;
    }

    public UserPreview extractUserPreview() {
        return new UserPreview(id, name);
    }
}
