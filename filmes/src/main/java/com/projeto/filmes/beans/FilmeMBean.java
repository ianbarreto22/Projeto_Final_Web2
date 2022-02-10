package com.projeto.filmes.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;
import org.primefaces.util.LangUtils;

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
	
	private String pesquisa = "";
	
	private static final String IMDB_KEY = "1fd50227d9msh3fa3b60f2c06f69p1b8e26jsn201fdee07872";

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

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
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
			filmes.add(filme);
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
	
	public void apagarFilme(Filme f) {
		try {
			filmes.remove(f);
			filmeDAO.excluir(f);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Filme deletado com sucesso!", ""));
		} catch(Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível deletar o filme!", ""));
		}
	}
	
	public void filtrarTabela() {
		List<Filme> lista = new ArrayList<Filme>();
		try {
			for(Filme f : filmeDAO.listar()) {
				if(f.getTitulo().toLowerCase().contains(pesquisa.toLowerCase())) {
					lista.add(f);
				}
			}
			this.filmes = lista;
		} catch (FilmesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
