package com.sahil.grip2;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.sahil.grip2.model.PersonaldetailModel;
import com.sahil.grip2.remote.APIService;
import com.sahil.grip2.remote.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EducationActivity extends AppCompatActivity {

    TextInputEditText et_start_year,et_degree,et_organisation,et_location,et_end_year;
    TextInputLayout il_start_year,il_degree,il_organisation,il_location,il_end_year;
    String start_year="",degree="",organisation="",location="",end_year="";
    private APIService mAPIService;
    String TAG = "ed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        Button submitBtn = findViewById(R.id.btn_submit);
        et_start_year = findViewById(R.id.et_start_year);
        et_degree = findViewById(R.id.et_degree);
        et_organisation = findViewById(R.id.et_organisation);
        et_end_year = findViewById(R.id.et_end_year);
        et_location = findViewById(R.id.et_location);


        il_start_year = findViewById(R.id.il_start_year);
        il_degree = findViewById(R.id.il_degree);
        il_organisation = findViewById(R.id.il_organisation);
        il_end_year = findViewById(R.id.il_end_year);
        il_location = findViewById(R.id.il_location);


        mAPIService = ApiUtils.getAPIService();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                start_year=et_start_year.getText().toString();
                degree=et_degree.getText().toString();
                organisation=et_organisation.getText().toString();
                location=et_location.getText().toString();
                end_year=et_end_year.getText().toString();

                if(degree.isEmpty() || degree.equals("")){
                    il_degree.setError("Enter Valid degree");
                }
                else {
                    JsonObject data = new JsonObject();
                    data.addProperty("start_year", start_year);
                    data.addProperty("degree", degree);
                    data.addProperty("organisation", organisation);
                    data.addProperty("location", location);
                    data.addProperty("end_year", end_year);
                    sendPost( data.toString());
                }
            }
        });

    }


    public void showErrorMessage() {
        Toast.makeText(this, R.string.mssg_error_submitting_post, Toast.LENGTH_SHORT).show();
    }

    public void sendPost(String body) {

        long id = 158;

        mAPIService.savePost(id, body).enqueue(new Callback<PersonaldetailModel>() {
            @Override
            public void onResponse(Call<PersonaldetailModel> call, Response<PersonaldetailModel> response) {

                if(response.isSuccessful()) {
//                    showResponse(response.body().toString());
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<PersonaldetailModel> call, Throwable t) {
                showErrorMessage();
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }
}
