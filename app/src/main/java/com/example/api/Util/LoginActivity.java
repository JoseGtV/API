package com.example.api.Util;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.api.Activity.UsuarioActivity;
import com.example.api.R;

public class LoginActivity extends AppCompatActivity {

    EditText txtLogin, txtSenha;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtLogin = findViewById(R.id.loginAdmin);
        txtSenha = findViewById(R.id.senhaAdmin);
        btnLogin = findViewById(R.id.btnLoginAdmin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String login = txtLogin.getText().toString().trim();
        String senha = txtSenha.getText().toString().trim();

        if (login.equals("admin") && senha.equals("123")) {
            // Login bem-sucedido, redireciona para a tela de usuário
            Intent intent = new Intent(LoginActivity.this, UsuarioActivity.class);
            startActivity(intent);
            finish(); // Finaliza a atividade de login para que o usuário não possa voltar para ela pressionando "Voltar"
        } else {
            // Login inválido, exibe uma mensagem de erro
            Toast.makeText(LoginActivity.this, "Login ou senha incorretos", Toast.LENGTH_SHORT).show();
        }
    }
}
