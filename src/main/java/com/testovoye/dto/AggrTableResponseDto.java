package com.testovoye.dto;

import lombok.Data;

@Data
public class AggrTableResponseDto {
    private String siteId;
    private Integer views;
    private Double ctr;
    private Double evpm;
}
