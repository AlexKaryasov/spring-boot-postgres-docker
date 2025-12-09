package com.example.demo.controller;

import com.example.demo.service.ClickHouseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final ClickHouseService service;

    public AnalyticsController(ClickHouseService service) {
        this.service = service;
    }

    @PostMapping("/event")
    public void logEvent(
            @RequestParam String source,
            @RequestParam String message
    ) {
        service.insertEvent(source, message);
    }

    @GetMapping("/events")
    public List<String> getEvents() {
        return service.getLatestEvents();
    }
}
