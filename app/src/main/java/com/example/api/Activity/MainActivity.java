package com.example.api.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.api.API.ApiCliente;
import com.example.api.API.UsuarioService;
import com.example.api.Adapter.UsuarioAdapter;
import com.example.api.Models.Usuario;
import com.example.api.R;
import com.example.api.Util.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerUsuario;
    UsuarioAdapter usuarioAdapter;
    UsuarioService apiService;
    List<Usuario> listaUsuarios;
    FloatingActionButton btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerUsuario = (RecyclerView) findViewById(R.id.recyclerUsuario);
        btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        apiService = ApiCliente.getUsuarioService();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        Intent i = new Intent(MainActivity.this, UsuarioActivity.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        obterUsuarios();
    }

    private void configurarRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerUsuario.setLayoutManager(layoutManager);
        usuarioAdapter = new UsuarioAdapter(listaUsuarios, this);
        recyclerUsuario.setAdapter(usuarioAdapter);
        recyclerUsuario.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void obterUsuarios() {
        retrofit2.Call<List<Usuario>> call = apiService.getUsuarios();
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                listaUsuarios = response.body();
                configurarRecyclerView();
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("TESTE","Error" + t.getMessage());
            }
        });
    }



}