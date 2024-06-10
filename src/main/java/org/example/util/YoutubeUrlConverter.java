package org.example.util;

public class YoutubeUrlConverter {

    public static String convertToEmbedUrl(String url){

        String videoId = url.split("v=")[1];

        int ampersandPosition = videoId.indexOf('&');
        if (ampersandPosition != -1) {
            videoId = videoId.substring(0, ampersandPosition);
        }

        return "https://www.youtube.com/embed/" + videoId;
    }
}
