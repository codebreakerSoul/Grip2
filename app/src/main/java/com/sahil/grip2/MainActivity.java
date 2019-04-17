package com.sahil.grip2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.sahil.grip2.model.PersonaldetailModel;
import com.sahil.grip2.remote.APIService;
import com.sahil.grip2.remote.ApiUtils;
import com.sahil.grip2.remote.RetrofitClient;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sahil.grip2.remote.ApiUtils.BASE_URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView mResponseTv;
    TextInputEditText et_name,et_mobile,et_skill,et_links,et_location,et_email;
    TextInputLayout il_skill,il_mobile,il_name,il_links,il_location,il_email;
    String name="",mobile="",skill="",links="",location="",email="";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String mobilePattern = "[1-9][0-9]{9}";
    Button btnviewdetails,btnedudetails,btnprodetails;
    int id=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        id = Objects.requireNonNull(intent.getExtras()).getInt("id");

        Button submitBtn = findViewById(R.id.btn_submit);
        mResponseTv = findViewById(R.id.tv_response);
        et_name = findViewById(R.id.et_name);
        et_mobile = findViewById(R.id.et_mobile);
        et_skill = findViewById(R.id.et_skill);
        et_links = findViewById(R.id.et_links);
        et_location = findViewById(R.id.et_location);
        et_email = findViewById(R.id.et_email);

        il_name = findViewById(R.id.il_name);
        il_mobile = findViewById(R.id.il_mobile);
        il_skill = findViewById(R.id.il_skill);
        il_links = findViewById(R.id.il_links);
        il_location = findViewById(R.id.il_location);
        il_email = findViewById(R.id.il_email);

        btnprodetails = findViewById(R.id.btn_pro_detail);
        btnedudetails = findViewById(R.id.btn_education_detail);
        btnviewdetails = findViewById(R.id.btn_showdetails);

        btnviewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,ViewDetails.class);
                in.putExtra("id", id);
                startActivity(in);
            }
        });
        btnedudetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,EducationActivity.class);
                in.putExtra("id", id);
                startActivity(in);
            }
        });
        btnprodetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,ProfessionalActivity.class);
                in.putExtra("id", id);
                startActivity(in);
            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email=et_email.getText().toString();
                mobile=et_mobile.getText().toString();
                links=et_links.getText().toString();
                location=et_location.getText().toString();
                skill=et_skill.getText().toString();
                name=et_name.getText().toString();

                if(!email.matches(emailPattern)){
                    il_email.setError("Enter Valid e-mail id");
                }
                else if(!mobile.matches(mobilePattern)){
                    il_mobile.setError("Enter Valid mobile number");
                }
                else if(name.isEmpty() || name.equals("")){
                    il_mobile.setError("Enter Valid name");
                }
                else {
                    JsonObject data = new JsonObject();
                    data.addProperty("skills", skill);
                    data.addProperty("mobile_no", mobile);
                    data.addProperty("name", name);
                    data.addProperty("links", links);
                    data.addProperty("location", location);
                    data.addProperty("email", email);
                    sendPost( data.toString());
                }
            }
        });
    }

    public void showErrorMessage() {
        Toast.makeText(this, R.string.mssg_error_submitting_post, Toast.LENGTH_SHORT).show();
    }

    public void sendPost(String body) {

        APIService api = RetrofitClient.getClient(BASE_URL).create(APIService.class);
        Call<PersonaldetailModel> sendbio = api.savePost(id, body);
        sendbio.enqueue(new Callback<PersonaldetailModel>() {
            @Override
            public void onResponse(Call<PersonaldetailModel> call, Response<PersonaldetailModel> response) {
                if(response.isSuccessful()) {
                    showResponse(response.body().getData().toString());
                    et_email.setText("");
                    et_mobile.setText("");
                    et_links.setText("");
                    et_location.setText("");
                    et_skill.setText("");
                    et_name.setText("");
                    Toast.makeText(MainActivity.this,"Success!",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<PersonaldetailModel> call, Throwable t) {
                showErrorMessage();
            }
        });
    }

    public void showResponse(String response) {
        if(mResponseTv.getVisibility() == View.GONE) {
            mResponseTv.setVisibility(View.VISIBLE);
        }
        mResponseTv.setText(response);
    }
}
