package br.com.can.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import br.com.can.R;
import br.com.can.entity.Jogo;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class CadastroJogoActivity extends AppCompatActivity implements View.OnClickListener {

    protected Realm realm;

    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int ano, mes, dia, hora, minuto;

    @BindView(R.id.input_name_clube)
    EditText nomeClubeMandante;
    @BindView(R.id.input_name_clube_visitante)
    EditText nomeClubeVisitante;
    @BindView(R.id.input_loca_partida)
    EditText localPartida;
    @BindView(R.id.btn_cadastrar_jogo)
    Button botaoCadastrarJogo;
    @BindView(R.id.categorias)
    Spinner categorias;

    private Jogo novoJogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_jogo);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        this.botaoCadastrarJogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                novoCadastro();
            }
        });
        this.btnDatePicker = (Button) findViewById(R.id.btn_date);
        this.btnTimePicker = (Button) findViewById(R.id.btn_time);
        this.txtDate = (EditText) findViewById(R.id.in_date);
        this.txtTime = (EditText) findViewById(R.id.in_time);
        this.btnDatePicker.setOnClickListener(this);
        this.btnTimePicker.setOnClickListener(this);
        Spinner spinner = (Spinner) findViewById(R.id.categorias);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        this.criaBancoRealm();
    }

    @Override
    public void onClick(View v) {
        if (v == this.btnDatePicker) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            this.ano = c.get(Calendar.YEAR);
            this.mes = c.get(Calendar.MONTH);
            this.dia = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }
                    }, this.ano, this.mes, this.dia);
            datePickerDialog.show();
        }
        if (v == this.btnTimePicker) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            this.hora = c.get(Calendar.HOUR_OF_DAY);
            this.minuto = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, this.hora, this.minuto, false);
            timePickerDialog.show();
        }
    }

    /**
     * Novo cadastro de usuário.
     */
    public void novoCadastro() {
        if (!this.validarCadastro()) {
            cadastroComFalha();
            return;
        }
        this.botaoCadastrarJogo.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(CadastroJogoActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.criando_partida));
        progressDialog.show();

        this.novoJogo = new Jogo();
        this.novoJogo.setHora(this.txtDate.getText().toString() + " " + this.txtTime.getText().toString());
        this.novoJogo.setNomeClubeA(this.nomeClubeMandante.getText().toString());
        this.novoJogo.setNomeClubeB(this.nomeClubeVisitante.getText().toString());
        this.novoJogo.setLocal(this.localPartida.getText().toString());
        this.novoJogo.setCategoria(this.categorias.getSelectedItem().toString());

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
                }, 3000);
    }

    /**
     * Validações cadastrais dos dado informados na tela pelo usuário.
     */
    public boolean validarCadastro() {
        boolean valid = true;
        valid = this.isValidData(valid);
        valid = this.isValidHora(valid);
        valid = this.isValidNomeClubeMandante(valid);
        valid = this.isValidNomeClubeVisitante(valid);
        valid = this.isValidLocalPartida(valid);
        valid = this.isValidCategoria(valid);
        return valid;
    }

    /**
     * Valida se a data foi informado pelo usuário.
     */
    private boolean isValidData(boolean valid) {
        if (this.txtDate.getText().toString().isEmpty()) {
            this.txtDate.setError(getString(R.string.campo_vazio));
            valid = false;
        } else {
            this.txtDate.setError(null);
        }
        return valid;
    }

    /**
     * Valida se a data foi informado pelo usuário.
     */
    private boolean isValidHora(boolean valid) {
        if (this.txtTime.getText().toString().isEmpty()) {
            this.txtTime.setError(getString(R.string.campo_vazio));
            valid = false;
        } else {
            this.txtTime.setError(null);
        }
        return valid;
    }

    /**
     * Valida se o nome do clube mandante foi informado pelo usuário.
     */
    private boolean isValidNomeClubeMandante(boolean valid) {
        if (this.nomeClubeMandante.getText().toString().isEmpty()) {
            this.nomeClubeMandante.setError(getString(R.string.campo_vazio));
            valid = false;
        } else {
            this.nomeClubeMandante.setError(null);
        }
        return valid;
    }

    /**
     * Valida se o nome do clube visitante foi informado pelo usuário.
     */
    private boolean isValidNomeClubeVisitante(boolean valid) {
        if (this.nomeClubeVisitante.getText().toString().isEmpty()) {
            this.nomeClubeVisitante.setError(getString(R.string.campo_vazio));
            valid = false;
        } else {
            this.nomeClubeVisitante.setError(null);
        }
        return valid;
    }

    /**
     * Valida se o local da partida foi informado pelo usuário.
     */
    private boolean isValidLocalPartida(boolean valid) {
        if (this.nomeClubeVisitante.getText().toString().isEmpty()) {
            this.nomeClubeVisitante.setError(getString(R.string.campo_vazio));
            valid = false;
        } else {
            this.nomeClubeVisitante.setError(null);
        }
        return valid;
    }

    /**
     * Valida se a categoria informada pelo usuário é válida.
     */
    private boolean isValidCategoria(boolean valid) {
        if (this.categorias.getSelectedItem().toString().equals("Selecione uma categoria")) {
            Toast.makeText(getBaseContext(), getString(R.string.categoria_invalido), Toast.LENGTH_LONG).show();
            valid = false;
        } else {
            this.nomeClubeVisitante.setError(null);
        }
        return valid;
    }

    //Salva o objeto no Banco,
    public void salvar() {
        this.realm.beginTransaction();
        this.realm.insertOrUpdate(this.novoJogo);
        this.realm.commitTransaction();
    }

    public void cadastroComSucesso() {
        Toast.makeText(getBaseContext(), getString(R.string.cadastro_sucesso), Toast.LENGTH_LONG).show();
        setResult(RESULT_OK, null);
        this.onBackPressed();
    }

    public void cadastroComFalha() {
        Toast.makeText(getBaseContext(), getString(R.string.login_invalido), Toast.LENGTH_LONG).show();
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
