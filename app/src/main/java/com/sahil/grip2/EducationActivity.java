package com.sahil.grip2;

import android.content.Intent;
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
import com.sahil.grip2.model.education.EducationdetailModel;
import com.sahil.grip2.remote.APIService;
import com.sahil.grip2.remote.ApiUtils;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EducationActivity extends AppCompatActivity {

    TextInputEditText et_start_year,et_degree,et_organisation,et_location,et_end_year;
    TextInputLayout il_start_year,il_degree,il_organisation,il_location,il_end_year;
    String start_year="",degree="",organisation="",location="",end_year="";
    private APIService mAPIService;
    String TAG = "ed";
    int id=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        Intent intent = getIntent();
        id = Objects.requireNonNull(intent.getExtras()).getInt("id");

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

        mAPIService.saveEduData(id, body).enqueue(new Callback<EducationdetailModel>() {
            @Override
            public void onResponse(Call<EducationdetailModel> call, Response<EducationdetailModel> response) {

                if(response.isSuccessful()) {
//                    showResponse(response.body().toString());
                    onBackPressed();
                    Toast.makeText(EducationActivity.this,"Success!",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                }
                else{
                    Toast.makeText(EducationActivity.this,"Try again Later!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EducationdetailModel> call, Throwable t) {
                showErrorMessage();
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
