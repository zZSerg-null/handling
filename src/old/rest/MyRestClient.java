package ru.old.rest;

import old.rest.dto.response.RestResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "questClient", url = "http://localhost:8080/api")
public interface MyRestClient {

    @PostMapping("/send")
    void sendResponse(@RequestBody RestResponseData update);

}
