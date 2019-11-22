package br.com.can.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import br.com.can.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ClubeActivity extends AppCompatActivity {

    private static final int REQUEST = 0;

    @BindView(R.id.historia)
    TextView historia;
    @BindView(R.id.contatos)
    TextView contatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clube);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        this.redirecionaHistoria();
        this.redirecionaContatos();
    }

    /**
     * Redirecionamento para a tela de Contatos do Clube.
     */
    private void redirecionaContatos() {
        contatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContatoActivity.class);
                startActivityForResult(intent, REQUEST);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    /**
     * Redirecionamento para a tela de Historia do Clube.
     */
    private void redirecionaHistoria() {
        historia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HistoriaActivity.class);
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
