package org.team10424102.whisky.models;

/**
 * Created by yy on 11/14/15.
 */
public class ApiResult {
    public static final int SUCCESS = 0;
    public static final int ERR_PHONE_EXISTED = 1;
    public static final int ERR_DATA_NOT_FOUND = 2;

    private int error;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
