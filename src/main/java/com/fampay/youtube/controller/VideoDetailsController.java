package com.fampay.youtube.controller;

import com.fampay.youtube.dto.response.BaseResponse;
import com.fampay.youtube.dto.response.VideoResponseDTO;
import com.fampay.youtube.service.VideoDetailsService;
import com.fampay.youtube.utils.ClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/youtube")
public class VideoDetailsController {

    private final VideoDetailsService videoDetailsService;

    @Autowired
    public VideoDetailsController(VideoDetailsService videoDetailsService) {
        this.videoDetailsService = videoDetailsService;
    }

    @GetMapping("/listVideos")
    public BaseResponse getPaginatedVideoList(Pageable pageable,
                                              @RequestParam(value = "sortParam", required = false, defaultValue = "publishTime") String sortParam,
                                              @RequestParam(value = "sortOrder", required = false, defaultValue = "desc") String sortOrder) {
        BaseResponse response = new BaseResponse();
        try {
            VideoResponseDTO videoResponseDTO = videoDetailsService.getVideoDetailsFromDB(pageable, sortParam, sortOrder);
            response.setData(videoResponseDTO);
            response.setMeta(ClientUtils.getSuccessResponse());
        } catch (Exception e) {
            log.error("Exception occurred while getting paginated video details from db", e);
            response.setMeta(ClientUtils.getInternalServerErrorResponse());
        }
        return response;
    }

    @GetMapping("/searchVideos")
    public BaseResponse getSearchedVideos(@RequestParam(value = "title") String title,
                                          @RequestParam(value = "description") String description) {
        BaseResponse response = new BaseResponse();
        try {
            response.setData(videoDetailsService.getVideosByTitleAndDescription(title, description));
            response.setMeta(ClientUtils.getSuccessResponse());
        } catch (Exception e) {
            log.error("Exception occurred while getting searched video details from db", e);
            response.setMeta(ClientUtils.getInternalServerErrorResponse());
        }
        return response;
    }


    /**
     * This endpoint triggers the service method to fetch video details from the YouTube API.
     * It is primarily used for testing purposes to manually initiate the fetching process.
     * In case of any exceptions during the process, they are logged and an internal server error response is returned.
     * @return A BaseResponse object containing the status of the operation.
     */
    @GetMapping("/trigger")
    public BaseResponse trigger() {
        BaseResponse response = new BaseResponse();
        try {
            videoDetailsService.getVideoFromYoutube();
            response.setMeta(ClientUtils.getSuccessResponse());
        } catch (Exception e) {
            log.error("Exception occurred while getting searched video details from db", e);
            response.setMeta(ClientUtils.getInternalServerErrorResponse());
        }
        return response;
    }
}
