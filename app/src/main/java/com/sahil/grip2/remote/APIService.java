package com.sahil.grip2.remote;

import com.sahil.grip2.model.PersonaldetailModel;
import com.sahil.grip2.model.education.EducationdetailModel;
import com.sahil.grip2.model.login.LogInModel;
import com.sahil.grip2.model.professional.ProfessionaldetailModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
    Call<PersonaldetailModel> savePost(@Path("id") int id, @Body String post);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("educationdetail/{id}")
    Call<EducationdetailModel> saveEduData(@Path("id") int id, @Body String post);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("professionaldetail/{id}")
    Call<ProfessionaldetailModel> saveProData(@Path("id") int id, @Body String post);

    @Headers({"Content-Type: application/json"})
    @POST("login")
    Call<LogInModel> getLogindata(@Body String body);

    @GET("personaldetail/{id}")
    Call<PersonaldetailModel> getPersonaldata(@Path("id") int id);

    @GET("educationdetail/{id}")
    Call<EducationdetailModel> getEducationdata(@Path("id") int id);
}
