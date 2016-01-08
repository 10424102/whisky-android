package org.team10424102.whisky.models.extensions;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import org.team10424102.whisky.App;

import java.io.IOException;

import javax.inject.Inject;

public class PostExtensionDeserializer extends JsonDeserializer<PostExtension> {

    @Inject PostExtensionManager mPostExtensionManager;

    public PostExtensionDeserializer() {
        App.getInstance().getObjectGraph().inject(this);
    }

    @Override
    public PostExtension deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        return mPostExtensionManager.parseJson(jp);
    }
}
