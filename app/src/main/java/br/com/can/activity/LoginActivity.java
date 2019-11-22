package br.com.can.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.can.R;
import br.com.can.entity.Usuario;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class LoginActivity extends AppCompatActivity {

    protected Realm realm;

    private static final int REQUEST = 0;

    @BindView(R.id.input_email)
    EditText email;
    @BindView(R.id.input_password)
    EditText senha;
    @BindView(R.id.btn_login)
    Button botaoLogin;
    @BindView(R.id.link_cadastro)
    TextView cadastro;

    private Usuario loginUsuario = new Usuario();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        this.botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        redirecionaCadastro();
        this.criaBancoRealm();
    }

    /**
     * redireciona o usuário para a craiação de um novo retgistro de usuário no APP
     */
    private void redirecionaCadastro() {
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivityForResult(intent, REQUEST);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    /**
     * Funcionamento do Login.
     */
    public void login() {
        if (!validarLogin()) {
            loginFalha();
            return;
        }
        botaoLogin.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.autenticando));
        progressDialog.show();

//        String email = this.email.getText().toString();
//        String senha = this.senha.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        logar();
                        // On complete call either loginSucesso or loginFalha
                        //loginSucesso();
                        // loginFalha();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    private void logar() {
        Usuario usuario = this.realm.where(Usuario.class).equalTo("email", this.email.getText().toString())
                .equalTo("senha", this.senha.getText().toString())
                .findFirst();
        this.populaLoginUsuario(usuario);
    }

    // TODO Rever melhor forma de fazer isso, estava dando erro de RealmProxy$ na hora do set de objeto, por isso essa gambiarra!
    private void populaLoginUsuario(Usuario usuario) {
        if (usuario != null) {
            this.loginUsuario.setId(usuario.getId() != 0 ? usuario.getId() : -1);
            this.loginUsuario.setNome(usuario.getNome() != null ? usuario.getNome() : "");
            this.loginUsuario.setCpf(usuario.getCpf() != 0 ? usuario.getCpf() : -1);
            this.loginUsuario.setEndereco(usuario.getEndereco() != null ? usuario.getEndereco() : "");
            this.loginUsuario.setEmail(usuario.getEmail() != null ? usuario.getEmail() : "");
            this.loginUsuario.setTipoUsuario(usuario.getTipoUsuario() != null ? usuario.getTipoUsuario() : "");
            this.loginUsuario.setUsuario(usuario.getUsuario() != null ? usuario.getUsuario() : "");
            this.loginUsuario.setSenha(usuario.getSenha() != null ? usuario.getSenha() : "");
            this.onBackPressedComBundle();
        }
    }

    public void onBackPressedComBundle() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("resultado", this.loginUsuario);
        //Chama a próxima Activity já com o objeto populado.
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("usuario", bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.left_animation, R.anim.rigth_animation);
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST) {
            if (resultCode == RESULT_OK) {
                // TODO: Implement successful novoCadastro logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    public void loginSucesso() {
        this.botaoLogin.setEnabled(true);
        finish();
    }

    public void loginFalha() {
        Toast.makeText(getBaseContext(), getString(R.string.login_invalido), Toast.LENGTH_LONG).show();
        this.botaoLogin.setEnabled(true);
    }

    /**
     * Validação inicial do login, validando apenas os dados informados pelo usuário ainda sem fazer a consistência doas dados na API/BD.
     */
    public boolean validarLogin() {
        boolean valid = true;
        valid = validarEmail(valid);
        valid = validarSenha(valid);
        return valid;
    }

    /**
     * Valida se o email informado pelo usuário é um email válido.
     */
    private boolean validarEmail(boolean valid) {
        if (this.email.getText().toString().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(this.email.getText().toString()).matches()) {
            this.email.setError(getString(R.string.email_invalido));
            valid = false;
        } else {
            this.email.setError(null);
        }
        return valid;
    }

    /**
     * Valida se a senha informada pelo usuário é um númerico de tamnho seis.
     */
    private boolean validarSenha(boolean valid) {
        if (this.senha.getText().toString().length() != 6) {
            this.senha.setError(getString(R.string.seis_numericos));
            valid = false;
        } else {
            this.senha.setError(null);
        }
        return valid;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_animation, R.anim.rigth_animation);
        this.finish();
    }

    public void criaBancoRealm() {
        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }

}
