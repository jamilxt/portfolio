package com.pondit.portfolio.utils;

import java.util.Base64;

public class PostUtils {
    public String getUniqueSlug(String post_title){
        post_title = post_title.toLowerCase().replaceAll("[^a-z0-9\\s]", "").replaceAll("\\s+", "-");
        long timestamp = System.currentTimeMillis();
        String base64Timestamp = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(Long.toString(timestamp).getBytes());
        String slug = post_title + "-" + base64Timestamp;
        return slug;
    }
}
