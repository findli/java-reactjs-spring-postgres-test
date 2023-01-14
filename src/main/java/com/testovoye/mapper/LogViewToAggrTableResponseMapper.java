package com.testovoye.mapper;

import com.testovoye.dto.AggrTableResponseDto;
import com.testovoye.repository.responce.AggrTableResult;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LogViewToAggrTableResponseMapper {

    AggrTableResponseDto logViewToAggrTableResponse(AggrTableResult source);

    List<AggrTableResponseDto> logViewToAggrTableResponse(List<AggrTableResult> source);
}
