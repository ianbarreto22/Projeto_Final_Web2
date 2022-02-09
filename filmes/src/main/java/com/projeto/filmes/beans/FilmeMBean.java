package com.projeto.filmes.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.projeto.filmes.dao.FilmeDAO;
import com.projeto.filmes.dao.ProducaoDAO;
import com.projeto.filmes.dominio.Filme;
import com.projeto.filmes.utils.FilmesException;


@ManagedBean
@ViewScoped

public class FilmeMBean implements Serializable{

	
	private static final long serialVersionUID = -7903130894080174404L;

	private Filme filme = new Filme();
	
	private String titulo = "";
	
	private FilmeDAO filmeDAO = new FilmeDAO();
	
	private List<Filme> filmes = inicializaFilmes();
	
	private List<Filme> filmesEncontrados = new ArrayList<Filme>();
	
	private static final String IMDB_KEY = "1647a78bb7mshd3d0c82dcbcd838p1b6f35jsn95ddc2ae7677";

	public Filme getFilme() {
		return filme;
	}

	public void setFilme(Filme filme) {
		this.filme = filme;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public List<Filme> getFilmesEncontrados() {
		return filmesEncontrados;
	}

	public void setFilmesEncontrados(List<Filme> filmesEncontrados) {
		this.filmesEncontrados = filmesEncontrados;
	}
	
	

	public List<Filme> getFilmes() {
		return filmes;
	}

	public void setFilmes(List<Filme> filmes) {
		this.filmes = filmes;
	}

	public void buscarFilme() throws UnirestException{
		this.filmesEncontrados = new ArrayList<Filme>();
		
		
		
		String q = this.titulo.replace(" ", "_");
		System.out.println(q);
		
		HttpResponse<String> response = Unirest.get("https://imdb8.p.rapidapi.com/title/find?q="+q)
				.header("x-rapidapi-host", "imdb8.p.rapidapi.com")
				.header("x-rapidapi-key", IMDB_KEY)
				.asString();
		
		JSONObject json = new JSONObject(response.getBody());
		JSONArray results = json.getJSONArray("results");
		
		for(int i=0; i<results.length(); i++) {
			JSONObject filmeJSON = results.getJSONObject(i);
			
			try {
				if(!filmeJSON.getString("titleType").equals("movie")) {
					continue;
				}
				Filme f = new Filme();
				
				
				f.setId(filmeJSON.getString("id").replace("/title/", "").replace("/", ""));
				f.setTitulo(filmeJSON.getString("title"));
				f.setAno(filmeJSON.getNumber("year").intValue());
				
				f.setPoster(filmeJSON.getJSONObject("image").getString("url"));
				
				f.setDuracao(filmeJSON.getNumber("runningTimeInMinutes").intValue());
				this.filmesEncontrados.add(f);
			} catch(Exception e) {
				continue;
			}
			
		}
		this.titulo="";
	}
	
	
	public void adicionarFilme() {
		try {
			System.out.println(filme.getId());
			System.out.println(filme.getTitulo());
			filmeDAO.salvar(filme);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Filme adicionado!", "Filme adicionado!"));
		} catch(Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar o filme!", "Não foi possível cadastrar o filme!"));
		}
	}
	
	private List<Filme> inicializaFilmes() {
		List<Filme> lista = new ArrayList<Filme>();
		
		try {
			lista.addAll(filmeDAO.listar());
		} catch (Exception e){
			e.printStackTrace();
			
		}
		
		return lista;
	}
	
	public void listarFilmes(){
		System.out.print("Atualizando...");
		List<Filme> lista = new ArrayList<Filme>();
		
		try {
			lista.addAll(filmeDAO.listar());
		} catch (Exception e){
			e.printStackTrace();
			
		}
		
		this.filmes = lista;
	}
	
}