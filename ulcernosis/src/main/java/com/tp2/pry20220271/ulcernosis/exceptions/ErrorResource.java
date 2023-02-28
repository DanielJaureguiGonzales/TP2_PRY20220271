package com.tp2.pry20220271.ulcernosis.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResource {
    private LocalDateTime timestamp;
    private String message;
    private String path;
    private String errorCode;
}
