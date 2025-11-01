package com.scheucher.library.dto.response;

import lombok.Data;

@Data
public class AuthorSummaryResponse {
    private Long id;
    private String fullName;
    private String nationality;
}