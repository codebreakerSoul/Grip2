package com.sahil.grip2.remote;

import com.sahil.grip2.model.PersonaldetailModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Sahil on 12/3/2019.
 */

public interface APIService {

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("personaldetail/{id}")
    @FormUrlEncoded
    Call<PersonaldetailModel> savePost(@Path("id") long id, @Body String post);


}
