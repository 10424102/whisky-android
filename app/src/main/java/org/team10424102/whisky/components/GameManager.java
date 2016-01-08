package org.team10424102.whisky.components;

import android.util.Log;

import org.team10424102.whisky.models.Game;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import retrofit.Response;

public class GameManager {
    private static final String TAG = "GameManager";

    private BlackServerApi mApi;

    private Map<String, Game> mGames = new ConcurrentHashMap<>();

    public GameManager(BlackServerApi api) {
        mApi = api;
    }


    public Game getGame(String identifier) {

        Game game = mGames.get(identifier);

        if (game != null) return game;

        try {
            Response<Game> resp = mApi.getGameInfo(identifier).execute();
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
