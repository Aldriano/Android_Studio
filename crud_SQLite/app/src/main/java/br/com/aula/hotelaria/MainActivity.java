package br.com.aula.hotelaria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import br.com.aula.hotelaria.database.DBCore;

public class MainActivity extends AppCompatActivity {

    //Escopo global da classe
    private EditText edtUsuario;
    private EditText edtSenha;

    private DBCore mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //irá inflar/estende a activity (XML) para poder manipular a UI

        mydb = new DBCore(this); //instancia da classe DBCore, se o banco não existir, cria

        inicializarObjetos();
    }
    private void inicializarObjetos() {
        //controlar os widgets/Elementos de entrada  da UI
        edtUsuario = (EditText) findViewById(R.id.email);
        edtSenha = (EditText) findViewById(R.id.password);
    }

    public void Login(View v) {
        Login objClasseLogin = new Login();
        //captura o conteúdo dos EditTexts
        objClasseLogin.setUsuario(edtUsuario.getText().toString());
        objClasseLogin.setSenha(edtSenha.getText().toString());
        if (objClasseLogin.Validarusuario() == false){
            // Atenção: use: this e nunca use getApplicationContext
            Util.Mensagem(this,"Dados Incorretos!","Atenção");

            mydb.insertLogin(objClasseLogin.getUsuario(),objClasseLogin.getSenha());
        }
        else {
            //se login OK inserir na base dados
            Intent janelaMenu = new Intent(MainActivity.this, MenuActivity.class);
            // enviando parâmetro "nome"
            janelaMenu.putExtra("nome", objClasseLogin.getUsuario());
            startActivity(janelaMenu);
            //startActivity(new Intent(MainActivity.this, Menu.class));
            //finish();  //fecha a activity/Tela/Janela
        }
    }

    public void Registro(View view) {
        //Util.Mensagem(this,"Em desenvolvimento", "titulo seu");
        startActivity(new Intent(MainActivity.this, CadastroLoginActivity.class));

    }

}