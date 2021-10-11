package br.com.aula.hotelaria;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class Util {
    static void Mensagem(Context contexto, String mensagen, String titulo) {
        new AlertDialog.Builder(contexto)
                .setTitle(titulo)
                .setMessage(mensagen)
                .setPositiveButton("OK", null)
                .setCancelable(false) //indica q não tem o btn Cancelar
                .show();
    }
}
