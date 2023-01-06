package com.example.address.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "open-api", url = "https://business.juso.go.kr")
public interface OpenAPIClient {
    @GetMapping("/addrlink/addrLinkApi.do")
    public String findAddress(@RequestParam(value = "confmKey") String confmKey,
                              @RequestParam(value = "keyword") String keyword,
                              @RequestParam(value = "resultType") String resultType);
}
