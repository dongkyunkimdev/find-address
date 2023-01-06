package com.example.address.controller;

import com.example.address.application.FindRoadNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FindAddressController {
    private final FindRoadNameService findRoadNameService;

    @GetMapping("/address/road-name/{str}")
    public String findRoadName(@PathVariable String str) {
        return findRoadNameService.findRoadName(str);
    }
}
