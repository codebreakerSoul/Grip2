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
import com.sahil.grip2.model.professional.ProfessionaldetailModel;
import com.sahil.grip2.remote.APIService;
import com.sahil.grip2.remote.ApiUtils;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfessionalActivity extends AppCompatActivity {

    TextInputEditText et_start_year,et_organisation,et_designation,et_end_year;
    TextInputLayout il_start_year,il_organisation,il_designation,il_end_year;
    String start_year="",organisation="",designation="",end_year="";
    private APIService mAPIService;
    String TAG = "pro";
    int id=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional);

        Intent intent = getIntent();
        id = Objects.requireNonNull(intent.getExtras()).getInt("id");

        Button submitBtn = findViewById(R.id.btn_submit);
        et_start_year = findViewById(R.id.et_start_year);
        et_organisation = findViewById(R.id.et_organisation);
        et_end_year = findViewById(R.id.et_end_year);
        et_designation = findViewById(R.id.et_designation);


        il_start_year = findViewById(R.id.il_start_year);
        il_designation = findViewById(R.id.il_designation);
        il_organisation = findViewById(R.id.il_organisation);
        il_end_year = findViewById(R.id.il_end_year);


        mAPIService = ApiUtils.getAPIService();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                start_year=et_start_year.getText().toString();
                designation=et_designation.getText().toString();
                organisation=et_organisation.getText().toString();
                end_year=et_end_year.getText().toString();

                if(designation.isEmpty() || designation.equals("")){
                    il_designation.setError("Enter Valid designation");
                }
                else {
                    JsonObject data = new JsonObject();
                    data.addProperty("start_year", start_year);
                    data.addProperty("designation", designation);
                    data.addProperty("organisation", organisation);
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

        mAPIService.saveProData(id, body).enqueue(new Callback<ProfessionaldetailModel>() {
            @Override
            public void onResponse(Call<ProfessionaldetailModel> call, Response<ProfessionaldetailModel> response) {

                if(response.isSuccessful()) {
//                    showResponse(response.body().toString());
                    onBackPressed();
                    Toast.makeText(ProfessionalActivity.this,"Success!",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                }
                else {
                    Toast.makeText(ProfessionalActivity.this,"Try again Later!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfessionaldetailModel> call, Throwable t) {
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
