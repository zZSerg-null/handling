package ru.zinoviev.quest.request.handler.domain;

import static org.mockito.ArgumentMatchers.any;

//@SpringBootTest
class RequestProcessor2Test {

    /*@Autowired
    private RequestProcessor requestProcessor;

    @Autowired
    private DispatcherRegistry registry;

    @MockitoBean
    private ResponsePublisher publisherMock;

    private final Long USERID = Math.abs(new Random().nextLong(Long.MAX_VALUE - 5));

    @ParameterizedTest
    @MethodSource("dispatchKeyStream")
    public void testCases(UserRole role, RequestType type, RequestData data) {
        requestProcessor.process(role, type, data);

        ActionDispatcher dispatcher = registry.get(role, type);

        if (dispatcher instanceof FallbackDispatcher) {
            verify(publisherMock, after(500).never())
                    .sendResponse(any());
        } else {
            verify(publisherMock, timeout(1000))
                    .sendResponse(any(ResponseData.class));
        }

        clearInvocations(publisherMock);
    }

    public Stream<Arguments> dispatchKeyStream() {
        return Arrays.stream(UserRole.values())
                .flatMap(role -> Arrays.stream(RequestType.values()) // для каждой роли возвращаем стрим типов
                        .map(type -> Arguments.of(role, type, requestData(type)))
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

    private RequestData requestData(RequestType requestType) {
        return switch (requestType) {
            case CALLBACK -> getCallbackTypeRequest();
            case MESSAGE -> getMessageTypeRequest();
            case POLL -> getPollTypeRequest();
            case LOCATION -> getLocationTypeRequest();
            case POLL_ANSWER -> getPollAnswerTypeRequest();
        };
    }

    private RequestData getCallbackTypeRequest() {
        return CallbackRequest.builder()
                .telegramId(USERID)
                .userName(request.getUserName())
                .data(request.getData())
                .build();
    }

    private RequestData getMessageTypeRequest() {
        return MessageRequest.builder()
                .telegramId(USERID)
                .userName(request.getUserName())
                .text(request.getText())
                .payloadObject(request.getFilePayload() != null ?
                        payloadTypeMap
                                .get(request.getFilePayload().getPayloadType())
                                .apply(request.getFilePayload())

                        : null
                )
                .build();
    }

    private RequestData getLocationTypeRequest() {
        return LocationRequest.builder()
                .telegramId(USERID)
                .userName(request.getUserName())
                .build();
    }

    private RequestData getPollTypeRequest() {
        return PollRequest.builder()
                .telegramId(USERID)
                .userName(request.getUserName())
                .pollId(request.getPollId())
                .type(request.getType())
                .allowMultipleAnswers(request.getAllowMultipleAnswers())
                .question(request.getQuestion())
                .explanation(request.getExplanation())
                .answers(request.getAnswers())
                .correctAnswersId(request.getCorrectAnswersId())
                .build();
    }

    private RequestData getPollAnswerTypeRequest() {
        return PollAnswerRequest.builder()
                .telegramId(USERID)
                .userName(request.getUserName())
                .pollId(request.getPollId())
                .optionIds(request.getOptionIds())
                .build();
    }


    private PayloadObject getAudio(FilePayload payload) {
        return PayloadObject.builder()
                .payloadType(PayloadType.AUDIO)
                .caption(payload.getCaption())
                .fileId(payload.getFileId())
                .fileUniqueId(payload.getFileUniqueId())
                .fileName(payload.getFileName())
                .duration(payload.getDuration())
                .mimeType(payload.getMimeType())
                .build();
    }

    private PayloadObject getAnimation(FilePayload payload) {
        return PayloadObject.builder()
                .payloadType(PayloadType.ANIMATION)
                .caption(payload.getCaption())
                .fileId(payload.getFileId())
                .fileUniqueId(payload.getFileUniqueId())
                .fileName(payload.getFileName())
                .height(payload.getHeight())
                .width(payload.getWidth())
                .duration(payload.getDuration())
                .mimeType(payload.getMimeType())
                .build();
    }

    private PayloadObject getPhoto(FilePayload payload) {
        return PayloadObject.builder()
                .payloadType(PayloadType.PHOTO)
                .caption(payload.getCaption())
                .fileId(payload.getFileId())
                .fileUniqueId(payload.getFileUniqueId())
                .filePath(payload.getFilePath())
                .height(payload.getHeight())
                .width(payload.getWidth())
                .fileSize(payload.getFileSize())
                .build();
    }

    private PayloadObject getVoice(FilePayload filePayload) {
        return PayloadObject.builder().build();
    }

    private PayloadObject getVideo(FilePayload payload) {
        return PayloadObject.builder()
                .payloadType(PayloadType.VIDEO)
                .caption(payload.getCaption())
                .fileId(payload.getFileId())
                .fileUniqueId(payload.getFileUniqueId())
                .fileName(payload.getFileName())
                .width(payload.getWidth())
                .height(payload.getHeight())
                .duration(payload.getDuration())
                .mimeType(payload.getMimeType())
                .build();
    }

    private PayloadObject getDocument(FilePayload payload) {
        return PayloadObject.builder()
                .payloadType(PayloadType.DOCUMENT)
                .caption(payload.getCaption())
                .fileId(payload.getFileId())
                .fileUniqueId(payload.getFileUniqueId())
                .fileName(payload.getFileName())
                .fileSize(payload.getFileSize())
                .mimeType(payload.getMimeType())
                .build();
    }

    private PayloadObject getSticker(FilePayload payload) {
        return PayloadObject.builder()
                .payloadType(PayloadType.STICKER)
                .caption(payload.getCaption())
                .fileId(payload.getFileId())
                .fileUniqueId(payload.getFileUniqueId())
                .width(payload.getWidth())
                .height(payload.getHeight())
                .isAnimated(payload.getIsAnimated())
                .build();
    }*/
}