package ru.old.domain.handlers.roles.impl.creator.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import old.domain.dto.MenuNodeInfo;
import old.domain.dto.response.keyboard.RespKeyboard;
import old.domain.enums.CreationStage;
import old.domain.handlers.h1.keyboard.CreatorKeyboardsKeeper;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CreatorMenuSelector {

    private final CreatorKeyboardsKeeper creatorKeyboardsKeeper;

    public RespKeyboard getMenuByStage(CreationStage stage, MenuNodeInfo info) {
        return switch (stage) {
            case NEW_QUEST_AWATING_NAME_CONFIRMATION -> creatorKeyboardsKeeper.getQuestCreationConfirmNameKeyboard();
            case STOP_CREATING -> creatorKeyboardsKeeper.stopCreationKeyboard();

            case UNSAVED_NODE_DATA -> creatorKeyboardsKeeper.dropUnsavedDataKeyboard();

            case MAIN_MENU_NODE_TYPE_SELECTION -> creatorKeyboardsKeeper.getNodeTypeSelectionKeyboard();
            case NODE_MENU_MESSAGE -> creatorKeyboardsKeeper.messageNodeMenuKeyboard(info);
            case NODE_MENU_POLL -> creatorKeyboardsKeeper.pollNodeMenuKeyboard(info);
            case NODE_MENU_GEO -> creatorKeyboardsKeeper.geoNodeMenuKeyboard(info);
            case NODE_MENU_MEDIAGROUP -> creatorKeyboardsKeeper.mediaGroupMenuKeyboard(info);
            case SUBMENU_EXPECTED_ANSWERS_MENU -> creatorKeyboardsKeeper.expectedAnswersKeyboard(info);
            case SUBMENU_GEOPOINT_RADIUS_MENU -> creatorKeyboardsKeeper.activeRadiusKeyboard();

            default -> creatorKeyboardsKeeper.creatorReplyAddKeyboard();
        };
    }

    public RespKeyboard getReplyRemoveKeyboard() {
        return creatorKeyboardsKeeper.creatorReplyRemoveKeyboard();
    }
    public RespKeyboard getReplyAddKeyboard(){
        return creatorKeyboardsKeeper.creatorReplyAddKeyboard();
    }

    public RespKeyboard getDynamicMenu(Map<String, String> nodeMap) {
        return creatorKeyboardsKeeper.getDynamicNodeEditKeyboard(nodeMap);
    }
}
