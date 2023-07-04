package com.claytoneduard.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    /*
    //capturar referenca do FireBase/ para o nó raiz do banco
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    // metodo para autenticacao
    private FirebaseAuth firebaseAuthUser = FirebaseAuth.getInstance();
     */
    private ImageView imageFoto;
    private Button buttonUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonUpload = findViewById(R.id.buttonUpload);
        imageFoto = findViewById(R.id.imageFoto);

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Configura para imagem ser salva em memoria
                imageFoto.setDrawingCacheEnabled(true);
                imageFoto.buildDrawingCache();

                //Recupera bitmap da imagem(imagem a ser carregada)
                Bitmap bitmap = imageFoto.getDrawingCache();

                //Comprimo bitmap para um formato png/jpeg
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos);

                //conver o baos para pixel brutos em uma matriz de bytes
                // (dados da imagem )
                byte[] dadosImagem = baos.toByteArray();

                //Define nos para o starage
                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                StorageReference imagens = storageReference.child("imagens");
                StorageReference imagemRef = imagens.child("celular.jpg");

               imagemRef.getDownloadUrl().addOnSuccessListener(MainActivity.this,new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(MainActivity.this).load(uri).into(imageFoto);
                    }
                }).addOnFailureListener(MainActivity.this, new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(
                               MainActivity.this,
                               "Erro ao fazer download: " + e.getMessage(),
                               Toast.LENGTH_LONG
                       ).show();
                   }
               });


                //deletar aquivo
                /*imagemRef.delete().addOnFailureListener(MainActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Erro ao deletar", Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(MainActivity.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this, "Sucesso ao deletar arquivo", Toast.LENGTH_LONG).show();
                    }
                });
                    */

                //nome da imagem
                //String nomeArquivo = UUID.randomUUID().toString();
                //StorageReference imagemRef = imagens.child(nomeArquivo+".jpeg");

                // retorna objeto que irá controlar o upload
              /*  UploadTask uploadTask = imagemRef.putBytes(dadosImagem);

                uploadTask.addOnFailureListener(MainActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Upload da imagem falhou: " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(MainActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imagemRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                //recuperar a url
                                Uri url = task.getResult();
                                Toast.makeText(MainActivity.this, "Sucesso ao fazer upload: " + url.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });*/

            }
        });

                // pega a referencia do nó usuarios no banco
                //DatabaseReference usuarios = reference.child("usuarios");

                // filtros - selecionar usuario por id unico, id automatico do Firebase
                //DatabaseReference usuariosPesquisa = usuarios.child("-NZS58EiwbZjzTAc55tQ");

                // usando query para pesquisa 1° vc ordena os dados e depois aplica os filtros
                //Query usuariosPesquisa = usuarios.orderByChild("nome").equalTo("Clayton");

                // ordernar por id e retornar os dois primeiros
                //Query usuariosPesquisa = usuarios.orderByKey().limitToFirst(2);

                //ordernar por id os dois ultimos
                //Query usuariosPesquisa = usuarios.orderByKey().limitToLast(2);

                // filtro de maior ou igual >= 40
                //Query usuariosPesquisa = usuarios.orderByChild("idade").startAt(40);

                // filtro de menor ou igual <= 22
                //Query usuariosPesquisa = usuarios.orderByChild("idade").endAt(22);

                // filtro entre dois valores
                //Query usuariosPesquisa = usuarios.orderByChild("idade").startAt(18).endAt(30);

                // filtro por palavras iniciando em c, anplia a busca
     /*   Query usuariosPesquisa = usuarios.orderByChild("nome").
                startAt("C").endAt("C" + "\uf8ff");
        usuariosPesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               /* Usuario dadosUsuario = snapshot.getValue(Usuario.class);
                Log.i("Dados usuario", " nome: "+dadosUsuario.getNome() + ", sobrenome: "+ dadosUsuario.getSobrenome() + ", idade: "+ dadosUsuario.getIdade());

                Log.i("Dados usuario", snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*Usuario usuario = new Usuario();
        usuario.setNome("Joana");
        usuario.setSobrenome("Maria");
        usuario.setIdade(54);
        // metodo push, cria o id auto incremento
        usuarios.push().setValue(usuario);
        */
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

        /* verifica se o usuario esta logado
        if(firebaseAuthUser.getCurrentUser() !=null){
            Log.i("CreateUser", "Usuario logado");
        }else {
            Log.i("CreateUser", "Usuario nao logado");
        }*/

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