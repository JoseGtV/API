package com.example.api.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.api.API.ApiCliente;
import com.example.api.API.UsuarioService;
import com.example.api.Activity.UsuarioActivity;
import com.example.api.Models.Usuario;
import com.example.api.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioHolder> {
    private final List<Usuario> usuarios;
    Context context;

    public UsuarioAdapter(List<Usuario> usuarios, Context context) {
        this.usuarios = usuarios;
        this.context = context;
    }

    @Override
    public UsuarioHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UsuarioHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_usuario, parent, false));
    }

    @Override
    public void onBindViewHolder(UsuarioHolder holder, int position) {
        holder.nome.setText(usuarios.get(position).getId() + " - " + usuarios.get(position).getNome());
        holder.email.setText(usuarios.get(position).getEmail());
        holder.btnexcluir.setOnClickListener(view -> removerItem(position));
        holder.btnEditar.setOnClickListener(view -> editarItem(position));
    }

    private void removerItem(int position) {
        int id = usuarios.get(position).getId();
        UsuarioService apiService = ApiCliente.getUsuarioService();
        Call<Void> call = apiService.deleteUsuario(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    usuarios.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, usuarios.size());
                    Toast.makeText(context,"Excluido com sucesso!", Toast.LENGTH_SHORT).show();
                }else{
                    Log.e("Exclusao", "Erro ao excluir.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Exclusao", "Erro ao excluir.");
            }
        });
    }

    private void editarItem(int position) {
        int id = usuarios.get(position).getId();
        Intent i = new Intent(context, UsuarioActivity.class);
        i.putExtra("id", id);
        context.startActivity(i);


    }

    @Override
    public int getItemCount() {
        return usuarios != null ? usuarios.size() : 0;
    }

    public class UsuarioHolder extends RecyclerView.ViewHolder {
        public TextView nome;
        public TextView email;
        public ImageView btnexcluir;
        public ImageView btnEditar;

        public UsuarioHolder(View itemView) {
            super(itemView);
            nome = (TextView) itemView.findViewById(R.id.txtNome);
            email = (TextView) itemView.findViewById(R.id.txtEmail);
            btnexcluir = (ImageView) itemView.findViewById(R.id.btnExcluir);
            btnEditar = (ImageView) itemView.findViewById(R.id.btnEditar);
        }
    }


}