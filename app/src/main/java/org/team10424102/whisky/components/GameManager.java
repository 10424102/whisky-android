package org.team10424102.whisky.components;

import android.util.Log;

import org.team10424102.whisky.components.api.ApiService;
import org.team10424102.whisky.models.Game;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit.Response;

public class GameManager {
    private static final String TAG = "GameManager";

    private ApiService mApiService;

    private Map<String, Game> games = new HashMap<>(); //TODO 到底多线程有没有问题?


    public GameManager(ApiService apiService) {
        mApiService = apiService;
    }


    public Game getGame(String identifier) {

        Game game = games.get(identifier);

        if (game != null) return game;

        try {
            Response<Game> resp = mApiService.getGameInfo(identifier).execute();
            if (resp.code() == 200 && resp.body() != null) {
                game = resp.body();
                games.put(identifier, game);
            }
        } catch (IOException e) {
            Log.e(TAG, "failed to fetch game from server", e);
        }

        return game;
    }
}
