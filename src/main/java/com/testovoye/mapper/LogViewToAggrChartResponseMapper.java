package com.testovoye.mapper;

import com.testovoye.dto.AggrChartResponseDto;
import com.testovoye.repository.responce.AggrChartResult;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LogViewToAggrChartResponseMapper {

    AggrChartResponseDto logViewToAggrTableResponse(AggrChartResult source);

    List<AggrChartResponseDto> logViewToAggrTableResponse(List<AggrChartResult> source);
}
