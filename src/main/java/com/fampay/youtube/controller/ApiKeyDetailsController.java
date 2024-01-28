package com.fampay.youtube.controller;

import com.fampay.youtube.dto.response.BaseResponse;
import com.fampay.youtube.service.ApiKeyDetailsService;
import com.fampay.youtube.utils.ClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/apikey")
@RestController
public class ApiKeyDetailsController {

    private final ApiKeyDetailsService apiKeyDetailsService;

    @Autowired
    public ApiKeyDetailsController(ApiKeyDetailsService apiKeyDetailsService) {
        this.apiKeyDetailsService = apiKeyDetailsService;
    }

    @PostMapping("/addNewKeys")
    public BaseResponse saveNewApiKey(@RequestBody List<String> apiKeyList) {
        BaseResponse response = new BaseResponse();
        try {
            apiKeyDetailsService.saveApiKey(apiKeyList);
            response.setMeta(ClientUtils.getSuccessResponse());
        } catch (Exception e) {
            log.error("Exception occurred while saving new API keys into db", e);
            response.setMeta(ClientUtils.getInternalServerErrorResponse());
        }
        return response;
    }
}
