package com.fampay.youtube.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchResultDTO {
    private String kind;
    private String etag;
    private VideoIdDTO id;
    private SnippetDTO snippet;
}
