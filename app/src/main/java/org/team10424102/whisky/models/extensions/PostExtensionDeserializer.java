package org.team10424102.whisky.models.extensions;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import org.team10424102.whisky.App;

import java.io.IOException;

public class PostExtensionDeserializer extends JsonDeserializer<PostExtensionData> {

    @Override
    public PostExtensionData deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        final PostExtensionManager postExtensionManager =
                (PostExtensionManager) App.getInstance().getComponent(PostExtensionManager.class);

        return postExtensionManager.parseJson(jp);
    }
}
