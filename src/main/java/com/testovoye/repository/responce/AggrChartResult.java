package com.testovoye.repository.responce;

import java.time.LocalDateTime;

public interface AggrChartResult {
    Integer getEvents();

    Double getCtr();

    Double getEvpm();
    LocalDateTime getIntervalAlias();

}
