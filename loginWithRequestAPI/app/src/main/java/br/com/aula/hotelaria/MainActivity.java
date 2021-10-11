package br.com.aula.hotelaria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

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

    public void LoginAPI(View view) {
        ConsumirAPI();
    }

    private void ConsumirAPI() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "https://aldriano.com.br/unip2021-ads/api/validalogin.php";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("UserName", edtUsuario.getText());
            jsonBody.put("UserPwd", edtSenha.getText());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //System.out.println(response);
                try{
                    String isStatus = response.getString("status");
                    if (isStatus.equalsIgnoreCase("ok")) {
                        chamarMenu();
                    }
                }catch (JSONException e){
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error.printStackTrace();
                String responseBody="";
                try {
                    responseBody = new String(error.networkResponse.data, "utf-8");
                    JSONObject jsonObject = new JSONObject(responseBody);
                    Toast.makeText(getApplicationContext(),jsonObject.getString("error"),Toast.LENGTH_LONG).show();
                }catch (Exception erro){
                    Toast.makeText(getApplicationContext(),"Exception: "+responseBody,Toast.LENGTH_LONG).show();
                    Log.v("minhaTAG", responseBody);
                }
            }
        });
        requestQueue.add(jsonObjectRequest);
        //#DICAS: https://stackoverflow.com/questions/67647393/post-request-with-volley-with-headers-and-body-java-android-studio
        // https://nabeelj.medium.com/making-a-simple-get-and-post-request-using-volley-beginners-guide-ee608f10c0a9
    }

    public  void chamarMenu(){
        Intent janelaMenu = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(janelaMenu);
    }

}