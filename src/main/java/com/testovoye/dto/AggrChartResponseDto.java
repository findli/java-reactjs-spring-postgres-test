package com.testovoye.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AggrChartResponseDto {
    private Integer events;
    private Double ctr;
    private Double evpm;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime intervalAlias;
}
