package com.projeto.filmes.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.projeto.filmes.dao.UsuarioDAO;
import com.projeto.filmes.dominio.Usuario;
import com.projeto.filmes.utils.FilmesException;

@ManagedBean
@SessionScoped
public class UsuarioMBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7976858260140386890L;

	private Usuario usuario = new Usuario();
	
	private UsuarioDAO usuarioDAO = new UsuarioDAO();

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String entrar() throws FilmesException {
		List<Usuario> usuarios = usuarioDAO.listar();
		
		for(Usuario u : usuarios) {
			if(this.usuario.getLogin().equals(u.getLogin()) && this.usuario.getSenha().equals(u.getSenha())) {
				
				HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
				
				sessao.setAttribute("usuario", u);
				sessao.setMaxInactiveInterval(900);
				
				return "/app/index.xhtml?faces-redirect=true";
			}
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senha errada!", "Senha errada!"));
		return "";
	}
	
	public String sair() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login?faces-redirect=true";
	}
	
	public void cadastrar() {
		
		try {
			usuarioDAO.salvar(usuario);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastrado com sucesso!", "Cadastrado com sucesso!"));
		} catch(Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar o usuário!", "Não foi possível cadastrar o usuário!"));
			
		}
		
		this.usuario = new Usuario();
	}
}
