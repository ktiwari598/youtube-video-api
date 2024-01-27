package com.fampay.youtube.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YoutubeSearchResponseDTO {

    private String kind;
    private String etag;
    private String nextPageToken;
    private String regionCode;
    private PageInfoDTO pageInfo;
    private List<SearchResultDTO> items;
}
