package org.team10424102.whisky.models.mapping;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import org.team10424102.whisky.components.GameManager;
import org.team10424102.whisky.models.Game;
import org.team10424102.whisky.models.Profile;

import java.io.IOException;

/**
 * Created by yy on 11/8/15.
 */
public class GameDeserializer extends JsonDeserializer<Game> {

    private GameManager mGameManager;


    public GameDeserializer(GameManager gameManager) {
        mGameManager = gameManager;
    }

    @Override
    public Game deserialize(JsonParser jp, DeserializationContext ctx)
            throws IOException, JsonProcessingException {

        JsonNode root = jp.getCodec().readTree(jp);

        if (root.isObject()) {
            return jp.readValueAs(Game.class);
        }


        String identifier = root.asText();

        return mGameManager.getGame(identifier);
    }
}
