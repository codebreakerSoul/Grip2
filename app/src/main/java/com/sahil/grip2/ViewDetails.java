package com.sahil.grip2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sahil.grip2.model.PersonaldetailModel;
import com.sahil.grip2.model.education.EducationdetailModel;
import com.sahil.grip2.remote.APIService;
import com.sahil.grip2.remote.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sahil.grip2.remote.ApiUtils.BASE_URL;

public class ViewDetails extends AppCompatActivity {
    ImageView imageView;
    TextView name,location,mobile,email,skill,startyear,endyear,edlocation,degree,organisation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        Intent intent = getIntent();
        int id = Objects.requireNonNull(intent.getExtras()).getInt("id");

        imageView= findViewById(R.id.imageView);
        name= findViewById(R.id.name);
        location= findViewById(R.id.location);
        mobile= findViewById(R.id.mobile);
        email= findViewById(R.id.email);
        skill= findViewById(R.id.skill);
        edlocation= findViewById(R.id.edlocation);
        organisation= findViewById(R.id.organisation);
        degree= findViewById(R.id.degree);
        startyear= findViewById(R.id.startyear);
        endyear= findViewById(R.id.endyear);
        loadPicture(id);

        initRetrofit(id);
        initRetrofitEducation(id);

    }

    private void initRetrofitEducation(int id) {
        APIService api = RetrofitClient.getClient(BASE_URL).create(APIService.class);
        Call<EducationdetailModel> sendbio = api.getEducationdata(id);
        sendbio.enqueue(new Callback<EducationdetailModel>() {
            @Override
            public void onResponse(Call<EducationdetailModel> call, Response<EducationdetailModel> response) {
                if(response.isSuccessful()) {
                    organisation.setText("Organisation:"+response.body().getData().getOrganisation());
                    degree.setText("Degree:"+response.body().getData().getDegree());
                    edlocation.setText("Location:"+response.body().getData().getLocation());
                    startyear.setText("Start year:"+response.body().getData().getStartYear());
                    endyear.setText("End year:"+response.body().getData().getEndYear());
                    Toast.makeText(ViewDetails.this,"Success!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EducationdetailModel> call, Throwable t) {
                showErrorMessage();
            }
        });
    }

    private void initRetrofit(int id) {
        APIService api = RetrofitClient.getClient(BASE_URL).create(APIService.class);
        Call<PersonaldetailModel> sendbio = api.getPersonaldata(id);
        sendbio.enqueue(new Callback<PersonaldetailModel>() {
            @Override
            public void onResponse(Call<PersonaldetailModel> call, Response<PersonaldetailModel> response) {
                if(response.isSuccessful()) {
                    name.setText("Name:"+response.body().getData().getName());
                    mobile.setText("Mobile:"+response.body().getData().getMobileNo());
                    location.setText("Location:"+response.body().getData().getLocation());
                    email.setText("E-mail:"+response.body().getData().getEmail());
                    skill.setText("Skill:"+response.body().getData().getSkills());
                    Toast.makeText(ViewDetails.this,"Success!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PersonaldetailModel> call, Throwable t) {
                showErrorMessage();
            }
        });
    }

    public void loadPicture(int id){
        String url = "http://139.59.65.145:9090/user/personaldetail/profilepic/"+id;
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_person_black_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .into(imageView);
    }

    public void showErrorMessage() {
        Toast.makeText(this, R.string.mssg_error_submitting_post, Toast.LENGTH_SHORT).show();
    }
}
