package com.fampay.youtube.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 2818186066152946823L;
    private Object data;
    private MetaResponse meta;
}
