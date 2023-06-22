package ru.serujimir.petstore_slepcovak;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostActivity extends AppCompatActivity {

    Context context_test;
    List<String> photoUrls;
    List<Tag> tagList;
    Button btnMenu, btnBack, btnPost, btnTest;
    EditText edId, edCategory, edName, edUrl, edStatus, edColor;
    String txtId, txtCategory, txtName, txtUrl, txtStatus, txtColor;
    String Url;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        btnMenu = findViewById(R.id.btnMenu);
        btnBack = findViewById(R.id.btnBack);
        btnPost = findViewById(R.id.btnPost);
        btnTest = findViewById(R.id.btnTest);

        edId = findViewById(R.id.edId);
        edCategory = findViewById(R.id.edCategory);
        edName = findViewById(R.id.edName);
        edUrl = findViewById(R.id.edUrl);
        edStatus = findViewById(R.id.edStatus);
        edColor = findViewById(R.id.edColor);
    }

    public void Test (View v) {
        Url = String.valueOf(edUrl.getText());
        Log.e("TEst", Url);
        if(Url.isEmpty()) {
            Toast.makeText(context, "Link field is Null", Toast.LENGTH_SHORT).show();
        }
        else {
            AlertDialog.Builder builder = new MaterialAlertDialogBuilder(this);
            ConstraintLayout view = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_test, null);
            builder.setView(view);
            ImageView image = (ImageView) view.findViewById(R.id.work);
            Button close = (Button) view.findViewById(R.id.btnClose);
            try {
                Picasso
                        .get()
                        .load(Url)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .priority(Picasso.Priority.HIGH)
                        .into(image);
            } catch (IllegalArgumentException illegalArgumentException) {
                Toast.makeText(context, "NUll/Error Link!", Toast.LENGTH_SHORT).show();
            }

            AlertDialog alertDialog = builder.create();
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
            Url = String.valueOf(edUrl.getText());
            edUrl.setText("");
            Log.e("TEst", Url);
        }
    }
    public void Post(View v) {
        txtId = String.valueOf(edId.getText());
        txtCategory = String.valueOf(edCategory.getText());
        txtName = String.valueOf(edName.getText());
        txtUrl = String.valueOf(edUrl.getText());
        txtStatus = String.valueOf(edStatus.getText());
        txtColor = String.valueOf(edColor.getText());

        if(!txtId.isEmpty() && !txtCategory.isEmpty()
                && !txtStatus.isEmpty() && !txtName.isEmpty()
                && !txtColor.isEmpty() && !txtUrl.isEmpty())
        {
            Post_s(v);
        }
        else {
            Toast.makeText(this, "Please fill all inputs!", Toast.LENGTH_SHORT).show();
        }
    }

    public void Post_s(View v) {

        int id = Integer.parseInt(txtId);


        Pet pet = new Pet();
        Category category = new Category();
        tagList = new ArrayList<Tag>();

        photoUrls = new ArrayList<String>();

        Tag tag = new Tag();
        tag.setName(txtColor);
        tagList.add(tag);

        photoUrls.add(txtUrl);

        category.setName(txtCategory);

        pet.setCategory(category);
        pet.setTags(tagList);
        pet.setName(txtName);
        pet.setStatus(txtStatus);

        pet.setPhotoUrls(photoUrls);
        pet.setId(id);


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://petstore.swagger.io/v2/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Void> call = jsonPlaceHolderApi.postPet(pet);


        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PostActivity.this, "Post successfull!", Toast.LENGTH_SHORT).show();
                } else if (!response.isSuccessful()) {
                    btnPost.setText("!Error!");
                    return;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                btnPost.setText("!Error! " + t.getMessage());
            }
        });

    }


    public void Menu (View v) {
        Intent Menu = new Intent(PostActivity.this, MenuActivity.class);
        startActivity(Menu);
        finish();
    }
    public void Back (View v) {
        Intent Back = new Intent(PostActivity.this, PetActivity.class);
        startActivity(Back);
        finish();
    }
}