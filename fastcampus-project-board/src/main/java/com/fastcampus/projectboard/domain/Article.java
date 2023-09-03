package com.fastcampus.projectboard.domain;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public class Article {
    private Long id;
    private String title;
    private String content;
    private String hashtag;

    private String createdBy;
    private String modifiedBy;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
