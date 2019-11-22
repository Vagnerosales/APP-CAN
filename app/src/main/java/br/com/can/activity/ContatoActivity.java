package br.com.can.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

import br.com.can.R;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


public class ContatoActivity extends AppCompatActivity {

    public static final String URL_FACEBOOK_CAN = "https://pt-br.facebook.com/pages/category/Amateur-Sports-Team/Clube-Atl%C3%A9tico-Nacional-149390378511514/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);
        getSupportActionBar().hide();
        this.formataPaginaContato();
    }

    private void formataPaginaContato() {
        simulateDayNight(/* DAY */ 3);
        Element adsElement = new Element();
        adsElement.setTitle("Anuncie conosco");
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.can)
                .setDescription(this.getResources().getString(R.string.texto_contatos))
                .addItem(new Element().setTitle("Versão 1.0"))
                .addItem(adsElement)
                .addGroup("conecte-se conosco")
                .addEmail("can@outlook.com")
                .addFacebook(URL_FACEBOOK_CAN)
                .addTwitter("https://twitter.com/login?lang=pt")
                .addYoutube("https://www.youtube.com/watch?v=AbFiU2IvKwg")
                .addInstagram("https://www.instagram.com/?hl=pt-br")
                .addItem(getCopyRightsElement())
                .create();
        setContentView(aboutPage);
    }

    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format(getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconDrawable(R.drawable.about_icon_copy_right);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ContatoActivity.this, copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }

    void simulateDayNight(int currentSetting) {
        final int DAY = 0;
        final int NIGHT = 1;
        final int FOLLOW_SYSTEM = 3;
        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        if (currentSetting == DAY && currentNightMode != Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        } else if (currentSetting == NIGHT && currentNightMode != Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else if (currentSetting == FOLLOW_SYSTEM) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, ClubeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_animation, R.anim.rigth_animation);
        this.finish();
    }

}
