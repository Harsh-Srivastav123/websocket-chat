package com.websocketchat.websocketchat.constants;

import org.springframework.http.MediaType;

import java.util.HashMap;

public class ApplicationConstants {

    public static final HashMap<String, MediaType> TYPES_MAPPING =new HashMap<>(){{
        put("application/pdf",MediaType.APPLICATION_PDF);
        put("image/png",MediaType.IMAGE_PNG);
        put("image/jpeg",MediaType.IMAGE_JPEG);
        put("audio/mpeg",MediaType.valueOf("audio/mpeg"));
        put("video/mp4",MediaType.valueOf("video/mp4"));
    }};
}
