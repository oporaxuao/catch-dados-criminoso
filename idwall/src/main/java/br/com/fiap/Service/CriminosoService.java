package br.com.fiap.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.Model.Criminoso;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class CriminosoService {

    public List<Criminoso> getFBI() {
        List<Criminoso> criminosoList = new ArrayList<>();
        int pag = 1;
        while (pag > 0){
            try {
                URL url = new URL("https://api.fbi.gov/@wanted?pageSize=20&page=" + pag + "&sort_on=modified&sort_order=desc");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                if (con.getResponseCode() != 200) {
                    break;
                }

                BufferedReader resposta = new BufferedReader(new InputStreamReader((con.getInputStream())));
                JSONTokener tokener = new JSONTokener(resposta);
                JSONObject json = new JSONObject(tokener);
                JSONArray criminosos = json.getJSONArray("items");

                for (int i = 0; i < criminosos.length(); i++) {
                    JSONObject criminosoJson = criminosos.getJSONObject(i);
                    Criminoso criminoso = new Criminoso();
                    criminoso.setNome(criminosoJson.getString("title"));
                    criminoso.setDataNascimento(criminosoJson.isNull("dates_of_birth_used") ? null : criminosoJson.getJSONArray("dates_of_birth_used").toString());
                    criminoso.setNacionalidade(criminosoJson.isNull("nationality") ? null : criminosoJson.getString("nationality"));
                    criminoso.setCrimes(criminosoJson.isNull("caution") ? null : criminosoJson.getString("caution"));
                    criminoso.setFoto(criminosoJson.isNull("images") ? null : (criminosoJson.getJSONArray("images")).getJSONObject(0).getString("original"));
                    System.out.println(criminoso);
                    criminosoList.add(criminoso);
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ++pag;
        }
        return criminosoList;
    }

    public static List<Criminoso> getInterpol() {
        List<Criminoso> criminosoList = new ArrayList<>();
        int resultPerPage = 160; // Define o número de resultados por página
        int page = 1;

        try {
            while (true) {
                URL url = new URL("https://ws-public.interpol.int/notices/v1/red?resultPerPage=" + resultPerPage + "&page=" + page);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                if (con.getResponseCode() != 200) {
                    throw new RuntimeException("HTTP error code : " + con.getResponseCode());
                }

                BufferedReader resposta = new BufferedReader(new InputStreamReader((con.getInputStream())));
                JSONTokener tokener = new JSONTokener(resposta);
                JSONObject json = new JSONObject(tokener);
                JSONArray criminosos = (json.getJSONObject("_embedded")).getJSONArray("notices");

                if (criminosos.length() == 0) {
                    break; // Não há mais páginas, saia do loop
                }

                for (int i = 0; i < criminosos.length(); i++) {
                    JSONObject criminosoJson = criminosos.getJSONObject(i);
                    Criminoso criminoso = new Criminoso();
                    criminoso.setNome(criminosoJson.getString("name") + criminosoJson.getString("forename"));
                    criminoso.setDataNascimento(criminosoJson.getString("date_of_birth"));
                    criminoso.setNacionalidade(criminosoJson.isNull("nationalities") ? null : criminosoJson.getJSONArray("nationalities").toString());
                    criminoso.setCrimes(null);
                    criminoso.setFoto(((criminosoJson.getJSONObject("_links")).getJSONObject("images")).getString("href"));
                    System.out.println(criminoso);
                    criminosoList.add(criminoso);
                }

                page++; // Vá para a próxima página
                Thread.sleep(1000); // Espere um segundo antes de fazer a próxima solicitação (pode ajustar o tempo conforme necessário)
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return criminosoList;
    }
}
