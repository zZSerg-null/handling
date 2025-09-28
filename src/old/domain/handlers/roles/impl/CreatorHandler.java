package ru.old.domain.handlers.roles.impl;

import old.domain.handlers.h1.BasicMessageBuilder;
import old.domain.handlers.roles.impl.creator.utils.CreatorMenuSelector;
import old.domain.handlers.roles.impl.creator.utils.CreatorMessageTextSelector;
import old.domain.handlers.roles.impl.creator.utils.QuestMapService;
import org.springframework.stereotype.Component;
import old.domain.jpa.service.CreationInfoService;
import old.domain.jpa.service.LastResponseJPARepositoryService;
import old.domain.jpa.service.QuestNodeRepositoryService;
import old.domain.jpa.service.UserRepositoryService;

@Component
public abstract class CreatorHandler extends BasicHandler {

    protected final UserRepositoryService userService;
    protected final CreationInfoService infoService;
    protected final QuestNodeRepositoryService nodeService;
    protected final CreatorMenuSelector menuSelector;
    protected final QuestMapService questMapService;
    protected final LastResponseJPARepositoryService lastResponseService;

    public CreatorHandler(BasicMessageBuilder messageBuilder, CreatorMessageTextSelector textSelector, UserRepositoryService userService, CreationInfoService infoService, QuestNodeRepositoryService nodeService, CreatorMenuSelector menuSelector, QuestMapService questMapService, LastResponseJPARepositoryService lastResponseService) {
        super(messageBuilder, textSelector);
        this.userService = userService;
        this.infoService = infoService;
        this.nodeService = nodeService;
        this.menuSelector = menuSelector;
        this.questMapService = questMapService;
        this.lastResponseService = lastResponseService;
    }
}
