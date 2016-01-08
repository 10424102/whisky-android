package org.team10424102.whisky.components;

import okhttp3.ResponseBody;

import org.team10424102.whisky.models.Activity;
import org.team10424102.whisky.models.Game;
import org.team10424102.whisky.models.LazyImage;
import org.team10424102.whisky.models.Post;
import org.team10424102.whisky.models.Profile;
import org.team10424102.whisky.models.BlackServer;
import org.team10424102.whisky.models.User;

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
import rx.Observable;

public interface BlackServerApi {

    @GET("/status")
    Call<BlackServer> getServerStatus();

    @GET("/api/users")
    Observable<Profile> getProfile();

    @GET("/api/users/token")
    Observable<TokenResult> getToken(@Query("phone") String phone, @Query("vcode") String vcode);

    @HEAD("/api/users/phone")
    Call<ResponseBody> isPhoneAvailable(@Query("q") String phone);

    @HEAD("/api/users/token")
    Call<ResponseBody> isTokenAvailable(@Query("q") String token);

    @GET("/api/users/focuses")
    Observable<List<User>> getFocuses();

    @GET("/api/users/fans")
    Observable<List<User>> getFans();

    @GET("/api/users/friends")
    Observable<List<User>> getFriends();

    @POST("/api/users/focuses/{id}")
    Observable<ResponseBody> focusSomeone(@Path("id") long id);

    @DELETE("/api/users/focuses/{id}")
    Observable<ResponseBody> unfocusSomeone(@Path("id") long id);

    @POST("/api/users/friends/{id}")
    Observable<ResponseBody> friendSomeone(@Path("id") long id, @Query("alias") String alias);

    @PUT("/api/users/friends/{id}")
    Observable<ResponseBody> updateFriendAlias(@Path("id") long id, @Query("alias") String alias);

    @DELETE("/api/users/friends/{id}")
    Observable<ResponseBody> unfriendSomeone(@Path("id") long id);

    @PATCH("/api/users/signature")
    Observable<ResponseBody> updateSignature(@Query("val") String value);

    @GET("/api/users/photos")
    Observable<List<LazyImage>> getPhotos(@Query("page") int page, @Query("size") int size);

    @GET("/api/posts")
    Observable<List<Post>> getPosts(@Query("category") String category,
                                    @Query("page") int page,
                                    @Query("size") int size);

    @GET("/api/games")
    Call<Game> getGameInfo(@Query("key") String key);

    @GET("/api/activities")
    Observable<List<Activity>> getActivities(@Query("category") String category,
                                             @Query("page") int page,
                                             @Query("size") int size);

    @GET("/api/activities/{id}")
    Observable<Activity> getActivity(@Path("id") long id);

    @POST("/api/activities")
    Call<ResponseBody> createActivity(@Body Activity activity);
}
