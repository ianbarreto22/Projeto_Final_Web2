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
import com.projeto.filmes.dao.ProducaoDAO;
import com.projeto.filmes.dao.SerieDAO;
import com.projeto.filmes.dominio.Serie;
import com.projeto.filmes.utils.FilmesException;

@ManagedBean
@ViewScoped
public class SerieMBean implements Serializable {

	private static final long serialVersionUID = 2000781882259817458L;

    private Serie serie = new Serie();

    private String titulo = " ";

    private SerieDAO serieDAO = new SerieDAO();
    
    private List<Serie> series = inicializaSeries();
	
	private List<Serie> seriesEncontradas = new ArrayList<Serie>();
	
	private static final String IMDB_KEY = "1647a78bb7mshd3d0c82dcbcd838p1b6f35jsn95ddc2ae7677";

	public Serie getSerie() {
		return serie;
	}

	public void setSerie(Serie serie) {
		this.serie = serie;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}  

    public List<Serie> getSeries() {
        return series;
    }
    
    public void setSeries(List<Serie> series) {
		this.series = series;
	}

    public List<Serie> getSeriesEncontradas() {
		return seriesEncontradas;
	}

	public void setSeriesEncontradas(List<Serie> seriesEncontradas) {
		this.seriesEncontradas = seriesEncontradas;
	}

	public void buscarSerie() throws UnirestException {
        this.seriesEncontradas = new ArrayList<Serie>();

        String tituloQuery = this.titulo.replace(" ", "_");
        System.out.println(tituloQuery);

        HttpResponse<String> response = Unirest.get("https://imdb8.p.rapidapi.com/title/find?q="+tituloQuery)
                .header("x-rapidapi-host", "imdb8.p.rapidapi.com")
				.header("x-rapidapi-key", IMDB_KEY)
				.asString();

        JSONObject json = new JSONObject(response.getBody());
        JSONArray results = json.getJSONArray("results");

        for(int i = 0; i < results.length(); i++) {
            JSONObject serieJSON = results.getJSONObject(i);

            try {
				if(!serieJSON.getString("titleType").equals("tvSeries")) {
					continue;
				}
				Serie f = new Serie();
				f.setId(serieJSON.getString("id").replace("/title/", "").replace("/", ""));
				f.setTitulo(serieJSON.getString("title"));
				f.setAno(serieJSON.getNumber("year").intValue());
				
				f.setPoster(serieJSON.getJSONObject("image").getString("url"));
				
				f.setDuracao(serieJSON.getNumber("runningTimeInMinutes").intValue());
				this.seriesEncontradas.add(f);
			} catch(Exception e) {
				continue;
			}


        }

    }

    public void adicionarSerie() {
    	ProducaoDAO producaoDAO = new ProducaoDAO();
        try {
            System.out.println(serie.getId());
            serieDAO.salvar(serie);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Série adicionada!", "Série adicionada!"));
        }
        catch(Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar a série!", "Não foi possível cadastrar a série!"));
        }
    }
    
    private List<Serie> inicializaSeries(){
    	List<Serie> lista = new ArrayList<Serie>();

		try {
			lista.addAll(serieDAO.listar());
		} catch (Exception e){
			e.printStackTrace();
			
		}
		
		return lista;
    }

	public void listarSeries() {
		List<Serie> lista = new ArrayList<Serie>();

		try {
			lista.addAll(serieDAO.listar());
		} catch (Exception e){
			e.printStackTrace();
			
		}
		
		this.series = lista;
	}
}
