package com.projeto.filmes.utils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.projeto.filmes.dao.FilmeDAO;
import com.projeto.filmes.dominio.Filme;

@FacesConverter(value="filmeConverter", forClass=Filme.class)
public class FilmeConverter implements Converter{
	public Object getAsObject(FacesContext ctx, UIComponent comp, String value) {
		Filme filme = new Filme();
		HttpResponse<String> response;
		try {
			response = Unirest.get("https://imdb8.p.rapidapi.com/title/get-details?tconst="+value)
					.header("x-rapidapi-host", "imdb8.p.rapidapi.com")
					.header("x-rapidapi-key", "1647a78bb7mshd3d0c82dcbcd838p1b6f35jsn95ddc2ae7677")
					.asString();
			JSONObject result = new JSONObject(response.getBody());
			
			filme.setId(value);
			filme.setTitulo(result.getString("title"));
			filme.setAno(result.getInt("year"));
			filme.setDuracao(result.getInt("runningTimeInMinutes"));
			filme.setPoster(result.getJSONObject("image").getString("url"));
		} catch (UnirestException | JSONException  e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		
		return filme;
    }

    public String getAsString(FacesContext ctx, UIComponent comp, Object obj) {
    	if(obj==null) {
    		return "";
    	}
    	Filme filme = (Filme) obj;
    	return filme.getId();
    }
}
