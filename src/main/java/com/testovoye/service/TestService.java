package com.testovoye.service;

import com.testovoye.dto.AggrChartResponseDto;
import com.testovoye.dto.AggrTablePreloadFilterResponseDto;
import com.testovoye.dto.AggrTableResponseDto;
import com.testovoye.mapper.LogViewToAggrChartResponseMapper;
import com.testovoye.mapper.LogViewToAggrTableResponseMapper;
import com.testovoye.repository.LogViewRepository;
import com.testovoye.repository.responce.AggrTableResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestService implements TestServiceI {

    private final LogViewRepository repository;
    private final LogViewToAggrTableResponseMapper tableResponseMapper;
    private final LogViewToAggrChartResponseMapper chartResponseMapper;

    public TestService(LogViewRepository repository, LogViewToAggrTableResponseMapper tableResponseMapper, LogViewToAggrChartResponseMapper chartResponseMapper) {
        this.repository = repository;
        this.tableResponseMapper = tableResponseMapper;
        this.chartResponseMapper = chartResponseMapper;
    }

    @Override
    public Page<AggrTableResponseDto> aggrTable(Pageable pageable) {
        final Page<AggrTableResult> aggrTable = repository.aggrTable(pageable);
        final List<AggrTableResponseDto> aggrTableResponseDtos = tableResponseMapper.logViewToAggrTableResponse(aggrTable.getContent());
        return new PageImpl<>(aggrTableResponseDtos, pageable, aggrTable.getTotalElements());
    }

    @Override
    public AggrTablePreloadFilterResponseDto preload() {
        return new AggrTablePreloadFilterResponseDto(this.repository.preloadMMDmas(), this.repository.preloadSiteIds());
    }

    @Override
    public List<AggrChartResponseDto> aggrChart(String siteId) {
        return chartResponseMapper.logViewToAggrTableResponse(repository.aggrChart(siteId));
    }
}
