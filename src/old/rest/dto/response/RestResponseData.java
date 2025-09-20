package ru.old.rest.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class RestResponseData {
    private List<RestResponseMessageData> messageDataList;
}
