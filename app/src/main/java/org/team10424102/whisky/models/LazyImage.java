package org.team10424102.whisky.models;

/**
 * Created by yy on 11/14/15.
 */
public class LazyImage {
    private String accessToken;
    private byte[] data;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
