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
import com.projeto.filmes.dominio.Serie;

@FacesConverter(value="serieConverter", forClass=Serie.class)
public class SerieConverter implements Converter {
    public Object getAsObject(FacesContext ctx, UIComponent comp, String value) {
		Serie serie = new Serie();
		HttpResponse<String> response;
		try {
			response = Unirest.get("https://imdb8.p.rapidapi.com/title/get-details?tconst="+value)
					.header("x-rapidapi-host", "imdb8.p.rapidapi.com")
					.header("x-rapidapi-key", "1fd50227d9msh3fa3b60f2c06f69p1b8e26jsn201fdee07872")
					.asString();
			JSONObject result = new JSONObject(response.getBody());
			
			serie.setId(value);
			serie.setTitulo(result.getString("title"));
			serie.setAno(result.getInt("year"));
			serie.setDuracao(result.getInt("runningTimeInMinutes"));
			serie.setPoster(result.getJSONObject("image").getString("url"));
            serie.setNumeroEpisodios(result.getInt("numberOfEpisodes"));
		} catch (UnirestException | JSONException  e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		
		return serie;
    }

    public String getAsString(FacesContext ctx, UIComponent comp, Object obj) {
    	if(obj==null) {
    		return "";
    	}
    	
    	Serie serie = (Serie) obj;
    	return serie.getId();
    }

}
