package wro.br.ufpe.cin.selecaoiot;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Walber on 11/08/2017.
 */

public class ParserSerie {
    //Parser simples do JSON recebido
    public List<Serie> parse(String series){
        List<Serie> listaRetorno = new ArrayList<>();
        Log.d("OLHA O LOOOG: ","<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+series.substring(0,10));
        Object obj = (series);
        JSONArray array = null;
        try {
            array = new JSONArray(series);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject objetoAuxiliar;
        Serie serieAuxiliar = null;
        for (int i =0; i <30; i++) {
            try {
                objetoAuxiliar = (JSONObject)array.get(i);
//                String[] generos = (objetoAuxiliar.getJSONArray("genres"));
                serieAuxiliar = new Serie(objetoAuxiliar.getString("name"),
                        ((JSONObject)objetoAuxiliar.get("image")).getString("medium"),
                        objetoAuxiliar.getString("url") ,
                        objetoAuxiliar.getString("language"),
                        ((JSONObject)objetoAuxiliar.get("rating")).getDouble("average"),
                        objetoAuxiliar.getString("officialSite"),
                        objetoAuxiliar.getString("summary")
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listaRetorno.add(serieAuxiliar);

        }
        return listaRetorno;
    }
}

