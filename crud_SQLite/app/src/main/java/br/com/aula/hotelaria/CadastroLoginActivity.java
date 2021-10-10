package br.com.aula.hotelaria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.aula.hotelaria.database.DBCore;

public class CadastroLoginActivity extends AppCompatActivity {

    EditText ediEmail;
    EditText ediSenha;
    Button btnCadastar;
    DBCore mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //desativa a Action Bar
        setContentView(R.layout.activity_cadastro_login);

        inicializarObjetos();
        btnCadastar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SalvarLogin();
            }
        });
    }


    private void inicializarObjetos() {
        ediEmail = (EditText) findViewById(R.id.emailCadastrar);
        ediSenha = (EditText) findViewById(R.id.passwordCadastrar);
        btnCadastar  = (Button) findViewById(R.id.CadastrarLogin);
        mydb = new DBCore(this);
    }

    public void SalvarLogin(){
        if(ediEmail.getText().toString().isEmpty() || ediSenha.getText().toString().isEmpty()){
            Util.Mensagem(this,"Informar o Email e senha!","Atenção");
            return;
        }
        try {
            mydb.insertLogin(ediEmail.getText().toString(),ediSenha.getText().toString());
            finish();
        }catch (Exception ERRO){
            Util.Mensagem(this,"Erro: "+ERRO.getMessage(), "Atenção");
        }
    }
}