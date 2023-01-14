package com.testovoye.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AggrTablePreloadFilterResponseDto {
    private List<Integer> mmDma;
    private List<String> siteId;
}
