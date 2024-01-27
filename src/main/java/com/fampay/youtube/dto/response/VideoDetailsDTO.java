package com.fampay.youtube.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoDetailsDTO implements Serializable {
    private String title;
    private String description;
    private String thumbnailUrl;
    private Timestamp publishTime;
}
