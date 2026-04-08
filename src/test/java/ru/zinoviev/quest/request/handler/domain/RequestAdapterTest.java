package ru.zinoviev.quest.request.handler.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.zinoviev.quest.request.handler.domain.dto.internal.*;
import ru.zinoviev.quest.request.handler.domain.dto.response.ResponseData;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.BotUserRole;
import ru.zinoviev.quest.request.handler.transport.protocol.RabbitPublisher;
import ru.zinoviev.quest.request.handler.transport.request.dto.PollType;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class RequestAdapterTest {

   // @Autowired
   // private RequestProcessor requestProcessor;

    @Autowired
    private DispatcherRegistry registry;

    @MockitoBean
    private RabbitPublisher publisherMock;

    @Mock
    private RabbitTemplate template;



    private static final Long USERID = Math.abs(new Random().nextLong(Long.MAX_VALUE - 5));
    private static final String USERNAME = "TEST";

    @ParameterizedTest
    @MethodSource("dispatchKeyStream")
    public void testCases(RequestData data) {

        registry.dispatch(data);

        verify(publisherMock, timeout(1000))
                    .sendResponse(any(ResponseData.class));

        clearInvocations(publisherMock);
    }

    public static Stream<Arguments> dispatchKeyStream() {
        return Arrays.stream(BotUserRole.values())
                .flatMap(role -> Arrays.stream(RequestType.values()) // для каждой роли возвращаем стрим типов
                        .map(type -> Arguments.of(getRequestDataByType(role, type)))
                );
    }


//    public Stream<Arguments> dispatchKeyStream() {
//        return Arrays.stream(UserRole.values())
//                .flatMap(role -> Arrays.stream(RequestType.values()) // для каждой роли возвращаем декартово произведение типов
//                        .map(requestType -> {
//                                    if (RequestType.MESSAGE.equals(requestType)) { // если тип - Мессадж, который умеет хранить пэйлоад
//                                        // возвращаем стрим аргументов с разными типами файлов
//                                        ///  вернуть разные типы файлов.
//                                        // А нужно ли тут?
//                                        //Arguments.of(role, requestType, requestData(requestType))
//                                        return ?
//                                    }
//                                    Arguments.of(role, requestType, requestData(requestType))
//                                }
//                        )
//                );
//    }

    private static RequestData getRequestDataByType(BotUserRole role, RequestType requestType) {
       RequestData requestData = switch (requestType) {
            case CALLBACK -> getCallbackTypeRequest();
            case MESSAGE -> getMessageTypeRequest();
            case POLL -> getPollTypeRequest();
            case LOCATION -> getLocationTypeRequest();
            case POLL_ANSWER -> getPollAnswerTypeRequest();
            case WEBAPP -> getWebAppRequest();
           default -> getUnexpectedTypeRequest();
        };

        requestData.setRole(role);
        return requestData;
    }

    private static RequestData getUnexpectedTypeRequest() {
        return UnexpectedTypeRequest.builder()
                .telegramId(USERID)
                .userName(USERNAME)
                .build();
    }

    private static RequestData getWebAppRequest() {
        return WebAppRequest.builder()
                .telegramId(USERID)
                .userName(USERNAME)
                .webAppData("TEST_CALLBACK")
                .build();
    }

    private static RequestData getCallbackTypeRequest() {
        return CallbackRequest.builder()
                .telegramId(USERID)
                .userName(USERNAME)
                .callbackData("TEST_CALLBACK") // а вот тут заминочка, потому что когда будут реализована обработка колбэков,
                                       // все неизвестные колбэки для роли:типа - уйдут в мусорку
                .build();
    }

    private static RequestData getMessageTypeRequest() {
        return MessageRequest.builder()
                .telegramId(USERID)
                .userName(USERNAME)
                .text("TEST")
                .payloadObject(null)
                .build();
    }

    private static RequestData getLocationTypeRequest() {
        return LocationRequest.builder()
                .telegramId(USERID)
                .userName(USERNAME)
                .build();
    }

    private static RequestData getPollTypeRequest() {
        return PollRequest.builder()
                .telegramId(USERID)
                .userName(USERNAME)
                .pollId("23849723984723987238472398")
                .type(PollType.REGULAR)
                .allowMultipleAnswers(true)
                .question("QUESTION")
                .explanation("EXPLANATION")
                .answers(List.of("ONE", "TWO"))
                .correctAnswersId(List.of(1,2))
                .build();
    }

    private static RequestData getPollAnswerTypeRequest() {
        return PollAnswerRequest.builder()
                .telegramId(USERID)
                .userName(USERNAME)
                .pollId("23849723984723987238472398")
                .optionIds(List.of(1,2))
                .build();
    }


}