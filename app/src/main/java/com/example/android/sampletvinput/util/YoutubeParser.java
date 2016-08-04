package com.example.android.sampletvinput.util;

import android.content.Context;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistItemSnippet;

/**
 * Created by Dpswtf on 01/08/2016.
 */
public class YoutubeParser {

    public enum Quality { HIGHESTMP4, LOWESTMP4 }
    public enum Domain { KEEPVID }


    public List<YoutubeVideo> retrievePlaylist(String playlistId, String apikey) throws IOException {
        ArrayList<YoutubeVideo> videos = new ArrayList<>();

            YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("youtube-retrieveliste").build();

            PlaylistItemListResponse playlistItemResponse = youtube.playlistItems().list("snippet").setKey(apikey).setPlaylistId(playlistId).execute();
            List<PlaylistItem> items = playlistItemResponse.getItems();

            System.out.println("SIZE: "+ items.size());
            for(PlaylistItem item : items){
                PlaylistItemSnippet snippet = item.getSnippet();
                videos.add(retrieveVideo("https://www.youtube.com/watch?v=" + snippet.getResourceId().getVideoId(), Domain.KEEPVID, Quality.HIGHESTMP4));
            }

        return videos;
    }

    /**
     *
     * @return HashMap object containing info about the video
     */
    public YoutubeVideo retrieveVideo(String url, Domain domain, Quality quality) throws IOException {
        String downloadDomain = getDomainURL(domain);
        String qualityFilter = getQualityFilter(domain, quality);
        Document doc = Jsoup.connect(downloadDomain+url).get();

        // Parse doc
        Element eleInfo = doc.select("#info").first();
        String thumbnailUrl = eleInfo.select(".pic").select("img").first().attr("src");
        String name = eleInfo.select(".text").select("a").select("h3").first().text();
        long length = timeInSeconds(eleInfo.select(".text").select(".q").first().text());
        String description = name;
        String id = url.substring(url.indexOf("watch?v=")+8, url.length());
        Element dlEle = null;
        dlEle = doc.select("li:contains("+qualityFilter+")").first();
        if(dlEle == null){
            dlEle = doc.select("li:contains("+getQualityFilter(Domain.KEEPVID, Quality.LOWESTMP4)+")").first();
        }
        String downloadUrl = dlEle.select("a").first().attr("href");

        return new YoutubeVideo(name,description, id, downloadUrl, thumbnailUrl, length);
    }

    private String getDomainURL(Domain domain){
        switch(domain){
            case KEEPVID:
                return "http://keepvid.com/?url=";
            default: break;
        }
        return null;
    }

    private String getQualityFilter(Domain domain, Quality quality){
        switch(domain){
            case KEEPVID:
                switch(quality){
                    case HIGHESTMP4:
                        return "720p";
                    case LOWESTMP4:
                        return "(Max 480p)";
                    default: break;
                }
            default: break;
        }
        return null;
    }

    // HH:MM:SS
    private long timeInSeconds(String time){
        String[] elements = time.split(":");
        long length = 0;
        for(int i = 0; i<elements.length; i++){
            long secs = (long) (Integer.parseInt(elements[elements.length-1-i]) * (Math.pow(60, i)));
            length+=secs;
        }
        return length;
    }
}
