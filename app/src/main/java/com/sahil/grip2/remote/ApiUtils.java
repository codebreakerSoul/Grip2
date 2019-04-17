package com.sahil.grip2.remote;

/**
 * Created by Sahil on 12/3/2019.
 */

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://139.59.65.145:9090/user/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
