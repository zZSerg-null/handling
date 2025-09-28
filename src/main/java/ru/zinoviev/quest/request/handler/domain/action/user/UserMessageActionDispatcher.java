package ru.zinoviev.quest.request.handler.domain.action.user;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.action.ActionDispatcher;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.action.PropertiesReader;
import ru.zinoviev.quest.request.handler.domain.dto.request.MessageRequest;
import ru.zinoviev.quest.request.handler.domain.dto.request.RequestData;
import ru.zinoviev.quest.request.handler.domain.dto.response.SendMessageData;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

@Component
public class UserMessageActionDispatcher extends ActionDispatcher {

    public UserMessageActionDispatcher(ResponsePublisher publisher, PropertiesReader propertiesReader) {
        super(publisher, propertiesReader);
    }

    public void dispatch(RequestData request) {
        MessageRequest messageRequest = (MessageRequest) request;
        System.out.println("UserMessageActionDispatcher");

        if (messageRequest.getText() != null){
            textHandling(messageRequest);
        } else if (messageRequest.getPayloadObject() != null){
            payloadHandling(messageRequest);
        }
    }

    @Override
    public DispatchKey key() {
        return new DispatchKey(getRole(), RequestType.MESSAGE);
    }

    @Override
    public UserRole getRole() {
        return UserRole.USER;
    }

    private void textHandling(MessageRequest messageRequest) {
        if (messageRequest.getText().toLowerCase().equals(UserTextCommand.START)) {
            sendHello(messageRequest);
        } else {
            anyMessage(messageRequest);
        }
    }

    //TODO формирование сообщения,
    // формирование выделения. О динамике речи нет, у нас пока статика.
    // Но если будет подключаться нейросеть для ответов то
    private void anyMessage(MessageRequest messageRequest) {


        String response = "✅ *Команды бота:* \n\n" +
                "📋 /start: - показывает основное меню" + "\n" +
                "*bold*\n" +
                "_italic_\n" +
                "[inline URL](http://www.example.com/)\n" +
                "`inline fixed-width code` " +"\n";

        sendResponse(SendMessageData.builder()
                .userId(messageRequest.getTelegramId())
                .message( escapeMarkdownV2( response ))
                .build());
    }

    private void sendHello(MessageRequest messageRequest) {
        String response = "*Добро пожаловать в квестовый бот!*\n\n" +
                "Здесь вы можете создавать и проходить квесты \n";


        sendResponse(SendMessageData.builder()
                .userId(messageRequest.getTelegramId())
                .message( escapeMarkdownV2( response ))
                .build());
    }

    private String escapeMarkdownV2(String text) {
        if (text == null) return "";
        return text.replaceAll("([-+\\\\=()#!{}.])", "\\\\$1");
    }

    private void payloadHandling(MessageRequest messageRequest) {

    }

    private void sendMainMenu(MessageRequest messageRequest) {
        sendResponse(SendMessageData.builder().build());
    }

    private void text(MessageRequest messageRequest) {

    }

}
