package com.projeto.filmes.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.projeto.filmes.dao.ReviewDAO;
import com.projeto.filmes.dominio.Review;
import com.projeto.filmes.dominio.Usuario;

@ManagedBean
@ViewScoped
public class ReviewMBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Review review = new Review();
	private ReviewDAO reviewDAO = new ReviewDAO();
	
	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}


	public void cadastrar(String idProducao) {
		
		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			Usuario usuario = (Usuario) sessao.getAttribute("usuario");
			review.setUsuario(usuario);
			review.setProducao_id(idProducao);
			reviewDAO.salvar(review);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastrado com sucesso!", "Cadastrado com sucesso!"));
		} catch(Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar o usuário!", "Não foi possível cadastrar o usuário!"));
		}
		
		this.review = new Review();
	}
}
