package br.com.can.base;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import br.com.can.R;
import br.com.can.activity.ClubeActivity;
import br.com.can.activity.JogadoresActivity;
import br.com.can.activity.JogosActivity;

public class AppCompatActivityBase extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView navigationView;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        //Cria o BottomNavigationView.
        this.navigationView = (BottomNavigationView) findViewById(R.id.bottomNav);
        this.navigationView.setOnNavigationItemSelectedListener(this);
    }

    //Define os itens do BottomNavigationView e chama as suas Activities conforme seleção.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clube: {
                Intent intent = new Intent(this, ClubeActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.menu_jogadores: {
                Intent intent = new Intent(this, JogadoresActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.menu_jogos: {
                Intent intent = new Intent(this, JogosActivity.class);
                startActivity(intent);
                finish();
                break;
            }
        }
        return true;
    }

}
