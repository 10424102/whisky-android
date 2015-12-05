package org.team10424102.whisky.models.extensions;

import android.os.Parcelable;
import android.view.View;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;

import org.team10424102.whisky.models.Post;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PostExtensionManager {
    private Map<String, PostExtension> extensions = new HashMap<>();

    private Map<String, EGameType> games = new HashMap<>();

    public void setGameFor(Post post) {
        String id = post.getExtension().getId();
        EGameType game = games.get(id);
        if (game != null) {
            post.setGameLogo(game.getDrawableResId());
        }
    }

    public void registerPostExtension(PostExtension pe) {
        Class cls = pe.getClass();
        PostExtensionIdentifier a = (PostExtensionIdentifier) cls.getAnnotation(PostExtensionIdentifier.class);
        String identifier = a.value();
        if (identifier == null) {
            identifier = cls.getCanonicalName();
        }
        extensions.put(identifier, pe);
        if (pe instanceof GamePostExtension) {
            GamePostExtension gpe = (GamePostExtension) pe;
            games.put(identifier, gpe.getGameType());
        }
    }

    public int getLayout(PostExtensionData data) {
        return extensions.get(data.getId()).getLayout();
    }

    @SuppressWarnings("unchecked")
    public void render(PostExtensionData data, View view) {
        if (data == null) return;
        extensions.get(data.getId()).render(data.getData(), view);
    }

    public PostExtensionData parseJson(JsonParser jp) throws IOException, JsonParseException {
        JsonNode root = jp.getCodec().readTree(jp);
        String id = root.get("id").asText();
        JsonNode dataNode = root.get("data");
        Parcelable data = extensions.get(id).parseJson(dataNode, jp);
        return new PostExtensionData(id, data);
    }
}
