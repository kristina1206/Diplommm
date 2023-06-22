package ru.serujimir.petstore_slepcovak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class OrderSearchActivity extends AppCompatActivity {
    Button btnFind, btnBack, btnPost;
    TextView tvText;
    EditText edOrderId;
    String path, id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_search);

        btnFind = findViewById(R.id.btnFind);
        btnBack = findViewById(R.id.btnPostS);
        btnPost = findViewById(R.id.btnPostS);
        tvText = findViewById(R.id.tvText);
        edOrderId = findViewById(R.id.edOrderId);
    }
    public void Find (View v) {
        try {
            id = String.valueOf(Integer.parseInt(String.valueOf(edOrderId.getText())));
        } catch (NumberFormatException numberFormatException) {
            Toast.makeText(this, "Enter correct Id!", Toast.LENGTH_SHORT).show();
            return;
        }
        Find_S(v);
    }
    public void Find_S (View v) {
        try {
            path = String.valueOf(edOrderId.getText());
        } catch (NumberFormatException numberFormatException) {
            Toast.makeText(this, "Enter correct Id!", Toast.LENGTH_SHORT).show();
        }

        if (Integer.parseInt(String.valueOf(edOrderId.getText())) > 10) {
            Toast.makeText(this, "Id must be under 10", Toast.LENGTH_SHORT).show();
        }
        else {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://petstore.swagger.io/v2/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

            Call<Order> call = jsonPlaceHolderApi.getOrder(path);

            call.enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call, Response<Order> response) {
                    if (!response.isSuccessful()) {
                        tvText.setText(String.valueOf(response.code()));
                        return;
                    }
                    Order order = response.body();

                    String content = "";
                    content += "Pet Id: " + String.valueOf(order.getPetId())
                            + "\nQuantity: " + String.valueOf(order.getQuantity())
                            + "\nShip date: " + String.valueOf(order.getShipDate())
                            + "\nStatus: " + String.valueOf(order.getStatus());
                    tvText.setText(content);
                }

                @Override
                public void onFailure(Call<Order> call, Throwable t) {
                    tvText.setText("Error: " + t.getMessage());
                }
            });
        }
    }
    public void Menu (View v) {
        Intent Menu = new Intent(OrderSearchActivity.this, MenuActivity.class);
        startActivity(Menu);
        finish();
    }
    public void Post (View v) {
        Intent Post = new Intent(OrderSearchActivity.this, StoreActivity.class);
        startActivity(Post);
        finish();
    }
}
