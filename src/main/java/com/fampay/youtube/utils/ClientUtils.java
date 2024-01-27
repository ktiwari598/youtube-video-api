package com.fampay.youtube.utils;

import com.fampay.youtube.dto.response.MetaResponse;
import com.fampay.youtube.enums.ErrorCode;
import com.fampay.youtube.enums.SuccessCode;
import org.slf4j.MDC;

public class ClientUtils {
    public static MetaResponse getSuccessResponse() {
        return new MetaResponse(SuccessCode.FP_SC_200100.getCode(), SuccessCode.FP_SC_200100.getMessage());
    }

    public static MetaResponse getInternalServerErrorResponse() {
        return new MetaResponse(ErrorCode.FP_EC_500100.getCode(), ErrorCode.FP_EC_500100.getMessage());
    }
}
