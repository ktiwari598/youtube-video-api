package com.fampay.youtube.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoResponseDTO implements Serializable {
    private Long totalCount;
    private List<VideoDetailsDTO> videoDetails;
}
