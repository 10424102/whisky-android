package org.team10424102.whisky.models.extensions;

import android.os.Parcelable;
import android.view.View;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;

import org.team10424102.whisky.App;
import org.team10424102.whisky.components.GameManager;
import org.team10424102.whisky.models.Post;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class PostExtensionManager {
    private Map<String, PostExtensionHandler> extensions = new HashMap<>();

    private Map<String, String> games = new HashMap<>();

    @Inject GameManager mGameManager;

    public PostExtensionManager() {
        App.getInstance().getObjectGraph().inject(this);
    }

    public void setGameFor(Post post) {
        String id = post.getExtension().getId();
        String gameIdentifier = games.get(id);
        if (gameIdentifier != null) {
            post.setGame(mGameManager.getGame(gameIdentifier));
        }
    }

    public void registerPostExtensionHandler(PostExtensionHandler pe) {
        Class cls = pe.getClass();
        PostExtensionIdentifier a = (PostExtensionIdentifier) cls.getAnnotation(PostExtensionIdentifier.class);
        String identifier = a.value();
        if (identifier == null) {
            identifier = cls.getCanonicalName();
        }
        extensions.put(identifier, pe);

        RelatedGame relatedGame = (RelatedGame) cls.getAnnotation(RelatedGame.class);
        if (relatedGame != null) {
            String gameIdentifier = relatedGame.value();
            if (gameIdentifier != null) {
                games.put(identifier, gameIdentifier);
            }
        }
    }

    public int getLayout(PostExtension data) {
        return extensions.get(data.getId()).getLayout();
    }

    @SuppressWarnings("unchecked")
    public void render(PostExtension data, View view) {
        if (data == null) return;
        extensions.get(data.getId()).render(data.getData(), view);
    }

    public PostExtension parseJson(JsonParser jp) throws IOException, JsonParseException {
        JsonNode root = jp.getCodec().readTree(jp);
        String id = root.get("id").asText();
        JsonNode dataNode = root.get("data");
        Parcelable data = extensions.get(id).parseJson(dataNode, jp);
        return new PostExtension(id, data);
    }
}
