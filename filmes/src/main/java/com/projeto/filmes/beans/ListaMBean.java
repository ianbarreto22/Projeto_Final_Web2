package com.projeto.filmes.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.projeto.filmes.dao.ListaDAO;
import com.projeto.filmes.dao.ProducaoDAO;
import com.projeto.filmes.dominio.Lista;
import com.projeto.filmes.dominio.Producao;
import com.projeto.filmes.dominio.Usuario;
import com.projeto.filmes.utils.FilmesException;

@ManagedBean
@ViewScoped
public class ListaMBean {
	
	private Lista lista = new Lista();
	
	private ProducaoDAO producaoDAO = new ProducaoDAO();
	
	private ListaDAO listaDAO = new ListaDAO();
	
	private List<Producao> producoesExistentes = new ArrayList<Producao>();
	
	private List<Producao> selecionados = new ArrayList<Producao>();
	
	private String titulo;
	
	

	public Lista getLista() {
		return lista;
	}

	public void setLista(Lista lista) {
		this.lista = lista;
	}
	
	
	
	public List<Producao> getProducoesExistentes() {
		return producoesExistentes;
	}

	public void setProducoesExistentes(List<Producao> producoesExistentes) {
		this.producoesExistentes = producoesExistentes;
	}
	
	

	public List<Producao> getSelecionados() {
		return selecionados;
	}

	public void setSelecionados(List<Producao> selecionados) {
		this.selecionados = selecionados;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<Producao> listarProducoes(){
		producoesExistentes = new ArrayList<Producao>();
		
		try {
			producoesExistentes.addAll(producaoDAO.listar());
		} catch (FilmesException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível carregar as produções!", ""));
			e.printStackTrace();
		}
		
		return producoesExistentes;
	}
	
	public void adicionarLista() {
		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			lista.setTitulo(titulo);
			lista.setUsuario_id(((Usuario) sessao.getAttribute("usuario")).getLogin());
			listaDAO.salvar(lista);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Lista criada!", "Lista criada!"));
		} catch(Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível criar a lista!", "Não foi possível criar a lista!"));
		}
	}

	public void adicionarProducao(Producao producao, Integer idLista) {
		try{
			lista = listaDAO.buscarId(idLista);
			producoesExistentes = lista.getProducoes();
			producoesExistentes.add(producao);
			lista.setProducoes(producoesExistentes);
			listaDAO.salvar(lista);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Filme adicionado à lista!", "Filme adicionado à lista!"));
		} catch(Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível adicionar a produção à lista!", "Não foi possível adicionar a produção à lista!"));
		}

	}

}
