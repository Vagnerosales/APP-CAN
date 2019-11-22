package br.com.can.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import br.com.can.R;
import br.com.can.adapter.JogoAdapter;
import br.com.can.entity.Jogo;
import io.realm.Realm;
import io.realm.RealmResults;

public class JogoActivity extends AppCompatActivity {

    private List<Jogo> listaJogos;
    private ListView listView;
    protected Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);
        listView = (ListView) findViewById(R.id.listView);
        this.criaBancoRealm();
        this.buscaJogos();
    }

    //Busca no BD os jogos já salvos.
    public void buscaJogos(){
        RealmResults<Jogo> listaJogosRecuperada = this.realm.where(Jogo.class).findAllAsync();
        listaJogosRecuperada.load();
        if (!listaJogosRecuperada.isEmpty()){
            this.listaJogos = listaJogosRecuperada;
            //Chama montagem da ListView.
            this.populaListViewProjetos(this.listaJogos);
        }
    }

    //Chama o Adapter.
    public void populaListViewProjetos(List<Jogo> listaJogosRecebida){
        final JogoAdapter adapter = new JogoAdapter(this, listaJogosRecebida);
        this.listView.setAdapter(adapter);
    }

    public void criaBancoRealm() {
        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, JogosActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_animation, R.anim.rigth_animation);
        this.finish();
    }

}
