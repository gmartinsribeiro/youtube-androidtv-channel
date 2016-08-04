package com.aptoide.pt.aptoidechannel.util;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Dpswtf on 02/08/2016.
 */
public class XmlTVDescriptor {

    private static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";

    public static void generateTvXmlFile(String name, List<YoutubeVideo> videos, Context context){

        File file = new File(context.getFilesDir(), name);

        FileOutputStream outputStream;

        String init = "<?xml version=\"1.0\" encoding=\"utf-8\"?><!DOCTYPE tv SYSTEM \"xmltv.dtd\"><tv><channel id=\"com.aptoide.android.tvinput.2-1\" repeat-programs=\"true\">" +
                "<display-name>Aptoide TV</display-name><display-number>2-1</display-number><icon src=\"http://cdn1.aptoide.com/includes/themes/default/images/installer/logo.png\"/></channel>";
        String end = "</tv>";


        try {
            outputStream = new FileOutputStream(file, true);
            outputStream.write(init.getBytes());

            String cal = getDate();
            long startTime = 0;
            for(YoutubeVideo video : videos){
                long endTime = startTime + video.getLength();
                String sTime = getDate() + getTime(startTime);
                String eTime = getDate() + getTime(endTime);
                String downloadUrl = video.getDownloadUrl().substring(0, video.getDownloadUrl().indexOf("&title="));
                downloadUrl = downloadUrl.replaceAll("&", "&amp;");
                String videoinfo = "<programme channel=\"com.aptoide.android.tvinput.2-1\" start=\""+ sTime +" +0000\" stop=\""+ eTime+" +0000\" video-src=\""+ downloadUrl+ "\" video-type=\"HTTP_PROGRESSIVE\">=";
                videoinfo += "<title>\""+ video.getName() +"\"</title>";
                videoinfo += "<desc>\""+ video.getDescription() +"\"</desc>";
                videoinfo += "<icon src=\""+ video.getThumbnailUrl() +"\"/></programme>";
                outputStream.write(videoinfo.getBytes());
                startTime = endTime;
            }

            outputStream.write(end.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.US);
        return format.format(calendar.getTime());
    }

    private static String getTime(long seconds){
        int hours = (int) (seconds /3600);
        int minutes = (int) ((seconds % 3600)/60);
        int secs = (int) (seconds % 60);

        String formatted = String.format(Locale.US, "%02d%02d%02d", hours, minutes, secs);
        return formatted;
    }
}
