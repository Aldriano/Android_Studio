package br.com.aula.hotelaria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import br.com.aula.hotelaria.database.DBCore;

public class ConsultaLoginActivity extends AppCompatActivity {

    public static DBCore mydb;
    public static Cursor cursor; // o objeto "cursor" irár armazenar os dados recuperados do banco
    public static ListView listaView;
    public static CustomAdapter adapter;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_login);

        context = this;

        inicializaComponentes();
    }

    private void inicializaComponentes() {
        mydb = new DBCore(this); //instancia da classe DBCore, se o banco não existir, cria
        cursor = mydb.carregaDados();

        listaView = (ListView) findViewById(R.id.listViewConsultaLogin);

        //https://www.devmedia.com.br/criando-um-crud-com-android-studio-e-sqlite/32815
        //https://www.thiengo.com.br/gridlayout-no-android-entendendo-e-utilizando
        //No trecho onde o SimpleCursorAdapter é instanciado, vale uma atenção especial, pois é onde se “amarram"
        // todas as informações: o layout onde está definido o estilo (nesse exemplo o GridLayout),
        // o cursor que contém os dados, o array de Strings com o nome dos campos, o id dos componentes que serão
        // utilizados para exibir o conteúdo e uma flag. Após concluído o processo, a lista deve ser amarrada com
        // seu componente ListView e o adapter deve ser setado nessa lista.
        //consultalogin_layout template view
        //https://stackoverflow.com/questions/12077955/android-using-simplecursoradapter-to-get-data-from-database-to-listview
        // SimpleCursorAdapter é usado para ligar um curso a uma visualizalção do adaptador usando o layout da visualização do item de lista
        // tem o layout R.layout.support_simple_spinner_dropdown_item
        //SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.consultalogin_layout,cursor,nomeCampos,idViews,0);

        atualizaListView();
    }
    public static void atualizaListView(){
        // FROM - arrays que contêm so campos a serem apresentados
        String[] nomeCampos = new  String[] {"_id","email","password"};
        // TO - arrays que contêm os componentes da UI (XML)
        int[] idViews = new int[] {R.id.idLogin,R.id.emailLogin,R.id.passwordLogin};
        cursor = mydb.carregaDados();
        adapter = new CustomAdapter(context,R.layout.consultalogin_layout,cursor,nomeCampos,idViews);
        //listaView é vinculada o seu componente ListView do XML (UI)
        listaView.setAdapter(adapter);
        Log.v("minhaTAG","passei atualizaListView: qtd: "+adapter.getCount());

    }

    public static void showDialogAtualizar(LayoutInflater myInflater,int _id, String email, String pwd){
            // Create the Dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            // Inflate and set the layout for the dialog
            LayoutInflater inflater = myInflater;  //context.this.getLayoutInflater();  //AtualizarLoginActivity.this.getLayoutInflater();
            // Pass null as the parent view because its going in the dialog layout
            //builder.setView(inflater.inflate(R.layout.dialog_atualizar, null));

            View dialogView = inflater.inflate(R.layout.dialog_atualizar, null);
            builder.setView(dialogView );

            EditText edtId    = (EditText) dialogView.findViewById(R.id.idLoginAtualiza);
            EditText edtEmail = (EditText) dialogView.findViewById(R.id.idLoginAtualiza);
            EditText edtPwd   = (EditText) dialogView.findViewById(R.id.emailLoginAtualiza);
            edtId.setText( Integer.toString(_id));
            edtEmail.setText(email);
            edtPwd.setText(pwd);

            // Add OK button
            builder.setPositiveButton("Atualizar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    //input = edit.getText().toString();
                    //text.setText(input);
                    Util.Mensagem(context,"Atualizado com sucesso!","Atenção");

                }
            });

            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            // Set other dialog properties
            builder.setTitle("Atualização de Dados");  //.setMessage("Atualizar Dados?")
            // Create the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();
    }


    //https://www.vogella.com/tutorials/AndroidListView/article.html - ver layout
}