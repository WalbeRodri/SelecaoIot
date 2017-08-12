package server;
import static spark.Spark.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import freemarker.core.ParseException;


public class ServidorTeste {
	public static final String URL_SHOWS = "http://api.tvmaze.com/shows";
	//	public static final String URL_API = "http://api.tvmaze.com/";
	public static void main(String[] args) {

		//metodo para receber 250 series em ordem crescente de ID, o numero passado via 'params' informa qual o intervalo de captura.  
		get("/allseries/:page", (request,response) -> {
			String id = request.params(":page");
			URL url = new URL(URL_SHOWS + "?page=" + id);			
			return conexaoApiExterna(url);
		});

		//Metodo para receber serie especifica baseando-se em id numerico
		get("/getserie/:id", (request, response) -> {
			String id = request.params(":id");
			URL url = new URL(URL_SHOWS +"/"+id);
			return conexaoApiExterna(url);

		});

	}
	private static Object conexaoApiExterna(URL url) throws IOException {

		HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
		InputStream content = conexao.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(content);
		BufferedReader leituraApi = new BufferedReader(inputStreamReader);
		return leituraApi.readLine();
	}
}
