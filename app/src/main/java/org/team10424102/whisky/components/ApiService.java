package org.team10424102.whisky.components;

import com.squareup.okhttp.Response;

import org.team10424102.whisky.models.Activity;
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
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

import static org.team10424102.whisky.App.API_ACTIVITY;
import static org.team10424102.whisky.App.API_AVAILABILITY;
import static org.team10424102.whisky.App.API_HEALTH;
import static org.team10424102.whisky.App.API_TOKEN;
import static org.team10424102.whisky.App.API_USER;

/**
 * Created by yy on 11/8/15.
 */
public interface ApiService {
    @GET(API_HEALTH)
    Call<ServerHealth> getServerHealth();

    @GET(API_USER)
    Call<Profile> getProfile();

    @PUT(API_USER)
    Call<Response> updateProfile(Profile profile);

    @GET(API_TOKEN)
    Call<TokenResult> getToken(@Query("phone") String phone, @Query("vcode") String vcode);

    @GET(API_AVAILABILITY + "/phone")
    Call<AvailabilityResult> isPhoneAvailable(@Query("q") String phone);

    @GET(API_AVAILABILITY + "/token")
    Call<AvailabilityResult> isTokenAvailable(@Query("q") String token);

    @GET(API_ACTIVITY)
    Call<List<Activity>> getActivities(@Query("category") String category, @Query("page") int page, @Query("size") int size);

    @GET(API_ACTIVITY + "/{id}")
    Call<Activity> getActivity(@Path("id") long id);

    @POST("/api/activity")
    Call<Response> createActivity(@Body Activity activity);

    @GET("/api/post/{category}")
    Call<List<Post>> getPosts(@Path("category") EMatchPostsCategory category, @Query("page") int page, @Query("size") int size, @Query("filter") String filter);

    @GET(API_USER + "/focuses")
    Call<List<User>> getFocuses();

    @GET(API_USER + "/fans")
    Call<List<User>> getFans();

    @GET(API_USER + "/friends")
    Call<List<User>> getFriends();

    @POST(API_USER + "/focuses/{id}")
    Call<Response> focusSomeone(@Path("category") long id);

    @DELETE(API_USER + "/focuses/{id}")
    Call<Response> unfocusSomeone(@Path("category") long id);

    @POST(API_USER + "/friends/{id}")
    Call<Response> friendSomeone(@Path("category") long id, @Query("alias") String alias);

    @PUT(API_USER + "/friends/{id}")
    Call<Response> updateFriendAlias(@Path("category") long id, @Query("alias") String alias);

    @DELETE(API_USER + "/friends/{id}")
    Call<Response> unfriendSomeone(@Path("category") long id);
}
