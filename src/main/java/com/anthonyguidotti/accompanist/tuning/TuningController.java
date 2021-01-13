package com.anthonyguidotti.accompanist.tuning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class TuningController {
    private final TuningService tuningService;

    public TuningController(
            TuningService tuningService
    ) {
        this.tuningService = tuningService;
    }

    @GetMapping(
            value = "/tunings/{name}",
            produces = "application/json"
    )
    public Tuning getTuning(
            @PathVariable String name,
            HttpServletResponse httpServletResponse
    ) {
        Tuning tuning = tuningService.get(name);
        if (tuning != null) {
            return tuning;
        } else {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }
}
