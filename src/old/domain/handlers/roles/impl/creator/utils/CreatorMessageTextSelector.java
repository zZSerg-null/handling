package ru.old.domain.handlers.roles.impl.creator.utils;

import ru.old.domain.handlers.roles.constants.MessageConstants;
import org.springframework.stereotype.Component;
import old.domain.enums.CreationStage;

@Component
public class CreatorMessageTextSelector {

    public String selectTextByStage(CreationStage currentStage) {
        return switch (currentStage) {
            case  UNSAVED_NODE_DATA -> MessageConstants.UNSAVED_NODE_DATA;

            case NEW_QUEST_AWATING_NAME_CONFIRMATION ->
                    MessageConstants.QUEST_CREATION_STARTED_TEXT + "\n" + MessageConstants.NODE_TYPE_SELECTION_MENU_TEXT;

            case STOP_CREATING -> MessageConstants.STOP_CREATING;

            case MAIN_MENU_NODE_TYPE_SELECTION -> MessageConstants.NODE_TYPE_SELECTION_MENU_TEXT;

            case NODE_MENU_GEO -> MessageConstants.GEO_NODE_MENU_TEXT;
            case NODE_MENU_POLL -> MessageConstants.POLL_NODE_MENU_TEXT;
            case NODE_MENU_MESSAGE -> MessageConstants.MESSAGE_NODE_MENU_TEXT;
            case NODE_MENU_MEDIAGROUP -> MessageConstants.MEDIA_NODE_MENU_TEXT;

            case ADDING_TEXT-> MessageConstants.ADDING_TEXT;
            case ADDING_FILE -> MessageConstants.ADDING_FILE;

            case ADDING_GEOPOINT -> MessageConstants.ADDING_GEOPOINT;
            case SUBMENU_GEOPOINT_RADIUS_MENU -> MessageConstants.GEOPOINT_RADIUS_MENU;
            case ADDING_GEOPOINT_RADIUS_MESSAGES -> MessageConstants.DIALOG_NODE_ADD_RADIUS_MESSAGES;
            case ADDING_GEOPOINT_RADIUS_VALUE -> MessageConstants.DIALOG_NODE_ADD_RADIUS_VALUE;

            case ADDING_POLL_NODE_ANSWERS -> MessageConstants.ADDING_POLL_NODE_ANSWERS;
            case ADDING_POLL_NODE_EXPLANATION -> MessageConstants.ADDING_POLL_NODE_EXPLANATION;
            case ADDING_POLL_NODE_QUESTION -> MessageConstants.ADDING_POLL_NODE_QUESTION;

            case SUBMENU_EXPECTED_ANSWERS_MENU -> MessageConstants.EXPECTED_ANSWERS_MENU;
            case ADDING_EXPECTED_ANSWERS -> MessageConstants.ADDING_EXPECTED_ANSWERS;
            case ADDING_EXPECTED_ANSWERS_COUNT -> MessageConstants.ADDING_EXPECTED_ANSWERS_COUNT;
            case ADDING_CORRECT_MESSAGE_REACT -> MessageConstants.ADDING_CORRECT_MESSAGE_REACT;
            case ADDING_WRONG_MESSAGE_REACT -> MessageConstants.ADDING_WRONG_MESSAGE_REACT;

            default -> MessageConstants.UNKNOWN_DATA;
        };
    }
}
