package ru.old.grps;

import lombok.AllArgsConstructor;
import ru.old.domain.RequestDispatcher;
import ru.old.mapper.GrpcMapper;
import org.springframework.stereotype.Component;
import ru.quest_bot.grpc.GrpcResponseData;
import ru.quest_bot.grpc.GrpcUpdateData;

@Component
@AllArgsConstructor
public class GrpcAdapter {

    private RequestDispatcher requestDispatcher;
    private GrpcMapper mapper;

    public GrpcResponseData getResponse(GrpcUpdateData updateData) {
        System.out.println(updateData);
    //    RequestData requestData = mapper.toServiceDto(updateData);
    //    QuestResponseData responseData = requestDispatcher.processUpdateData(requestData);

        return null;//mapper.toGrpcResponse(responseData);
    }

}
