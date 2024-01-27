package com.fampay.youtube.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    FP_EC_500100("FP_EC_500100", "Internal Server Error"),
    FP_EC_404101("FP_EC_404101", "User Not Found");

    private final String code;
    private final String message;

    @Override
    public String toString() {
        return code;
    }
}