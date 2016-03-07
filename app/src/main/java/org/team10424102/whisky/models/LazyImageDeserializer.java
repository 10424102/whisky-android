package org.team10424102.whisky.models;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import org.team10424102.whisky.models.LazyImage;

import java.io.IOException;

import timber.log.Timber;

/**
 * Created by yy on 11/14/15.
 */
public class LazyImageDeserializer extends JsonDeserializer<LazyImage> {

    @Override
    public LazyImage deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        //Timber.d("lazy image");
        JsonNode root = jp.getCodec().readTree(jp);
        String[] parts = root.asText().split("~");
        String token = parts[0];
        String hash = parts[1];
        return new LazyImage(token, hash);
    }
}
