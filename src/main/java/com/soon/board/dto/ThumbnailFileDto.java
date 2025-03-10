package com.soon.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThumbnailFileDto {
    private String name;
    private long size;
    private String type;
    private long lastModified;
}

