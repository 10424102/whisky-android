package org.team10424102.whisky.components;

import android.util.Log;

import org.team10424102.whisky.App;
import org.team10424102.whisky.models.Game;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dagger.Lazy;
import retrofit.Response;

public class GameManager {
    private static final String TAG = "GameManager";

    private Lazy<BlackServerApi> mApi;

    private Map<String, Game> mGames = new ConcurrentHashMap<>();

    public GameManager() {
        App.getInstance().getObjectGraph().inject(this);
    }

    public Game getGame(String identifier) {

        Game game = mGames.get(identifier);

        if (game != null) return game;

        try {
            Response<Game> resp = mApi.get().getGameInfo(identifier).execute();
            if (resp.code() == 200 && resp.body() != null) {
                game = resp.body();
                mGames.put(identifier, game);
            }
        } catch (IOException e) {
            Log.e(TAG, "failed to fetch game from server", e);
        }

        return game;
    }
}
