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
import com.projeto.filmes.dominio.Filme;
import com.projeto.filmes.dominio.Producao;

@FacesConverter(value="producaoConverter", forClass=Producao.class)
public class ProducaoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Producao p = new Producao();
		HttpResponse<String> response;
		try {
			response = Unirest.get("https://imdb8.p.rapidapi.com/title/get-details?tconst="+value)
					.header("x-rapidapi-host", "imdb8.p.rapidapi.com")
					.header("x-rapidapi-key", "1fd50227d9msh3fa3b60f2c06f69p1b8e26jsn201fdee07872")
					.asString();
			JSONObject result = new JSONObject(response.getBody());
			
			p.setId(value);
			p.setTitulo(result.getString("title"));
			p.setAno(result.getInt("year"));
			p.setDuracao(result.getInt("runningTimeInMinutes"));
			p.setPoster(result.getJSONObject("image").getString("url"));
		} catch (UnirestException | JSONException  e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		
		return p;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value==null) {
    		return "";
    	}
    	Producao p = (Producao) value;
    	return p.getId();
	}

}
