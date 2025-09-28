package ru.old.domain.handlers.roles.impl.creator.utils;

import org.springframework.stereotype.Component;
import old.domain.dto.BasicFileInfo;
import old.domain.dto.EditNodeInfo;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestMapService {

    //TODO хранить имя бота в базе и держать в памяти
    private final static String BOT_NAME = "BrainBuster_bot";
    private final static String NO_CONTENT = "<i>- не заполнено -</i>";

    public String getQuestMapMenu(List<EditNodeInfo> infoList) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Список этапов:</b>\n\n")
                .append(" ")
                .append(" ");

        for (int i = 0; i <infoList.size(); i++) {
            EditNodeInfo nodeInfo = infoList.get(i);

            stringBuilder
                    .append(getNodeEditMarker(nodeInfo)).append(" ")// стоит ли восклицательный знак и примечание что нода не дописана

                    //1) Сообщение [Редактировать]
                    .append("<b>").append(i).append(") </b>")
                    .append(nodeInfo.getType().getDisplayName())
                    .append(getNodeEditButton(nodeInfo)).append("\n")

                    .append(getNodeContent(nodeInfo)).append("\n") // текст ноды, названия файлов
                    .append(getControls(nodeInfo.getNodeId())) // поднять, опустить, удалить
                    .append(getNodeSpace()); // поднять, опустить, удалить
        }

        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }


    private String getNodeEditMarker(EditNodeInfo nodeInfo) {
        String DONE = "✅";
        String EMPTY = "❗";
        return switch (nodeInfo.getType()) {
            case MESSAGE -> nodeInfo.getMainText() != null || nodeInfo.getMediaList() != null ? DONE : EMPTY;
            case POLL -> nodeInfo.getMainText() != null && nodeInfo.isPollAnswersPresent() ? DONE : EMPTY;
            case GEO -> nodeInfo.isGeoPointPresent() ? DONE : EMPTY;
            case MEDIA -> nodeInfo.getMediaList() != null && !nodeInfo.getMediaList().isEmpty()? DONE : EMPTY;
        };
    }

    private String getNodeEditButton(EditNodeInfo nodeInfo) {
        return "<a href=\"https://t.me/" + BOT_NAME + "?start=editCommand_editNode_" + nodeInfo.getNodeId() + "\">[редактировать]</a>";
    }

    private String getNodeContent(EditNodeInfo nodeInfo) {
        return switch (nodeInfo.getType()) {
            case MESSAGE -> getMessageContent(nodeInfo);
            case POLL -> nodeInfo.getMainText() != null? "Вопрос: <i>"+nodeInfo.getMainText()+"</i>": "Вопрос: "+NO_CONTENT;
            case GEO -> nodeInfo.isGeoPointPresent() ? "Точка на карте: <i>установлена</i>" :"Точка на карте: "+ NO_CONTENT;
            case MEDIA -> getMediaName(nodeInfo.getMediaList());
        };
    }

    private String getMessageContent(EditNodeInfo nodeInfo) {
        String result;
        if (nodeInfo.getMainText() != null) {
            result = "Текст: <i>"+ nodeInfo.getMainText()+"</i>";
            return result;
        } else if (nodeInfo.getMediaList() != null && !nodeInfo.getMediaList().isEmpty()) {
            result = "Файл(ы): <i>"+getMediaName(nodeInfo.getMediaList())+"</i>";
        } else result = "Текст: "+ NO_CONTENT;

        return result;
    }

    private String getMediaName(List<BasicFileInfo> mediaList) {
        return mediaList.stream()
                .map(file -> file.getFileName() != null ?
                        file.getFileName()+": "+getFileSize(file.getFileSize())+" мб\n" :
                        file.getMimeType()+": "+getFileSize(file.getFileSize())+" мб\n"
                )
                .collect(Collectors.joining());

    }

    private double getFileSize(Long size){
        return size == null ? 0.0 : Math.round((size / (1024.0 * 1024.0)) * 100) / 100.0;
    };

    private String getControls(Long nodeId) {
        return " 🔼 <a href=\"https://t.me/"+BOT_NAME+"?start=editCommand_moveUp_"+nodeId+"\">Поднять</a>,   🔽 <a href=\"https://t.me/"+BOT_NAME+"?start=editCommand_moveDown_"+nodeId+"\">Опустить</a>\n"+
                "❌ <a href=\"https://t.me/"+BOT_NAME+"?start=editCommand_delete_"+nodeId+"\">Удалить</a>";
    }

    private String getNodeSpace() {
        return "\n--------------------\n\n";
    }
}
