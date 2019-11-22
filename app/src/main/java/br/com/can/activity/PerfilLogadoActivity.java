package br.com.can.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.can.R;
import br.com.can.entity.Usuario;

public class PerfilLogadoActivity extends AppCompatActivity {

    private Usuario usuarioRecebido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_logado);
        this.capturaUsuarioLogado();
    }

    private void capturaUsuarioLogado() {
        //Recebe os dados passados na Intent da Classe anterior por mecanismo de Bundle.
        Bundle bundle = getIntent().getBundleExtra("usuario");
        if (bundle != null) {
            this.usuarioRecebido = (Usuario) bundle.getSerializable("resultado");
        }
    }

}
