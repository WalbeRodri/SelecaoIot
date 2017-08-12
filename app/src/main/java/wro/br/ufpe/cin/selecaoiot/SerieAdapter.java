package wro.br.ufpe.cin.selecaoiot;

/**
 * Created by Walber on 11/08/2017.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SerieAdapter  extends RecyclerView.Adapter<SerieAdapter.SerieViewHolder> {
    public List<Serie> listaSeries;
    OnSelectedListener selec;

    public SerieAdapter( List<Serie> listaSeries, OnSelectedListener selec) {
        this.listaSeries = listaSeries;
        this.selec = selec;
    }
    @Override
    public SerieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_view, viewGroup, false);
        return new SerieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SerieViewHolder holder, final int position) {

        //povoa as Cardviews
        Serie serie = listaSeries.get(position);
        holder.vNome.setText(serie.getNome());
        holder.vNota.setText("Nota: "+ serie.getMedia());

        //evita reloads demasiados das imagens em tempo de scroll
        if (holder.vImagem.getDrawable() != null) holder.vImagem.setVisibility(View.GONE);

        //abre task extra para download da imagem, download nunca pode ser feito na thread principal
        new DownloadImagemTask(holder.vImagem).execute(serie.getUrlImagem());


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selec.onSelected(listaSeries.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaSeries.size();
    }

    public interface OnSelectedListener {
        public void onSelected(Serie serie);
    }
    //ViewHolder
    public static class SerieViewHolder extends RecyclerView.ViewHolder {
        protected TextView vNome;
        protected ImageView vImagem;
        protected TextView vNota;
        protected View view;
        public SerieViewHolder(View v) {
            super(v);
            this.view = v;
            vImagem = (ImageView) v.findViewById(R.id.imagem);
            vNome =  (TextView) v.findViewById(R.id.txtNome);
            vNota =  (TextView) v.findViewById(R.id.txtAverage);
        }
    }
    //Task assincrona para download das imagens, semelhante ao download da API na main activiy, vide codigo para comentarios
    public static class DownloadImagemTask extends AsyncTask<String, Void, Bitmap> {
        ImageView vImagem;

        public DownloadImagemTask(ImageView vImagem) {
            this.vImagem = vImagem;
        }

        protected Bitmap doInBackground(String... urls) {
            Bitmap imagem = null;
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                InputStream entrada = conexao.getInputStream();
                imagem = BitmapFactory.decodeStream(entrada);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return imagem;
        }

        protected void onPostExecute(Bitmap result) {
            vImagem.setImageBitmap(result);
            vImagem.setVisibility(View.VISIBLE);
        }
    }




}
