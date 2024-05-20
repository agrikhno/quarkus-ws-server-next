package quarkus.ws.server.test.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ClientMessage {

    private String id;
    private String room;
    private String msg;

}
