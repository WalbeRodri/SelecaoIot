package wro.br.ufpe.cin.selecaoiot;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
        public static final String URL = "http://192.168.25.21:4567/allseries/0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // __________________________________________-
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    public void onStart(){
        super.onStart();
        new CarregaSeriesTask().execute(URL);
    }


    private class CarregaSeriesTask extends AsyncTask<String, Void, List<Serie>> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected List<Serie> doInBackground(String... params) {
            String conteudo = "provavelmente deu erro...";
            ParserSerie parser = new ParserSerie();
            List<Serie> resultado = null;
            try {
                conteudo = carregaSeries(params[0]);
                resultado = parser.parse(conteudo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultado;
        }


        @Override
        protected void onPostExecute(List<Serie> resultado) {
            mAdapter = new SerieAdapter(resultado);
            mRecyclerView.setAdapter(mAdapter);

        }
    }

    //metodo de captura das series via conexao http
    private String carregaSeries(String urlServidor) throws IOException {
        InputStream entrada = null;
        String shows = "";
        try {
            URL url = new URL(urlServidor);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            entrada = conexao.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(entrada);
            BufferedReader leituraApi = new BufferedReader(inputStreamReader);
            shows = leituraApi.readLine();
        } catch(Exception e) {
            e.printStackTrace();
        } finally
        {
            if (entrada != null) {
                entrada.close();
            }
            return shows;
        }

    }

}
