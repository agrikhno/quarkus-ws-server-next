package quarkus.ws.server.test.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.websockets.next.TextMessageCodec;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import quarkus.ws.server.test.event.OutgoingWebsocketEvent;
import lombok.SneakyThrows;

import java.lang.reflect.Type;

@Singleton
public class TextWebsocketEventOutputCodec implements TextMessageCodec<OutgoingWebsocketEvent<?>> {

    @Inject
    ObjectMapper mapper;

    @Override
    public boolean supports(Type type) {
        return type.equals(OutgoingWebsocketEvent.class);
    }

    @SneakyThrows
    @Override
    public String encode(OutgoingWebsocketEvent<?> value) {
        return mapper.writeValueAsString(value);
    }

    @Override
    public OutgoingWebsocketEvent<?> decode(Type type, String value) {
        return null;
    }
}
