package com.projeto.filmes.dominio;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="series")
public class Serie extends Producao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable=false)
    private Integer numeroEpisodios;
    

    public Serie() {
        super();
    }

	public Integer getNumeroEpisodios() {
		return numeroEpisodios;
	}

	public void setNumeroEpisodios(Integer numeroEpisodios) {
		this.numeroEpisodios = numeroEpisodios;
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
		Serie other = (Serie) obj;
		return Objects.equals(ano, other.ano) && Objects.equals(duracao, other.duracao) && Objects.equals(id, other.id)
				&& Objects.equals(poster, other.poster)
				&& Objects.equals(titulo, other.titulo);
	}

	@Override
	public String toString() {
		return this.id;
	}

	
	
}
