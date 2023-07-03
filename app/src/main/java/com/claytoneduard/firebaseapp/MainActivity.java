package com.claytoneduard.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    //capturar referenca do FireBase/ para o nó raiz do banco
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    // metodo para autenticacao
    private FirebaseAuth firebaseAuthUser = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        // deslogar usuario
        firebaseAuthUser.signOut();

        // entrar com usuario cadastrado
        firebaseAuthUser.signInWithEmailAndPassword("clayton@gmail.com" ,
                "cl12345").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i("signI", "Sucesso ao logar usuario");
                }else{
                    Log.i("signI", "Erro ao logar usuario");
                }
            }
        });
*/

        /* verifica se o usuario esta logado*/
        if(firebaseAuthUser.getCurrentUser() !=null){
            Log.i("CreateUser", "Usuario logado");
        }else {
            Log.i("CreateUser", "Usuario nao logado");
        }

        // criar um usuario para autenticar
       /* firebaseAuthUser.createUserWithEmailAndPassword(
                "clayton@gmail.com" ,
                "cl12345").
                addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i("CreateUser", "Sucesso ao cadastrar o usuario");
                        }else{
                            Log.i("CreateUser", "Erro ao cadastrar o usuario");
                        }
                    }
                });
        */


        // salvando no modo basico
        //reference.child("pontos").setValue("200");

     /*
        // pega a referencia do nó usuarios no banco
        DatabaseReference usuarios = reference.child("usuarios").child("001");
        DatabaseReference produtos = reference.child("produtos");

        // salvar dados
       /*   // criando um ususario
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



        //recuperar dados
        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("FIREBASE", snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

      */
    }
}