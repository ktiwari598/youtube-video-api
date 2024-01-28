package com.fampay.youtube.service;

import com.fampay.youtube.model.ApiKeyDetailsModel;
import com.fampay.youtube.repository.ApiKeyDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ApiKeyDetailsService {
    private final ApiKeyDetailsRepository apiKeyDetailsRepository;

    @Autowired
    public ApiKeyDetailsService(ApiKeyDetailsRepository apiKeyDetailsRepository) {
        this.apiKeyDetailsRepository = apiKeyDetailsRepository;
    }

    public void saveApiKey(String apiKey) {
        ApiKeyDetailsModel apiKeyDetailsModel = ApiKeyDetailsModel.builder()
                .apiKey(apiKey)
                .build();
        apiKeyDetailsRepository.save(apiKeyDetailsModel);
    }

    public void saveApiKey(List<String> apiKeyList) {
        List<ApiKeyDetailsModel> apiKeyDetailsModelList = apiKeyList.stream()
                .map(apiKey -> ApiKeyDetailsModel.builder().apiKey(apiKey).build())
                .toList();
        apiKeyDetailsRepository.saveAll(apiKeyDetailsModelList);
    }

    public void getFirstApiKey(String apiKey) {
        apiKeyDetailsRepository.findFirstApi();
    }
}
