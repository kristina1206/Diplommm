package ru.serujimir.petstore_slepcovak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindUserActivity extends AppCompatActivity {

    EditText edUserId;
    Button btnFindUser;
    TextView tvUserInfo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finduser);
        edUserId = findViewById(R.id.edUserId);
        btnFindUser = findViewById(R.id.btnUserFind);
        tvUserInfo = findViewById(R.id.tvUserInfo);
    }
    public void Find (View v) {

        String path = String.valueOf(edUserId.getText());

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://petstore.swagger.io/v2/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        retrofit2.Call<User> call = jsonPlaceHolderApi.getUser(path);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()) {
                    tvUserInfo.setText(String.valueOf(response.code()));
                    return;
                }
                User user = response.body();
                String conteny = "";
                conteny +="Id: " + String.valueOf(user.getId())
                        + "\nUsername: " + String.valueOf(user.getUsername())
                        + "\nFirst name: " + String.valueOf(user.getfirstName())
                        + "\nLast name: " + String.valueOf(user.getlastName())
                        + "\nEmail: " + String.valueOf(user.getEmail())
                        + "\nPassword: " + String.valueOf(user.getPassword())
                        + "\nPhone: " + String.valueOf(user.getPhone())
                        + "\nStatus: " + String.valueOf(user.getUserStatus());
                tvUserInfo.setText(conteny);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                tvUserInfo.setText("Error: " + t.getMessage());
            }
        });
    }
    public void Menu (View v) {
        Intent Menu = new Intent(FindUserActivity.this, MenuActivity.class);
        startActivity(Menu);
        finish();
    }
    public void Post (View v) {
        Intent Post = new Intent(FindUserActivity.this, PostUserActivity.class);
        startActivity(Post);
        finish();
    }
}
