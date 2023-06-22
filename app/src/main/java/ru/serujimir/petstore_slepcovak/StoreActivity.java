package ru.serujimir.petstore_slepcovak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StoreActivity extends AppCompatActivity {

    EditText edOrderPostId, edOrderPedId, edOrderQuantityPost, edOrderShipDate, edOrderStatusPost;
    Button btnOrderBack, btnFindOrder, btnOrderPost;
    Switch swOrder;
    int id, pedid, quantity;
    String shipdate, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        edOrderPostId = findViewById(R.id.edOrderPostId);
        edOrderPedId = findViewById(R.id.edOrderPedId);
        edOrderQuantityPost = findViewById(R.id.edOrderQuantityPost);
        edOrderShipDate = findViewById(R.id.edOrderShipDate);
        edOrderStatusPost = findViewById(R.id.edOrderStatusPost);

        btnOrderBack = findViewById(R.id.btnOrderBack);
        btnFindOrder = findViewById(R.id.btnFindOrder);
        btnOrderPost = findViewById(R.id.btnOrderPost);

        swOrder = findViewById(R.id.swOrder);

        btnOrderPost.setClickable(false);

        swOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnOrderPost.setClickable(true);
                } else {
                    btnOrderPost.setClickable(false);
                }
            }
        });
    }

    public void Post(View v) {
        try {
            id = Integer.parseInt(String.valueOf(edOrderPostId.getText()));
            pedid = Integer.parseInt(String.valueOf(edOrderPedId.getText()));
            quantity = Integer.parseInt(String.valueOf(edOrderQuantityPost.getText()));
            shipdate = String.valueOf(edOrderShipDate.getText());
            status = String.valueOf(edOrderStatusPost.getText());
        } catch (Exception e) {
            Toast.makeText(this, "Some error, don't worry!", Toast.LENGTH_SHORT).show();
        }


        if(
                !edOrderPostId.getText().toString().isEmpty()
                && !edOrderPedId.getText().toString().isEmpty()
                && !edOrderQuantityPost.getText().toString().isEmpty()
                && !edOrderShipDate.getText().toString().isEmpty()
                && !edOrderStatusPost.getText().toString().isEmpty()) {
            Post_S(v);
        }
        else {
            Toast.makeText(this, "Fill all fields!", Toast.LENGTH_SHORT).show();
        }
    }

    public void Post_S(View v) {
        Boolean confirmation = true;

        Order order = new Order();

        order.setId(id);
        order.setPetId(pedid);
        order.setQuantity(quantity);
        order.setShipDate(shipdate);
        order.setStatus(status);
        order.setComplete(confirmation);


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://petstore.swagger.io/v2/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Void> call = jsonPlaceHolderApi.postOrder(order);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(StoreActivity.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(StoreActivity.this, "Post successfull!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(StoreActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void Menu (View v) {
        Intent Menu = new Intent(StoreActivity.this, MenuActivity.class);
        startActivity(Menu);
    }
    public void Find (View v) {
        Intent Find = new Intent(StoreActivity.this, OrderSearchActivity.class);
        startActivity(Find);
    }
}