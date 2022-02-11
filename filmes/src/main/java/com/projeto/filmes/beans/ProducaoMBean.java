package com.projeto.filmes.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.projeto.filmes.dao.ProducaoDAO;
import com.projeto.filmes.dominio.Producao;

public class ProducaoMBean {
	
	private static final long serialVersionUID = -7903130894080174404L;

	private Producao Producao = new Producao();
	
	
	private ProducaoDAO ProducaoDAO = new ProducaoDAO();


	public Producao getProducao() {
		return Producao;
	}


	public void setProducao(Producao producao) {
		Producao = producao;
	}


	public ProducaoDAO getProducaoDAO() {
		return ProducaoDAO;
	}


	public void setProducaoDAO(ProducaoDAO producaoDAO) {
		ProducaoDAO = producaoDAO;
	}
	
	
}
