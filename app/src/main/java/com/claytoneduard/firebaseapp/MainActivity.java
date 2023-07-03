package com.claytoneduard.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    //capturar referenca do FireBase/ para o nó raiz do banco
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // salvando no modo basico
        //reference.child("pontos").setValue("200");

        // pega a referencia do nó usuarios no banco
        DatabaseReference usuarios = reference.child("usuarios");

        // criando um ususario
        Usuario usuario = new Usuario();
        usuario.setNome("Nathalia");
        usuario.setSobrenome("Prateado");
        usuario.setIdade(32);
        //usuarios.child("002").setValue(usuario);


        // pega a referencia do nó usuarios no banco
        DatabaseReference produtos = reference.child("produtos");

        Produto produto = new Produto();
        produto.setDescricao("Iphone X");
        produto.setMarca("Apple");
        produto.setPreço(7.999);

        produtos.child("001").setValue(produto);

    }
}