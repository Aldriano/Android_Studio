package br.com.aula.hotelaria;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import br.com.aula.hotelaria.database.DBCore;

import static androidx.core.content.ContextCompat.startActivity;

//https://www.luiztools.com.br/post/tutorial-crud-em-android-com-sqlite-e-recyclerview-2/
public class CustomAdapter extends SimpleCursorAdapter {

    private Context mContext;
    private Context appContext;
    private int layout;
    private Cursor cr;
    private final LayoutInflater inflater;

    public CustomAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.layout = layout;
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.cr = c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(layout, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        final int row_id = cursor.getInt(cursor.getColumnIndex("_id"));
        final String row_email    = cursor.getString(cursor.getColumnIndex("email"));
        final String row_password = cursor.getString(cursor.getColumnIndex("password"));

        ImageButton btnEditar = (ImageButton) view.findViewById(R.id.btnEditar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //https://stackoverflow.com/questions/3477422/what-does-layoutinflater-in-android-do
                //Esta classe é usada para instanciar o arquivo XML de layout em seus Viewobjetos correspondentes .
                // Nunca deve ser usado diretamente - use getLayoutInflater()ou getSystemService(String)para recuperar uma
                // LayoutInflaterinstância padrão que já está conectada ao contexto atual e configurada corretamente para o
                // dispositivo em que você está executando
                LayoutInflater mInflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ConsultaLoginActivity.showDialogAtualizar(mInflater,row_id, row_email,row_password);
            }
        });

        //Botão de excluir
        ImageButton btnDeletar = (ImageButton) view.findViewById(R.id.btnDeletar);
        btnDeletar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                confirmarExclusao(context,row_id);
            }
        });
    }

    private void confirmarExclusao(Context context, int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Atenção");
        builder.setMessage("Excluir registro?");

        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                DBCore mydb = new DBCore(context);
                if (mydb.deleteContact(id) >0){  //Excluiu com sucesso
                    Util.Mensagem(context,"Excluído com sucesso!","Atenção");
                    if (ConsultaLoginActivity.adapter != null) {
                        //ConsultaLoginActivity.adapter.notifyDataSetChanged();
                        //ConsultaLoginActivity.listaView.refreshDrawableState();
                        ConsultaLoginActivity.atualizaListView();
                        Log.v("minhaTAG","passei no delete ACHOU o adapter");
                    }else{
                        Log.v("minhaTAG","passei no delete mas não achou o adapter");
                    }
                }else{
                    Util.Mensagem(context,"Erro ao excluído registro!","Atenção");
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}