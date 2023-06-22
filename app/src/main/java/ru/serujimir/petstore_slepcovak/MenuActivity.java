package ru.serujimir.petstore_slepcovak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    public void goPet (View v) {
        Intent Pet = new Intent(MenuActivity.this, PetActivity.class);
        startActivity(Pet);
        finish();
    }
    public void goStore (View v) {
        Intent Store = new Intent(MenuActivity.this, OrderSearchActivity.class);
        startActivity(Store);
        finish();
    }
    public void User (View v) {
        Intent User = new Intent(MenuActivity.this, FindUserActivity.class);
        startActivity(User);
        finish();
    }
}