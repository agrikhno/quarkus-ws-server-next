package quarkus.ws.server.test.event;

import quarkus.ws.server.test.event.type.IncomingEventType;
import quarkus.ws.server.test.model.ClientMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IncomingWebsocketEvent {

    /**
     * Event type
     */
    private IncomingEventType type;

    /**
     * Event payload
     */
    private ClientMessage event;

    public static IncomingWebsocketEvent message(ClientMessage incomingCallMessage) {
        return IncomingWebsocketEvent.builder()
                .type(IncomingEventType.MESSAGE)
                .event(incomingCallMessage)
                .build();
    }

}
