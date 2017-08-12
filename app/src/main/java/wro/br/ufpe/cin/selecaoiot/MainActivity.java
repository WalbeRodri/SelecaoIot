package wro.br.ufpe.cin.selecaoiot;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
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
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SerieAdapter.OnSelectedListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private Parcelable recyclerViewState;
    private int totalSeries;
    public static final String URL = "http://192.168.25.21:4567/allseries/";
    private int contador = 1;
    List<Serie> resultado, resultadoAux;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        recyclerViewState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        mRecyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);


        //foi adicionado um scrollListener para criar uma "lista infinita" das series
        //presentes no site da API, as devidas modificacoes que tornam isto possível foram feitas tambem no servidor
        //Eu nao gostaria de carregar tantos itens numa unica view, isto pode gerar travamentos do sistema, assim como prejudicar a performace
        //Mas entendo que isto foi o solicitado, caso tenha entendido errado, posso alterar para melhorar a performace do app
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                totalSeries = mLayoutManager.getItemCount();

                super.onScrolled(recyclerView, dx, dy);

                //checa-se se o ultimo elemento esta em foco, uma vez sendo isto verdade,
                // passa um novo valor para uma nova thread assincrona que rodara a requisicao web ao nosso servidor
                if(totalSeries==(mLayoutManager.findLastVisibleItemPosition()+1)) {
                    atualizaView();
                }
            }
        });
        new CarregaSeriesTask().execute(URL+0);
    }

    //Metodo para suavizar o scroll, carregando apenas 30 itens por vez
    protected  void atualizaView(){
        SerieAdapter adapter = (SerieAdapter) mRecyclerView.getAdapter();
        if ((resultadoAux.size()!=0)&&(resultado.size()%resultadoAux.size())==0) {
            new CarregaSeriesTask().execute(URL + contador++);
        }
        List<Serie> subListAux = new ArrayList<>();
        if ((resultado.size() % resultadoAux.size()) + 30 > resultadoAux.size())
            subListAux = resultadoAux.subList((resultado.size() % (resultadoAux.size())), resultadoAux.size());
        else
            subListAux = resultadoAux.subList((resultado.size() % (resultadoAux.size())), (resultado.size()%resultadoAux.size()) +30);

        for (Serie item : subListAux) {
            resultado.add(item);
            adapter.notifyItemInserted(resultado.size());

        }


    }

//    @Override
//    public void onStart(){
//        super.onStart();
//
//    }

    @Override
    public void onSelected(Serie serie) {
        //adiciona os valores do objeto no intent, evita nova utilizacao da rede para download do texto
        Intent intent = new Intent(this, SerieUnicaActivity.class);
        intent.putExtra("nome", serie.getNome());
        intent.putExtra("idioma", serie.getIdioma());
        intent.putExtra("resumo", serie.getResumo());
        intent.putExtra("linkOficial", serie.getSiteOficial());
        intent.putExtra("linkApi", serie.getUrlTvMaze());
        intent.putExtra("media", serie.getMedia());
        intent.putExtra("imagem", serie.getUrlImagem());

        //inicia a activity de Serie unica como foi solicitado
        startActivity(intent);
    }

    //Thread assincrona responsavel pelo download dos itens do servidor
    private class CarregaSeriesTask extends AsyncTask<String, Void, List<Serie>> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected List<Serie> doInBackground(String... params) {
            String conteudo = "";
            ParserSerie parser = new ParserSerie();
            resultadoAux = new ArrayList<>();
            try {
                //Pega o string do host passado como params
                conteudo = carregaSeries(params[0]);

                //realiza o parse e recebe a lista de itens que serao passados ao adapter no postExecute
                resultadoAux = parser.parse(conteudo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultadoAux;
        }


        @Override
        protected void onPostExecute(List<Serie> resultadoAux) {
            if(resultado==null){
                resultado = new ArrayList<>();
            }
            for (Serie item : resultadoAux.subList(0,30)){
                resultado.add(item);
            }
            //Atualiza o adapter da view com a lista de resultados
            mAdapter = new SerieAdapter(resultado, MainActivity.this);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

    }
    //metodo de captura das series via conexao http
    private String carregaSeries(String urlServidor) throws IOException {
        InputStream entrada = null;
        String shows = "";
        try {

            //Transforma num objeto URL a string passada indicativa do endereco do host
            URL url = new URL(urlServidor);

            //realiza conexão
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            entrada = conexao.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(entrada);
            BufferedReader leituraApi = new BufferedReader(inputStreamReader);

            //Le a string que foi adicionada ao buffer apos leitura do array de bytes do inputStream
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
