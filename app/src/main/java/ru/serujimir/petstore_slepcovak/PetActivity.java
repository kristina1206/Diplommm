package ru.serujimir.petstore_slepcovak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PetActivity extends AppCompatActivity {

    TextView name;
    String photoURL;
    String inpId;
    String Url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);
        name = findViewById(R.id.tvName);
        name.setText("");
    }

    public void petInfo(View v) {
        name = findViewById(R.id.tvName);
        EditText etid = findViewById(R.id.petId);
        ImageView image = findViewById(R.id.imgView);
        inpId = String.valueOf(etid.getText());

        String path = String.valueOf(etid.getText());
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://petstore.swagger.io/v2/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        retrofit2.Call<Pet> call = jsonPlaceHolderApi.getPet(path);

        call.enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(retrofit2.Call<Pet> call, Response<Pet> response) {
                if (!response.isSuccessful()) {
                    name.setText(String.valueOf(response.code()));
                    image.setImageResource(R.drawable.nya);
                    return;
                }

                Pet pet = response.body();
                Category category = pet.getCategory();
                List<Tag> tagList = pet.getTags();
                Tag tag = tagList.get(0);
                String content = "";
                content += "Name: " + String.valueOf(pet.getName())
                        + "\nStatus: " + String.valueOf(pet.getStatus())
                        + "\nCategory: " + String.valueOf(category.getName())
                        + "\nColor: " + String.valueOf(tag.getName());

                name.setText(content);

                List<String> photoURLS = pet.getPhotoUrls();
                photoURL = photoURLS.get(0);
                if (photoURL.equals("string")|| photoURL.equals("[\"string\"]")
                || photoURL.equals(null) || photoURL.equals("") || photoURL.isEmpty()) {
                    image.setImageResource(R.drawable.nya);
                }
                else
                {
                    Picasso.get().load(photoURL).into(image);
                }
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                name.setText("errror" + t.getMessage());
                image.setImageResource(R.drawable.nya);
            }
        });

//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("https://petstore.swagger.io/v2/pet/" + inpId)
//                .get()
//                .build();
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//        try {
//            Response response = client.newCall(request).execute();
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    name.setText("Error");
//                    status.setText("Error");
//                    category.setText("Error");
//                    id.setText("Error");
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    String responceData = response.body().string();
//                    try {
//                        JSONObject json = new JSONObject(responceData);
//
//                        JSONObject categoryObj = json.getJSONObject("category");
//
//                        JSONArray array = json.getJSONArray("photoUrls");
//                        String txtPhotoUrl = array.getString(0);
//
//                        String txtCategoryName = categoryObj.getString("name");
//                        String txtId = json.getString("id");
//                        String txtName = json.getString("name");
//                        String txtStatus = json.getString("status");
//
//
//                        runOnUiThread(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                try {
//                                    if(!txtPhotoUrl.equals("[\"string\"]") && !txtPhotoUrl.equals(null)) {
//                                        Url = txtPhotoUrl;
//                                        Picasso.get().load(Url).into(image);
//                                    }
//                                    else {
//                                        image.setImageResource(R.drawable.nya);
//                                    }
//                                } catch (Exception e) {
//                                    throw new RuntimeException(e);
//                                }
//                                Log.e("Testt",Url);
//                                category.setText("Категория: " + txtCategoryName);
//                                name.setText("Имя: " + txtName);
//                                status.setText("Статус: " + txtStatus);
//                                id.setText("Id: " + txtId);
//                            }
//                        });
//
//
//
//                    } catch (JSONException e) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                e.printStackTrace();
//                                name.setText("Error");
//                                status.setText("Error");
//                                category.setText("Error");
//                                id.setText("Error");
//                                image.setImageResource(R.drawable.nya);
//                            }
//                        });
//
//                    }
//                }
//            });
//        } catch (IOException e) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    e.printStackTrace();
//                    name.setText("Error");
//                    status.setText("Error");
//                    category.setText("Error");
//                    id.setText("Error");
//                    image.setImageResource(R.drawable.nya);
//                }
//            });
//
//        }
    }
    public void goPost (View v) {
        Intent Post = new Intent(PetActivity.this, PostActivity.class);
        startActivity(Post);
        finish();
    }
    public void goMenu (View v) {
        Intent Menu = new Intent(PetActivity.this, MenuActivity.class);
        startActivity(Menu);
        finish();
    }
}