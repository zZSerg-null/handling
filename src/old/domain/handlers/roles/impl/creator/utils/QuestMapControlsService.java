package ru.old.domain.handlers.roles.impl.creator.utils;

import lombok.RequiredArgsConstructor;
import old.domain.handlers.h1.BasicMessageBuilder;
import org.springframework.stereotype.Component;
import old.domain.dto.UserInfo;
import old.domain.dto.request.RequestData;
import old.domain.dto.response.RespMessageData;
import old.domain.jpa.service.QuestNodeRepositoryService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestMapControlsService {

    private final QuestNodeRepositoryService nodeService;
    private final BasicMessageBuilder messageBuilder;
    private final CreatorMenuSelector menuSelector;
    private final QuestMapService questMapService;

    public List<RespMessageData> editSelectedNode(RequestData request, UserInfo userInfo, Long nodeId) {
        //TODO веруть меню редактирования конкретной ноды
        return defaultMessage(request, "editSelectedNode");
    }

    public List<RespMessageData> moveSelectedNodeUp(RequestData request, UserInfo userInfo, Long nodeId) {
        nodeService.moveNodeUp(nodeId);
        var nodeInfoList = nodeService.getNodeListByQuestId(userInfo.getCreationInfo());
        System.out.println(nodeInfoList);
        System.out.println(questMapService.getQuestMapMenu(nodeInfoList));
        return defaultMessage(request, questMapService.getQuestMapMenu(nodeInfoList));
    }

    public List<RespMessageData> moveSelectedNodeDown(RequestData request, UserInfo userInfo, Long nodeId) {
        nodeService.moveNodeDown(nodeId);
        var nodeInfoList = nodeService.getNodeListByQuestId(userInfo.getCreationInfo());
        System.out.println(nodeInfoList);
        System.out.println(questMapService.getQuestMapMenu(nodeInfoList));
        return defaultMessage(request, questMapService.getQuestMapMenu(nodeInfoList));
    }

    public List<RespMessageData> removeSelectedNode(RequestData request, UserInfo userInfo, Long nodeId) {
        nodeService.deleteNode(userInfo.getCreationInfo());
        var nodeInfoList = nodeService.getNodeListByQuestId(userInfo.getCreationInfo());
        return defaultMessage(request, questMapService.getQuestMapMenu(nodeInfoList));
    }

    private List<RespMessageData> defaultMessage(RequestData request, String messageText) {
        //var message = messageBuilder.buildHTMLPageMessage(request, RespMessageType.MESSAGE);
       // message.setText(messageText);
        //return List.of(message);
        return null;
    }


}
