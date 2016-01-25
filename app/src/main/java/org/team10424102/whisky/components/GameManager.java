package org.team10424102.whisky.components;

import android.support.annotation.NonNull;
import android.util.Log;

import org.team10424102.whisky.App;
import org.team10424102.whisky.models.Game;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Inject;

import dagger.Lazy;
import retrofit2.Response;
import timber.log.Timber;

public class GameManager {

    @Inject Lazy<BlackServerApi> api;

    private Map<String, Game> games = new ConcurrentHashMap<>();
    private Map<String, ReentrantLock> gameLocks = new ConcurrentHashMap<>();

    public GameManager() {
        App.getInstance().getObjectGraph().inject(this);
    }

    public Game getGame(@NonNull String identifier) {

        Game game = games.get(identifier);
        if (game != null) return game;

        ReentrantLock lock = gameLocks.get(identifier);
        if (lock == null) {
            lock = new ReentrantLock();
            gameLocks.put(identifier, lock);
        }

        lock.lock();
        game = games.get(identifier);
        if (game != null) return game;
        try {
            Response<Game> resp = api.get().getGameInfo(identifier).execute();
            if (resp.code() == 200 && resp.body() != null) {
                game = resp.body();
                games.put(identifier, game);
            }
        } catch (IOException e) {
            Timber.e("failed to fetch game from server", e);
            e.printStackTrace();
        }
        lock.unlock();

        return game;
    }
}
