package com.projeto.filmes.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usuarios")
public class Usuario implements Serializable{
		private static final long serialVersionUID = 1L;
		
		
		@Id
		@Column(length=50, nullable=false, unique=true)
		private String login;
		
		@Column(length=50, nullable=false)
		private String senha;
		
		

		public Usuario() {
			super();
			// TODO Auto-generated constructor stub
		}

		public String getLogin() {
			return login;
		}

		public void setLogin(String login) {
			this.login = login;
		}

		public String getSenha() {
			return senha;
		}

		public void setSenha(String password) {
			this.senha = password;
		}
		
		
}
