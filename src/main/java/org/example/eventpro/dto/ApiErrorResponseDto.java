package org.example.eventpro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApiErrorResponseDto {

    private LocalDateTime timeStamp ;
    private int codeStatu;
    private String error ;
    private String message;
    private String path ;
}