package ru.old.domain.jpa.service.impl;

import lombok.RequiredArgsConstructor;
import old.domain.dto.request.RequestData;
import old.domain.dto.response.QuestResponseData;
import old.domain.dto.response.RespMessageData;
import ru.old.domain.enums.RetryType;
import ru.old.domain.enums.UserRole;
import ru.old.domain.handlers.h1.BasicMessageBuilder;
import ru.old.domain.handlers.roles.constants.MessageConstants;
import old.domain.jpa.entity.LastResponseJPA;
import old.domain.jpa.entity.QuestUserJPA;
import ru.old.domain.jpa.repository.LastResponseJPARepository;
import ru.old.domain.jpa.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import old.domain.jpa.mapper.LastResponseJPAMapper;
import ru.old.domain.jpa.service.LastResponseJPARepositoryService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LastResponseRepositoryServiceImpl implements LastResponseJPARepositoryService {

    private final LastResponseJPARepository lastResponseJPARepository;
    private final LastResponseJPAMapper lastResponseJPAMapper;
    private final UserRepository userRepository;
    private final BasicMessageBuilder messageBuilder;

    @Override
    public List<RespMessageData> getLastServiceResponseToUser(RequestData request, UserRole userRole) {
        Long telegramId = request.getFrom().getTelegramUserId();

        Optional<LastResponseJPA> lastResponseOptional = lastResponseJPARepository.findByTelegramIdAndUserRole(telegramId, userRole);

        if (lastResponseOptional.isEmpty()) {
            var message = messageBuilder.buildMessage(request, RespMessageType.MESSAGE, RetryType.IGNORE);
            message.setText(MessageConstants.EMPTY_UPDATE_DATA);

            return List.of(message);
        }
        return lastResponseJPAMapper.toMessageList(lastResponseOptional.get().getMessageDataList());
    }

    @Override
    @Transactional
    public void saveLastServiceResponseToUser(QuestResponseData responseData) {
        Long telegramId = responseData.getMessageDataList().get(0).getChatId();

        Optional<QuestUserJPA> userOptional = userRepository.findByTelegramId(telegramId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Юзеришко не найден, а должен был");
        }
        Optional<LastResponseJPA> lastResponseOptional = lastResponseJPARepository.findByTelegramIdAndUserRole(telegramId, userOptional.get().getUserRole());
        lastResponseOptional.ifPresent(lastResponseJPARepository::delete);

        LastResponseJPA entity = lastResponseJPAMapper.toEntity(responseData, userOptional.get().getUserRole());
        lastResponseJPARepository.save(entity);
    }
}
