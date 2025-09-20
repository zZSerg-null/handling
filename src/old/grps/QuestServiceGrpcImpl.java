package ru.old.grps;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.quest_bot.grpc.GrpcResponseData;
import ru.quest_bot.grpc.GrpcUpdateData;
import ru.quest_bot.grpc.QuestServiceGrpc;


@GrpcService
@RequiredArgsConstructor
public class QuestServiceGrpcImpl extends QuestServiceGrpc.QuestServiceImplBase {

    private final GrpcAdapter grpcAdapter;

    @Override
    public void processUpdate(GrpcUpdateData request, StreamObserver<GrpcResponseData> responseObserver) {
        GrpcResponseData responseData = grpcAdapter.getResponse(request);
        responseObserver.onNext(responseData);
        responseObserver.onCompleted();
    }

}
