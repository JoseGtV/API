package com.example.api.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api.API.ApiCliente;
import com.example.api.API.UsuarioService;
import com.example.api.Models.Usuario;
import com.example.api.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioActivity extends AppCompatActivity {

    Button btnSalvar;
    UsuarioService apiService;
    TextView txtemail, txtsenha, txtnome;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_acticity);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        apiService = ApiCliente.getUsuarioService();
        txtemail= (TextView) findViewById(R.id.txtEmailUsuario);
        txtnome = (TextView) findViewById(R.id.txtNomeUsuario);
        txtsenha = (TextView) findViewById(R.id.txtSenhaUsuario);
        id = getIntent().getIntExtra("id",0);
        if(id > 0){
            apiService.getUsuarioById(id).enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if(response.isSuccessful()){
                        txtemail.setText(response.body().getEmail());
                        txtnome.setText(response.body().getNome());
                        txtsenha.setText(response.body().getSenha());
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Log.e("Obter Usuario", "Error ao obter usuario");
                }
            });
        }
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario = new Usuario();
                usuario.setEmail(txtemail.getText().toString());
                usuario.setNome(txtnome.getText().toString());
                usuario.setSenha(txtsenha.getText().toString());

                if(id == 0)
                {
                    inserirUsuario(usuario);
                }else {
                    usuario.setId(id);
                    editarUsuario(usuario);
                }
            }

            private void inserirUsuario(Usuario usuario) {
                Call<Usuario> call = apiService.postUsuario(usuario);
                call.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if(response.isSuccessful()){
                            Usuario createdPost = response.body();
                            Toast.makeText(UsuarioActivity.this,"Inserido com sucesso!",Toast.LENGTH_SHORT).show();
                        }else{
                            Log.e("Inserir","Erro ao criar" + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        Log.e("Inserir","Erro ao criar" + t.getMessage());
                    }
                });

            }

            private void editarUsuario(Usuario usuario) {
                Call<Usuario> call = apiService.putUsuario(id,usuario);
                call.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if(response.isSuccessful()){
                            Usuario createdPost = response.body();
                            Toast.makeText(UsuarioActivity.this,"Editado com sucesso",Toast.LENGTH_SHORT).show();
                        }else {
                            Log.e("Editar","Erro ao editar" + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        Log.e("Editar","Erro ao editar" + t.getMessage());
                    }
                });
            }
        });

    }
}