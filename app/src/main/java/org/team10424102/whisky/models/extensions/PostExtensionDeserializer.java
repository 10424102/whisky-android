package org.team10424102.whisky.models.extensions;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

import javax.inject.Inject;

public class PostExtensionDeserializer extends JsonDeserializer<PostExtension> {

    @Inject PostExtensionManager mPostExtensionManager;

    public PostExtensionDeserializer(PostExtensionManager postExtensionManager) {
        mPostExtensionManager = postExtensionManager;
    }

    @Override
    public PostExtension deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        return mPostExtensionManager.parseJson(jp);
    }
}
