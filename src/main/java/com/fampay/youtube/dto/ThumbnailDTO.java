package com.fampay.youtube.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThumbnailDTO {
    private String url;
    private int width;
    private int height;
}
