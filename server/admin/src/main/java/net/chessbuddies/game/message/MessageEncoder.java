package net.chessbuddies.game.message;

import java.io.IOException;
import java.io.Writer;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageEncoder implements Encoder.TextStream<Message<?>> {
	private ObjectMapper mapper = new ObjectMapper();
    @Override
    public void init(EndpointConfig config) {}

    @Override
    public void destroy() {}

	@Override
	public void encode(Message<?> object, Writer writer) throws EncodeException, IOException {
		mapper.writeValue(writer, object);
	}
}