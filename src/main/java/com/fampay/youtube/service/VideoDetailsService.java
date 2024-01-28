package com.fampay.youtube.service;

import com.fampay.youtube.dto.SearchResultDTO;
import com.fampay.youtube.dto.SnippetDTO;
import com.fampay.youtube.dto.VideoIdDTO;
import com.fampay.youtube.dto.YoutubeSearchResponseDTO;
import com.fampay.youtube.dto.response.VideoDetailsDTO;
import com.fampay.youtube.dto.response.VideoResponseDTO;
import com.fampay.youtube.model.VideoDetailsModel;
import com.fampay.youtube.repository.ApiKeyDetailsRepository;
import com.fampay.youtube.repository.VideoDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Slf4j
@Service
public class VideoDetailsService {

    private static final String YOUTUBE_API_ENDPOINT = "https://youtube.googleapis.com/youtube/v3/search?part=snippet&q=";
    private static final String PREDEFINED_QUERY = "StockMarket";

    private final VideoDetailsRepository videoDetailsRepository;
    private final ApiKeyDetailsRepository apiKeyDetailsRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public VideoDetailsService(VideoDetailsRepository videoDetailsRepository,
                               ApiKeyDetailsRepository apiKeyDetailsRepository,
                               RestTemplate restTemplate) {
        this.videoDetailsRepository = videoDetailsRepository;
        this.apiKeyDetailsRepository = apiKeyDetailsRepository;
        this.restTemplate = restTemplate;
    }


    /**
     * This service method is scheduled to run every 20 seconds. It calls the YouTube API to fetch video details
     * based on a predefined query, and stores the fetched video details in the database.
     * The YouTube API is queried for videos published within the last hour.
     * In case of any exceptions during the process, they are logged and the method execution is halted.
     */
    @Scheduled(fixedRate = 20000)
    public void getVideoFromYoutube() {
        try {
            long startTime = System.currentTimeMillis();
            String publishAfterDateString = Instant.now().minusSeconds(3600).toString();
            String apiKey = apiKeyDetailsRepository.findFirstApi()
                    .orElseThrow(() -> new Exception("No API key present in table"))
                    .getApiKey();
            String url = YOUTUBE_API_ENDPOINT + PREDEFINED_QUERY + "&order=date&type=video&publishedAfter="
                    + publishAfterDateString + "&key=" + apiKey;
            YoutubeSearchResponseDTO youtubeSearchResponseDTO = restTemplate.getForObject(url, YoutubeSearchResponseDTO.class);
            assert youtubeSearchResponseDTO != null;
            List<SearchResultDTO> items = youtubeSearchResponseDTO.getItems();
            List<VideoDetailsModel> videoDetailsModelList = items.stream()
                    .map(this::getVideoDetails)
                    .toList();
            videoDetailsRepository.saveAll(videoDetailsModelList);
            log.info("Youtube API called and data fetched successfully in {}ms", System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            getNewApiKey(e.getMessage());
            log.error("Error occurred while getting video details from youtube search API", e);
        }
    }

    /**
     * This API will return videos data from db in paginated form
     * @param pageable An object that abstracts pagination information, including the page number and the number of items per page.
     * @param sortParam The parameter by which the results should be sorted.
     * @param sortOrder The order in which the results should be sorted (asc or desc).
     * @return A VideoResponseDTO object containing the paginated video data.
     */
    public VideoResponseDTO getVideoDetailsFromDB(Pageable pageable, String sortParam, String sortOrder) {
        int pageNumber = pageable.getPageNumber() <= 0 ? 1 : pageable.getPageNumber();
        int pageSize = pageable.getPageSize() <= 0 ? 10 : pageable.getPageSize();
        int pageIndex = pageNumber - 1;
        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize, Sort.Direction.DESC, sortParam);
        log.info("Started getting videoDetails from db, with pageIndex: {}, pageSize: {}", pageIndex, pageSize);
        List<VideoDetailsDTO> videoDetailsDTOList = videoDetailsRepository.findAll(pageRequest)
                .map(this::convertToVideoDetailsDTO)
                .toList();
        return VideoResponseDTO.builder()
                .videoDetails(videoDetailsDTOList)
                .totalCount(videoDetailsRepository.count())
                .build();
    }

    /**
     * This method searches for videos in the database that match the provided title and description.
     * It uses the findByTitleOrDescriptionContaining method in the VideoDetailsRepository to perform the search.
     * The search is case-insensitive and can match partial words in the title or description.
     * @param title The title of the video to search for.
     * @param description The description of the video to search for.
     * @return A list of VideoDetailsDTO objects that match the search criteria.
     */
    public List<VideoDetailsDTO> getVideosByTitleAndDescription(String title, String description) {
        log.info("Started searching for videos with title: {}, description : {}", title, description);
        return videoDetailsRepository
                .findByTitleOrDescriptionContaining(title, description)
                .stream()
                .map(this::convertToVideoDetailsDTO)
                .toList();
    }

    private void getNewApiKey(String message) {
        try {
            if (message.startsWith("403 Forbidden")) {
                Long id = apiKeyDetailsRepository.findFirstApi()
                        .orElseThrow(() -> new Exception("No API key present in table"))
                        .getId();
                log.info("Deleting api key with id : {} as this has exceeded quota limit/expired", id);
                apiKeyDetailsRepository.deleteById(id);
            }
        } catch (Exception e) {
            log.error("Exception occurred while deleting current API key");
        }
    }

    private VideoDetailsModel getVideoDetails(SearchResultDTO searchResultDTO) {
        SnippetDTO snippetDTO = searchResultDTO.getSnippet();
        VideoIdDTO videoIdDTO = searchResultDTO.getId();
        VideoDetailsModel videoDetailsModel = new VideoDetailsModel();
        BeanUtils.copyProperties(snippetDTO, videoDetailsModel);
        videoDetailsModel.setVideoId(videoIdDTO.getVideoId());
        videoDetailsModel.setPublishTime(Timestamp.from(Instant.parse(snippetDTO.getPublishTime())));
        videoDetailsModel.setThumbnailUrl(snippetDTO.getThumbnails().getHigh().getUrl());
        return videoDetailsModel;
    }

    private VideoDetailsDTO convertToVideoDetailsDTO(VideoDetailsModel videoDetailsModel) {
        VideoDetailsDTO videoDetailsDTO = new VideoDetailsDTO();
        BeanUtils.copyProperties(videoDetailsModel, videoDetailsDTO);
        return videoDetailsDTO;
    }
}
