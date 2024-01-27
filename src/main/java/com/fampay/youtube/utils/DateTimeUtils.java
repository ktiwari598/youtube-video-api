package com.fampay.youtube.utils;

import lombok.experimental.UtilityClass;

import java.time.Instant;

@UtilityClass
public class DateTimeUtils {

    public String addTimeToDateString(String dateString) {
        Instant dateInstant = Instant.parse(dateString);
        Instant newDateInstant = dateInstant.plusSeconds(10);
        return newDateInstant.toString();
    }
}
