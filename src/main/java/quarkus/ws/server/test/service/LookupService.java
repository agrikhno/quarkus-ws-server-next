package quarkus.ws.server.test.service;

import quarkus.ws.server.test.chat.ChatRoom;


public interface LookupService {

    ChatRoom roomLookup(String roomContextKey);

}
