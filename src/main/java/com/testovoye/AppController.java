package com.testovoye;

import com.testovoye.dto.AggrChartResponseDto;
import com.testovoye.dto.AggrRequestParams;
import com.testovoye.dto.AggrTablePreloadFilterResponseDto;
import com.testovoye.dto.AggrTableResponseDto;
import com.testovoye.service.TestServiceI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AppController {

    private final TestServiceI testServiceI;

    public AppController(TestServiceI testServiceI) {
        this.testServiceI = testServiceI;
    }

    @RequestMapping(value = "/preload", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity<AggrTablePreloadFilterResponseDto> preload() {
        return ResponseEntity.ok(testServiceI.preload());
    }

    @PostMapping(value = "/aggr-chart")
    @ResponseBody
    ResponseEntity<List<AggrChartResponseDto>> getAggrChart(@Valid @RequestBody AggrRequestParams params) {
        return ResponseEntity.ok(testServiceI.aggrChart(params.getSiteId()));
    }

    @PostMapping(value = "/aggr-table")
    @ResponseBody
    ResponseEntity<Page<AggrTableResponseDto>> getAggrTable(@Valid @RequestBody AggrRequestParams params) {
        return ResponseEntity.ok(testServiceI.aggrTable(PageRequest.of(params.getPage(), params.getSize())));
    }

}
