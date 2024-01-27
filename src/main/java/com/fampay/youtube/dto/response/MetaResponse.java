package com.fampay.youtube.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetaResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -8571479354042000082L;
    private String code;
    private String message;
}
