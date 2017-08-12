package wro.br.ufpe.cin.selecaoiot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class SerieUnicaActivity extends AppCompatActivity {
    private TextView vNomeUnico, vIdioma, vTextResumo, vTxtLinkOficial, vTxtLinkApi;
    private RatingBar vRatingBar;
    private ImageView vImagem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie_unica);


        this.vNomeUnico  = (TextView) findViewById(R.id.txtNomeUnico);
        this.vIdioma  = (TextView) findViewById(R.id.txtIdioma);
        this.vTextResumo = (TextView) findViewById(R.id.txtResumo);
        this.vTxtLinkApi  = (TextView) findViewById(R.id.txtLinkSiteApi);
        this.vTxtLinkOficial  = (TextView) findViewById(R.id.txtLinkOficial);
        this.vRatingBar = (RatingBar) findViewById(R.id.rBarNota);
        this.vImagem = (ImageView) findViewById(R.id.imagem);

        Bundle extras = getIntent().getExtras();

        this.vNomeUnico.setText(extras.getString("nome"));
        this.vIdioma.setText(extras.getString("idioma"));
        this.vTextResumo.setText(extras.getString("resumo"));
        this.vTxtLinkOficial.setText(extras.getString("linkOficial"));
        this.vTxtLinkApi.setText(extras.getString("linkApi"));
        this.vRatingBar.setNumStars(10);
        this.vRatingBar.setClickable(false);

        this.vRatingBar.setRating((float)(extras.getDouble("media")));

        new SerieAdapter.DownloadImagemTask(this.vImagem).execute(extras.getString("imagem"));

    }
}
