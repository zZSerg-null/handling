package ru.old.rest;

import lombok.RequiredArgsConstructor;
import old.rest.dto.request.RestUpdateData;
import old.rest.dto.response.RestResponseData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class Controller {

    private final RestAdapter adapter;

    @PostMapping("/syncRestQuestRequest")
    public RestResponseData sendSyncMessage(@RequestBody RestUpdateData update){
        return adapter.getResponse(update);
    }

    @PostMapping("/asyncRestQuestRequest")
    public void sendAsyncMessage(@RequestBody RestUpdateData update){
        adapter.proceedUpdate(update);
    }

}
