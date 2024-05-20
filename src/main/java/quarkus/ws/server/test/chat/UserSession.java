package quarkus.ws.server.test.chat;

import io.quarkus.websockets.next.WebSocketConnection;
import quarkus.ws.server.test.event.OutgoingWebsocketEvent;
import quarkus.ws.server.test.model.ChatMessage;
import quarkus.ws.server.test.model.OutgoingChatMessage;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
public class UserSession {

    private final WebSocketConnection webSocketConnection;
    private final ChatRoom chatRoom;
    private final AtomicLong messageCounter;

    public void handleIncomingMessage(String message) {
        OutgoingWebsocketEvent<OutgoingChatMessage> outgoingWebsocketEvent = OutgoingWebsocketEvent
                .message(OutgoingChatMessage
                        .builder()
                        .message(
                                ChatMessage.builder()
                                        .id(messageCounter.incrementAndGet())
                                        .userName("UserName")
                                        .message(message)
                                        .build()
                        )
                        .build()
                );

//        List<Uni<Void>> uni = new ArrayList<>();

        for (UserSession userSession : chatRoom.getUserSessions()) {
//            uni.add(userSession.webSocketConnection.sendText(outgoingWebsocketEvent));
            webSocketConnection.sendTextAndAwait(outgoingWebsocketEvent);
        }

//        return Uni.combine().all().unis(uni).discardItems();
    }

}
