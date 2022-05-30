package com.example.gerenciadorcontatos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.gerenciadorcontatos.Contatos.Main;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, new Main()).commit();
        }
    }
}