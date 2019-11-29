package br.com.can.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import br.com.can.R;
import br.com.can.base.Mail;
import br.com.can.entity.Usuario;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class CadastroActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    protected Realm realm;

    @BindView(R.id.input_name)
    EditText nome;
    @BindView(R.id.input_email)
    EditText email;
    @BindView(R.id.input_password)
    EditText senha;
    @BindView(R.id.input_reEnterPassword)
    EditText repetirSenha;
    @BindView(R.id.btn_signup)
    Button botaoCadastrar;
    @BindView(R.id.link_login)
    TextView loginLink;
    @BindView(R.id.perfis)
    Spinner perfis;

    private Usuario novoUsuario;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                novoCadastro();
            }
        });
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        Spinner spinner = (Spinner) findViewById(R.id.perfis);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.perfis, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        this.criaBancoRealm();
    }

    /**
     * Novo cadastro de usuário.
     */
    public void novoCadastro() {
        if (!this.validarCadastro()) {
            cadastroComFalha();
            return;
        }
        botaoCadastrar.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(CadastroActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.criando_conta));
        progressDialog.show();

        this.novoUsuario = new Usuario();
//        String nome = this.nome.getText().toString();
//        String email = this.email.getText().toString();
//        String senha = this.senha.getText().toString();
//        String repetirSenha = this.repetirSenha.getText().toString();
//        String perfil = this.perfis.getSelectedItem().toString();
        // TODO: Implement your own novoCadastro logic here.

        this.novoUsuario.setNome(this.nome.getText().toString());
        this.novoUsuario.setEmail(this.email.getText().toString());
        this.novoUsuario.setSenha(this.senha.getText().toString());
        this.novoUsuario.setTipoUsuario(this.perfis.getSelectedItem().toString());

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        salvar();
                        // On complete call either cadastroComSucesso or cadastroComFalha
                        // depending on success
                        cadastroComSucesso();
                        // cadastroComFalha();
                        progressDialog.dismiss();
                    }
                }, 4000);
    }

    public void cadastroComSucesso() {
        Toast.makeText(getBaseContext(), getString(R.string.cadastro_sucesso), Toast.LENGTH_LONG).show();
        botaoCadastrar.setEnabled(true);
        setResult(RESULT_OK, null);
        this.onBackPressedComBundle();
    }

    private void enviarEmail() {
        final String nome = this.novoUsuario.getNome();
        final String email = this.novoUsuario.getEmail();
        final String subject = "Confirmação cadastro de novo usuário APP CAN";
        final String body = "<p><span style=\"color: rgb(51, 51, 51); font-family: &quot;Times New Roman&quot;, Times, serif; font-size: 18px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 300; letter-spacing: normal; orphans: 2; text-align: left; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-style: initial; text-decoration-color: initial; float: none; display: inline !important;\">Prezado&nbsp;</span></p>" + nome + "\n" +
                "<p><span style=\"font-size: 18px;\"><span style=\"font-family: 'Times New Roman',Times,serif;\"><span style=\"color: rgb(51, 51, 51); font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 300; letter-spacing: normal; orphans: 2; text-align: left; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-style: initial; text-decoration-color: initial; float: none; display: inline !important;\">O Clube Atlético Nacional&nbsp;</span> acaba de receber a sua solictação cadastral e te informa que você agora faz parte do cadastro de usuários do APP do Can.</span></span></p>\n" +
                "<p><span style=\"font-size: 18px;\"><span style=\"font-family: 'Times New Roman',Times,serif;\">Faça um bom proveito do nosso APP.</span></span></p>\n" +
                "<p><span style=\"font-size: 18px;\"><span style=\"font-family: 'Times New Roman',Times,serif;\">\n" +
                "      <br>\n" +
                "    </span></span></p>\n" +
                "<p><span style=\"font-size: 18px;\"><span style=\"font-family: 'Times New Roman',Times,serif;\">Saudações do&nbsp;</span></span><span style=\"color: rgb(51, 51, 51); font-family: &quot;Times New Roman&quot;, Times, serif; font-size: 18px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 300; letter-spacing: normal; orphans: 2; text-align: left; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-style: initial; text-decoration-color: initial; float: none; display: inline !important;\">Clube Atlético Nacional &nbsp;o Clube do Povo!</span></p>";
        if (!isOnline()) {
            Toast.makeText(getApplicationContext(), "Não estava online para enviar e-mail!", Toast.LENGTH_SHORT).show();
            System.exit(0);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Mail m = new Mail();
                String[] toArr = {email};
                m.setTo(toArr);
                //m.setFrom("seunome@seuemail.com.br"); //caso queira enviar em nome de outro
                m.setSubject(subject);
                m.setBody(body);
                try {
                    //m.addAttachment("pathDoAnexo");//anexo opcional
                    m.send();
                } catch (RuntimeException rex) {
                }//erro ignorado
                catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
                Toast.makeText(getApplicationContext(), "Você receberá um email de confimação!", Toast.LENGTH_SHORT).show();
            }
        }).start();
    }

    public boolean isOnline() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Erro ao verificar se estava online! (" + ex.getMessage() + ")", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void cadastroComFalha() {
        Toast.makeText(getBaseContext(), getString(R.string.login_invalido), Toast.LENGTH_LONG).show();
        botaoCadastrar.setEnabled(true);
    }

    /**
     * Validações cadastrais dos dado informados na tela pelo usuário.
     */
    public boolean validarCadastro() {
        boolean valid = true;
        valid = this.isValidNome(valid);
        valid = this.isValidEmail(valid);
        valid = this.isValidSenha(valid);
        return valid;
    }

    /**
     * Valida se o nome informado pelo usuário é maior que três caracteres.
     */
    private boolean isValidNome(boolean valid) {
        if (this.nome.getText().toString().isEmpty() || this.nome.getText().toString().length() < 3) {
            this.nome.setError(getString(R.string.tres_caracteres));
            valid = false;
        } else {
            this.nome.setError(null);
        }
        return valid;
    }

    /**
     * Valida se o email informado pelo usuário é um email válido.
     */
    private boolean isValidEmail(boolean valid) {
        if (this.email.getText().toString().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(this.email.getText().toString()).matches()) {
            this.email.setError(getString(R.string.email_valido));
            valid = false;
        } else {
            this.email.setError(null);
        }
        return valid;
    }

    /**
     * Valida se a senha informada pelo usuário é um númerico de tamnho seis e se a senha foi confirmada no campo de repetição de senha.
     */
    private boolean isValidSenha(boolean valid) {
        if (this.senha.getText().toString().length() != 6) {
            this.senha.setError(getString(R.string.seis_numericos));
            valid = false;
        } else {
            this.senha.setError(null);
        }

        if (!this.repetirSenha.getText().toString().equals(this.senha.getText().toString())) {
            this.repetirSenha.setError(getString(R.string.senha_nao_confere));
            valid = false;
        } else {
            this.repetirSenha.setError(null);
        }
        return valid;
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    //Salva o objeto no Banco,
    public void salvar() {
        this.realm.beginTransaction();
        this.realm.insertOrUpdate(this.novoUsuario);
        this.realm.commitTransaction();
        this.enviarEmail();
    }

    public void onBackPressedComBundle() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("resultado", this.novoUsuario);
        //Chama a próxima Activity já com o objeto populado.
        Intent intent = new Intent(CadastroActivity.this, HomeActivity.class);
        intent.putExtra("usuario", bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.left_animation, R.anim.rigth_animation);
        this.finish();
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
