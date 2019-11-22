package br.com.can.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import br.com.can.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class JogosActivity extends AppCompatActivity {

    private static final int REQUEST = 0;

    @BindView(R.id.jogos)
    TextView listaJogos;
    @BindView(R.id.cadastrar_jogo)
    TextView cadastrarJogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogos);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        this.redirecionaListaJogos();
        this.redirecionaCadastrarJogo();
    }

    /**
     * Redirecionamento para a tela de lista de jogos.
     */
    private void redirecionaListaJogos() {
        listaJogos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JogoActivity.class);
                startActivityForResult(intent, REQUEST);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    /**
     * Redirecionamento para a tela de cadastro de jogo.
     */
    private void redirecionaCadastrarJogo() {
        cadastrarJogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CadastroJogoActivity.class);
                startActivityForResult(intent, REQUEST);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_animation, R.anim.rigth_animation);
        this.finish();
    }


}
