package ru.serujimir.petstore_slepcovak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostUserActivity extends AppCompatActivity {

    Button btnUserMenu, btnUserFind, btnUserPost;
    EditText edUserPostId, edUserPostUsername, edUserPostFirstname,
            edUserPostLastname, edUserPostEmail, edUserPostPassword,
            edUserPostPhone, edUserPostStatus;

    String txtId, txtUsername, txtFirstname, txtLastname, txtEmail, txtPassword,
            txtPhone, txtStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postuser);

        btnUserMenu = findViewById(R.id.btnUserMenu);
        btnUserFind = findViewById(R.id.btnUserFInd);
        btnUserPost = findViewById(R.id.btnUserPost);

        edUserPostId = findViewById(R.id.edUserPostId);
        edUserPostUsername = findViewById(R.id.edUserPostUsername);
        edUserPostFirstname = findViewById(R.id.edUserPostFirstName);
        edUserPostLastname = findViewById(R.id.edUserPostLastName);
        edUserPostEmail = findViewById(R.id.edUserPostEmail);
        edUserPostPassword = findViewById(R.id.edUserPostPassword);
        edUserPostPhone = findViewById(R.id.edUserPostPhone);
        edUserPostStatus = findViewById(R.id.edUserPostStatus);
    }

    public void Post(View v) {
        txtId = String.valueOf(edUserPostId.getText());
        txtUsername = String.valueOf(edUserPostUsername.getText());
        txtFirstname = String.valueOf(edUserPostFirstname.getText());
        txtLastname = String.valueOf(edUserPostLastname.getText());
        txtEmail = String.valueOf(edUserPostEmail.getText());
        txtPassword = String.valueOf(edUserPostPassword.getText());
        txtPhone = String.valueOf(edUserPostPhone.getText());
        txtStatus = String.valueOf(edUserPostStatus.getText());

        if(!txtId.isEmpty() && !txtUsername.isEmpty()
                && !txtFirstname.isEmpty() && !txtLastname.isEmpty()
                && !txtEmail.isEmpty() && !txtPassword.isEmpty()
                && !txtPhone.isEmpty() && !txtStatus.isEmpty()) {
            Post_S(v);
        }
        else {
            Toast.makeText(this, "Fill all fields!", Toast.LENGTH_SHORT).show();
        }
    }

    public void Post_S(View v) {

        int id = Integer.parseInt(txtId);
        int status = Integer.parseInt(txtStatus);
        User user = new User();
        user.setId(id);
        user.setUsername(txtUsername);
        user.setfirstName(txtFirstname);
        user.setlastName(txtLastname);
        user.setEmail(txtEmail);
        user.setPassword(txtPassword);
        user.setPhone(txtPhone);
        user.setStatus(status);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://petstore.swagger.io/v2/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Void> call = jsonPlaceHolderApi.postUser(user);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(PostUserActivity.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(PostUserActivity.this, "User posted!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(PostUserActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    public void Menu (View v) {
        Intent Menu = new Intent(PostUserActivity.this, MenuActivity.class);
        startActivity(Menu);
        finish();
    }
    public void Back (View v) {
        Intent Back = new Intent(PostUserActivity.this, FindUserActivity.class);
        startActivity(Back);
        finish();
    }
}
