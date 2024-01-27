package com.fampay.youtube.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    FP_SC_200100("FP_500100", "Success");

    private final String code;
    private final String message;

    @Override
    public String toString() {
        return code;
    }
}