package com.kb.team2.common.response;

import lombok.Getter;
import lombok.Builder;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ApiExceptionEntity {
    private String errorCode;
    private String errorMessage;

    @Builder
    public ApiExceptionEntity(HttpStatus status, String errorCode, String errorMessage ){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
