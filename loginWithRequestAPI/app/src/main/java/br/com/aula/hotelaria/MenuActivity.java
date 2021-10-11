package br.com.aula.hotelaria;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
//import androidx.appcompat.widget.Toolbar;  //para minipular a Toolbar

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    //-------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       /*Via programação
        //tais opções poderão vir de uma base de dados - opçõe de menu para usuário
        menu.add(0,1,Menu.NONE,"First");
        menu.add(0,2,Menu.NONE,"Second");
        return super.onCreateOptionsMenu(menu);
        */
        //https://www.simplifiedcoding.net/android-toolbar-example/
        MenuInflater menuInflater = getMenuInflater(); //Inflate a menu to be displayed in the toolbar
        menuInflater.inflate(R.menu.main_menu,menu);

        //Navigation Drawer
        //https://www.journaldev.com/9958/android-navigation-drawer-example-tutorial
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*Programaticamente
        if(item.getItemId() == 1) {
            Util.Mensagem(this,"Firt item","");
        }*/
        switch(item.getItemId()){
            case R.id.menuAbout:
                Toast.makeText(this, "You clicked about", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuSettings:
                Toast.makeText(this, "You clicked settings", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuLogout:
                Toast.makeText(this, "You clicked logout", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuRelogio:
                //Toast.makeText(this, "You clicked clock", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MenuActivity.this, ConsultaLoginActivity.class));

                break;
        }
        return true;
    }
    //-------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu33);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //substitui a ActionBar pela Toolbar
        toolbar.setTitle("Menu");  //Configura o titulo
        //Configura o ícone exibido n lado esquerdo da ToolBar (NavigationIcon)
        toolbar.setNavigationIcon(R.drawable.ic_seta_esquerda_branca);

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Back clicked!", Toast.LENGTH_SHORT).show();
                //finish();   //vai desempilhando as activitys
                onBackPressed();
            }
        });

        if (getSupportActionBar() != null){
            //Util.Mensagem(this,"Action Bar substituido","");
        }

    }
}