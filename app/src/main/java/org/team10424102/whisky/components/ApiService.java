package org.team10424102.whisky.components;

import com.squareup.okhttp.Response;

import org.team10424102.whisky.models.Activity;
import org.team10424102.whisky.models.Profile;
import org.team10424102.whisky.models.RefreshTokenResult;
import org.team10424102.whisky.models.RegisterResult;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by yy on 11/8/15.
 */
public interface ApiService {
    @FormUrlEncoded
    @POST("/api/register")
    Call<RegisterResult> register(@Field("zone") String zone, @Field("phone") String phone, @Field("vcode") String vcode);

    @GET("/api/profile")
    Call<Profile> getProfile();

    @PUT("/api/profile")
    Call<Response> updateProfile(Profile profile);

    @GET("/api/token")
    Call<RefreshTokenResult> refreshToken(@Query("zone") String zone, @Query("phone") String phone, @Query("vcode") String vcode);

    @GET("/status")
    Call<Integer> getServerStatus();

    @GET("/api/image")
    Call<Response> getImage(String accessToken);

    @GET("/api/availability/phone")
    Call<Boolean> isPhoneAvailable(@Query("q") String phone);

    @GET("/api/availability/token")
    Call<Boolean> isTokenAvailable(@Query("q") String token);

    @GET("/api/activity/recommandations")
    Call<List<Activity>> getRecommandedActivities();

    @GET("/api/activity/{type}")
    Call<List<Activity>> getActivities(@Path("type") Activity.Type type, @Query("page") int page, @Query("size") int size, @Query("filter") String filter);

    @GET("/api/activity/{id}")
    Call<Activity> getActivity();

    @POST("/api/activity")
    Call<Response> createActivity(@Body Activity activity);

}
