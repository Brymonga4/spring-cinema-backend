package org.example.util;

public class YoutubeUrlConverter {

    public static String convertToEmbedUrl(String url) {
        if (url.contains("embed")) {
            return url;
        }

        if (url.contains("watch?v=")) {

            String videoId = url.split("v=")[1];
            int ampersandPosition = videoId.indexOf('&');
            if (ampersandPosition != -1) {
                videoId = videoId.substring(0, ampersandPosition);
            }
            return "https://www.youtube.com/embed/" + videoId;
        }

        if (url.contains("youtu.be")) {

            String videoId = url.substring(url.lastIndexOf("/") + 1);
            int questionMarkPosition = videoId.indexOf('?');
            if (questionMarkPosition != -1) {
                videoId = videoId.substring(0, questionMarkPosition);
            }
            return "https://www.youtube.com/embed/" + videoId;
        }

        throw new IllegalArgumentException("URL de YouTube no válida: " + url);
    }
}
