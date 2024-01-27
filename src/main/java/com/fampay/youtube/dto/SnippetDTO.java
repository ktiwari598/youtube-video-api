package com.fampay.youtube.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SnippetDTO {
    private String publishedAt;
    private String channelId;
    private String title;
    private String description;
    private ThumbnailsDTO thumbnails;
    private String channelTitle;
    private String liveBroadcastContent;
    private String publishTime;
}
