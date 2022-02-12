package com.projeto.filmes.dominio;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Producao implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	protected String id;
	
	@Column(nullable=false)
	protected String titulo;
	
	@Column(nullable=false)
	protected String poster;
	
	@Column(nullable=false)
	protected Integer duracao;
	
	@Column(nullable=false)
	protected Integer ano;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "producao_id")
	private Set<Review> reviews;

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public Integer getDuracao() {
		return duracao;
	}

	public void setDuracao(Integer duracao) {
		this.duracao = duracao;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}
	
	public Set<Review> getReviews() {
		return reviews;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(ano, duracao, id, poster, titulo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producao other = (Producao) obj;
		return Objects.equals(ano, other.ano) && Objects.equals(duracao, other.duracao) && Objects.equals(id, other.id)
				&& Objects.equals(poster, other.poster) && Objects.equals(titulo, other.titulo);
	}

	@Override
	public String toString() {
		return this.id;
	}
}
