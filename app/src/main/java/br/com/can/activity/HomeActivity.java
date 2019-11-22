package br.com.can.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import br.com.can.R;
import br.com.can.base.AppCompatActivityBase;
import br.com.can.entity.Usuario;

public class HomeActivity extends AppCompatActivityBase {

    public static final String URL_FACEBOOK_CAN = "https://pt-br.facebook.com/pages/category/Amateur-Sports-Team/Clube-Atl%C3%A9tico-Nacional-149390378511514/";
    private WebView webView;
    private ProgressBar progressBar;
    private Usuario usuarioRecebido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.webView = (WebView) findViewById(R.id.webview);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.progressBar.setVisibility(View.GONE);
        this.capturaUsuarioLogado();
    }

    private void capturaUsuarioLogado() {
        //Recebe os dados passados na Intent da Classe anterior por mecanismo de Bundle.
        Bundle bundle = getIntent().getBundleExtra("usuario");
        if (bundle != null) {
            this.usuarioRecebido = (Usuario) bundle.getSerializable("resultado");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        MenuItem login = menu.findItem(R.id.login);
        MenuItem logado = menu.findItem(R.id.usuariLogado);
        if (usuarioRecebido == null){
            login.setVisible(true);
            logado.setVisible(false);
        } else {
            logado.setVisible(true);
            login.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Chama a tela de Login.
        if (item.getItemId() == R.id.login) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            return true;
        } else if (item.getItemId() == R.id.usuariLogado) {
            this.onBackPressedComBundle();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.loadPagina(URL_FACEBOOK_CAN);
    }

    /**
     * Carrega a URL definida na entrada do APP ou digitada pelo usuário.
     */
    public void loadPagina(String url) {
        WebSettings webSetting = webView.getSettings();
        webSetting.setBuiltInZoomControls(true);
        webSetting.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Cria a webview para exibição da página do Facebook do Clube.
     */
    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

    public void onBackPressedComBundle() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("resultado", this.usuarioRecebido);
        //Chama a próxima Activity já com o objeto populado.
        Intent intent = new Intent(this, PerfilLogadoActivity.class);
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

}
