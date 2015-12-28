package org.team10424102.whisky.components;

import com.squareup.okhttp.Response;

import org.team10424102.whisky.models.Activity;
import org.team10424102.whisky.models.Game;
import org.team10424102.whisky.models.Post;
import org.team10424102.whisky.models.Profile;
import org.team10424102.whisky.models.ServerHealth;
import org.team10424102.whisky.models.User;
import org.team10424102.whisky.models.enums.EMatchPostsCategory;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.HEAD;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

import static org.team10424102.whisky.App.*;

/**
 * Created by yy on 11/8/15.
 */
public interface ApiService {
    @GET(API_STATUS)
    Call<ServerHealth> getServerHealth();

    @GET(API_USER)
    Call<Profile> getProfile();

    @PATCH(API_USER)
    Call<Response> updateSignature(@Query("val") String value);

    @GET(API_USER + "/token")
    Call<TokenResult> getToken(@Query("phone") String phone, @Query("vcode") String vcode);

    @HEAD(API_USER + "/phone")
    Call<Void> isPhoneAvailable(@Query("q") String phone);

    @HEAD(API_USER + "/token")
    Call<Void> isTokenAvailable(@Query("q") String token);

    @GET(API_USER + "/focuses")
    Call<List<User>> getFocuses();

    @GET(API_USER + "/fans")
    Call<List<User>> getFans();

    @GET(API_USER + "/friends")
    Call<List<User>> getFriends();

    @POST(API_USER + "/focuses/{id}")
    Call<Response> focusSomeone(@Path("id") long id);

    @DELETE(API_USER + "/focuses/{id}")
    Call<Response> unfocusSomeone(@Path("id") long id);

    @POST(API_USER + "/friends/{id}")
    Call<Response> friendSomeone(@Path("id") long id, @Query("alias") String alias);

    @PUT(API_USER + "/friends/{id}")
    Call<Response> updateFriendAlias(@Path("id") long id, @Query("alias") String alias);

    @DELETE(API_USER + "/friends/{id}")
    Call<Response> unfriendSomeone(@Path("id") long id);

    @GET(API_ACTIVITY)
    Call<List<Activity>> getActivities(@Query("category") String category, @Query("page") int page, @Query("size") int size);

    @GET(API_ACTIVITY + "/{id}")
    Call<Activity> getActivity(@Path("id") long id);

    @POST(API_ACTIVITY)
    Call<Response> createActivity(@Body Activity activity);

    @GET(API_POST)
    Call<List<Post>> getPosts(@Query("category") String category, @Query("page") int page, @Query("size") int size, @Query("filter") String filter);

    @GET(API_GAME)
    Call<Game> getGameInfo(@Query("key") String key);
}
