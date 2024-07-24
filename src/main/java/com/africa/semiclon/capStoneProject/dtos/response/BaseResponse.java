package com.africa.semiclon.capStoneProject.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseResponse <T> {
    private int code;
    private boolean success;
    private T data;
}
