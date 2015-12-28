package org.team10424102.whisky.models.mapping;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import org.team10424102.whisky.models.LazyImage;

import java.io.IOException;

/**
 * Created by yy on 11/14/15.
 */
public class LazyImageDeserializer extends JsonDeserializer<LazyImage> {

    @Override
    public LazyImage deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode root = jp.getCodec().readTree(jp);
        LazyImage image = new LazyImage();
        String[] parts = root.asText().split("~");
        image.setAccessToken(parts[0]);
        image.setHash(parts[1]);
        return image;
    }
}
