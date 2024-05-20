package quarkus.ws.server.test.service.impl;

import quarkus.ws.server.test.chat.ChatRoom;
import quarkus.ws.server.test.service.LookupService;

import java.util.Map;

public class DefaultLookupService implements LookupService {
    private final Map<String, ChatRoom> refKeyChatRoomMap;

    public DefaultLookupService(Map<String, ChatRoom> refKeyChatRoomMap) {
        this.refKeyChatRoomMap = refKeyChatRoomMap;
    }

    @Override
    public ChatRoom roomLookup(String roomContextKey) {
        return refKeyChatRoomMap.get(roomContextKey);
    }

}
