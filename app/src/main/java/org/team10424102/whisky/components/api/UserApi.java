package org.team10424102.whisky.components.api;

import com.squareup.okhttp.Response;

import org.team10424102.whisky.components.auth.NoAuthentication;
import org.team10424102.whisky.components.TokenResult;
import org.team10424102.whisky.models.Profile;
import org.team10424102.whisky.models.User;

import java.util.List;

import retrofit.Call;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.HEAD;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

public interface UserApi {
    String PREFIX = "/api/users";

    @GET(PREFIX)
    Call<Profile> getProfile();

    @NoAuthentication
    @GET(PREFIX + "/token")
    Call<TokenResult> getToken(@Query("phone") String phone, @Query("vcode") String vcode);

    @HEAD(PREFIX + "/phone")
    Call<Void> isPhoneAvailable(@Query("q") String phone);

    @HEAD(PREFIX + "/token")
    Call<Void> isTokenAvailable(@Query("q") String token);

    @GET(PREFIX + "/focuses")
    Call<List<User>> getFocuses();

    @GET(PREFIX + "/fans")
    Call<List<User>> getFans();

    @GET(PREFIX + "/friends")
    Call<List<User>> getFriends();

    @POST(PREFIX + "/focuses/{id}")
    Call<Response> focusSomeone(@Path("id") long id);

    @DELETE(PREFIX + "/focuses/{id}")
    Call<Response> unfocusSomeone(@Path("id") long id);

    @POST(PREFIX + "/friends/{id}")
    Call<Response> friendSomeone(@Path("id") long id, @Query("alias") String alias);

    @PUT(PREFIX + "/friends/{id}")
    Call<Response> updateFriendAlias(@Path("id") long id, @Query("alias") String alias);

    @DELETE(PREFIX + "/friends/{id}")
    Call<Response> unfriendSomeone(@Path("id") long id);

    @PATCH(PREFIX + "/signature")
    Call<Response> updateSignature(@Query("val") String value);
}
