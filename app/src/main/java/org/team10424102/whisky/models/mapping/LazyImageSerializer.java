package org.team10424102.whisky.models.mapping;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.team10424102.whisky.models.LazyImage;

import java.io.IOException;

/**
 * Created by yy on 11/14/15.
 */
public class LazyImageSerializer extends JsonSerializer<LazyImage> {

    @Override
    public void serialize(LazyImage value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeString(value.getAccessToken());
    }
}
