package quarkus.ws.server.test;

import io.quarkus.websockets.next.OnClose;
import io.quarkus.websockets.next.OnError;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.OnPongMessage;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;
import io.quarkus.websockets.next.WebSocketConnection;
import io.vertx.core.buffer.Buffer;
import jakarta.enterprise.context.ApplicationScoped;
import quarkus.ws.server.test.chat.ChatRoom;
import quarkus.ws.server.test.chat.UserSession;
import quarkus.ws.server.test.convert.TextWebsocketEventInputCodec;
import quarkus.ws.server.test.convert.TextWebsocketEventOutputCodec;
import quarkus.ws.server.test.event.IncomingWebsocketEvent;
import quarkus.ws.server.test.model.ClientMessage;
import quarkus.ws.server.test.service.LookupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@WebSocket(path = "/wstest/{room}/{userId}")
@ApplicationScoped
@RequiredArgsConstructor
public class WebSocketServerController {

    private final LookupService lookupService;

    @OnOpen
    public void onOpen(WebSocketConnection webSocketConnection) {
        final String roomName = webSocketConnection.pathParam("room");
        final String userId = webSocketConnection.pathParam("userId");

        ChatRoom chatRoom = lookupService.roomLookup(roomName);
        chatRoom.addUser(userId, webSocketConnection);
    }

    @OnClose
    public void onClose(WebSocketConnection webSocketConnection) {
        log.info("onClose> ");

        final String roomName = webSocketConnection.pathParam("room");
        final String userId = webSocketConnection.pathParam("userId");

        ChatRoom chatRoom = lookupService.roomLookup(roomName);
        chatRoom.removeUser(userId);
    }

    @OnError
    public void onError(Throwable throwable) {
        log.error("onError>: " + throwable);
    }

    @OnTextMessage(
            codec = TextWebsocketEventInputCodec.class,
            outputCodec = TextWebsocketEventOutputCodec.class
    )
    public void onMessage(WebSocketConnection webSocketConnection, IncomingWebsocketEvent event) {
        handleIncomingMessage(event.getEvent());
    }

    @OnPongMessage
    void pong(Buffer data) {
        // ....
    }

    private void handleIncomingMessage(ClientMessage clientMessage) {
        final ChatRoom chatRoom = lookupService.roomLookup(clientMessage.getRoom());

        final UserSession userSession = chatRoom.getUserSessionById(clientMessage.getId());
        if (null != userSession) {
            userSession.handleIncomingMessage(clientMessage.getMsg());
        }
    }

}
