package quarkus.ws.server.test.convert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.websockets.next.TextMessageCodec;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import quarkus.ws.server.test.event.IncomingWebsocketEvent;
import quarkus.ws.server.test.model.ClientMessage;
import lombok.SneakyThrows;

import java.lang.reflect.Type;

@Singleton
public class TextWebsocketEventInputCodec implements TextMessageCodec<IncomingWebsocketEvent> {

    @Inject
    ObjectMapper mapper;

    @Override
    public boolean supports(Type type) {
        return type.equals(IncomingWebsocketEvent.class);
    }

    @SneakyThrows
    @Override
    public String encode(IncomingWebsocketEvent incomingWebsocketEvent) {
        return "";
    }

    @SneakyThrows
    @Override
    public IncomingWebsocketEvent decode(Type type, String value) {
        final JsonNode payload = mapper.readTree(value).get("source");
        return IncomingWebsocketEvent.message(mapper.treeToValue(payload, ClientMessage.class));
    }

}
