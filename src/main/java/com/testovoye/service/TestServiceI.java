package com.testovoye.service;

import com.testovoye.dto.AggrChartResponseDto;
import com.testovoye.dto.AggrTablePreloadFilterResponseDto;
import com.testovoye.dto.AggrTableResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestServiceI {
    Page<AggrTableResponseDto> aggrTable(Pageable pageable);

    List<AggrChartResponseDto> aggrChart(String siteId);

    AggrTablePreloadFilterResponseDto preload();
}
