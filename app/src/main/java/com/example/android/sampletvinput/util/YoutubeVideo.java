package com.example.android.sampletvinput.util;

/**
 * Created by Dpswtf on 01/08/2016.
 */
public class YoutubeVideo {

    private String name;
    private String description;
    private String id;
    private String downloadUrl;
    private String thumbnailUrl;
    private long length; // In seconds

    public YoutubeVideo(String name, String description, String id, String downloadUrl, String thumbnailUrl, long length) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.downloadUrl = downloadUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}
